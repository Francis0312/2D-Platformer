import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 * @author Francisco Reyna
 * University of Texas at Austin - Computer Science
 */


public class Wall {
    
    public int x; 
    public int y;
    public int width;
    public int height;
    public Rectangle hitBox;
    private Color wallColor;

    public Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        hitBox = new Rectangle(x, y, width, height);
    }
    

    public void draw(Graphics2D gtd) {

    }
}