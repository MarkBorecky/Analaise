package pl.maro.analise.model;

import com.github.miachm.sods.Sheet;

import java.util.Arrays;
import java.util.Objects;

public class StatisticsMapper {
    public static Statistics map(Sheet sheet) {
        var sheetName = sheet.getName();
        var range = sheet.getDataRange();
        var values = range.getValues();
        var people = Arrays.stream(values)
                .skip(1)
                .filter(StatisticsMapper::isRowNotNulls)
                .map(objects -> PersonMapper.map(objects, sheetName))
                .filter(Person::withName)
                .toList();
        return new Statistics(sheet.getName(), Range.Y1800_1939, people);
    }

    private static boolean isRowNotNulls(Object[] objects) {
        return Arrays.stream(objects).anyMatch(Objects::nonNull);
    }

}
