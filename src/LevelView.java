
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
        HashMap<String, Color> colors = new HashMap<>();
        colors.put("uranium(0)", uranium0Color);
        colors.put("uranium(1)", uranium1Color);
        colors.put("uranium(2)", uranium2Color);
        colors.put("iron", ironColor);
        colors.put("ice", iceColor);
        colors.put("coal", coalColor);
        if (m != null) {
            return colors.getOrDefault(m.toString(), new Color(27, 20, 100));
        }
        return new Color(27,20,100);
    }

    /**
    * A háttéren elhelyezett nap betöltése
    */
    ImageIcon img = new ImageIcon("sun.png"); //nap betoltese
    
    /**
     * 
     */
    public static Color uranium0Color = new Color(15, 147, 71);

    /**
     * 
     */
    public static Color uranium1Color = new Color(16, 105, 55);

    /**
     * 
     */
    public static Color uranium2Color = new Color(2, 73, 34);

    /**
     * 
     */
    public static Color iceColor = new Color(195, 255, 255);

    /**
     * 
     */
    public static Color coalColor = new Color(179, 179, 179);

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

    LevelView(Game game){
        this.game = game;
        //init();
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void init(){
        Random random = new Random();
        List<Asteroid> asteroids = game.getSun().getAsteroids();
        for(Asteroid a : asteroids){
            asteroidViews.put(a, new AsteroidView(a, 20,20));   //próbaértékek, megváltoztatandó
        }
        List<Teleport> teleports = game.getGates();
        if(teleports.size() != 0){
            for(Teleport t : teleports){
                Color color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
                for (TeleportView tv : teleportViews.values())
                    if (tv.isPair(t)){
                        color = tv.getColor();
                        break;
                    }
                teleportViews.put(t, new TeleportView(t, color, 20, 20));   //próbaértékek, megváltoztatandó
            }
        }
        List<Settler> settlers= game.getSettlers();
        List<Robot> robots= game.getRobots();
        List<UFO> UFOs= game.getUFOs();
        for(int i = 0; i < settlers.size(); i++){
            SettlerView sv = new SettlerView(settlers.get(i), this);
            settlerViews.add(sv);
            travellerViews.add(sv);
        }
        for(int i = 0; i < robots.size(); i++){
            travellerViews.add(new RobotView(robots.get(i), this));
        }
        for(int i = 0; i < robots.size(); i++){
            travellerViews.add(new UFOView(UFOs.get(i), this));
        }
    }

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

    public void setInventory(InventoryView inventory) {
        this.inventory = inventory;
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
        inventory.Update();
    }

    public InventoryView getInventory() {
        return inventory;
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
        //TODO: mi legyen a koordin�t�kkal?
        if(t.getNeighbour() == null){
            teleportViews.put(t, new TeleportView(t, color, -1, -1));
            return;
        }
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
            travellerViews.add(new RobotView(r, this));
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
        Sun sun = game.getSun();
        List<Asteroid> asteroids = sun.getAsteroids();
        HashMap<Asteroid, AsteroidView> remaining = new HashMap<Asteroid, AsteroidView>();
        for (Asteroid a : asteroids)
            remaining.put(a, asteroidViews.get(a));
        asteroidViews = remaining;
    }

    public void addAsteroidView(Asteroid a, int x, int y){
        asteroidViews.put(a, new AsteroidView(a, x, y));
    }

    public void addTeleportView(Teleport t, Color c, int x, int y){
        teleportViews.put(t, new TeleportView(t,c, x, y));
    }

    public void addSettlerView(Settler s){
        SettlerView sv = new SettlerView(s, this);
        settlerViews.add(sv);
        travellerViews.add(sv);
    }

    public void addUFOView(UFO ufo){
        travellerViews.add(new UFOView(ufo, this));
    }

    /**
     * @param  x
     * @param  y
     */
    public INeighbour click(int x, int y) {
        for(TeleportView tv : teleportViews.values())
            if(tv.clicked(x, y))
                return tv.getTeleport();
        for (AsteroidView av : asteroidViews.values())
            if (av.clicked(x, y))
                return av.getAsteroid();
        return null;
    }

    /**
     * @param g
     */
    public void draw(Graphics g) {
        g.setColor(new Color(27, 20, 100)); //hatterszin beallitasa
    	g.fillRect(0, 0, getWidth(), getHeight()); // hatterszin beallitasa
    	img.paintIcon(this, g, getWidth() - img.getIconWidth(), 0); //nap berajzolasa
        drawNeighbourLines(g);
        for (AsteroidView av : asteroidViews.values())
            av.draw(g);
        for (TeleportView teleportv : teleportViews.values())
            teleportv.draw(g);
        for (TravellerView travellerv : travellerViews)
            travellerv.draw(g);
        //TODO: k�rd�ses
        //inventory.draw(g);
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public InventoryView getInventoryView(){
        return inventory;
    }
}
