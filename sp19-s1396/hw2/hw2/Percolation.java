package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // create N-by-N grid, with all sites initially blocked
    private boolean[][] openings;
    private WeightedQuickUnionUF connections;
    private WeightedQuickUnionUF noBackwash;
    private int N;
    private int openSites = 0;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("illegal argument");
        }
        this.N = N;
        openings = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                openings[i][j] = false;
            }
        }
        connections = new WeightedQuickUnionUF(N * N + 2);
        noBackwash =  new WeightedQuickUnionUF(N * N + 1);
        for (int i = 0; i < N; i++) {
            connections.union(xyTo1D(0, i), N * N);
            connections.union(xyTo1D(N - 1, i), N * N + 1);
        }
    }

    private int xyTo1D(int r, int c) {
        return r * N + c;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > N - 1 || row < 0 || col > N - 1 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException("index out of bounds");
        }
        if (isOpen(row, col)) {
            return;
        }

        openings[row][col] = true;
        openSites += 1;

        if (row  < N - 1) {
            if (isOpen(row + 1, col)) {
                noBackwash.union(xyTo1D(row, col), xyTo1D(row + 1, col));
                connections.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
        }
        if (row > 0) {
            if (isOpen(row - 1, col)) {
                noBackwash.union(xyTo1D(row, col), xyTo1D(row - 1, col));
                connections.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
        }
        if (col  < N - 1) {
            if (isOpen(row, col + 1)) {
                noBackwash.union(xyTo1D(row, col), xyTo1D(row, col + 1));
                connections.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        }
        if (col > 0) {
            if (isOpen(row, col - 1)) {
                noBackwash.union(xyTo1D(row, col), xyTo1D(row, col - 1));
                connections.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
        }

        if (row == 0) {
            noBackwash.union(xyTo1D(0, col), N * N);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > N - 1 || row < 0 || col > N - 1 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException("index out of bounds");
        }
        return openings[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > N - 1 || row < 0 || col > N - 1 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException("index out of bounds");
        }
        if (!isOpen(row, col)) {
            return false;
        }
        return noBackwash.connected(xyTo1D(row, col), N * N);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (N == 1) {
            if (!isOpen(0, 0)) {
                return false;
            }
        }
        return connections.connected(N * N, N * N + 1);
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {
    }

}
