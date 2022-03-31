package pl.maro.analise.stream;

import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class StreamUtils {
    public static List<String> mapToStrings(Object[] row) {
        return Arrays.stream(row)
                .limit(10)
                .map(Object::toString)
                .toList();
    }

    public static Stream<Sheet> SheetsStream(String resource) {
        try {
            return new SpreadSheet(Files.newInputStream(Paths.get(resource)))
                    .getSheets().stream();
        } catch (IOException e) {
            throw new RuntimeException("Can't read file %s".formatted(resource));
        }
    }

    public static Stream<String> linesStream(String path) {
        try {
            return Files.lines(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file %s".formatted(path));
        }
    }
}
