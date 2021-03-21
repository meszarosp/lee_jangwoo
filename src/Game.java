
import java.util.*;

/**
 * 
 */
public class Game {

    /**
     * Default constructor
     */
    public Game() {
    }

    /**
     * 
     */
    private Sun sun;

    /**
     * 
     */
    private List<Settler> settlers = new ArrayList<Settler>();

    /**
     * 
     */
    private List<Robot> robots = new ArrayList<Robot>();

    public void addSettler(Settler s){
        settlers.add(s);
    }

    /**
     * @param r
     */
    public void removeRobot(Robot r) {
        // TODO implement here
    }

    /**
     * @param s
     */
    public void removeSettler(Settler s) {
        // TODO implement here
    }

    /**
     * @param n
     */
    public void init(int n) {
        // TODO implement here
    }

    /**
     * @param r
     */
    public void addRobot(Robot r) {
        // TODO implement here
    }

    /**
     * @return
     */
    public boolean checkWin() {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public boolean checkLose() {
        // TODO implement here
        return false;
    }

}