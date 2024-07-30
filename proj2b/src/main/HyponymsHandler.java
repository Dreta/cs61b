package main;

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
        return wordNet.hyponyms(q.words().get(0)).toString();
    }
}
