package pl.maro.analise.utils;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.apache.commons.lang3.Range;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.maro.analise.model.NameOccurring;
import pl.maro.analise.model.Statistics;

class StatisticsUtilsTest {
	public static final List<NameOccurring> LIST = List.of(
			new NameOccurring("a", 1830),
			new NameOccurring("a", 1939),
			new NameOccurring("b", 1900),
			new NameOccurring("a", 1899),
			new NameOccurring("b", 1888),
			new NameOccurring("b", 1940)
	);
	
	@Test
	void makeStatisticsFor1() {
		var range = Range.between(1830, 1939);
		Seq<Statistics> statistics = StatisticsUtils.makeStatisticsFor(LIST, range);
		Assertions.assertThat(statistics).isNotNull();
		Assertions.assertThat(statistics).contains(
				new Statistics("a", 3, "60,0%"),
				new Statistics("b", 2, "40,0%")
		);
	}
	
	@Test
	void makeStatisticsFor2() {
		var range = Range.between(1885, 1939);
		Seq<Statistics> statistics = StatisticsUtils.makeStatisticsFor(LIST, range);
		Assertions.assertThat(statistics).isNotNull();
		Assertions.assertThat(statistics).contains(
				new Statistics("a", 2, "50,0%"),
				new Statistics("b", 2, "50,0%")
		);
	}
}