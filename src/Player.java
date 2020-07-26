import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 * @author Francisco Reyna
 * University of Texas at Austin - Computer Science
 */

public class Player {

    // Constants
    private static double SPEED_MULTIPLIER = 0.8;
    private static double PLAYER_ACCELERATION = 1.0;
    private static double MAX_SPEED = 7.0;
    private static double JUMP_ACCEL = -6;
    private static double GRAVITY = 0.3;

    // Instance Variables
    private GamePanel panel;
    private Color playerColor = new Color(191, 87, 0);;

    // Used for the player's movement and position.
    private int x;
    private int y;
    private int width;
    private int height;
    private double xSpeed;
    private double ySpeed;
    private Rectangle hitBox;

    // Keys used for KeyEvent. Whether or not the key is pressed down at the moment.
    public boolean keyLeft;
    public boolean keyRight;
    public boolean keyUp;
    public boolean keyDown;
    
    // Constructs a player. Only one is allowed though.
    public Player(int x, int y, GamePanel panel) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        width = 50;
        height = 100;
        hitBox = new Rectangle(x, y, width, height);
    }


    /**
     * Updates the player's position
     */
    public void set() {
        // Moving left and right
        if(keyLeft && keyRight || !keyLeft && !keyRight) {
            xSpeed *= SPEED_MULTIPLIER;
        } else if(keyLeft && !keyRight) {
            xSpeed = xSpeed - PLAYER_ACCELERATION;
        } else if(keyRight && !keyLeft) {
            xSpeed = xSpeed + PLAYER_ACCELERATION;
        }

        // If the player moves sufficiently slowly, make him stand still instead. This is to prevent 
        // drifting/sliding.
        if(xSpeed > 0 && xSpeed < 0.75) {
            xSpeed = 0;
        }
        if(xSpeed < 0 && xSpeed > -0.75) {
            xSpeed = 0;
        }

        // Setting a maximum speed for the player
        if(xSpeed > MAX_SPEED) {
            xSpeed = MAX_SPEED;
        }
        if(xSpeed < -7) {
            xSpeed = -1 * MAX_SPEED;
        }

        // Jumping, only allowed to jump when you are right above the ground.
        if(keyUp) {
            hitBox.y++;
            for(Wall wall: panel.walls) {
                if(wall.getHitBox().intersects(hitBox)) {
                    ySpeed = JUMP_ACCEL;
                }
            }
            hitBox.y--;
        }
        // Gravity
        ySpeed += GRAVITY;

        // Horizontal & Vertical collisions
        hitBox.x += xSpeed;
        for(Wall wall: panel.walls) {
            if(hitBox.intersects(wall.getHitBox())) {
                hitBox.x -= xSpeed;
                // While there is not a collision, then we can move until we are touching the wall
                while(!wall.getHitBox().intersects(hitBox)) {
                    hitBox.x += Math.signum(xSpeed);
                }
                hitBox.x -= Math.signum(xSpeed);
                xSpeed = 0;
                x = hitBox.x;
            }
        }

        // Vertical collisions
        hitBox.y += ySpeed;
        for(Wall wall: panel.walls) {
            if(hitBox.intersects(wall.getHitBox())) {
                hitBox.y -= ySpeed;
                // While there is not a collision, then we can move until we are touching the wall
                while(!wall.getHitBox().intersects(hitBox)) {
                    hitBox.y += Math.signum(ySpeed);
                }
                hitBox.y -= Math.signum(ySpeed);
                ySpeed = 0;
                y = hitBox.y;
            }
        }

        // Move the player's position.
        x += xSpeed;
        y += ySpeed; 

        // We move the hitBox along with the Player.
        hitBox.x = x;
        hitBox.y = y;
    }


    /**
     * Updates the JPanel's graphics on every frame.
     * @param gtd The Graphics2D Object that draws onto the GUI.
     */
    public void draw(Graphics2D gtd) {
        gtd.setColor(playerColor);
        gtd.fillRect(x, y, width, height);
        
    }
}