package pl.maro.analise.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum Characters {
	POLISH(createIntSet(65, 90, 211, 260, 262, 280, 321, 346, 377, 379, 47, 32)),
	RUSSIAN(createIntSet(1040, 1071, 1030, 1122, 1025, 1138, 47, 32)),
	TO_REMOVE(List.of(45));
	
	Characters(List<Integer> code) {
		this.code = code;
	}
	
	private final List<Integer> code;
	
	private static List<Integer> createIntSet(int from, int until, int... additional) {
		var base = IntStream.rangeClosed(from, until);
		var additionalStream = IntStream.of(additional);
		return IntStream.concat(base, additionalStream).boxed().toList();
	}
	
	public static Boolean isInPolishCharacters(Integer i) {
		return POLISH.code.contains(i);
	}
	
	public static Boolean isInRussianCharacters(Integer i) {
		return RUSSIAN.code.contains(i);
	}
	
	public static String filter(String check) {
		return check.chars()
				.filter(c -> !TO_REMOVE.code.contains(c))
				.mapToObj(Character::toString)
				.collect(Collectors.joining());
	}
}
