
import java.util.*;

/**
 * 
 */
public abstract class Traveller {

    /**
     * Default constructor
     */
    public Traveller() {
    }

    /**
     * 
     */
    protected Asteroid asteroid;

    /**
     * 
     */
    protected Game game;

    /**
     * @param number
     */
    public void move(int number) {
        // TODO implement here
    }

    /**
     * 
     */
    public void drill() {
        // TODO implement here
    }

    /**
     * 
     */
    public abstract void hitByBlast();

    /**
     * 
     */
    public abstract void die();

    /**
     * @param a
     */
    public void setAsteroid(Asteroid a) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Asteroid getAsteroid() {
        // TODO implement here
        return null;
    }

}