package pl.maro.analise.sheet;

import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FunctionalSpreadSheet {
    private final SpreadSheet spreadSheet;

    private FunctionalSpreadSheet(List<Sheet> list) {
        this.spreadSheet = new SpreadSheet();
        list.forEach(spreadSheet::appendSheet);
    }

    public static FunctionalSpreadSheet create(List<Sheet> list) {
        return new FunctionalSpreadSheet(list);
    }

    public void save(String file) {
        try {
            this.spreadSheet.save(new File(file));
        } catch (IOException e) {
            throw new RuntimeException("Can't write file FunctionalSpreadSheet.save()");
        }
    }

}
