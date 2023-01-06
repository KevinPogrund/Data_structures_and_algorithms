import edu.princeton.cs.algs4.Stack;

public class Board {

    private final int n;
    private final int[][] currentBoard;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();

        this.n = tiles[0].length;
        this.currentBoard = tiles.clone();
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int x = currentBoard[i][j];
                s.append(String.format("%2d ", x));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int ham = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (currentBoard[i][j] != (n * i + j + 1) && currentBoard[i][j] != 0) ham++;
            }
        }
        return ham;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int man = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int gapHorizontal = Math.abs(i - currentBoard[i][j] % n);
                int gapVertical = Math.abs(j - currentBoard[i][j] / n);
                man += gapHorizontal + gapVertical;
            }
        }
        return man;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (getClass() != y.getClass()) return false;
        Board other = (Board) y;
        if (this.dimension() != other.dimension()) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.currentBoard[i][j] != other.currentBoard[i][j]) return false;
            }
        }
        return true;
    }

    private int[][] swap(int oldRow, int oldCol, int newRow, int newCol) {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = currentBoard[i][j];
            }
        }
        int temp = copy[oldRow][oldCol];
        copy[oldRow][oldCol] = copy[newRow][newCol];
        copy[newRow][newCol] = temp;
        return copy;
    }

    private int[] getBlank() {
        int[] bl = new int[2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (currentBoard[i][j] == 0) {
                    bl[0] = i;
                    bl[1] = j;
                    return bl;
                }
            }
        }
        return null;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> boardStack = new Stack<Board>();
        int[] blankSpace = getBlank();
        int row = blankSpace[0];
        int col = blankSpace[1];

        if (row > 0) boardStack.push(new Board(swap(row, col, row - 1, col)));
        if (row < this.n - 1) boardStack.push(new Board(swap(row, col, row + 1, col)));
        if (col > 0) boardStack.push(new Board(swap(row, col, row, col - 1)));
        if (row < this.n) boardStack.push(new Board(swap(row, col, row + 1, col)));

        return boardStack;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twinBoard = new Board(currentBoard);
        int[] blankSpace = getBlank();
        int row = blankSpace[0];
        int col = blankSpace[1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if ((row != i || col != j) && (row != i || col != j + 1)) {
                    return new Board(swap(row, col, row, col + 1));

                }
            }
        }
        return twinBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
    }

}