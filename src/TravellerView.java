
import java.util.*;

/**
 * 
 */
public abstract class TravellerView implements View {

    /**
     * Default constructor
     */
    public TravellerView() {
    }

    /**
     * 
     */
    protected int x;

    /**
     * 
     */
    protected int y;

    /**
     * 
     */
    protected LevelView levelView;

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

    /**
     * @param g
     */
    public void draw(Graphics g) {
        // TODO implement here
    }

}