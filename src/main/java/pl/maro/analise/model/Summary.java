package pl.maro.analise.model;

import io.vavr.collection.List;

public record Summary(String collectionName, int all, int division1, int division2) {
	public List<Integer> getNumbers() {
		return List.of(all, division1, division2);
	}
}
