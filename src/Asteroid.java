import java.util.*;

/**
 * Az aszteroida felel a rajta lévő utazók nyilvántartásáért, értesítenie kell őket amennyiben az aszteroida 
 * felrobban, valamint, ha napszél éri el ezt az aszteroidát. Felelőssége nyilvántartani a kérgének vastagságát,
 * a magjában tartott nyersanyagot, valamint azt, hogy közel van-e a naphoz.
 * Felelőssége lehetővé tenni a telepesnek, hogy az üreges aszteroidába tegyen egy nyersanyagot. 
 * Ha megfúrják, csökkentenie kell a kérgének vastagságát, ha bányásszák, oda kell adnia a benne lévő nyersanyagot.
 * Nyilván kell tartania szomszédait (ezek lehetnek aszteroidák és teleportkapuk), és vissza is kell adnia
 * egy szomszédjának objektumát, ha az utazó mozogni szeretne. Ha napközelben van és felszínre kerül a nyersanyag,
 * akkor ezt jeleznie kell a nyersanyag felé.
 * Megvalósítja az INeighbour interfészt (ahogy a Teleport osztály is).
 */
public class Asteroid implements INeighbour {

	/**
     * Az aszteroida kérgének vastagságát jellemző szám.
     */
    private int shell;
    
    /**
     * Az aszteroida naphoz közeli állapotát tárolja el. Ha értéke igaz (true), 
     * akkor az aszteroida napközelben van, ha false, akkor naptávolban van.
     */
    private boolean closeToSun;
	
    /**
     * Az aszteroida szomszédainak listája, ezen keresztül tudja értesíteni őket felrobbanásakor.
     * Valamint létezése lehetővé teszi, hogy az utazó lekérje egy szomszédját.
     */
    private List<INeighbour> neighbours = new ArrayList<INeighbour>();

    /**
     * Az aszteroidán lévő utazók listája. Ezen keresztül értesíti őket az azteroida, ha napszél éri, vagy felrobban.
     */
    private List<Traveller> travellers = new ArrayList<Traveller>();
    
    /**
     * Mielőtt az aszteroida felrobban értesíti a napot, hogy fel fog robbanni.
     */
    private Sun sun;
    
    /**
     * Az aszteroida magjában lévő nyersanyag, ez lehet üres is, ekkor üreges az aszteroida.
     */
    private Mineral core;
    
    /**
     * Default constructor
     */
    public Asteroid() {}

    /**
     * Konstruktor, ami a paraméterben kapott értékek alapján inicializálja az aszteroida objektumot.
     * @param shell az aszteroida kéregvastagsága
     * @param closeToSun az aszteroida közel van-e a naphoz?
     * @param core az aszteroida magja
     * @param sun az aszteroidához tartozó Nap
     */
    public Asteroid(int shell, boolean closeToSun, Mineral core, Sun sun) {
        this.shell = shell;
        this.closeToSun = closeToSun;
        this.core = core;
        this.sun = sun;
    }

    /**
     * Az aszteroida "meg lett fúrva", ez csökkenti a shell-t, amennyiben az nem nulla.
     * Ha nulla lesz a shell, megnézi a closeToSun változót, amennyiben ez igaz, 
     * meghívja a core exposedToSun metódusát, magát adva paraméterként.
     */
    public boolean onDrill() {
        if (shell > 0) {
            shell--;
            if (shell == 0 && closeToSun)
                core.exposedToSun(this);
            return true;
        }else{
            return false;
        }

    }

    /**
     * Ha a shell 0, a nyersanyag, amely van, vagy nincs az aszteroida magjában "kibányászódik".
     * Ha volt nyersanyag, akkor visszaadja visszatérési értékként a core-t, amennyiben nem is volt,
     *  vagy a shell nem 0, akkor null-al tér vissza. Ha volt benne nyersanyag, akkor azt null-ra állítja.
     * @return
     */
    public Mineral onMine() {
        if (shell == 0) {
        	Mineral tmp = core;
        	core = null;
        	return tmp;
        }
        return null;
    }

    /**
     * Meghívja minden Traveller-re a travellers listában a hitByBlast metódusukat, 
     * ezután minden szomszédjára a removeNeighbour függvényt magával paraméterként. 
     * Ezután a sun-nak meghívja a removeAsteroid metódusát magával paraméterként.
     */
    public void radioactiveBlast() {
    	for (int i = 0; i < travellers.size(); i++)
    		travellers.get(i).hitByBlast();
    	for (int i = 0; i < neighbours.size(); i++)
    		neighbours.get(i).removeNeighbour(this);
    	sun.removeAsteroid(this);
    }

