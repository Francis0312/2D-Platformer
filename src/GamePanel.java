import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 * 
 * @author Francisco Reyna
 * University of Texas at Austin - Computer Science
 */

public class GamePanel extends JPanel implements ActionListener {
    
    private Player player;
    private Timer gameTimer;
    private int delay = 0;
    //1000 ms / 17 ms = ~60 fps
    private int fps = 17;
    private ArrayList<Wall> walls = new ArrayList<Wall>();
    private JLabel livesLine;

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
        livesLine = new JLabel("Lives: " + player.getLives());
        add(livesLine);
        livesLine.setBounds(20, 20, 80, 40);

        makeWalls(50);
    }


    public void reset() {
        player.setX(200);
        player.setY(150);
        player.setXSpeed(0);
        player.setYSpeed(0);
        walls.clear();
        int offset = 50;
        makeWalls(offset);
    }


    /**
     * Creates wall objects on the map
     */
    public void makeWalls(int offset) {
        for(int i = 50; i < 650; i+=50) {
            walls.add(new Wall(i, 600, 50, 50));
        }
        walls.add(new Wall(50, 550, 50, 50));
        walls.add(new LavaWall(200, 550, 50, 50));
    }


    //
    public ArrayList<Wall> getWallsArray() {
        return walls;
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


    // Required method for ActionEvent's implementation
    public void actionPerformed(ActionEvent e) {}
    
}