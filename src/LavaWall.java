import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics2D;

/**
 * 
 * @author Francisco Reyna
 * University of Texas at Austin - Computer Science
 */

public class LavaWall extends Wall{

    // Instance Variables
    private static Color LAVA_COLOR;
    
    // Wall block that kills the Player upon contact.
    public LavaWall(int x, int y, int width, int height) {
        super(x, y, width, height);
        LAVA_COLOR = Color.RED;
        this.setIsEnemy(true);
    }

    // Draws the Lava Wall
    @Override
    public void draw(Graphics2D gtd) {
        gtd.setColor(Color.BLACK);
        gtd.drawRect(x, y, width, height);
        gtd.setColor(LAVA_COLOR);
        //Offset to fit slightly inside of the previously drawn Rectangle
        gtd.fillRect(x + 1, y + 1, width - 2, height - 2);
    }
}