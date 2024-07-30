package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wordNet;

    public HyponymsHandler(WordNet wordNet) {
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
        return result.toString();
    }
}
