package pl.maro.analise.model;

import io.vavr.Tuple2;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PersonMapper {
	public static Person map(Object[] objects, String sheetName) {
		var pair = getTuple(objects, sheetName);
		return new Person(getStatisticName(pair._1), pair._1, pair._2.intValue());
	}
	
	private static String getStatisticName(String originalName) {
		var check = originalName.toUpperCase().trim()
				.replace("*", "");
		var name = Characters.filter(check);
		checkString(name);
		var result =  StatisticNameMapper.map(name)
				.orElse(name).toUpperCase();
		checkString(result);
		return result;
	}
	
	private static void checkString(String name) {
		if (name.isEmpty()) {
			return;
		}
		name = name.replace("/","")
				.replace(" ","");
		var length = name.length();
		var polishResult = name.chars().boxed().collect(Collectors.groupingBy(Characters::isInPolishCharacters));
		var russianResult = name.chars().boxed().collect(Collectors.groupingBy(Characters::isInRussianCharacters));
		var polishFalseResult = getSize(polishResult, false);
		var russianFalseResult = getSize(russianResult, false);
		var polishTrueResult = getSize(polishResult, true);
		var russianTrueResult = getSize(russianResult, true);
		if (polishFalseResult != 0 && polishFalseResult != length) {
			print(polishResult, russianResult);
			throwException(name);
		}
		
		if (russianFalseResult != 0 && russianFalseResult != length) {
			print(polishResult, russianResult);
			throwException(name);
		}
		
		if (russianTrueResult != 0 && polishTrueResult != 0) {
			print(polishResult, russianResult);
			throwException(name);
		}
		
		if (russianTrueResult == 0 && polishTrueResult == 0) {
			print(polishResult, russianResult);
			throwException(name);
		}
	}
	
	private static void print(Map<Boolean, List<Integer>> polishResult, Map<Boolean, List<Integer>> russianResult) {
		System.out.println("POLISH");
		getList(polishResult, false).forEach(x -> System.out.printf("%d ", x));
		System.out.println("\nRUSSIAN");
		getList(russianResult, false).forEach(x -> System.out.printf("%d ", x));
	}
	
	private static int getSize(Map<Boolean, List<Integer>> polishResult, boolean key) {
		return getList(polishResult, key).size();
	}
	
	private static List<Integer> getList(Map<Boolean, List<Integer>> polishResult, boolean key) {
		return polishResult.getOrDefault(key, List.of());
	}
	
	private static void throwException(String originalName) {
		System.err.printf("%s result is incorrect%n\n\n", originalName);
	}
	
	private static Tuple2<String, Double> getTuple(Object[] objects, String sheetName) {
		try {
			return switch (sheetName) {
				case "baza studentow" -> new Tuple2<>(objects[1].toString(), (Double) objects[9]);
				case "patronimika" -> new Tuple2<>(objects[0].toString(), (Double) objects[1]);
				default -> throw new IllegalStateException("Unexpected value: " + sheetName);
			};
		} catch (NullPointerException e) {
			throw new RuntimeException("NULLLLLLL!");
		}
	}
}
