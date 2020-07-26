import java.awt.Rectangle;

import jdk.internal.jline.internal.TestAccessible;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 * 
 */

public class Wall {
    
    public int x; 
    public int y;
    public int width;
    public int height;
    public Rectangle hitBox;
    private Color wallColor;

    public Wall(int x, int y, int width, int height, Color wallColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        hitBox = new Rectangle(x, y, width, height);
        this.wallColor = wallColor;
    }
    

    public void draw(Graphics2D gtd) {

    }
}