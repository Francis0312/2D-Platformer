import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * 
 * @author Francisco Reyna
 * University of Texas at Austin - Computer Science
 */


public class GamePanel extends JPanel implements ActionListener {
    
    public Player player;
    public Timer gameTimer;
    public int delay = 0;
    //1000 ms / 17 ms = ~60 fps
    public int fps = 17;

    public GamePanel() {
        player = new Player(400, 300, this);
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask(){
            
            @Override
            public void run() {
                player.set();
                repaint();
            }
        }, delay, fps);
    }


    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gtd = (Graphics2D) g;
        player.draw(gtd);
    }

    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'a') {
            player.keyLeft = true;
        }
        if(e.getKeyChar() == 'w') {
            player.keyUp = true;
        }
        if(e.getKeyChar() == 's') {
            player.keyDown = true;
        }
        if(e.getKeyChar() == 'd') {
            player.keyRight = true;
        }
    }


    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == 'a') {
            player.keyLeft = false;
        }
        if(e.getKeyChar() == 'w') {
            player.keyUp = false;
        }
        if(e.getKeyChar() == 's') {
            player.keyDown = false;
        }
        if(e.getKeyChar() == 'd') {
            player.keyRight = false;
        }
    }


    public void actionPerformed(ActionEvent e) {}
    
}