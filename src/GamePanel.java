import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
    ArrayList<Wall> walls = new ArrayList<Wall>();

    // Constructs a new instance of the game, by initializing the primary JPanel.
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
        makeWalls();
    }


    /**
     * 
     */
    public void makeWalls() {
        for(int i = 50; i < 650; i+=50) {
            walls.add(new Wall(i, 600, 50, 50));
        }
        walls.add(new Wall(50, 550, 50, 50));
    }


    /**
     * Repaints the Panel every frame of the game.
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gtd = (Graphics2D) g;
        player.draw(gtd);
        //Print all the walls from the makeWalls method.
        for(Wall wall: walls) {
            wall.draw(gtd);
        }
    }

    
    /**
     * Detects the key inputs from the player.
     * @param e The character which is pressed by the player.
     */
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


    /**
     * Detects when the player releases a key.
     * @param e The character which is released by the player.
     */
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


    // 
    public void actionPerformed(ActionEvent e) {}
    
}