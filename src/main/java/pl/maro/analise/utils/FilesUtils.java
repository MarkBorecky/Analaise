package pl.maro.analise.utils;

import io.vavr.collection.List;
import io.vavr.control.Try;

import java.nio.file.Files;
import java.nio.file.Path;


public class FilesUtils {
	
	public static List<String> readFile(String path) {
		return Try.of(() -> List.ofAll(Files.readAllLines(Path.of(path))))
				.onFailure(Throwable::printStackTrace)
				.getOrElseThrow(e -> new RuntimeException());
	}
	
	
	public static void save(String path, byte[] data) {
		Try.of(() -> Files.write(Path.of(path), data))
				.onFailure(Throwable::printStackTrace);
	}
}
