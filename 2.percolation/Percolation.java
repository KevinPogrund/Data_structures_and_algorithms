public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private int[][] grid;
    private int[] group;

    public Percolation(int n) {
        grid = new int[n][n];
        group = new int[n * n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
                group[count] = count;
                count++;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid.length) {
            throw new IllegalArgumentException("Value is out of range");
        }
        else if (isOpen(row, col)) {
            grid[row][col] = 1;
            //Get the grouping values of the surrounding cells
            int left = -1;
            int right = -1;
            int above = -1;
            int below = -1;

            int val = group[row * grid.length + col];
            if (row != 0 && isOpen(row - 1, col)) {
                above = group[grid.length * (row - 1) + col];
            }
            if (col != 0 && isOpen(row, col - 1)) {
                left = group[grid.length * row + col - 1];
            }

            if (row != group.length && isOpen(row + 1, col)) {
                below = group[grid.length * (row + 1) + col];
            }
            if (col != group.length&& isOpen(row, col + 1)) {
                right = group[grid.length * row + col + 1];
            }
            for (int i = 0; i < group.length; i++) {
                if(group[i]==above ||group[i]==below ||group[i]==left ||group[i]==right){
                    group[i]=val;
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid.length) {
            throw new IllegalArgumentException("Value is out of range");
        }
        else {
            return grid[row][col] == 1;
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
