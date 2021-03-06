import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.ArrayList;


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
    private static double JUMP_ACCEL = -8;
    private static double GRAVITY = 0.3;
    private static int DEATH_LINE = 800;
    private static int START_LIVES = 3;
    private Color playerColor = new Color(191, 87, 0);

    // Instance Variables
    private GamePanel panel;
    private int lives;

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

    // Character Graphics 
    private BufferedImage genjiRight;
    private BufferedImage genjiLeft;
    private boolean isAcceleratingRight;

    // Heart Images
    private BufferedImage lifeHeart;
    
    // Constructs a player. Only one is allowed though.
    public Player(int x, int y, GamePanel panel) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        lives = START_LIVES;

        width = 50;
        height = 100;
        hitBox = new Rectangle(x, y, width, height);

        // Creating our custom retro gaming font
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, 
            new File("Retro Gaming.ttf")).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException e) {
            System.out.println("ERROR: Could not load Font File.");
            e.printStackTrace();
        } catch(FontFormatException e) {
            System.out.println("ERROR: Failed to create Font.");
            e.printStackTrace();
        }            

        // Creating an image for our player.
        double scale = .075;
        try {
            genjiRight = ImageIO.read(new File("genjiRight.png")); 
            genjiLeft = ImageIO.read(new File("genjiLeft.png"));
            lifeHeart = ImageIO.read(new File("pixel_heart.png"));
            genjiRight = scaleDown(genjiRight, scale);
            genjiLeft = scaleDown(genjiLeft,scale);
            lifeHeart = scaleDown(lifeHeart, .008);
            
        } catch (IOException e) {
            System.out.println("ERROR: Failed to load player images.");
        }

    }


    /**
     * Scales down the image by a factor of double scale
     * @param img The Image that will be resized
     * @param scale The scale that the image will be resized by
     * @return The newly resized image
     */
    public static BufferedImage scaleDown(BufferedImage img, double scale) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(img, after);
        return after;
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
            this.isAcceleratingRight = false;
        } else if(keyRight && !keyLeft) {
            xSpeed = xSpeed + PLAYER_ACCELERATION;
            this.isAcceleratingRight = true;
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
            for(Wall wall: panel.getWallsArray()) {
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
        for(Wall wall: panel.getWallsArray()) {
            if(hitBox.intersects(wall.getHitBox())) {
                hitBox.x -= xSpeed;
                // While there is not a collision, then we can move until we are touching the wall
                while(!wall.getHitBox().intersects(hitBox)) {
                    hitBox.x += Math.signum(xSpeed);
                }
                hitBox.x -= Math.signum(xSpeed);
                panel.cameraX += x - hitBox.x;
                xSpeed = 0;
                hitBox.x = x;
            }
        }

        // Vertical collisions
        hitBox.y += ySpeed;
        for(Wall wall: panel.getWallsArray()) {
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
        y += ySpeed; 

        // We move the hitBox along with the Player.
        hitBox.x = x;
        hitBox.y = y;

        // Death code
        if(y > DEATH_LINE) {
            panel.reset();
        }

        panel.cameraX -= xSpeed;
    }


    // Returns the amount of lives
    public int getLives() {
        return lives;
    }


    // Sets the amount of lives
    public void setLives(int lives) {
        this.lives = lives;
    }


    /**
     * Updates the JPanel's graphics on every frame.
     * @param gtd The Graphics2D Object that draws onto the GUI.
     */
    public void draw(Graphics2D gtd) {
        gtd.setColor(playerColor);
        // Uncomment this line to show the hitbox rectangle.
        // gtd.drawRect(x, y, width, height);

        // Draws out the Heart images for each life.
        for(int i = 0; i < lives; i++) {
            gtd.drawImage(lifeHeart, null, 30 + i * 30, 38);
        }

        // Displays genjiRight if the player is *accelerating* to the right
        if(this.isAcceleratingRight) {
            gtd.drawImage(genjiRight, null, x, y + 3);
        } else {
            // And genjiLeft if the player is accelerating to the left
            gtd.drawImage(genjiLeft, null, x - (int)hitBox.getWidth() + 10, y + 3);
        }
        
        // Prints out the text for the lives at the top left of the screen
        gtd.setColor(Color.BLACK);
        gtd.setFont(new Font("Retro Gaming", Font.PLAIN, 20));
        lives = START_LIVES - panel.getNumResets();
        gtd.drawString("LIVES: " + lives, 30, 30);
    }


    // --- GETTERS & SETTERS ---

     // Returns player's X
     public int getX() {
        return x;
    }


    // Returns player's Y
    public int getY() {
        return y;
    }


    // Sets player's X
    public void setX(int x) {
        this.x = x;
    }


    // Sets player's Y
    public void setY(int y) {
        this.y = y;
    }


    // Returns the X Speed
    public double getXSpeed() {
        return xSpeed;
    }

    
    // Returns the Y Speed
    public double getYSpeed() {
        return ySpeed;
    }


    // Sets the player's X speed
    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }


    // Sets the player's Y speed
    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }


    // Returns the player's hitbox object
    public Rectangle getHitbox() {
        return hitBox;
    }


    // Returns the number of starting lives
    public int getStartLives() {
        return START_LIVES;
    }

}