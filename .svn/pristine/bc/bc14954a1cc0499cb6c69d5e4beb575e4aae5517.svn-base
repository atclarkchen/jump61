package jump61;

import static jump61.Side.*;
import static jump61.Square.square;

import java.util.ArrayList;
import java.util.Stack;

/** A Jump61 board state that may be modified.
 *  @author ClarkChen
 */


class MutableBoard extends Board {

    /** An N x N board in initial configuration. */
    MutableBoard(int N) {
        _board = new Square[N][N];
        for (int r = 0; r < N; r++) {
            _board[r] = new Square[N];
            for (int c = 0; c < N; c++) {
                _board[r][c] = Square.square(WHITE, 0);
            }
        }
        history = new Stack<SquareX>();
    }

    /** A board whose initial contents are copied from BOARD0, but whose
     *  undo history is clear. */
    MutableBoard(Board board0) {
        this(board0.size());
        this.copy(board0);
    }

    @Override
    void clear(int N) {
        announce();

        MutableBoard clear = new MutableBoard(N);
        _board = clear._board;
    }

    @Override
    void copy(Board board) {
        for (int r = 1; r <= size(); r++) {
            for (int c = 1; c <= size(); c++) {
                Square s = board.get(r, c);
                _board[r - 1][c - 1] = Square.square(s.getSide(), s.getSpots());
            }
        }
    }

    /** Copy the contents of BOARD into me, without modifying my undo
     *  history.  Assumes BOARD and I have the same size. */
    private void internalCopy(MutableBoard board) {
    }

    @Override
    int size() {
        return _board.length;
    }

    @Override
    Square get(int n) {
        int r = row(n);
        int c = col(n);

        return _board[r - 1][c - 1];
    }

    @Override
    int numOfSide(Side side) {
        int count = 0;

        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < this.size(); j++) {
                if (_board[i][j].getSide() == side) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    int numPieces() {
        int total = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (_board[i][j] == null) {
                    System.out.flush();
                }
                total += _board[i][j].getSpots();
            }
        }
        return total;
    }

    /** Returns arraylist of Neighbor indices using row R and col C.*/
    ArrayList<int[]> getNeighbors(int r, int c) {
        ArrayList<int[]> neighbors = new ArrayList<int[]>();
        if (this.exists(r - 1, c)) {
            neighbors.add(new int[]{r - 1, c});
        }
        if (this.exists(r, c + 1)) {
            neighbors.add(new int[]{r, c + 1});
        }
        if (this.exists(r + 1, c)) {
            neighbors.add(new int[]{r + 1, c});
        }
        if (this.exists(r, c - 1)) {
            neighbors.add(new int[]{r, c - 1});
        }
        return neighbors;
    }

    @Override
    /** ADD1. DOES NON-OVERFULL CASE. CALLS ADD2 (if overfull).
     * assumes isLegal*/
    /** Note that undo is supposed to revert to NON-EXPLOSIVE case.*/
    /** Takes in side PLAYER, row R, and col C.*/

    void addSpot(Side player, int r, int c) {
        announce();

        if (this.isGameOver()) {
            return;
        }
        int n = this.sqNum(r, c);
        Square s = get(n);

        int spotCount = s.getSpots() + 1;

        if (spotCount > this.neighbors(n)) {
            history.add(sentinel);
            SquareX sx = new SquareX(s, r, c);
            history.add(sx);
            addSpot(player, n);

        } else {
            SquareX sx = new SquareX(s, r, c);
            history.add(sx);
            Square sq = Square.square(player, spotCount);
            this.internalSet(n, sq);
        }
    }


    @Override
    /** ADD 2, handles OVERFULL cases.*/
    /** Takes a side PLAYER and square number N.*/
    void addSpot(Side player, int n) {
        announce();

        int r = row(n);
        int c = col(n);
        _board[r - 1][c - 1] = Square.square(player, 1);

        ArrayList<int[]> neighbors = this.getNeighbors(r, c);

        for (int[] info : neighbors) {
            int i = info[0];
            int j = info[1];
            this.addSpot(player, i, j);
        }
        history.add(sentinel);
    }

    @Override
    void set(int r, int c, int num, Side player) {
        internalSet(sqNum(r, c), square(player, num));
        history = new Stack<SquareX>();
    }

    @Override
    void set(int n, int num, Side player) {
        internalSet(n, square(player, num));
        announce();
        history = new Stack<SquareX>();
    }

    @Override
    /** Look at moves history STACK.*/
    /** Pop first item off and ensure that board looks like that condition.*/
    void undo() {
        if (history.isEmpty()) {
            return;
        }

        if (!history.peek().isSentinel()) {
            SquareX x = history.pop();
            Square y = x.getSquare();
            Side side = y.getSide();
            int spots = y.getSpots();
            _board[x.getR() - 1][x.getC() - 1] = Square.square(side, spots);
        } else {
            history.pop();
            SquareX sq = history.pop();
            Square sq1 = sq.getSquare();
            Side side = sq1.getSide();
            int spots = sq1.getSpots();
            _board[sq.getR() - 1][sq.getC() - 1] = Square.square(side, spots);
            while (!history.peek().isSentinel()) {
                SquareX s = history.pop();
                Square s1 = s.getSquare();
                Side side1 = s1.getSide();
                int spots1 = s1.getSpots();
                int row = s.getR() - 1;
                int col = s.getC() - 1;
                _board[row][col] = Square.square(side1, spots1);
            }
            history.pop();
        }
    }

    /** Record the beginning of a move in the undo history. */
    private void markUndo() {

    }

    /** Set the contents of the square with index IND to SQ. Update counts
     *  of numbers of squares of each color.  */
    private void internalSet(int ind, Square sq) {
        int r = row(ind);
        int c = col(ind);
        _board[r - 1][c - 1] = Square.square(sq.getSide(), sq.getSpots());


    }

    /** Returns boolean taking in R and C, checking if square is overFull. */
    boolean isOverFull(int r, int c) {
        if (_board[r - 1][c - 1].getSpots() > this.neighbors(r, c)) {
            return true;
        }
        return false;
    }


    /** Notify all Observers of a change. */
    private void announce() {
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MutableBoard)) {
            return obj.equals(this);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 0;
    }

    /** Returns a Stack of SquareX objects.*/
    public Stack<SquareX> getHistory() {
        return history;
    }

    /** Private fields. */
    private Square[][] _board;


    /** stack or queue of NON-EXPLOSIVE MOVES.*/
    /** A typical "move" is [int r, int c, int spots].*/
    private Stack<SquareX> history;
    /**Special SquareX object that is a sentinel. */
    private SquareX sentinel = new SquareX();
}
