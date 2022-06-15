package pl.maro.analise;

import io.vavr.collection.List;
import pl.maro.analise.model.NameOccurring;
import pl.maro.analise.utils.FilesUtils;
import pl.maro.analise.utils.NameStandardiser;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static pl.maro.analise.utils.FilesUtils.*;
import static pl.maro.analise.utils.FilesUtils.getBytes;


public class RunStandardiser {
	public static final String STUDENTS = "./src/main/resources/students.csv";
	public static final String PATRONIMIKA = "./src/main/resources/patronomika.csv";
	public static final String NAMES = "./src/main/resources/names.csv";
	public static final String TARGET = "./src/main/resources/standardised/";
	
	public static void main(String... args) {
		var standardiser = NameStandardiser.createNameStandardizer(NAMES);
		
		var studentList = readFile(STUDENTS);
		var standardisedStudent = standardiser.standard(studentList);
		
		var patronomikaList = readFile(PATRONIMIKA);
		var standardisedPatronimika = standardiser.standard(patronomikaList);
		
		var standardisedTogether = standardisedStudent.appendAll(standardisedPatronimika);
		
		createTargetFolder();
		
		save(TARGET.concat("standardised_students.csv"), getBytes(standardisedStudent));
		save(TARGET.concat("standardised_patronomika.csv"), getBytes(standardisedPatronimika));
		save(TARGET.concat("standardised_together.csv"), getBytes(standardisedTogether));
		
	}
	
	private static void createTargetFolder() {
		var targetDir = new File(TARGET);
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}
	}
}
