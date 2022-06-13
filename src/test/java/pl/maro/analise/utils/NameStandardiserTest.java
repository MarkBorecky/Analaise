package pl.maro.analise.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.*;
import static pl.maro.analise.utils.NameStandardiser.createNameMap;

class NameStandardiserTest {
	
	public static final String NAMES = "./src/test/resources/names.csv";
	
	@Test
	void map() {
		var map = createNameMap(NAMES);
		assertSoftly(soft -> {
			soft.assertThat(map.get("АРЕНЪ").getOrNull()).isEqualTo("ААРОН");
			soft.assertThat(map.get("АЛЬБЕРТ").getOrNull()).isEqualTo("АДАЛЬБЕРТ");
			soft.assertThat(map.get("АДАЛЬБЕРТЪ").getOrNull()).isEqualTo("АДАЛЬБЕРТ");
			soft.assertThat(map.get("АЛЬБЕРТЪ").getOrNull()).isEqualTo("АДАЛЬБЕРТ");
			soft.assertThat(map.get("ВОЙЦЕХ").getOrNull()).isEqualTo("АДАЛЬБЕРТ");
			soft.assertThat(map.get("МЕЛЕНТІЙ").getOrNull()).isEqualTo("МЕЛЕТИЙ");
			soft.assertThat(map.get("МЕЛЕНТИЙ").getOrNull()).isEqualTo("МЕЛЕТИЙ");
		});
	}
}