    /**
     * Negálja a closeToSun-t, és ha a closeToSun true és a shell 0 meghívja a core-ra (ha van)
     *  az exposedToSun metódusát önmagát adva paraméterként.
     */
    public void setCloseToSun() {
        closeToSun = !closeToSun;
        if (closeToSun && shell == 0 && core != null)
        	core.exposedToSun(this);
    }

    /**
     * Megpróbálja betenni a core-ba a paraméterül kapott Mineral-t. Ez akkor sikerül, ha a core üres,
     * és az aszteroidának ki van fúrva a kérge. Ha sikerül, akkor true-val tér vissza, egyébként false-al,
     * nem módosítja a core-ban lévő Mineral-t.
     * @param m az aszteroida magjába (core) betenni kívánt nyersanyag
     * @return bool a nyersanyag viszzatevésének sikerességéről
     */
    public boolean putMineralBack(Mineral m) {
        if (core == null && shell == 0) {
        	core = m;
        	return true;
        }
        return false;
    }

    /**
     * A függvényt meghívva az aszteroida magja null értéket vesz fel.
     */
    public void removeMineral() {
        core = null;
    }

    
    /**
     * A neighbours listából visszaadja az i-edik szomszédot, ha nincs ilyen elem, akkor null-al tér vissza.
     * @param i a lekérdezni kívánt szomszéd sorszáma a neighbours listában.
     */
    public INeighbour getNeighbourAt(int i) {
        if (i <= neighbours.size() && i > 0)
        	return neighbours.get(i);
        return null;
    }

    public int getShell() {
        return shell;
    }

    /**
     * A megvalósított interfész függvénye, elhelyezi a traveller-t a listájában és meghívja
     * erre az utazóra a setAsteroid függvényét saját magát paraméterül adva.
     * @param traveller az elhelyezni kívánt utazó
     */
    @Override
    public void placeTraveller(Traveller traveller){
    	if (!travellers.contains(traveller)) {
    		travellers.add(traveller);
    		traveller.setAsteroid(this);
    	}
    }
    
    /**
     * Kiveszi a kapott utazót a travellers listából.
     * @param traveller az eltávolítani kívánt traveller
     */
    public void removeTraveller(Traveller traveller) {
        travellers.remove(traveller);
    }

    /**
     * A megvalósított interfész függvénye, kiveszi a paraméterül kapott szomszédot a neighbours listából.
     * @param neighbour az eltávolítani kívánt szomszéd
     */
    @Override
    public void removeNeighbour(INeighbour neighbour) {
    	neighbours.remove(neighbour);
    }
    
    /**
     * Visszaadja az aszteroida szomszédainak számát.
     * @return a szomszédok száma
     */
    public int getNeighbourCount() {
    	return neighbours.size();
    }
    
    /**
     * A teleport mozgatásáért felelős metódus. Meghívja a paraméterül kapott teleporton a
     * setNeighbour metódust magát adva paraméterül. Beteszi a teleportot a neighbours listájába és igazzal tér vissza.
     * @param t a mozgatandó teleport
     * @return a mozgatás sikeressége
     */
    @Override
	public boolean moveTeleport(Teleport t) {
		t.setNeighbour(this);
		neighbours.add(t);
		return true;
	}
    
    /**
     * Ha a core nem üres, vagy a shell nem 0, az összes Traveller-re a travellers listában meghívja
     * a Traveller die metódusát. 
     * Ha az i nem egyenlő 0-val, akkor az összes szomszédjára meghívja a solarWind metódust eggyel 
     * csökkentett i-vel. (ez egy szélességi bejárás).
     * @param i napszél mélysége (hogy mekkora területet ér majd el)
     */
    @Override
    public void solarWind(int i) {
        if (core != null || shell != 0)
        	for (int j = 0; j < travellers.size(); j++)
        		travellers.get(j).die();
        if (i > 0)
        	for (int j = 0; j < neighbours.size(); j++)
        		neighbours.get(j).solarWind(i - 1);
    }

    /**
     * a megvalósított interfész függvénye, hozzáadja a neighbours listájához a szomszédot,
     * amennyiben az még nem része.
     * @param neighbour a hozzáadni kívánt szomszéd
     */
    public void addNeighbour(INeighbour neighbour){
    	if (!neighbours.contains(neighbour))
    		neighbours.add(neighbour);
    }
    
    /** Visszaadja az aszteroida magját.
     * @return az aszteroida magja
     */
    public Mineral getCore() {
        return core;
    }
    
    /**
     * Lekérdezi hogy napközel van-e?
     * @return boolean aszerint hogy napközel (true) vagy naptávol (false) van
     */
    public boolean getCloseToSun(){
    	return closeToSun;
    }
}