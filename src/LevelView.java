
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

    /**
     * Megmondja, hogy az adott nyersanyaghoz milyen szín tartozik.
     * @param m A nyersanyag
     * @return A szín, ami a nyersanyaghoz tartozik
     */

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
     * Az urán 0. állapotának színe.
     */
    public static Color uranium0Color = new Color(15, 147, 71);

    /**
     * Az urán 1. állapotának színe.
     */
    public static Color uranium1Color = new Color(16, 105, 55);

    /**
     * Az urán 2. állapotának színe.
     */
    public static Color uranium2Color = new Color(2, 73, 34);

    /**
     * A jég színe.
     */
    public static Color iceColor = new Color(195, 255, 255);

    /**
     * A szén színe.
     */
    public static Color coalColor = new Color(0, 0, 0);

    /**
     * A vas színe.
     */
    public static Color ironColor = new Color(179, 179, 179);

    /**
     * Az aktív telepes, az aki most lép.
     */
    private Settler activeSettler;

    /**
     * A játék, amelyik éppen zajlik.
     */
    private Game game;

    /**
     * A telepesek nézeteinek listája.
     */
    private ArrayList<SettlerView> settlerViews = new ArrayList<>();

    /**
     * Az utazók nézeteinek listája.
     */
    private ArrayList<TravellerView> travellerViews = new ArrayList<>();

    /**
     * A felületen lévõ inventoryview.
     */
    private InventoryView inventory;

    /**
     * Az aszteroidák és a hozzájuk tartozó nézetek összerendelése.
     */
    private HashMap<Asteroid, AsteroidView> asteroidViews = new HashMap<>();

    /**
     * Az teleportkapuk és a hozzájuk tartozó nézetek összerendelése.
     */
    private HashMap<Teleport, TeleportView> teleportViews = new HashMap<>();


    /**
     * A teleportkapuk és a hozzájuk tartozó teleportkapu színek összerendelése.
     */
    private HashMap<Teleport, Color> teleportcolors = new HashMap<>();

    /**
     * Konstruktor, a játékot kell megadni.
     * @param game A játék
     */
    LevelView(Game game){
        this.game = game;
    }

    /**
     * Setter a game attríbútumhoz
     * @param game Az új játék
     */
    public void setGame(Game game){
        this.game = game;
    }

    /*public void init(){
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
    }*/

    /**
     * Végigiterál az asteroidViews listán (a HashMap-bõl
     * elkéri a values-t), a jelenleg kiválasztott aszteroida nézetnek elkéri az aszteroidáját a
     * getAsteroid metódussal, és a koordinátáit a getterekkel. Az aszteroidát nevezzük
     * mondjuk currAsteroid-nak. Egy belsõ ciklusban végigiterál, a listában ezután
     * következõ aszteroida nézeteken, és meghívja rájuk az isThisYourNeighbour metódust
     * a currAsteroid-ot paraméterül adva. Ha igazzal tér vissza, akkor elkéri ezen
     * asteroidView koordinátáit getterekkel, és ezek közé vonalat húz. Ha ezzel a belsõ
     * ciklussal végzett, a teleportkapu nézeteken (teleportViews) iterál végig, ezekre
     * meghívja az isThisYourNeighbour metódust a currAsteroid-ot adva paraméterül, és ha
     * ez igazzal tér vissza, akkor elkéri a TeleportView koordinátáit getterekkel, és vonalat
     * rajzol közéjük. Ezen metódus végeztével az összes szomszédság vonal be van húzva.
     */
    private void drawNeighbourLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.GRAY);
        for (AsteroidView av : asteroidViews.values()){
            if (av == null) continue;
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
                if (tv != null && tv.isThisYourNeighbour(a)){
                    int x2 = tv.getX();
                    int y2 = tv.getY();
                    g2d.drawLine(x1, y1, x2, y2);
                }
        }
    }

    /**
     * Beállítja az inventoryview-t.
     * @param inventory Az inventory új értéke
     */
    public void setInventory(InventoryView inventory) {
        this.inventory = inventory;
    }

    /**
     * A paraméterként kapott teleportot
     * kulcsként használva elkéri az ehhez tartozó TeleportView-t a teleportViews
     * HashMapbõl, és ezt visszaadja.
     * @param t A teleport, amihez kell a nézet
     * @return A teleporthoz tartozó nézet
     */
    public TeleportView getTeleportView(Teleport t) {
        return teleportViews.get(t);
    }

    /**
     * A paraméterként kapott aszteroidát
     * kulcsként használva elkéri az ehhez tartozó AsteroidView-t az asteroidViews
     * HashMapbõl, és ezt visszaadja.
     * @param a Az aszteroida, amihez kell a nézet
     * @return Az aszteroidához tartozó nézet
     */
    public AsteroidView getAsteroidView(Asteroid a) {
        return asteroidViews.get(a);
    }

    /**
     * Hívja magára az updateAsteroidView, updateTeleportView,
     * updateSettlerView, updateTravellerView metódusokat. A travellerViews listában
     * meghívja mindenkire az Update metódust.
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

    /**
     * Getter az inventory attribútumhoz.
     * @return Az inventory.
     */
    public InventoryView getInventory() {
        return inventory;
    }

    /**
     * Végigiterál a teleportViews listán, és
     * mindegyikre meghívja az isPair metódust. Ha valamelyik igazzal tér vissza, elkéri tõle
     * a színét a getColor metódussal, és létrehoz egy TeleportView objektumot a
     * paraméterül kapott teleporttal, és a megkapott színnel. Ha nem talál ilyet, akkor egy
     * randomizált színt ad meg a TeleportView-nek. Ezután ezt beteszi a teleportViews
     * HashMap-jébe a paraméterül kapott teleportot használva kulcsként.
     * @param t A teleportkapu, amihez kell a nézet
     */
    private void addTeleportView(Teleport t) {
        boolean found = false;
        Random random = new Random();
        Color color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
        for (TeleportView tv : teleportViews.values())
            if (tv != null && tv.isPair(t)){
                found = true;
                color = tv.getColor();
                break;
            }
        /*if(t.getNeighbour() == null){
            teleportViews.put(t, new TeleportView(t, color, av.getX() +30, av.getY()+30));
            return;
        }*/
        if (teleportcolors.containsKey(t))
            color = teleportcolors.get(t);
        else if(t.getPair() != null && teleportcolors.containsKey(t.getPair()))
            color = teleportcolors.get(t.getPair());
        AsteroidView av = getAsteroidView(t.getNeighbour());
        TeleportView tv = new TeleportView(t, color, av.getX() +30, av.getY()+50);
        teleportViews.put(t, tv);
        teleportcolors.put(t, color);
        if (t.getPair() != null)
            teleportcolors.put(t.getPair(), color);
    }

    /**
     * Megadja, hogy a paraméterként adott teleportkapunak milyen színe van.
     * @param t A kérdéses teleportkapu
     * @return A teleportkapu színe
     */
    public Color getTeleportColor(Teleport t ){
        if (teleportcolors.containsKey(t))
            return teleportcolors.get(t);
        if (t.getPair() != null && teleportcolors.containsKey(t.getPair()))
            return teleportcolors.get(t.getPair());
        Random random = new Random();
        Color color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
        teleportcolors.put(t, color);
        if (t.getPair() != null)
            teleportcolors.put(t.getPair(), color);
        return color;
    }

    /**
     * Megmondja, hogy ki az aktív telepes.
     * @return Az aktív telepes.
     */
    public Settler getActiveSettler() {
        return activeSettler;
    }

    /**
     * Beállítja az aktív telepes értékét.
     * @param s Az új aktív telepes.
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
     * Hívja a game getGates metódusát. A megkapott
     * teleportkapukat kulcsként használva a teleportViews mapjén kigyûjti az élõ teleportok
     * nézetét, ezekbõl csinál egy új HashMap-et és értékül adja a teleportViews-nek. Ha
     * volt olyan teleport, melyet kulcsként használva nem volt találat, meghívja rá az
     * addTeleportView metódust. (A felrobbant teleportkapuk kikerülnek a game gates
     * listájából. Ezzel az update-el kiszûrjük a már felrobbant kapuk nézetét, hogy azokat
     * már ne ábrázoljuk)
     */
    private void updateTeleportView() {
        List<Teleport> gates = game.getGates();
        HashMap<Teleport, TeleportView> remainingViews = new HashMap<Teleport, TeleportView>();
        ArrayList<Teleport> noView = new ArrayList<Teleport>();
        for (Teleport t : gates) {
            if (!teleportViews.containsKey(t) && t.getNeighbour() != null)
                noView.add(t);
            else if (teleportViews.containsKey(t))
                remainingViews.put(t, teleportViews.get(t));
        }
        teleportViews = remainingViews;
        for (Teleport t : noView) {
            if (t != null)
                addTeleportView(t);
        }
    }

    /**
     * Hívja a game getSettlers, getUFOs és getRobots
     * metódusát. A travellerViews listán meghívja az összes elemre az identify metódust az
     * összes settler-, robot- és UFO-val paraméterül. Az egyszer is true-val visszatérõ
     * TravellerView objektumokból listát készítünk, és ezt kivesszük a travellerViews
     * listából (csak hogy gyorsabb legyen a bejárás). Ha volt olyan robot, melynél egy
     * identify metódus sem tért vissza true-val, akkor létrehoz egy RobotView objektumot a
     * robottal paraméterként, és ezt hozzáfûzi az új TravellerView típusú listához. Ha
     * végigért, akkor az újonnan készített TravellerView listát értékül adja a
     * travellerViews-nek. (Azaz ha létrejött új robot, azaz eddig nem volt rá nézet, akkor
     * csinálunk neki, és befûzzük a listánkba, a meghal UFO, Settler és Robotokat pedig
     * kiszûrjük azzal, hogy az õ View-jukat már nem tesszük bele az új listába. Az újonnan
     * létrehozott robot majd az Update metódus végén kap koordinátát.)
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
     * Hívja a game a getSettlers metódusát. Végigiterál a
     * settlerViews listán, és meghívja mindegyikre az identify metódust, minden kapott
     * Settlerrel. Ha a SettlerView objektum identify metódusa egyszer is true-val tér vissza,
     * akkor befûzzük egy új SettlerView listába. A végül elkészült új SettlerView listát
     * értékül adjuk a settlerViews attribútumnak. (A meghalt Settler-ek kikerülnek a game
     * settlers listájából. Ezzel az update-el kiszûrjük a már meghalt Settlerek nézetét, hogy
     * azokat már ne ábrázoljuk)
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
     * A game-re meghívjuk a getSun metódust, majd a
     * kapott Sun-ra a getAsteroids metódust. A kapott aszteroidákból, és nézeteikbõl új
     * HashMap-et készítünk (az asteroidViews segítségével), majd ha végeztünk, a kapott
     * HashMap-et értékül adjuk az asteroidViews attribútumnak.
     */
    private void updateAsteroidView() {
        Sun sun = game.getSun();
        List<Asteroid> asteroids = sun.getAsteroids();
        HashMap<Asteroid, AsteroidView> remaining = new HashMap<Asteroid, AsteroidView>();
        for (Asteroid a : asteroids)
            remaining.put(a, asteroidViews.get(a));
        asteroidViews = remaining;
    }

    /**
     * Hozzáad egy új asteroidview-t a megadott aszteroidához, a megadott koordinátákkal.
     * @param a Az aszteroida
     * @param x Az x koordináta
     * @param y Az y koordináta
     */
    public void addAsteroidView(Asteroid a, int x, int y){
        asteroidViews.put(a, new AsteroidView(a, x, y));
    }


    /**
     * Hozzáad egy új teleportview-t a megadott teleportkapuhoz, a megadott koordinátákkal.
     * @param t A teleportkapu
     * @param x Az x koordináta
     * @param y Az y koordináta
     */
    public void addTeleportView(Teleport t, Color c, int x, int y){
        TeleportView tv = new TeleportView(t,c, x, y);
        teleportViews.put(t, tv);
        teleportcolors.put(t, c);
        if (t.getPair() != null)
            teleportcolors.put(t.getPair(), c);
    }

    /**
     * Készít egy settlerview-t a megadott telepeshez.
     * @param s A telepes, amihez kell a nézet.
     */
    public void addSettlerView(Settler s){
        SettlerView sv = new SettlerView(s, this);
        settlerViews.add(sv);
        travellerViews.add(sv);
    }

    /**
     * Készít egy ufoview-t a megadott telepeshez.
     * @param ufo Az ufo, amihez kell a nézet.
     */
    public void addUFOView(UFO ufo){
        travellerViews.add(new UFOView(ufo, this));
    }

    /**
     * Végigkérdezi a teleportview-kat és az asteroidview-kat, hogy
     * melyikre történt a kattintás. Visszatér azzal a
     * teleportkapuval/aszteroidával, amelyik nézetére kattintottak.
     * Ha nem történt egyikre sem kattintás, akkor null-nal tér vissza.
     * @param  x A kattintás x koordinátáka.
     * @param  y A kattintás y koordinátája.
     */
    public INeighbour click(int x, int y) {
        for(TeleportView tv : teleportViews.values())
            if(tv != null && tv.clicked(x, y))

                    return tv.getTeleport();
        for (AsteroidView av : asteroidViews.values())
            if (av != null && av.clicked(x, y))
                return av.getAsteroid();
        return null;
    }

    /**
     * Kirajzolja a nézeteket.
     * @param g  A Graphics objektum, amire a rajzolás történik.
     */
    public void draw(Graphics g) {
        g.setColor(new Color(27, 20, 100)); //hatterszin beallitasa
    	g.fillRect(0, 0, getWidth(), getHeight()); // hatterszin beallitasa
    	img.paintIcon(this, g, getWidth() - img.getIconWidth(), 0); //nap berajzolasa
        drawNeighbourLines(g);
        for (AsteroidView av : asteroidViews.values())
            av.draw(g);
        for (TeleportView teleportv : teleportViews.values())
            if (teleportv != null)
                teleportv.draw(g);
        for (TravellerView travellerv : travellerViews)
            travellerv.draw(g);
    }

    /**
     * Õsbõl származó metódus, kirajzolja a komponenst
     * a paraméterként megadott Graphics objektum.
     * @param g A Graphics objektum, amire a rajzolás történik.
     */
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Getter az aszteroidák és nézeteinek összerendeléseihez.
     * @return Az aszteroidák és a nézetek hashmap-je.
     */
    public HashMap<Asteroid, AsteroidView> getAsteroidViews() {
        return asteroidViews;
    }

    /**
     * Getter az teleportkapuk és nézeteinek összerendeléseihez.
     * @return A teleportkapuk és a nézetek hashmap-je.
     */
    public HashMap<Teleport, TeleportView> getTeleportViews() {
        return teleportViews;
    }
}
