package pl.maro.analise.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.*;

class FilesUtilsTest {
	
	@Test
	void save(@TempDir Path path) {
		var testFile = path.toString().concat("test.csv");
		FilesUtils.save(testFile, "QWERTY".getBytes());
		assertThat(Files.exists(Paths.get(testFile))).isTrue();
	}
}