package ngrams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.In;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    private Map<String, TimeSeries> words;
    private TimeSeries counts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        words = new HashMap<>();
        In wordsIn = new In(wordsFilename);
        while (wordsIn.hasNextLine()) {
            String[] parts = wordsIn.readLine().split("\t");
            String word = parts[0];
            int year = Integer.parseInt(parts[1]);
            double amount = Double.parseDouble(parts[2]);
            if (words.containsKey(word)) {
                words.get(word).put(year, amount);
            } else {
                TimeSeries ts = new TimeSeries();
                ts.put(year, amount);
                words.put(word, ts);
            }
        }
        wordsIn.close();

        In countsIn = new In(countsFilename);
        counts = new TimeSeries();
        while (countsIn.hasNextLine()) {
            String[] parts = countsIn.readLine().split(",");
            int year = Integer.parseInt(parts[0]);
            double amount = Double.parseDouble(parts[1]);
            counts.put(year, amount);
        }
        countsIn.close();
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (words.containsKey(word)) {
            return new TimeSeries(words.get(word), startYear, endYear);
        }
        return new TimeSeries();
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(counts, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (words.containsKey(word)) {
            TimeSeries ts = new TimeSeries();
            for (int year = startYear; year <= endYear; year++) {
                double count = words.get(word).getOrDefault(year, 0.0);
                double total = counts.getOrDefault(year, 0.0);
                if (count == 0.0) {
                    continue;
                }
                ts.put(year, count / total);
            }
            return ts;
        } else {
            return new TimeSeries();
        }
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        List<TimeSeries> series = new ArrayList<>();
        for (String word : words) {
            if (this.words.containsKey(word)) {
                series.add(weightHistory(word, startYear, endYear));
            }
        }
        TimeSeries summed = new TimeSeries();
        for (TimeSeries serie : series) {
            summed = summed.plus(serie);
        }
        return summed;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }
}
