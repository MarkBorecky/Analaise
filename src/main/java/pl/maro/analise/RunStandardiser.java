package pl.maro.analise;

import io.vavr.collection.List;
import pl.maro.analise.model.NameOccurring;
import pl.maro.analise.utils.FilesUtils;
import pl.maro.analise.utils.NameStandardiser;

import java.io.File;
import java.nio.charset.StandardCharsets;


public class RunStandardiser {
	public static final String STUDENTS = "./src/main/resources/students.csv";
	public static final String PATRONIMIKA = "./src/main/resources/patronomika.csv";
	public static final String NAMES = "./src/main/resources/names.csv";
	public static final String TARGET = "./src/main/resources/standardised/";
	public static final String ENTER = "\n";
	
	public static void main(String[] args) {
		var standardiser = NameStandardiser.createNameStandardizer(NAMES);
		var standardisedStudent = standardiser.standard(STUDENTS);
		var standardisedPatronimika = standardiser.standard(PATRONIMIKA);
		var standardisedTogether = standardisedStudent.appendAll(standardisedPatronimika);
		
		createTargetFolder();
		
		FilesUtils.save(TARGET.concat("standardised_students.csv"), getBytes(standardisedStudent));
		FilesUtils.save(TARGET.concat("standardised_patronomika.csv"), getBytes(standardisedPatronimika));
		FilesUtils.save(TARGET.concat("standardised_together.csv"), getBytes(standardisedTogether));
		
	}
	
	private static void createTargetFolder() {
		var targetDir = new File(TARGET);
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}
	}
	
	private static byte[] getBytes(List<NameOccurring> standardisedStudent) {
		return standardisedStudent
				.map(NameOccurring::toCsv)
				.mkString(ENTER)
				.getBytes(StandardCharsets.UTF_8);
	}
}
