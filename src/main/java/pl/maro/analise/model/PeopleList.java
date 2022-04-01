package pl.maro.analise.model;

import java.util.List;

public record PeopleList(String name, Range range, List<Person> people) {
    public int size() {
        return people.size();
    }

    public void concat(List<Person> statistics) {
        this.people.addAll(statistics);
    }
}
