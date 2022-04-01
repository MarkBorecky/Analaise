package pl.maro.analise.sheet;

import com.github.miachm.sods.Sheet;
import pl.maro.analise.model.PeopleList;

import java.util.stream.Stream;

public class SheetMapper {
    public static Sheet map(PeopleList statistics) {
        var sheet = new Sheet(statistics.name(), statistics.size(), 2);
        Object[] objects = statistics.people().stream()
                .flatMap(person -> Stream.of(person.statisticName(), person.birthYear()))
                .toArray();
        sheet.getDataRange().setValues(objects);
        return sheet;
    }
}
