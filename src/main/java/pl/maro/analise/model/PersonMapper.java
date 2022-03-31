package pl.maro.analise.model;

import io.vavr.Tuple2;

public class PersonMapper {
    public static Person map(Object[] objects, String sheetName) {
        var pair = getTuple(objects, sheetName);
        return new Person(getStatisticName(pair._1), pair._1, pair._2.intValue());
    }

    private static String getStatisticName(String originalName) {
        var name = originalName.toUpperCase().trim().replace("*", "");
        return StatisticNameMapper.map(name)
                .orElse(originalName);
    }

    private static Tuple2<String, Double> getTuple(Object[] objects, String sheetName) {
        try {
            return switch (sheetName) {
                case "baza studentow" -> new Tuple2<>(objects[1].toString(), (Double) objects[9]);
                case "patronimika" -> new Tuple2<>(objects[0].toString(), (Double) objects[1]);
                default -> throw new IllegalStateException("Unexpected value: " + sheetName);
            };
        } catch (NullPointerException e) {
            throw new RuntimeException("NULLLLLLL!");
        }
    }
}
