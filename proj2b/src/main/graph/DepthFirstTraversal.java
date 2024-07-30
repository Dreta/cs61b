package main.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DepthFirstTraversal {
    private boolean[] marked;
    private int[] edgeTo;

    public DepthFirstTraversal(DirectedGraph graph, int s) {
        marked = new boolean[graph.vertices()];
        edgeTo = new int[graph.vertices()];
        dfs(graph, s);
    }

    private void dfs(DirectedGraph graph, int v) {
        marked[v] = true;
        for (int w : graph.adjacent(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(graph, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Collection<Integer> marked() {
        List<Integer> markedVertices = new ArrayList<>();
        for (int i = 0; i < marked.length; i++) {
            if (marked[i]) {
                markedVertices.add(i);
            }
        }
        return markedVertices;
    }
}
