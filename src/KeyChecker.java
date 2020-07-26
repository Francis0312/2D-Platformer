import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyChecker extends KeyAdapter {

    GamePanel panel;

    // Used for key input
    public KeyChecker(GamePanel panel) {
        this.panel = panel;
    }

    // Notifies when a key is pressed
    @Override
    public void keyPressed(KeyEvent e) {
        panel.keyPressed(e);
    }

    // Notifies when a key is released
    @Override
    public void keyReleased(KeyEvent e) {
        panel.keyReleased(e);
    }
}