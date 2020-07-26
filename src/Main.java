import java.awt.Dimension;
import java.awt.Toolkit;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * 
 * @author Francisco Reyna
 * University of Texas at Austin - Computer Science
 */


public class Main {
    
    //Constants
    private static Dimension DIM = new Dimension(700, 700);
    private static String GAME_TITLE = "Platformer Game Prototype";

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setSize(DIM);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int)(screenSize.getWidth() / 2 - frame.getSize().getWidth() / 2), 
        (int)(screenSize.getHeight() / 2 - frame.getSize().getHeight() / 2));
        frame.setResizable(false);
        frame.setTitle(GAME_TITLE);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}