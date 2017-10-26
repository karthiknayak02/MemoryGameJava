import java.awt.event.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

public class Board
{
    // ArrayList to hold board cards
    private List<FlippableCard> cards = new ArrayList<FlippableCard>();

    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();

    public Board(int size, ActionListener AL)
    {
        // Fill the Cards array
        int imageIdx = 1;

        for (int i = 0; i < size; i += 2) {

            // Load the front image from the resources folder
            String imgPath = "res/hub" + imageIdx + ".jpg";
            ImageIcon img = new ImageIcon(loader.getResource(imgPath));

            // Setup two cards at a time
            FlippableCard c1 = new FlippableCard(img);
            FlippableCard c2 = new FlippableCard(img);

            c1.setID(imageIdx);
            c2.setID(imageIdx);

            c1.addActionListener(AL);
            c2.addActionListener(AL);

            // Add them to the array
            cards.add(c1);
            cards.add(c2);

            imageIdx++;  // get ready for the next pair of cards
        }

        //Randomize the card positions
        Collections.shuffle(cards);


    }

    public void fillBoardView(JPanel view)
    {
        for (FlippableCard c : cards) {
            view.add(c);
        }
    }

    public void removeCards()
    {
        // Delete all cards
        for (int i = 0; i < cards.size(); i += 1) {
            cards.remove(i);
        }

    }
}
