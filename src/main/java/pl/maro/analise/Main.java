package pl.maro.analise;


import pl.maro.analise.model.MyCollector;
import pl.maro.analise.model.PeopleList;
import pl.maro.analise.model.PeopleListMapper;
import pl.maro.analise.sheet.SheetMapper;
import pl.maro.analise.sheet.SpreadSheetCollector;
import pl.maro.analise.stream.StreamUtils;

import static pl.maro.analise.model.NameStatisticsMapper.createNameStatistics;

public class Main {
    public final static String resource = "./src/main/resources/Baza_dr_analiza 29.03. ods.ods";
    public final static String result = "./src/main/resources/Result.ods";

    public static void main(String[] args) {
        var list = StreamUtils.SheetsStream(resource)
                .map(PeopleListMapper::map)
                .collect(MyCollector.getAndAddTogether());

        var denominators = list.stream()
                .map(PeopleList::people);

        var nameStatistics = list.stream()
                .map(peopleList -> createNameStatistics(peopleList.people(), null))
                        .toList();

        list.stream()
                .map(SheetMapper::map)
                .collect(SpreadSheetCollector.toSpreadSheet())
                .save(result);
    }

}
