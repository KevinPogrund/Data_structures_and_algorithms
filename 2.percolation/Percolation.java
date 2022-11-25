public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private int[][] grid;

    public Percolation(int n) {
        setGrid(new int[n][n]);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                getGrid()[i][j] = 0;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        getGrid()[row][col] = 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return getGrid()[row][col] == 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        }
        if (row == 0 || getGrid()[row - 1][col] == 1) {
            return true;
        }
        if (col > 0 && isOpen(row, col - 1) && isOpen(row, col - 1)) {
            isFull(row, col - 1);

        }
        if (col < getGrid().length && isOpen(row, col + 1) && isOpen(row, col + 1)) {
            isFull(row, col + 1);

        }

        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < getGrid().length; i++) {
            for (int j = 0; j < getGrid()[0].length; j++) {
                if ((getGrid()[i][j] == 1)) {
                    count++;
                }
            }

        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < getGrid().length; i++) {
            boolean row_check = false;
            for (int j = 0; j < getGrid().length; j++) {
                if (isFull(i, j)) {
                    row_check = true;
                    break;
                }
            }
            if (!row_check) {
                return false;
            }
        }
        return true;
    }

    // test client (optional)
    public static void main(String[] args) {
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }
}
