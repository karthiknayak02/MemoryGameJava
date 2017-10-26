import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.Timer;
import java.util.List;

public class MemoryGame extends JFrame implements ActionListener
{
    // Core game play objects
    private Board gameBoard;
    private FlippableCard prevCard1 = null, prevCard2 = null;

    // Labels to display game info
    private JLabel errorLabel, timerLabel, scoreLabel;

    // layout objects: Views of the board and the label area
    private JPanel boardView, labelView;

    // Record keeping counts and times
    private int clickCount = 0, gameTime = 0, errorCount = 0;
    private int pairsFound = 0;

    // Game timer: will be configured to trigger an event every second
    private Timer gameTimer;
    private Timer noMatchToggleDelay;


    public MemoryGame()
    {
        // Call the base class constructor
        super("Hubble Memory Game");

        // Allocate the interface elements
        JButton restart = new JButton("Restart");
        JButton quit = new JButton("Quit");
        timerLabel = new JLabel("Timer: 0");
        errorLabel = new JLabel("Errors: 0");
        scoreLabel = new JLabel("Score: 0");

        quit.addActionListener(this);
        restart.addActionListener(this);

        /*
         * To-Do: Setup the interface objects (timer, error counter, buttons)
         * and any event handling they need to perform
         */

        ActionListener seconds = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameTime += 1;
                timerLabel.setText("Timer:  " + gameTime);
            }
        };

        gameTimer = new Timer(1000, seconds);


        int delayTime = 500; //milliseconds
        ActionListener delayHide = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prevCard1.hideFront();
                prevCard2.hideFront();
                prevCard1 = null;
                prevCard2 = null;
            }
        };

        noMatchToggleDelay = new Timer(delayTime, delayHide);
        noMatchToggleDelay.setRepeats(false);


        // Allocate two major panels to hold interface
        labelView = new JPanel();  // used to hold labels
        boardView = new JPanel();  // used to hold game board

        // get the content pane, onto which everything is eventually added
        Container c = getContentPane();

        // Setup the game board with cards
        gameBoard = new Board(24, this);

        // Add the game board to the board layout area
        boardView.setLayout(new GridLayout(4, 6, 2, 0));
        gameBoard.fillBoardView(boardView);

        // Add required interface elements to the "label" JPanel
        labelView.setLayout(new GridLayout(2, 4, 2, 2));
        labelView.add(quit);
        labelView.add(restart);
        labelView.add(timerLabel);
        labelView.add(errorLabel);
        labelView.add(scoreLabel);

        // Both panels should now be individually layed out
        // Add both panels to the container
        c.add(labelView, BorderLayout.NORTH);
        c.add(boardView, BorderLayout.SOUTH);

        setSize(745, 470);
        setVisible(true);

    }




    /* Handle anything that gets clicked and that uses MemoryGame as an
     * ActionListener */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("Quit")) { System.exit(0); }

        else if (e.getActionCommand().equals("Restart")){
            restartGame(); }



        else if (!noMatchToggleDelay.isRunning()){

            // Get the currently clicked card from a click event
            FlippableCard currCard = (FlippableCard)e.getSource();
            gameTimer.start();


            if (prevCard1 == null && prevCard2 == null) {
                prevCard1 = currCard;
                prevCard1.removeActionListener(this);
                prevCard1.showFront();
                clickCount += 1;
            }

            else if (prevCard1 != null && prevCard2 == null) {
                prevCard2 = currCard;
                prevCard2.removeActionListener(this);
                prevCard2.showFront();
                clickCount += 1;
            }

            if (prevCard1 != null && prevCard2 != null) {

                if (prevCard1.id() != prevCard2.id()){
                    prevCard1.addActionListener(this);
                    prevCard2.addActionListener(this);
                    noMatchToggleDelay.start();
                    errorCount += 1;
                    errorLabel.setText("Errors: " + errorCount);
                }

                else if (prevCard1.id() == prevCard2.id()){
                    prevCard1 = null;
                    prevCard2 = null;
                    pairsFound += 1;
                    scoreLabel.setText("Score: " + pairsFound);
                }

            }

            if (pairsFound == 12){
                gameTimer.stop();
                JOptionPane.showMessageDialog(null, "YOU WIN!", "Hubble Memory Game", JOptionPane.INFORMATION_MESSAGE);
            }

        }

    }

    private void restartGame()
    {
        gameTimer.stop();

        pairsFound = 0;
        gameTime = 0;
        clickCount = 0;
        errorCount = 0;
        timerLabel.setText("Timer: 0");
        errorLabel.setText("Errors: 0");
        scoreLabel.setText("Score: 0");

        // Clear the boardView and have the gameBoard generate a new layout
        boardView.removeAll();
        gameBoard.removeCards();
        gameBoard = new Board(24, this);
        gameBoard.fillBoardView(boardView);

    }

    public static void main(String args[])
    {
        MemoryGame M = new MemoryGame();
        M.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
    }
}
