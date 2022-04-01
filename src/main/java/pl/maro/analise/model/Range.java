package pl.maro.analise.model;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Range {
    /*
    # a.
    # I okres 1830-1839 (włącznie)
    # II okres 1840-1939 (włącznie)
    #
    # b.
    # I okres 1830-1884 (włącznie)
    # II okres 1885-1939 (włącznie)

     */
    Y1800_1939(1800, 1939),
    Y1800_1839(1800, 1839),
    Y1840_1939(1840, 1939),
    Y1800_1884(1800, 1884),
    Y1885_1939(1885, 1939);

    final int from;
    final int until;

    Range(int from, int until) {
        this.from = from;
        this.until = until;
    }

    public static Stream<Range> stream(Integer year) {
        return Arrays.stream(Range.values())
                .filter(range -> range.contains(year));
    }

    private boolean contains(Integer year) {
        return year >= from && year <=until;
    }
}
