package pl.maro.analise.utils;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import org.apache.commons.lang3.Range;
import pl.maro.analise.model.NameOccurring;
import pl.maro.analise.model.Statistics;

import java.text.DecimalFormat;
import java.util.function.Predicate;

public class StatisticsUtils {
	
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.0%");
	
	public static List<Statistics> makeStatisticsFor(List<NameOccurring> list, Range<Integer> range) {
		Predicate<NameOccurring> yearInRange = nameOccurring -> range.contains(nameOccurring.year());
		var all = list
				.filter(yearInRange)
				.size();
		return list
				.filter(yearInRange)
				.groupBy(NameOccurring::name)
				.map(tuple -> map(all, tuple))
				.toList();
	}
	
	private static Statistics map(double all, Tuple2<String, List<NameOccurring>> tuple) {
		int occurrence = tuple._2.size();
		String percentage = getPercentage(occurrence, all);
		return new Statistics(tuple._1, occurrence, percentage);
	}
	
	private static String getPercentage(int occurrence, double all) {
		return DECIMAL_FORMAT.format(occurrence/all);
	}
	
}
