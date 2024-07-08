package ngrams;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (int i = startYear; i <= endYear; i++) {
            if (ts.containsKey(i)) {
                put(i, ts.get(i));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        return entrySet().stream().sorted((a, b) -> a.getKey() - b.getKey())
                .map(e -> e.getKey())
                .collect(Collectors.toList());
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        return entrySet().stream().sorted((a, b) -> a.getKey() - b.getKey())
                .map(e -> e.getValue())
                .collect(Collectors.toList());
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        if (isEmpty() && ts.isEmpty()) {
            return new TimeSeries();
        }

        TimeSeries result = new TimeSeries();
        for (int year : years()) {
            if (ts.containsKey(year)) {
                result.put(year, get(year) + ts.get(year));
            } else {
                result.put(year, get(year));
            }
        }
        for (int year : ts.years()) {
            if (!result.containsKey(year)) {
                result.put(year, ts.get(year));
            }
        }
        return result;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries result = new TimeSeries();
        for (int year : years()) {
            if (ts.containsKey(year)) {
                result.put(year, get(year) / ts.get(year));
            } else {
                throw new IllegalArgumentException("Missing year " + year);
            }
        }
        return result;
    }
}
