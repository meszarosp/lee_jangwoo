
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
    private Set<SettlerView> settlerViews;

    /**
     * 
     */
    private Set<TravellerView> travellerViews;

    /**
     * 
     */
    private InventoryView inventory;

    /**
     * 
     */
    private AsteroidView asteroidViews;

    /**
     * 
     */
    private TeleportView teleportViews;

    /**
     * @param g
     */
    public void draw(Graphics g) {
        // TODO implement here
    }

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
    public void click(void int x, void int y) {
        // TODO implement here
    }

    /**
     * @param g
     */
    public void draw(Graphics g) {
        // TODO implement here
    }

}