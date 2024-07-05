import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private int N;
    private WeightedQuickUnionUF ufTop; // A UF without the bottom virtual site, used to determine if a site is full.
    private WeightedQuickUnionUF uf; // A UF with everything, used to determine if percolates.
    private boolean[] opened;
    private int openedSites = 0;

    public Percolation(int N) {
        // Let 0 be the virtual top site and N * N + 1 be the virtual bottom site in both UFs.
        this.N = N;
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.ufTop = new WeightedQuickUnionUF(N * N + 2);
        this.uf = new WeightedQuickUnionUF(N * N + 2);
        this.opened = new boolean[N * N + 2];
    }

    private int getIndex(int row, int col) {
        // Convert a 2D index to a 1D index.
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException();
        }
        return 1 + N * row + col;
    }

    private void unionBoth(int a, int b) {
        ufTop.union(a, b);
        uf.union(a, b);
    }

    public void open(int row, int col) {
        opened[getIndex(row, col)] = true;
        openedSites++;
        // If this site is in the top row, connect it to the top virtual site.
        if (row == 0) {
            unionBoth(0, getIndex(row, col));
        }

        // If this site is in the bottom row, connect it to the bottom virtual site.
        if (row == N - 1) {
            uf.union(N * N + 1, getIndex(row, col));
        }

        // Connect with adjacent sites that are open.
        if (row > 0 && isOpen(row - 1, col)) {
            unionBoth(getIndex(row, col), getIndex(row - 1, col));
        }
        if (row < N - 1 && isOpen(row + 1, col)) {
            unionBoth(getIndex(row, col), getIndex(row + 1, col));
        }
        if (col > 0 && isOpen(row, col - 1)) {
            unionBoth(getIndex(row, col), getIndex(row, col - 1));
        }
        if (col < N - 1 && isOpen(row, col + 1)) {
            unionBoth(getIndex(row, col), getIndex(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        return opened[getIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        return ufTop.connected(0, getIndex(row, col)); // If the top site is connected to this site
    }

    public int numberOfOpenSites() {
        return openedSites;
    }

    public boolean percolates() {
        return uf.connected(0, N * N + 1); // If the top virtual site is connected to the bottom virtual site
    }
}
