package pl.maro.analise.model;


import io.vavr.Tuple2;
import io.vavr.collection.List;

public record NameStandard(String mainForm, List<String> secondaryForm) {
	public List<Tuple2<String, String>> getEntryStream() {
		return secondaryForm
				.map(secondaryForm -> new Tuple2<>(secondaryForm, mainForm));
	}
}
