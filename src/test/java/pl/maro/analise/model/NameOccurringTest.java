package pl.maro.analise.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class NameOccurringTest {
	
	@Test
	void map() {
		NameOccurring result = NameOccurring.map("Адам Александер;1857");
		assertThat(result.name()).isEqualTo("Адам");
	}
}