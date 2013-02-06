import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import view.Canvas;


/**
 * Creates window that can be moved, resized, and closed by the user.
 * @author Robert C. Duvall
 */
public final class Main {
    /**
     * The Dimension of the Canvas
     */
    public static final Dimension SIZE = new Dimension(800, 600);
    /**
     * The Title of the Frame
     */
    public static final String TITLE = "Springies!";


    private Main() {
        // does not make sense to construct this class
    }

    /**
     * Main --- where the program starts
     * @param args the arguments used for main
     */
    public static void main(String[] args) {
        // view of user's content
        Canvas display = new Canvas(SIZE);
        // container that will work with user's OS
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add our user interface components
        frame.getContentPane().add(display, BorderLayout.CENTER);
        // display them
        frame.pack();
        frame.setVisible(true);
        // start animation
        display.start();
    }
}
