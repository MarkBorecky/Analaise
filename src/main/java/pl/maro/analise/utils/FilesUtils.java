package pl.maro.analise.utils;

import io.vavr.collection.List;
import io.vavr.control.Try;
import pl.maro.analise.model.CSV;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public class FilesUtils {
	
	public static final String ENTER = "\n";
	
	public static List<String> readFile(String path) {
		return Try.of(() -> List.ofAll(Files.readAllLines(Path.of(path))))
				.onFailure(Throwable::printStackTrace)
				.getOrElseThrow(e -> new RuntimeException());
	}
	
	
	public static void save(String path, byte[] data) {
		Try.of(() -> Files.write(Path.of(path), data))
				.onFailure(Throwable::printStackTrace);
	}
	
	public static byte[] getBytes(List<? extends CSV> csvList) {
		return csvList
				.map(CSV::toCSV)
				.mkString(ENTER)
				.getBytes(StandardCharsets.UTF_8);
	}
}
