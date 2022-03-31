package pl.maro.analise.sheet;

import com.github.miachm.sods.Sheet;
import pl.maro.analise.model.Statistics;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;

public class SheetMapper {
    public static Sheet map(Statistics statistics) {
        var sheetName = "%s_%s".formatted(statistics.name(), statistics.range());
        var sheet = new Sheet(sheetName, statistics.size(), 2);
        Object[] objects = statistics.people().stream()
                .flatMap(person -> Stream.of(person.statisticName(), person.birthYear()))
                .toArray();
        sheet.getDataRange().setValues(objects);
        return sheet;
    }
}
