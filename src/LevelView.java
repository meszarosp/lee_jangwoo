
import java.awt.*;
import java.util.*;

/**
 * 
 */
public class LevelView implements View {

    /**
     * Default constructor
     */
    public LevelView() {
    }

    /**
     * 
     */
    public static Color uranium0Color;

    /**
     * 
     */
    public static Color uranium1Color;

    /**
     * 
     */
    public static Color uranium2Color;

    /**
     * 
     */
    public static Color iceColor;

    /**
     * 
     */
    public static Color coalColor;

    /**
     * 
     */
    public static Color ironColor;

    /**
     * 
     */
    private Settler activeSettler;

    /**
     * 
     */
    private Game game;

    /**
     * 
     */
    private ArrayList<SettlerView> settlerViews;

    /**
     * 
     */
    private ArrayList<TravellerView> travellerViews;

    /**
     * 
     */
    private InventoryView inventory;

    /**
     * 
     */
    private HashMap<Asteroid, AsteroidView> asteroidViews;

    /**
     * 
     */
    private HashMap<Teleport, TeleportView> teleportViews;


    /**
     * 
     */
    private void drawNeighbourLines() {
        // TODO implement here
    }

    /**
     * @param t 
     * @return
     */
    public TeleportView getTeleportView(Teleport t) {
        // TODO implement here
        return null;
    }

    /**
     * @param a 
     * @return
     */
    public AsteroidView getAsteroidView(Asteroid a) {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void Update() {
        // TODO implement here
    }

    /**
     * @param t
     */
    private void addTeleportView(Teleport t) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Settler getActiveSettler() {
        // TODO implement here
        return null;
    }

    /**
     * @param s
     */
    public void setActiveSettler(Settler s) {
        // TODO implement here
    }

    /**
     * 
     */
    private void updateTeleportView() {
        // TODO implement here
    }

    /**
     * 
     */
    private void updateTravellerView() {
        // TODO implement here
    }

    /**
     * 
     */
    private void updateSettlerView() {
        // TODO implement here
    }

    /**
     * 
     */
    private void updateAsteroidView() {
        // TODO implement here
    }

    /**
     * @param int x 
     * @param int y
     */
    public void click(int x, nt y) {
        // TODO implement here
    }

    /**
     * @param g
     */
    public void draw(Graphics g) {
        // TODO implement here
    }

}