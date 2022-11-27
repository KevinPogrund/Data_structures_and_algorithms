import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private final boolean[][] grid;
    private final int sz;
    private final int top;
    private final int bottom;
    final WeightedQuickUnionUF wqf;
    private final int openSites;


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        else {
            sz = n;
            grid = new boolean[sz][sz];
            top = 0;
            bottom = sz * sz + 1; // creates a virtual top and bottom site
            wqf = new WeightedQuickUnionUF(sz * sz + 2);
            openSites = 0;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col < 0 || row > sz || col > sz) {
            throw new IllegalArgumentException();
        }
        else if (!isOpen(row - 1, col - 1)) {
            grid[row - 1][col - 1] = true;
            // If top or bottom
            if (row == 1) {
                wqf.union(sz * (row - 1) + col, top);
            }
            if (row == sz) {
                wqf.union(sz * (row - 1) + col, bottom);
            }

            // if this is in any of the middle rows and there are open neighbors

            if (row > 1 && isOpen(row - 1, col)) {
                wqf.union(sz * (row - 1) + col, sz * (row - 2) + col);
            }
            if (col > 1 && isOpen(row, col - 1)) {
                wqf.union(sz * (row - 1) + col, sz * (row - 1) + col - 1);
            }
            if (row < sz && isOpen(row + 1, col)) {
                wqf.union(sz * (row - 1) + col, sz * (row) + col);
            }
            if (col < sz && isOpen(row, col + 1)) {
                wqf.union(sz * (row - 1) + col, sz * (row - 1) + col + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid.length) {
            throw new IllegalArgumentException();
        }
        else {
            return grid[row-1][col-1];
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid.length) {
            throw new IllegalArgumentException("Value is out of range");
        }
        else {
            if (!isOpen(row, col)) {
                return false;
            }
            if (row == 0) {
                return true;
            }
            int val = group[row * group.length + col];
            for (int i = 0; i < grid.length; i++) {
                if (group[(row - 1) + i] == val) {
                    return true;
                }
            }
            return false;
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if ((grid[i][j] == 1)) {
                    count++;
                }
            }

        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < grid.length; i++) {
            int val = group[i];
            for (int j = 0; j < grid.length; j++) {
                if (group[grid.length * (grid.length - 1) + j] == val) {
                    return true;
                }
            }
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
