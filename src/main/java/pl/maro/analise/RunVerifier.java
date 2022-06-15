package pl.maro.analise;

import io.vavr.CheckedFunction0;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Try;
import org.apache.commons.lang3.Range;
import pl.maro.analise.model.Statistics;
import pl.maro.analise.model.Summary;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Stream;

import static pl.maro.analise.utils.FilesUtils.readFile;

public class RunVerifier {
	public static final String STUDENTS = "./src/main/resources/students.csv";
	public static final String PATRONIMIKA = "./src/main/resources/patronomika.csv";
	public static final String STATISTICS = "./src/main/resources/statistics/";
	public static final String NAME_BETWEEN_UNDERSCORES = "statistics_|_\\[\\d{4}\\.{2}\\d{4}\\]\\.csv";
	public static final String YEAR = "^(.*)\\[|\\]\\.csv$";
	public static final Function<String, String> getCollectionName = name -> name.replaceAll(NAME_BETWEEN_UNDERSCORES, "");
	public static final Function<String, String> getCollectionYears = name -> name.replaceAll(YEAR, "");
	public static final Function<Path, String> getFileName = path -> path.toFile().getName();
	
	public static final Range<Integer> A = Range.between(1830, 1939);
	public static final Range<Integer> B = Range.between(1830, 1839);
	public static final Range<Integer> C = Range.between(1840, 1939);
	public static final Range<Integer> D = Range.between(1830, 1884);
	public static final Range<Integer> E = Range.between(1885, 1939);
	
	public static void main(String... args) {
		var studentsAmount = readFile(STUDENTS).size();
		var patronimikusAmount = readFile(PATRONIMIKA).size();
		
		CheckedFunction0<Stream<Path>> readStatisticsDir = () -> Files.list(Paths.get(STATISTICS));
		
		var paths = Try.of(readStatisticsDir)
				.getOrElseThrow(() -> new IllegalArgumentException("Can't read statistics folder!"))
				.toList();
		
		var result = List.ofAll(paths)
				.groupBy(getCollectionName.compose(getFileName))
				.mapValues(val -> val.map(RunVerifier::getTuple2))
				.map(x -> x.apply(RunVerifier::mapSummary));
		
		result.forEach(System.out::println);
		System.out.println();
		var x = result.unzip3(Summary::all, Summary::division1, Summary::division2)
				.map((a, b, c) -> new Tuple3<>(a.toSeq(), b.toSeq(), c.toSeq()))
				.map((a, b, c) -> new Tuple3<>(wrapTail(a), wrapTail(b), wrapTail(c)));
		
		System.out.println(x);
		
		System.out.println();
		System.out.println("students amount = " + studentsAmount);
		System.out.println("patronimikus amount = " + patronimikusAmount);
		var togheter = studentsAmount + patronimikusAmount;
		System.out.println("togheter = " + togheter);
		
	}
	
	private static Seq<Number> wrapTail(Seq<Integer> seq) {
		return List.of(seq.head(), seq.tail().sum());
	}
	
	
	private static Summary mapSummary(String collectionName, List<Tuple2<String, Integer>> list) {
		
		int all = 0;
		int division1 = 0;
		int division2 = 0;
		
		for (Tuple2<String, Integer> tuple : list) {
			switch (tuple._1) {
				case "1830..1839", "1840..1939" -> division1 += tuple._2;
				case "1830..1884", "1885..1939" -> division2 += tuple._2;
				case "1830..1939" -> all += tuple._2;
			}
		}
		return new Summary(collectionName, all, division1, division2);
	}
	
	private static Tuple2<String, Integer> getTuple2(Path path) {
		return new Tuple2<>(getCollectionYears.compose(getFileName).apply(path),
				countRecords(readFile(path.toString())));
	}
	
	private static int countRecords(List<String> list) {
		return list.map(Statistics::map)
				.map(Statistics::occurrence)
				.sum()
				.intValue();
	}
}
