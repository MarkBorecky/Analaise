package pl.maro.analise.utils;

import io.vavr.collection.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pl.maro.analise.model.NameOccurring;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static pl.maro.analise.utils.NameStandardiser.createNameMap;

class NameStandardiserTest {
	
	public static final String NAMES = "./src/test/resources/names.csv";
	public static final String NAMES_FULL = "./src/test/resources/names_full.csv";
	
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
			soft.assertThat(map.get("Антонъ/Антоній").getOrNull()).isEqualTo("Антон");
		});
	}
	
	@Test
	void standard() {
		var standardiser = NameStandardiser.createNameStandardizer(NAMES_FULL);
		var csvRows = List.of(
				"Александръ;1858",
				"Аренъ*;1841",
				"Адальберт;1836",
				"Альбертъ;1841",
				"Мелентій;1867",
				"Валеріанъ;1867",
				"Венедиктъ;1836",
				"ВЛАДИМИР;1856",
				"Иванъ;1845",
				"Игнатъ;1845",
				"Мартинъ;1845",
				"Осипъ;1845",
				"Платонъ;1845",
				"Ромуалдъ;1845",
				"Францискъ;1845",
				"Юліанъ;1845"
		);
		List<NameOccurring> standardised = standardiser.standard(csvRows);
		SoftAssertions.assertSoftly(soft -> {
			soft.assertThat(standardised.get(0).name()).isEqualTo("Александр");
			soft.assertThat(standardised.get(1).name()).isEqualTo("Аарон");
			soft.assertThat(standardised.get(2).name()).isEqualTo("Адальберт");
			soft.assertThat(standardised.get(3).name()).isEqualTo("Адальберт");
			soft.assertThat(standardised.get(4).name()).isEqualTo("Мелетий");
			soft.assertThat(standardised.get(5).name()).isEqualTo("Валерьян");
			soft.assertThat(standardised.get(6).name()).isEqualTo("Венедикт");
			soft.assertThat(standardised.get(7).name()).isEqualTo("Владимир");
			soft.assertThat(standardised.get(8).name()).isEqualTo("Иван");
			soft.assertThat(standardised.get(9).name()).isEqualTo("Игнатий");
			soft.assertThat(standardised.get(10).name()).isEqualTo("Мартин");
			soft.assertThat(standardised.get(11).name()).isEqualTo("Осип");
			soft.assertThat(standardised.get(12).name()).isEqualTo("Платон");
			soft.assertThat(standardised.get(13).name()).isEqualTo("Ромуальд");
			soft.assertThat(standardised.get(14).name()).isEqualTo("Франциск");
			soft.assertThat(standardised.get(15).name()).isEqualTo("Юлиан");
		});
	}
}