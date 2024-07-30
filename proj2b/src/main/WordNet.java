package main;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import edu.princeton.cs.algs4.In;
import main.graph.DepthFirstTraversal;
import main.graph.DirectedGraph;

public class WordNet {
    private Map<Integer, String[]> synsetsMap = new HashMap<>();
    private Multimap<String, Integer> synsetsMapReverse = HashMultimap.create();
    private DirectedGraph graph;

    public WordNet(String synsets, String hyponyms) {
        In synsetsIn = new In(synsets);
        while (synsetsIn.hasNextLine()) {
            String[] parts = synsetsIn.readLine().split(",");
            synsetsMap.put(Integer.parseInt(parts[0]), parts[1].split(" "));
            for (String s : parts[1].split(" ")) {
                synsetsMapReverse.put(s, Integer.parseInt(parts[0]));
            }
        }
        graph = new DirectedGraph(synsetsMap.size());
        synsetsIn.close();

        In hyponymsIn = new In(hyponyms);
        while (hyponymsIn.hasNextLine()) {
            String[] parts = hyponymsIn.readLine().split(",");
            for (int i = 1; i < parts.length; i++) {
                graph.addEdge(Integer.parseInt(parts[0]), Integer.parseInt(parts[i]));
            }
        }
        hyponymsIn.close();
    }

    public Collection<String> hyponyms(String word) {
        Set<String> result = new TreeSet<>();
        for (int wordID : synsetsMapReverse.get(word)) {
            DepthFirstTraversal dfs = new DepthFirstTraversal(graph, wordID);
            for (int marked : dfs.marked()) {
                for (String s : synsetsMap.get(marked)) {
                    result.add(s);
                }
            }
        }
        return result;
    }
}
