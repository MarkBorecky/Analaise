package pl.maro.analise.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class MyCollector implements Collector<PeopleList, List<PeopleList>, List<PeopleList>> {
    private static PeopleList together = new PeopleList("together", Range.Y1800_1939, new ArrayList<>());

    public static MyCollector getAndAddTogether() {
        return new MyCollector();
    }

    @Override
    public Supplier<List<PeopleList>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<PeopleList>, PeopleList> accumulator() {
        return (list, statistics) -> {
            list.add(statistics);
            together.concat(statistics.people());
        };
    }

    @Override
    public BinaryOperator<List<PeopleList>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Function<List<PeopleList>, List<PeopleList>> finisher() {
        return (list) -> {
            list.add(together);
            return list;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}
