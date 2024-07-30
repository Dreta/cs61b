package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import ngrams.NGramMap;

public class HyponymsHandler extends NgordnetQueryHandler {
    private NGramMap map;
    private WordNet wordNet;

    public HyponymsHandler(NGramMap map, WordNet wordNet) {
        this.map = map;
        this.wordNet = wordNet;
    }

    @Override
    public String handle(NgordnetQuery q) {
        if (q.ngordnetQueryType() != NgordnetQueryType.HYPONYMS) {
            throw new IllegalArgumentException("Invalid query type");
        }
        Set<String> result = new TreeSet<>();
        List<Collection<String>> results = new ArrayList<>();
        for (String word : q.words()) {
            Collection<String> thisResult = wordNet.hyponyms(word);
            result.addAll(thisResult);
            results.add(thisResult);
        }
        for (Collection<String> thisResult : results) {
            result.retainAll(thisResult);
        }

        if (q.k() == 0) {
            return result.toString();
        }

        Map<String, Integer> wordToCount = new HashMap<>();
        for (String s : result) {
            int sum = map.countHistory(s, q.startYear(), q.endYear()).data().stream().mapToInt(Double::intValue).sum();
            if (sum > 0) {
                wordToCount.put(s, sum);
            }
        }

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordToCount.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue())); // Sort in descending order

        List<String> topKKeys = sortedEntries.stream()
                                            .limit(q.k())
                                            .map(Map.Entry::getKey)
                                            .collect(Collectors.toList());
        topKKeys.sort(String::compareTo);
        
        return topKKeys.toString();
    }
}
