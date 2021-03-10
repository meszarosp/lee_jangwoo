
import java.util.*;

/**
 * 
 */
public class Asteroid implements INeighbour {

    /**
     * Default constructor
     */
    public Asteroid() {
    }

    /**
     * 
     */
    private int shell;

    /**
     * 
     */
    private boolean closeToSun;

    /**
     * 
     */
    private List<INeighbour> neighbours;

    /**
     * 
     */
    private Mineral core;

    /**
     * 
     */
    private List<Traveller> travellers;

    private Sun sun;

    /**
     * 
     */
    public void onDrill() {
        // TODO implement here
    }

    /**
     * @return
     */
    public Mineral onMine() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void solarWind() {
        // TODO implement here
    }

    /**
     * 
     */
    public void radioactiveBlast() {
        // TODO implement here
    }

    /**
     * 
     */
    public void setCloseToSun() {
        // TODO implement here
    }

    /**
     * @param m 
     * @return
     */
    public boolean putMineralBack(Mineral m) {
        // TODO implement here
        return false;
    }

    /**
     * 
     */
    public void removeMineral() {
        // TODO implement here
    }

    /**
     * @param i
     */
    public void getNeighbourAt(int i) {
        // TODO implement here
    }

    /**
     * @param traveller
     */
    public void removeTraveller(Traveller traveller) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Mineral getCore() {
        // TODO implement here
        return null;
    }

    /**
     * @param traveller
     */
    public void placeTraveller(Traveller traveller){

    }

    /**
     * @param neighbour
     */
    public void removeNeighbour(INeighbour neighbour) {

    }

    /**
     * @param neighbour
     */
    public void addNeighbour(INeighbour neighbour){

    }

}