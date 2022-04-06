package pl.maro.analise.model;

public record Occurrence(int count, double percentage) {
	public String percentageString() {
		return percentage + " %";
	}
	
	public String countStr() {
		return String.valueOf(count);
	}
}
