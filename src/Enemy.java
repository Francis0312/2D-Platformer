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
import javax.swing.JPanel;

/**
 * 
 */

public class Enemy {

    // Constants
    private JPanel panel;
    private Color ENEMY_COLOR = Color.RED;
    private String ENEMY_NAME = "ENEMY";

    // Instance Variables
    private int x;
    private int y;
    private int width;
    private int height;
    private double xSpeed;
    private double ySpeed;
    private Rectangle hitBox;
    
    // Constructs an enemy Brig object
    public Enemy(int x, int y, JPanel panel) {
        this.x = x;
        this.y = y;
        this.panel = panel;
        width = 50; 
        height = 100;
        hitBox = new Rectangle(width, height);
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
     * Updates the JPanel's graphics on every frame.
     * @param gtd The Graphics2D Object that draws onto the GUI.
     */
    public void draw(Graphics2D gtd) {
        gtd.setColor(ENEMY_COLOR);
        // Hitbox
        gtd.drawRect(x, y, width, height);
        // Name
        gtd.setFont(new Font("Retro Gaming", Font.PLAIN, 18));
        gtd.drawString(ENEMY_NAME, x + 15, y);
    }


    /**
     * 
     */
    public void set() {
        x += xSpeed;
    }

}