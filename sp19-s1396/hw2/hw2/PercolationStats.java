package hw2;

import edu.princeton.cs.algs4.StdStats;
import static edu.princeton.cs.introcs.StdRandom.uniform;

public class PercolationStats {
    // perform T independent experiments on an N-by-N grid
    private double[] samples;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("illegal argument");
        }

        samples = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation percolationGrid = pf.make(N);
            while (!percolationGrid.percolates()) {
                percolationGrid.open(uniform(N), uniform(N));
            }
            samples[i] = (double) percolationGrid.numberOfOpenSites() / (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(samples);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(samples);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(samples.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(samples.length);
    }
}
