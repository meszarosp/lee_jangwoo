
import java.util.*;

/**
 * 
 */
public class RobotView extends TravellerView {

    /**
     * Default constructor
     */
    public RobotView() {
    }

    /**
     * 
     */
    private Robot robot;

    /**
     * @param g
     */
    public void draw(Graphics g) {
        // TODO implement here
    }

    /**
     * 
     */
    public void Update() {
        // TODO implement here
    }

    /**
     * @param t 
     * @return
     */
    public boolean identify(Traveller t) {
        // TODO implement here
        return false;
    }

    /**
     * @param g
     */
    public abstract void draw(Graphics g);

    /**
     * 
     */
    public abstract void Update();

    /**
     * @param t 
     * @return
     */
    public abstract boolean identify(Traveller t);

}