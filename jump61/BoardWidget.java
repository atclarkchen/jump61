package jump61;

import ucb.gui.Pad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;

import static jump61.Side.*;

/** A GUI component that displays a Jump61 board, and converts mouse clicks
 *  on that board to commands that are sent to the current Game.
 *  @author ClarkChen
 */
class BoardWidget extends Pad {

    /** Length of the side of one square in pixels. */
    private static final int SQUARE_SIZE = 50;
    /** Width and height of a spot. */
    private static final int SPOT_DIM = 8;
    /** Minimum separation of center of a spot from a side of a square. */
    private static final int SPOT_MARGIN = 10;
    /** Width of the bars separating squares in pixels. */
    private static final int SEPARATOR_SIZE = 3;
    /** Width of square plus one separator. */
    private static final int SQUARE_SEP = SQUARE_SIZE + SEPARATOR_SIZE;

    /** Colors of various parts of the displayed board. */
    private static final Color
        NEUTRAL = Color.WHITE,
        SEPARATOR_COLOR = Color.BLACK,
        SPOT_COLOR = Color.BLACK,
        RED_TINT = new Color(255, 200, 200),
        BLUE_TINT = new Color(200, 200, 255);

    /** A new BoardWidget that monitors and displays GAME and its Board, and
     *  converts mouse clicks to commands to COMMANDWRITER. */
    BoardWidget(Game game, PrintWriter commandWriter) {
        _game = game;
        _board = _bufferedBoard = game.getBoard();
        _side = _board.size() * SQUARE_SEP + SEPARATOR_SIZE;
        setPreferredSize(_side, _side);
        setMouseHandler("click", this, "doClick");
        _commandOut = commandWriter;
    }

    /** Update my display depending on any changes to my Board.  Here, we
     *  save a copy of the current Board (so that we can deal with changes
     *  to it only when we are ready for them), and resize the Widget if the
     *  size of the Board should change. */
    synchronized void update() {
        _bufferedBoard = new MutableBoard(_board);
        int side0 = _side;
        _side = _board.size() * SQUARE_SEP + SEPARATOR_SIZE;
        if (side0 != _side) {
            setPreferredSize(_side, _side);
        }
    }

    @Override
    public synchronized void paintComponent(Graphics2D g) {
        g.setColor(NEUTRAL);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.fillRect(0, 0, _side, _side);
        g.setColor(SEPARATOR_COLOR);

        for (int k = 0; k <=  _side; k +=  SQUARE_SEP) {
            g.fillRect(0, k,  _side,  SEPARATOR_SIZE);
            g.fillRect(k, 0,  SEPARATOR_SIZE,  _side);
        }

        Board b =  _game.getBoard();

        for (int r = 1; r <= b.size(); r++) {
            for (int c = 1; c <= b.size(); c++) {
                displaySpots(g, r, c);
            }
        }
    }

    /** Color and display the spots on the square at row R and column C
     *  on G.  (Used by paintComponent). */
    private void displaySpots(Graphics2D g, int r, int c) {
        Board b =  _game.getBoard();
        Square square = b.get(r, c);
        int spots = square.getSpots();
        Side side = square.getSide();
        if (side == RED) {
            g.setColor(RED_TINT);
        }
        if (side == BLUE) {
            g.setColor(BLUE_TINT);
        }
        if (side == WHITE) {
            g.setColor(NEUTRAL);
        }

        int x = (r - 1) * (SQUARE_SIZE + SEPARATOR_SIZE) + SEPARATOR_SIZE;
        int y = (c - 1) * (SQUARE_SIZE + SEPARATOR_SIZE) + SEPARATOR_SIZE;
        g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);

        int spotx = 0;
        int spoty = 0;
        for (int i = 0; i < spots; i++) {
            if (i == 0) {
                spotx = x + SPOT_MARGIN;
                spoty = y + SPOT_MARGIN;
            }
            if (i == 1) {
                spotx = x + SQUARE_SIZE - SPOT_MARGIN;
                spoty = y + SPOT_MARGIN;
            }
            if (i == 2) {
                spotx = x + SQUARE_SIZE - SPOT_MARGIN;
                spoty = y + SQUARE_SIZE - SPOT_MARGIN;
            }
            if (i == 3) {
                spotx = x +  SPOT_MARGIN;
                spoty = y +  SQUARE_SIZE -  SPOT_MARGIN;
            }
            spot(g, spotx, spoty);
        }
    }

    /** Draw one spot centered at position (X, Y) on G. */
    private void spot(Graphics2D g, int x, int y) {
        g.setColor(SPOT_COLOR);
        g.fillOval(x - SPOT_DIM / 2, y - SPOT_DIM / 2, SPOT_DIM, SPOT_DIM);
    }

    /** Respond to the mouse click depicted by EVENT. */
    public void doClick(MouseEvent event) {
        int x = event.getX() - SEPARATOR_SIZE,
            y = event.getY() - SEPARATOR_SIZE;

        int r = ((x - SEPARATOR_SIZE) / (SQUARE_SIZE +  SEPARATOR_SIZE)) + 1;
        int c = ((y - SEPARATOR_SIZE) / (SQUARE_SIZE +  SEPARATOR_SIZE)) + 1;
        _commandOut.printf("%d %d%n", r, c);
    }

    /** The Game I am playing. */
    private Game _game;
    /** The Board I am displaying. */
    private Board _board;
    /** An internal snapshot of _board (to prevent race conditions). */
    private Board _bufferedBoard;
    /** Dimension in pixels of one side of the board. */
    private int _side;
    /** Destination for commands derived from mouse clicks. */
    private PrintWriter _commandOut;
}
