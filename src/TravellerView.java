
import java.awt.*;
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
    public void draw(Graphics g) {
    }

    /**
     * 
     */
    public  void Update() {
    }

    /**
     * @param t 
     * @return
     */
    public boolean identify(Traveller t){
        return false;
    }

}