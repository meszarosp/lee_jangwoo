
import java.awt.*;
import java.util.*;

/**
 * 
 */
public class TeleportView implements View {

    /**
     * Default constructor
     */
    public TeleportView() {
    }

    /**
     * 
     */
    private int x;

    /**
     * 
     */
    private int y;

    /**
     * 
     */
    private Color teleportColor;

    /**
     * 
     */
    private Teleport teleport;

    /**
     * @param g
     */
    public void draw(Graphics g) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Teleport getTeleport() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public int getX() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public int getY() {
        // TODO implement here
        return 0;
    }

    /**
     * @param a 
     * @return
     */
    public boolean isThisYourNeighbour(Asteroid a) {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public Color getColor() {
        // TODO implement here
        return null;
    }

    /**
     * @param int x 
     * @param int y 
     * @return
     */
    public boolean clicked(int x, int y) {
        // TODO implement here
        return false;
    }

    /**
     * @param t 
     * @return
     */
    public boolean isPair(Teleport t) {
        // TODO implement here
        return false;
    }

}