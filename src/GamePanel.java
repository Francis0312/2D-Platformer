import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

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
    public int cameraX;
    private int offset;

    // Constructs a new instance of the game, by initializing the primary JPanel.
    public GamePanel() {
        player = new Player(0, 300, this);
        reset();
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                
                // If the farthest wall to the x, then it's about to be on screen (screen is 700)
                if(walls.get(walls.size() - 1).x < 800) {
                    offset += 700;
                    makeWalls(offset);
                    System.out.println(walls.size());
                }
                player.set();
                for(Wall wall: walls) {
                    wall.set(cameraX);
                }
                //removeWalls(walls);

                repaint();
            }
        }, delay, fps);

        livesLine = new JLabel("Lives: " + player.getLives());
        add(livesLine);
        livesLine.setBounds(20, 20, 80, 40);
        
    }


    /**
     * 
     * @param walls
     */
    public static void removeWalls(ArrayList<Wall> walls) {
        for(int i = 0; i < walls.size(); i++) {
            if(walls.get(i).x < -800) {
                walls.remove(i);
            }
        }
    }


    // Resets everything when the player dies.
    public void reset() {
        player.setX((int)(700 / 2 - player.getHitbox().getWidth()));
        player.setY(150);
        cameraX = 150;
        player.setXSpeed(0);
        player.setYSpeed(0);
        walls.clear();
        offset = -150;
        makeWalls(offset);
    }


    /**
     * Creates wall objects on the map
     */
    public void makeWalls(int offset) {
        int length = 50;
        Random rand = new Random();
        int index = rand.nextInt(1);

        switch(index) { 
            case 0:
                for(int i = 0; i < 14; i++) {
                    walls.add(new Wall(offset + i * 50, 600, length, length));
                }
                break;

        }
    }


    // Returns the ArrayList of walls
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