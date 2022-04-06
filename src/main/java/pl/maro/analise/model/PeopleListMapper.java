package pl.maro.analise.model;

import com.github.miachm.sods.Sheet;

import java.util.Arrays;
import java.util.Objects;

public class PeopleListMapper {
    public static PeopleList map(Sheet sheet) {
        var sheetName = sheet.getName();
        var range = sheet.getDataRange();
        var values = range.getValues();
        var people = Arrays.stream(values)
                .skip(1)
                .filter(PeopleListMapper::isRowNotNulls)
                .map(objects -> PersonMapper.map(objects, sheetName))
                .filter(Person::withName)
                .toList();
        return new PeopleList(sheet.getName(), Range.Y1830_1939, people);
    }

    private static boolean isRowNotNulls(Object[] objects) {
        return Arrays.stream(objects).anyMatch(Objects::nonNull);
    }

}
