package pl.maro.analise.sheet;

import com.github.miachm.sods.Sheet;
import pl.maro.analise.model.NameStatistics;
import pl.maro.analise.model.PeopleList;
import pl.maro.analise.model.Range;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class SheetMapper {
	public static Sheet map(PeopleList statistics) {
		var sheet = new Sheet(statistics.name(), statistics.size()+1, 2);
		Object[] objects = statistics.people().stream()
				.flatMap(person -> Stream.of(
						person.statisticName(),
						person.birthYear()))
				.toArray();
		var headers = new Object[] {"Name", "Birth year"};
		var data = concat(headers, objects);
		sheet.getDataRange().setValues(data);
		return sheet;
	}
	
	private static Object[] concat(Object[] headers, Object[] objects) {
		return Stream.concat(Arrays.stream(headers), Arrays.stream(objects)).toArray();
	}
	
	public static Sheet mapEntry(Map.Entry<String, List<NameStatistics>> map) {
		var headers = getGeaders();
		var name = "%s_%s".formatted(map.getKey(), "statistics");
		var list = map.getValue();
		var sheet = new Sheet(name, list.size() + 1, 11);
		var data = getData(headers, list);
		sheet.getDataRange().setValues(data);
		return sheet;
	}
	
	private static Object[] getData(io.vavr.collection.List<String> headers, List<NameStatistics> list) {
		return headers.appendAll(list.stream().flatMap(NameStatistics::toStringStream).toList()).toJavaStream().toArray();
	}
	
	private static io.vavr.collection.List<String> getGeaders() {
		var columns = Arrays.stream(Range.values()).map(Range::getName)
				.flatMap(range -> Stream.of("%s amount".formatted(range), "%s percentage".formatted(range))).toList();
		return io.vavr.collection.List.of("Name").appendAll(columns);
	}
}
