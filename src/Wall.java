import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 * @author Francisco Reyna
 * University of Texas at Austin - Computer Science
 */

public class Wall {
    
    // Instance Variables
    public int x; 
    public int y;
    public int width;
    public int height;
    private Rectangle hitBox;
    private Color fillColor;
    private Color outlineColor; 
    private boolean isEnemy;
    private int startX;

    // Wall that makes up the scenery
    public Wall(int x, int y, int width, int height) {
        this.x = x;
        startX = x;
        this.y = y;
        this.width = width;
        this.height = height;
        isEnemy = false;
        hitBox = new Rectangle(x, y, width, height);
        fillColor = Color.WHITE;
        outlineColor = Color.BLACK;
    }


    // Returns the wall's hitbox
    public Rectangle getHitBox() {
        return hitBox;
    }


    // Returns whether or not the wall is an enemy
    public boolean getIsEnemy() {
        return isEnemy;
    }
    

    // Sets whether or not the wall is an enemy
    public void setIsEnemy(boolean isEnemy) {
        this.isEnemy = isEnemy;
    }


    /**
     * Draws the wall Object onto the JPanel environment
     */
    public void draw(Graphics2D gtd) {
        gtd.setColor(outlineColor);
        gtd.drawRect(x, y, width, height);
        gtd.setColor(fillColor);
        //Offset to fit slightly inside of the previously drawn Rectangle
        gtd.fillRect(x + 1, y + 1, width - 2, height - 2);
    }


    public int set(int cameraX) {
        x = startX + cameraX;
        hitBox.x = x;

        return x;
    }
}