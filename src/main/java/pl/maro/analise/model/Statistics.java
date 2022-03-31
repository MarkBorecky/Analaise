package pl.maro.analise.model;

import java.util.List;

public record Statistics(String name, Range range, List<Person> people) {
    public int size() {
        return people.size();
    }
}
