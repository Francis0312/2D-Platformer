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


/**
 * 
 * @author Francisco Reyna,
 * University of Texas at Austin - Computer Science
 */

public class GamePanel extends JPanel implements ActionListener {
    
    private Player player;
    private Timer gameTimer;
    private int delay = 0;
    //1000 ms / 17 ms = ~60 fps
    private int fpsDividend = 17;
    private ArrayList<Wall> walls = new ArrayList<Wall>();
    public int cameraX;
    private int offset;
    private int numResets = -1;

    // Constructs a new instance of the game, by initializing the primary JPanel.
    public GamePanel() {
        player = new Player(0, 300, this);
        reset();
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                if(numResets > player.getStartLives() - 1);
                // If the farthest wall to the x, then it's about to be on screen (screen is 700)
                if(walls.get(walls.size() - 1).x < 800) {
                    offset += 700;
                    makeWalls(offset);
                }
                player.set();
                for(Wall wall: walls) {
                    wall.set(cameraX);
                }
                //removeWalls(walls);
                repaint();
            }
        }, delay, fpsDividend);
    }


    /**
     * Removes all the walls from the game.
     * @param walls The ArrayList of walls.
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
        numResets++;
        makeWalls(offset);
    }


    /**
     * Creates wall objects on the map
     */
    public void makeWalls(int offset) {
        Random rand = new Random();
        int length = 50;
        int index = rand.nextInt(7);
        int xOffset = 0;
        int wallLimit = 6;
        int gap = 150;

        switch(index) {
            // Flat ground
            case 0:
                for(int i = 0; i < 14; i++) {
                    walls.add(new Wall(offset + i * 50, 600, length, length));
                }
                break;
            // Staircase jump
            case 1:   
                for(int height = 600; height > 400; height -= 50) {
                    for(int boxIndex = 0; boxIndex < wallLimit; boxIndex++) {
                        walls.add(new Wall(offset + xOffset + boxIndex * 50, height, length, length));     
                    }
                    xOffset += length;
                    wallLimit--;
                }
                xOffset = 300;
                xOffset += gap;
                wallLimit = 6;
                for(int height = 600; height > 400; height -= 50) {
                    for(int boxIndex = 0; boxIndex < wallLimit; boxIndex++) {
                        walls.add(new Wall(offset + xOffset + boxIndex * 50, height, length, length));     
                    }
                    wallLimit--;
                }
                break;
            // Long slightly elevated platform
            case 2:
                xOffset = 100;
                for(int i = 0; i < 11; i++) {
                    walls.add(new Wall(xOffset + offset + i * 50, 550, length, length));
                }
                break;
            // 3 platforms: _ - _ 
            case 3:
                xOffset = 100;
                makeLineWalls(walls, 3, 550, length, xOffset, offset);
                xOffset += 100;
                xOffset += 150;
                makeLineWalls(walls, 3, 450, length, xOffset, offset);
                xOffset += 250;
                makeLineWalls(walls, 3, 550, length, xOffset, offset);
                break;
            // Floating individual blocks
            case 4: 
                xOffset = 100;
                walls.add(new Wall(xOffset + offset, 500, length, length));
                xOffset += 150;
                walls.add(new Wall(xOffset + offset, 400, length, length));
                xOffset += 150;
                walls.add(new Wall(xOffset + offset, 300, length, length));
                xOffset += 200;
                walls.add(new Wall(xOffset + offset, 350, length, length));
                break;
            // Tunnel
            case 5:
                xOffset = 100;
                makeLineWalls(walls, 12, 600, length, xOffset, offset);
                xOffset = 150;
                makeLineWalls(walls, 10, 450, length, xOffset, offset);
                xOffset = 200;
                makeLineWalls(walls, 8, 300, length, xOffset, offset);
                xOffset = 250;
                makeLineWalls(walls, 6, 150, length, xOffset, offset);
                break;
            case 6:
            // Yes No Yes No Yes No.. pattern of individual blocks close to the ground
                xOffset = 100;
                boolean nextSpace = false;
                for(int i = 0; i < 12; i++) {
                    if(!nextSpace) {
                        walls.add(new Wall(xOffset + offset, 550, length, length));   
                        nextSpace = !nextSpace; 
                    } else {
                        nextSpace = !nextSpace; 
                    }
                    xOffset += length;
                }
                break;
                
        }
    }


    /**
     * Creates a line of walls.
     * @param walls The Main ArrayList of walls.
     * @param num The amount of walls to be created.
     * @param height The height at which the walls should be produced.
     * @param length The length of said walls (Square) .
     * @param xOffset The distance the walls should begin offset from the left side.
     * @param offset An offset required for rendering.
     */
    private static void makeLineWalls(ArrayList<Wall> walls, int num, int height, int length, 
        int xOffset, int offset) {
        for(int i = 0; i < num; i++) {
            walls.add(new Wall(offset + xOffset + i * 50, height, length, length));
        }
    }


    // Returns the ArrayList of walls
    public ArrayList<Wall> getWallsArray() {
        return walls;
    }


    /**
     * Repaints the Panel every frame of the game.
     * @param Graphics object used to draw.
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


    // Returns the number of times the JPanel has reset. Used for lives.
    public int getNumResets() {
        return numResets;
    }
}