
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 
 */
public class LevelView extends JPanel implements View {

    /**
     * Default constructor
     */
    public LevelView() {
    }

    public static Color mineralColor(Mineral m) {
        switch (m.toString()) {
            // TODO szín switch
            case "uranium(0)":

        }
        return new Color(255,255,255);
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
    private ArrayList<SettlerView> settlerViews = new ArrayList<>();

    /**
     * 
     */
    private ArrayList<TravellerView> travellerViews = new ArrayList<>();

    /**
     * 
     */
    private InventoryView inventory;

    /**
     * 
     */
    private HashMap<Asteroid, AsteroidView> asteroidViews = new HashMap<>();

    /**
     * 
     */
    private HashMap<Teleport, TeleportView> teleportViews = new HashMap<>();


    /**
     * 
     */
    private void drawNeighbourLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.GRAY);
        for (AsteroidView av : asteroidViews.values()){
            Asteroid a = av.getAsteroid();
            int x1 = av.getX();
            int y1 = av.getY();
            for (AsteroidView av2 : asteroidViews.values())
                if (av2.isThisYourNeighbour(a)){
                    int x2 = av2.getX();
                    int y2 = av2.getY();
                    g2d.drawLine(x1, y1, x2, y2);
                }
            for (TeleportView tv : teleportViews.values())
                if (tv.isThisYourNeighbour(a)){
                    int x2 = tv.getX();
                    int y2 = tv.getY();
                    g2d.drawLine(x1, y1, x2, y2);
                }
        }
    }

    /**
     * @param t 
     * @return
     */
    public TeleportView getTeleportView(Teleport t) {
        return teleportViews.get(t);
    }

    /**
     * @param a 
     * @return
     */
    public AsteroidView getAsteroidView(Asteroid a) {
        return asteroidViews.get(a);
    }

    /**
     * 
     */
    public void Update() {
        updateAsteroidView();
        updateTeleportView();
        updateSettlerView();
        updateTravellerView();
        for (TravellerView tv : travellerViews)
            tv.Update();
    }

    /**
     * @param t
     */
    private void addTeleportView(Teleport t) {
        boolean found = false;
        Random random = new Random();
        Color color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
        for (TeleportView tv : teleportViews.values())
            if (tv.isPair(t)){
                found = true;
                color = tv.getColor();
                break;
            }
        //TODO: mi legyen a koordinátákkal?
        AsteroidView av = getAsteroidView(t.getNeighbour());
        teleportViews.put(t, new TeleportView(t, color, av.getX() +20, av.getY()+ 20));
    }

    /**
     * @return
     */
    public Settler getActiveSettler() {
        return activeSettler;
    }

    /**
     * @param s
     */
    public void setActiveSettler(Settler s) {
        for (SettlerView sv : settlerViews){
            if (sv.identify(activeSettler))
                sv.setActive(false);
            if (sv.identify(s))
                sv.setActive(true);
        }
        activeSettler = s;
        Update();
    }

    /**
     * 
     */
    private void updateTeleportView() {
        List<Teleport> gates = game.getGates();
        HashMap<Teleport, TeleportView> remainingViews = new HashMap<Teleport, TeleportView>();
        ArrayList<Teleport> noView = new ArrayList<Teleport>();
        for (Teleport t : gates)
            if (!teleportViews.containsKey(t))
                noView.add(t);
            else
                remainingViews.put(t, teleportViews.get(t));
        teleportViews = remainingViews;
        for (Teleport t : noView)
            addTeleportView(t);
    }

    /**
     * 
     */
    private void updateTravellerView() {
        List<Settler> settlers = game.getSettlers();
        List<UFO> UFOs = game.getUFOs();
        List<Robot> robots = game.getRobots();
        Set<Robot> notFoundRobot = new HashSet<Robot>(robots);
        ArrayList<TravellerView> remainingViews = new ArrayList<TravellerView>();
        for (TravellerView tv : travellerViews){
            boolean didIdentify = false;
            for (Settler s : settlers) {
                if (didIdentify) break;
                didIdentify = tv.identify(s);
            }
            for (UFO u : UFOs) {
                if (didIdentify) break;
                didIdentify = tv.identify(u);
            }
            for (Robot r : robots) {
                if (didIdentify) break;
                if(didIdentify = tv.identify(r))
                    notFoundRobot.remove(r);
            }
            if (didIdentify)
                remainingViews.add(tv);
        }
        travellerViews = remainingViews;
        for (Robot r : notFoundRobot)
            travellerViews.add(new RobotView(r));
    }

    /**
     * 
     */
    private void updateSettlerView() {
        List<Settler> settlers = game.getSettlers();
        ArrayList<SettlerView> remainingViews = new ArrayList<SettlerView>();
        for (SettlerView sv : settlerViews){
            boolean didIdentify = false;
            for (Settler s : settlers){
                if (didIdentify) break;
                didIdentify = sv.identify(s);
            }
            if (didIdentify)
                remainingViews.add(sv);
        }
        settlerViews = remainingViews;
    }

    /**
     * 
     */
    private void updateAsteroidView() {
        //Sun sun = game.getSun();

    }

    /**
     * @param  x
     * @param  y
     */
    public void click(int x, int y) {
        // TODO implement here
    }

    /**
     * @param g
     */
    public void draw(Graphics g) {
        drawNeighbourLines(g);
        for (AsteroidView av : asteroidViews.values())
            av.draw(g);
        for (TeleportView teleportv : teleportViews.values())
            teleportv.draw(g);
        for (TravellerView travellerv : travellerViews)
            travellerv.draw(g);
        //TODO: kérdéses
        //inventory.draw(g);
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
}