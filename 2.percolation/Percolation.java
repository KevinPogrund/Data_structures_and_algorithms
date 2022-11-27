import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private final boolean[][] grid;
    private final int sz;
    private final int top;
    private final int bottom;
    private final WeightedQuickUnionUF wqu;
    private int openSites;


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        sz = n;
        grid = new boolean[sz][sz];
        top = 0;
        bottom = sz * sz + 1; // creates a virtual top and bottom site
        wqu = new WeightedQuickUnionUF(sz * sz + 2);
        openSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > sz || col > sz) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row - 1, col - 1)) {
            grid[row - 1][col - 1] = true;
            // If top or bottom
            if (row == 1) {
                wqu.union(getIndex(row, col), top);
            }
            if (row == sz) {
                wqu.union(getIndex(row, col), bottom);
            }

            // if this is in any of the middle rows and there are open neighbors

            if (row > 1 && isOpen(row - 1, col)) {
                wqu.union(getIndex(row, col), getIndex(row - 1, col));
            }
            if (col > 1 && isOpen(row, col - 1)) {
                wqu.union(getIndex(row, col), getIndex(row, col) - 1);
            }
            if (row < sz && isOpen(row + 1, col)) {
                wqu.union(getIndex(row, col), getIndex(row + 1, col));
            }
            if (col < sz && isOpen(row, col + 1)) {
                wqu.union(getIndex(row, col), getIndex(row, col + 1));
            }
            openSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid.length) {
            throw new IllegalArgumentException();
        }

        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > sz || col > sz) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row - 1, col - 1)) {
            return false;
        }
        return wqu.find(top) == wqu.find(getIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return wqu.find(top) == wqu.find(bottom);
        // using the trick in the notes i.e if virtual top and virtual bottom connect, it percolates
    }

    private int getIndex(int row, int col) {
        return sz * (row - 1) + col;
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
