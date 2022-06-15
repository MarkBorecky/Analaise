package pl.maro.analise;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.maro.analise.RunVerifier.getCollectionName;
import static pl.maro.analise.RunVerifier.getCollectionYears;

class RunVerifierTest {
	
	@Test
	void testFunctionGetCollectionName() {
		var result = List.of(
						"statistics_students_[1830..1939].csv",
						"statistics_patronimika_[1885..1939].csv",
						"statistics_together_[1840..1939].csv")
				.map(getCollectionName);
		
		assertThat(result).contains(
				"students",
				"patronimika",
				"together"
		);
	}
	
	@Test
	void testFunctionGetCollectionYears() {
		var result = List.of(
						"statistics_students_[1830..1939].csv",
						"statistics_patronimika_[1885..1939].csv",
						"statistics_together_[1840..1939].csv")
				.map(getCollectionYears);
		
		assertThat(result).contains(
				"1830..1939",
				"1885..1939",
				"1840..1939"
		);
	}
}