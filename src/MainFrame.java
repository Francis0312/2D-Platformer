import javax.swing.JFrame;
import java.awt.Color;

/**
 * 
 * @author Francisco Reyna
 * University of Texas at Austin - Computer Science
 */

public class MainFrame extends JFrame {

    // Constants
    private static Color BACKGROUND_COLOR = new Color(135, 206, 235);
   
    // Constructs the main JFrame of the project
    public MainFrame() {
        GamePanel panel = new GamePanel();
        panel.setLocation(0,0);
        panel.setSize(this.getSize());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setVisible(true);
        this.add(panel);
        addKeyListener(new KeyChecker(panel));
    }
    
}