package pl.maro.analise;


import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;
import pl.maro.analise.model.MyCollector;
import pl.maro.analise.model.NameStatistics;
import pl.maro.analise.model.NameStatisticsMapper;
import pl.maro.analise.model.PeopleList;
import pl.maro.analise.model.PeopleListMapper;
import pl.maro.analise.model.Range;
import pl.maro.analise.model.Sum;
import pl.maro.analise.sheet.SheetMapper;
import pl.maro.analise.sheet.SpreadSheetCollector;
import pl.maro.analise.stream.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
	public final static String resource = "./src/main/resources/Baza_dr_analiza 29.03. ods.ods";
	public final static String result = "./src/main/resources/Result.ods";
	
	public static void main(String[] args) throws IOException {
		var peopleLists = StreamUtils.SheetsStream(resource)
				.map(PeopleListMapper::map)
				.collect(MyCollector.getAndAddTogether());
		
		var statistics = getStatistics(peopleLists);
		
		var lists = peopleLists.stream()
				.map(SheetMapper::map)
				.toList();
		
		var lists2 = statistics.entrySet().stream()
				.map(SheetMapper::mapEntry)
				.toList();
		
		var array = new ArrayList<Sheet>();
		array.addAll(lists);
		array.addAll(lists2);
		array.stream().collect(SpreadSheetCollector.toSpreadSheet()).save(result);
		
		var spreadSheet = new SpreadSheet();
		array.forEach(spreadSheet::appendSheet);
		spreadSheet.save(new File(result));
	}
	
	private static Map<String, List<NameStatistics>> getStatistics(List<PeopleList> peopleLists) {
		var denominators =
				getDenominators(peopleLists.stream().filter(peopleList -> peopleList.name().equals("together")).findFirst().orElseThrow());
		return peopleLists.stream()
				.map(list -> Map.entry(list.name(), NameStatisticsMapper.createNamesStatistics(list.people(), denominators)))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
	
	private static Sum getDenominators(PeopleList peopleList) {
		var map = new Sum();
		for (var person : peopleList.people()) {
			for (var range : Range.values()) {
				if (range.contains(person.birthYear())) {
					map.increment(range);
				}
			}
		}
		return map;
	}
}
