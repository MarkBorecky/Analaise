package pl.maro.analise;

import pl.maro.analise.model.StatisticsMapper;
import pl.maro.analise.sheet.SheetMapper;
import pl.maro.analise.sheet.SpreadSheetCollector;
import pl.maro.analise.stream.StreamUtils;

public class Main {
    public final static String resource = "./src/main/resources/Baza_dr_analiza 29.03. ods.ods";
    public final static String result = "./src/main/resources/Result.ods";

    public static void main(String[] args) {
        StreamUtils.SheetsStream(resource)
                .map(StatisticsMapper::map)
                .map(SheetMapper::map)
                .collect(SpreadSheetCollector.toSpreadSheet())
                .save(result);
    }

}
