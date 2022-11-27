import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.95;
    private final int totalExperiments;
    private final double[] ratios;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        totalExperiments = trials;
        ratios = new double[totalExperiments];
        for (int i = 0; i < totalExperiments; i++) {
            Percolation perc = new Percolation(n);
            int sitesOpened = 0;
            while (!perc.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    sitesOpened++;
                }
                ratios[i] = (double) sitesOpened / (n * n);
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(ratios);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(ratios);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(totalExperiments);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(totalExperiments);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
                                                   Integer.parseInt(args[1]));
        StdOut.printf("mean                    = %d\n", ps.mean());
        StdOut.printf("stddev                  = %d\n", ps.stddev());
        StdOut.printf("95% condidence interval = [%d, %d]", ps.confidenceLo(), ps.confidenceHi());
    }
}
