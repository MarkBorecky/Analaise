package pl.maro.analise;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import pl.maro.analise.model.NameOccurring;
import pl.maro.analise.utils.NameStandardiser;

import static org.assertj.core.api.Assertions.assertThat;

class RunStandardiserTest {

    public static final String NAMES = "./src/test/resources/names.csv";
    public static final String STUDENTS = "./src/main/resources/students.csv";

    @Test
    void main() {
        // given
        var standardiser = NameStandardiser.createNameStandardizer(NAMES);
        var studentList = List.of(
                "Александръ;1838",
                "Антонъ/Антоній;1841",
                "Антонъ/Антоній;1843");

        // when
        var standardisedStudent = standardiser.standard(studentList);

        // then
        assertThat(standardisedStudent.map(NameOccurring::name))
                .contains("Александр", "Антон", "Антон");
    }
}