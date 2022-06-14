package pl.maro.analise.utils;

import io.vavr.collection.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pl.maro.analise.model.NameOccurring;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static pl.maro.analise.utils.NameStandardiser.createNameMap;

class NameStandardiserTest {
	
	public static final String NAMES = "./src/test/resources/names.csv";
	
	@Test
	void map() {
		var map = createNameMap(NAMES);
		assertSoftly(soft -> {
			soft.assertThat(map.get("Аренъ").getOrNull()).isEqualTo("Аарон");
			soft.assertThat(map.get("Альберт").getOrNull()).isEqualTo("Адальберт");
			soft.assertThat(map.get("Адальбертъ").getOrNull()).isEqualTo("Адальберт");
			soft.assertThat(map.get("Альбертъ").getOrNull()).isEqualTo("Адальберт");
			soft.assertThat(map.get("Войцех").getOrNull()).isEqualTo("Адальберт");
			soft.assertThat(map.get("Мелентій").getOrNull()).isEqualTo("Мелетий");
			soft.assertThat(map.get("Мелентий").getOrNull()).isEqualTo("Мелетий");
		});
	}
	
	@Test
	void standard() {
		var standardiser = NameStandardiser.createNameStandardizer(NAMES);
		var csvRows = List.of(
				"Александръ;1858",
				"Аренъ*;1841",
				"Адальберт;1836",
				"Альбертъ;1841",
				"Мелентій;1867");
		List<NameOccurring> standardised = standardiser.standard(csvRows);
		SoftAssertions.assertSoftly(soft -> {
			soft.assertThat(standardised.get(0).name()).isEqualTo("Александръ");
			soft.assertThat(standardised.get(1).name()).isEqualTo("Аарон");
			soft.assertThat(standardised.get(2).name()).isEqualTo("Адальберт");
			soft.assertThat(standardised.get(3).name()).isEqualTo("Адальберт");
			soft.assertThat(standardised.get(4).name()).isEqualTo("Мелетий");
		});
	}
}