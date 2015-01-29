package jump61;

import ucb.gui.TopLevel;
import ucb.gui.LayoutSpec;

import java.io.PrintWriter;
import java.io.Writer;

import java.util.Observable;
import java.util.Observer;

import static jump61.Side.*;

/** The GUI controller for jump61.  To require minimal change to textual
 *  interface, we adopt the strategy of converting GUI input (mouse clicks)
 *  into textual commands that are sent to the Game object through a
 *  a Writer.  The Game object need never know where its input is coming from.
 *  A Display is an Observer of Games and Boards so that it is notified when
 *  either changes.
 *  @author ClarkChen
 */


class Display extends TopLevel implements Observer {

    /** A new window with given TITLE displaying GAME, and using COMMANDWRITER
     *  to send commands to the current game. */
    Display(String title, Game game, Writer commandWriter) {
        super(title, true);
        _game = game;
        _board = game.getBoard();
        _commandOut = new PrintWriter(commandWriter);
        _boardWidget = new BoardWidget(game, _commandOut);
        add(_boardWidget, new LayoutSpec("y", 1, "width", 2));


        addMenuButton("Game->Quit", "quit");

        addMenuButton("Game->Start", "start");
        addMenuButton("Game->Help", "help");
        addMenuButton("Game->Clear", "clear");

        addMenuButton("Game->Size", "size");

        addMenuButton("Game->AIRed", "autoRed");
        addMenuButton("Game->ManualRed", "manualRed");
        addMenuButton("Game->AIBlue", "autoBlue");
        addMenuButton("Game->ManualBlue", "manualBlue");

        _board.addObserver(this);
        _game.addObserver(this);
        display(true);
    }
    /** AutoRed button implementation.*/
    void autoRed(String dummy) {
        _commandOut.println("auto red");
    }
    /** AutoBlue button implementation.*/
    void autoBlue(String dummy) {
        _commandOut.println("auto blue");
    }
    /** ManualBlue button implementation.*/
    void manualBlue(String dummy) {
        _commandOut.println("manual blue");
    }
    /** ManualRed button implementation.*/
    void manualRed(String dummy) {
        _commandOut.println("manual red");
    }

    /** Response to "Size" button click. */
    void size(String dummy) {
        String input = getTextInput("SET SIZE", "SIZE", "", "input board size");
        _commandOut.printf("size %s%n", input);
        this.update(_board, _game);
    }

    /** Response to "Quit" button click. */
    void quit(String dummy) {
        System.exit(0);
    }
    /** Response to "Start" button click. */
    void start(String dummy) {
        _commandOut.println("start");
    }
    /** Response to "Help" button click. */
    void help(String dummy) {
        _commandOut.println("help");
    }
    /** Response to "Clear" button click. */
    void clear(String dummy) {
        _commandOut.println("clear");
    }
    @Override
    public void update(Observable obs, Object obj) {
        _boardWidget.update();
        frame.pack();
        _boardWidget.repaint();
    }

    /** The current game that I am controlling. */
    private Game _game;
    /** The board maintained by _game (readonly). */
    private Board _board;
    /** The widget that displays the actual playing board. */
    private BoardWidget _boardWidget;
    /** Writer that sends commands to our game. */
    private PrintWriter _commandOut;
}
