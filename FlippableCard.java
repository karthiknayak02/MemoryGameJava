import javax.swing.*;

public class FlippableCard extends JButton
{
    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();

    // Card front icon
    private Icon front;
    // Card back image
    private Icon back = new ImageIcon(loader.getResource("res/Back.jpg"));
    private boolean status;


    // ID + Name
    private int id;
    private String customName;

    // Default constructor
    public FlippableCard() { super(); }

    // Constructor with card front initialization
    public FlippableCard(ImageIcon frontImage)
    {
        super();
        front = frontImage;
        super.setIcon(back);
    }

    // Set the image used as the front of the card
    public void setFrontImage(ImageIcon frontImage) { front = frontImage; }

    // Card flipping functions
    public void showFront() { /* To-Do: Show the card front */ super.setIcon(front); }
    public void hideFront() { /* To-Do: Show the card back  */ super.setIcon(back); }

    // Metadata: ID number
    public int id() { return id; }
    public void setID(int i) { id = i; }

//    // Metadata: Custom name -----    REDUNDANT
//    public String customName() { return customName; }
//    public void setCustomName(String s) { customName = s; }
}
