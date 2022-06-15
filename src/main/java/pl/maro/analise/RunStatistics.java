package pl.maro.analise;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import org.apache.commons.lang3.Range;
import pl.maro.analise.model.NameOccurring;
import pl.maro.analise.model.Statistics;
import pl.maro.analise.utils.FilesUtils;
import pl.maro.analise.utils.StatisticsUtils;

import java.io.File;

import static pl.maro.analise.utils.FilesUtils.getBytes;

public class RunStatistics {
	
	public static final String STUDENTS = "./src/main/resources/standardised/standardised_students.csv";
	public static final String PATRONIMIKA = "./src/main/resources/standardised/standardised_patronomika.csv";
	public static final String TOGETHER = "./src/main/resources/standardised/standardised_together.csv";
	public static final String TARGET = "./src/main/resources/statistics/";
	public static final Range<Integer> A = Range.between(1830, 1939);
	public static final Range<Integer> B = Range.between(1830, 1839);
	public static final Range<Integer> C = Range.between(1840, 1939);
	public static final Range<Integer> D = Range.between(1830, 1884);
	public static final Range<Integer> E = Range.between(1885, 1939);
	
	
	public static void main(String... args) {
		createTargetFolder();
		
		Tuple2<String, List<NameOccurring>> studentList = new Tuple2<>("students", readNameList(STUDENTS));
		Tuple2<String, List<NameOccurring>> patronimikaList = new Tuple2<>("patronimika", readNameList(PATRONIMIKA));
		Tuple2<String, List<NameOccurring>> togetherList = new Tuple2<>("together", readNameList(TOGETHER));
		
		var collections = List.of(studentList, patronimikaList, togetherList);
		var ranges = List.of(A, B, C, D, E);
		
		cartesianProduct(collections, ranges)
				.map(RunStatistics::makeStatistics)
				.forEach(RunStatistics::save);
	}
	
	private static Tuple2<String, List<Statistics>> makeStatistics(Tuple2<Tuple2<String, List<NameOccurring>>, Range<Integer>> tuple) {
		var list = tuple._1._2;
		var collectionName = tuple._1._1;
		var statistics = StatisticsUtils.makeStatisticsFor(list, tuple._2);
		String fileName = "statistics_%s_%s.csv".formatted(collectionName, tuple._2);
		return new Tuple2<>(fileName, statistics);
	}
	
	private static void save(Tuple2<String, List<Statistics>> tuple) {
		var bytes = getBytes(tuple._2);
		FilesUtils.save(TARGET.concat(tuple._1), bytes);
	}
	
	private static <A, B> List<Tuple2<A, B>> cartesianProduct(List<A> collections, List<B> ranges) {
		return collections.flatMap(elA -> ranges.map(elB -> new Tuple2<>(elA, elB)));
	}
	
	private static List<NameOccurring> readNameList(String csv) {
		return FilesUtils.readFile(csv).map(NameOccurring::map);
	}
	
	private static void createTargetFolder() {
		var targetDir = new File(TARGET);
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}
	}
}
