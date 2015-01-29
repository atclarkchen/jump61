package jump61;


/**
 * Class that contains a Square and its coordinates on the board.
 * @author clarkchen
 */
public class SquareX {
    /** Constructs a SquareX object that is a sentinel.. */
    public SquareX() {
        isSentinel = true;

    }
    /** Overloaded constructor taking in SQ, ROW, and COL. */
    public SquareX(Square sq, int row, int col) {
        isSentinel = false;
        this.square = sq;
        this.r = row;
        this.c = col;
    }
    /** ToString representation returns a string. */
    public String toString() {
        String out = "";
        out += "[" + r + " , " + c + " , " + square.getSide() + " , ";
        out += square.getSpots() + "]";
        return out;
    }
    /** Returns the square of the squareX object. */
    public Square getSquare() {
        return this.square;
    }
    /** Returns the row index.*/
    public int getR() {
        return r;
    }
    /** Returns the column index. */
    public int getC() {
        return c;
    }
    /** Returns boolean for whether or not SquareX object is a sentinel.*/
    public boolean isSentinel() {
        return isSentinel;
    }

/** Private Fields. */
    private Square square;
    /** Row index. */
    private int r;
    /** Column index. */
    private int c;
    /** Is this object a sentinel. */
    private boolean isSentinel;
}
