package pl.maro.analise.sheet;

import com.github.miachm.sods.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class SpreadSheetCollector implements Collector<Sheet, List<Sheet>, FunctionalSpreadSheet> {

    public static SpreadSheetCollector toSpreadSheet() {
        return new SpreadSheetCollector();
    };

    @Override
    public Supplier<List<Sheet>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<Sheet>, Sheet> accumulator() {
        return List::add;
    }

    @Override
    public BinaryOperator<List<Sheet>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Function<List<Sheet>, FunctionalSpreadSheet> finisher() {
        return FunctionalSpreadSheet::create;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}
