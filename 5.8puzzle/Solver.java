import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    private class Move implements Comparable<Move> {
        private int moves;
        private Board node;
        private Move prev;

        public Move(Board board) {
            this.moves = 0;
            this.node = board;
            this.prev = null;
        }

        public Move(Board board, Move move) {
            this.moves = prev.moves+1;
            this.node = board;
            this.prev = prev;
        }

        public int compareTo(Move move) {
            return (this.node.manhattan() - move.node.manhattan()) + (this.moves - move.moves);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    private MinPQ<Move> pq;
    private MinPQ<Move> twinPQ;
    private Move lastMove;

    public Solver(Board initial) {
        //main
        pq = new MinPQ<>();
        pq.insert(new Move(initial));

        //twin
        twinPQ = new MinPQ<>();
        twinPQ.insert(new Move(initial.twin()));

        while (true) {
            lastMove = expand(pq);
            if(lastMove!=null || expand(twinPQ)!=null) return;
        }
    }

    private Move expand(MinPQ<Move> moves) {
        if (moves.isEmpty()) return null;
        Move bestMove = moves.delMin();
        if (bestMove.node.isGoal()) return bestMove;
        for (Board b : bestMove.node.neighbors()) {
            if (bestMove.prev == null || !b.equals(bestMove.prev.node)) {
                pq.insert(new Move(b, bestMove));
            }
        }
        return null;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return lastMove!=null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        if(!isSolvable()) return -1;
        return lastMove.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        if(!isSolvable()) return null;

        Stack<Board> solution = new Stack<>();
        Move temp = lastMove;
        while (temp.prev!= null){
            solution.push(temp.node);
            temp = temp.prev;
        }
        solution.push(temp.node);
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
    }

}