package main.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DirectedGraph {
    private int vertices;
    private List<Integer>[] adj;

    public DirectedGraph(int vertices) {
        this.vertices = vertices;
        this.adj = new List[vertices];
    }

    public void addEdge(int from, int to) {
        if (adj[from] == null) {
            adj[from] = new ArrayList<>();
        }
        adj[from].add(to);
    }

    public Collection<Integer> adjacent(int v) {
        return adj[v] == null ? Collections.emptyList() : adj[v];
    }

    public int vertices() {
        return vertices;
    }

    public int edges() {
        return Arrays.stream(adj).mapToInt(List::size).sum();
    }
}
