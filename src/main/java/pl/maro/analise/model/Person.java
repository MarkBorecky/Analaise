package pl.maro.analise.model;

public record Person(String statisticName, String originalName, int birthYear) {
    public boolean withName() {
        return !"-".equals(statisticName);
    }
}
