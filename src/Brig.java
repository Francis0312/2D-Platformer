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
 */


public class Brig {
    
    // Constants

    
    private int x;
    private int y;
    private int width;
    private int height;
    private double xSpeed;
    private double ySpeed;
    private Rectangle hitBox;

    // Constructs an enemy Brig object
    public Brig(int x, int y, JPanel panel) {
        this.x = x;
        this.y = y;
        
    }


}