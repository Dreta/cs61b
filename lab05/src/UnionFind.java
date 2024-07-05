import java.util.ArrayList;
import java.util.List;

public class UnionFind {
    private int[] sets;

    /*
     * Creates a UnionFind data structure holding N items. Initially, all
     * items are in disjoint sets.
     */
    public UnionFind(int N) {
        sets = new int[N];
        for (int i = 0; i < N; i++) {
            sets[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return -sets[find(v)];
    }

    /*
     * Returns the parent of V. If V is the root of a tree, returns the
     * negative size of the tree for which V is the root.
     */
    public int parent(int v) {
        return sets[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /*
     * Returns the root of the set V belongs to. Path-compression is employed
     * allowing for fast search-time. If invalid items are passed into this
     * function, throw an IllegalArgumentException.
     */
    public int find(int v) {
        if (v < 0 || v >= sets.length) {
            throw new IllegalArgumentException("Invalid item");
        }
        int current = v;
        List<Integer> toMoveUp = new ArrayList<>();
        while (parent(current) >= 0) {
            toMoveUp.add(current);
            current = parent(current);
        }
        for (int i : toMoveUp) {
            sets[i] = current; // Complete path compression
        }
        return current;
    }

    /*
     * Connects two items V1 and V2 together by connecting their respective
     * sets. V1 and V2 can be any element, and a union-by-size heuristic is
     * used. If the sizes of the sets are equal, tie break by connecting V1's
     * root to V2's root. Union-ing an item with itself or items that are
     * already connected should not change the structure.
     */
    public void union(int v1, int v2) {
        int rootV1 = find(v1);
        int rootV2 = find(v2);
        if (rootV1 == rootV2) {
            return;
        }
        if (sizeOf(rootV1) <= sizeOf(rootV2)) {
            sets[rootV1] = rootV2;
            sets[rootV2] -= sizeOf(rootV1);
        } else {
            sets[rootV2] = rootV1;
            sets[rootV1] -= sizeOf(rootV2);
        }
    }
}
