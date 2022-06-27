package pl.maro.analise.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author marek.borecki
 * @since 27.6.2022
 */
public class MyStringUtils {
	
	public static final String SLASH = "/";
	
	public static String capitalize(String str) {
		return Arrays.stream(str.split(SLASH))
				.map(StringUtils::capitalize)
				.collect(Collectors.joining(SLASH));
	}
}
