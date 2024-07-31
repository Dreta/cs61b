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

    @Override
    public DirectedGraph clone() {
        DirectedGraph clone = new DirectedGraph(vertices);
        for (int i = 0; i < vertices; i++) {
            if (adj[i] != null) {
                for (int j : adj[i]) {
                    clone.addEdge(i, j);
                }
            }
        }
        return clone;
    }

    public DirectedGraph reverse() {
        List<Integer>[] reversed = new List[vertices];
        for (int i = 0; i < vertices; i++) {
            if (adj[i] != null) {
                for (int j : adj[i]) {
                    if (reversed[j] == null) {
                        reversed[j] = new ArrayList<>();
                    }
                    reversed[j].add(i);
                }
            }
        }
        adj = reversed;
        return this;
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
