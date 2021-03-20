
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
     * Default constructor
     */
    public Asteroid() {
    }

    /**
     * Constructor, mely a paraméterben kapott nyersanyagot az aszteroida magjába helyezi.
     * @param c a leendő mag (nyersanyag típus)
     */
    public Asteroid(Mineral c){
        core = c;
    }

    /**
     * Az aszteroida kérgének vastagságát jellemző szám.
     */
    private int shell;

    /**
     * Az aszteroida naphoz közeli állapotát tárolja el. Ha értéke igaz (true), 
     * akkor az aszteroida napközelben van, különben ha false, akkor naptávolban van.
     */
    private boolean closeToSun;

    /**
     * Az aszteroida szomszédainak listája, ezen keresztül tudja értesíteni őket felrobbanásakor.
     * Valamint létezése lehetővé teszi, hogy az utazó lekérje egy szomszédját.
     */
    private List<INeighbour> neighbours = new ArrayList<INeighbour>();

    /**
     * Az aszteroida magjában lévő nyersanyag, ez lehet üres is, ekkor üreges az aszteroida.
     */
    private Mineral core;

    /**
     * Az aszteroidán lévő utazók listája. Ezen keresztül értesíti őket az azteroida, ha napszél éri, vagy felrobban.
     */
    private List<Traveller> travellers = new ArrayList<Traveller>();

    /**
     * Mielőtt az aszteroida felrobban értesíti a napot, hogy fel fog robbanni.
     */
    private Sun sun;

    /**
     * Az aszteroida "meg lett fúrva", ez csökkenti a shell-t, amennyiben az nem nulla.
     * Ha nulla lesz a shell, megnézi a closeToSun változót, amennyiben ez igaz, 
     * meghívja a core exposedToSun metódusát, magát adva paraméterként.
     */
    public void onDrill() {
        Skeleton.startMethod(this, "onDrill", null);
        shell = Skeleton.intQuestion("How thick is the shell?(int)");
        if (shell > 0)
        	shell--;
        if (shell == 0 && Skeleton.yesnoQuestion("Is the asteroid close to sun?(yes/no)") && !Skeleton.yesnoQuestion("Is the asteroiod hollow?(yes/no)"))
        	core.exposedToSun(this);
        Skeleton.endMethod(this, null);
    }

    /**
     * ha a shell 0, a nyersanyag, amely van, vagy nincs az aszteroida magjában "kibányászódik".
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
     * Ha a core nem üres, vagy a shell nem 0, az összes Traveller-re a travellers listában meghívja
     * a Traveller die metódusát. Egyébként csak visszatér.
     */
    public void solarWind() {
        if (core != null || shell != 0)
        	for (Traveller t : travellers)
        		t.die();
    }

    /**
     * Meghívja minden Traveller-re a travellers listában a hitByBlast metódusukat, 
     * ezután minden szomszédjára a removeNeighbour függvényt magával paraméterként. 
     * Ezután a sun-nak meghívja a removeAsteroid metódusát.
     */
    public void radioactiveBlast() {
    	for (Traveller t : travellers)
    		t.hitByBlast();
    	for (INeighbour n : neighbours)
    		n.removeNeighbour(this);
    	sun.removeAsteroid(this);
    }

    /**
     * Negálja a closeToSun-t, és ha a closeToSun true és a shell 0 meghívja a core-ra (ha van)
     *  az exposedToSun metódusát önmagát adva paraméterként.
     */
    public void setCloseToSun() {
        closeToSun = !closeToSun;
        if (closeToSun && shell == 0)
        	if (core != null)
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

    
    //---------------itt a ha nincs olyan elem résszel bővítettem, és nem volt visszatérési érték, ugyh tettem------
    /**
     * A neighbours listából visszaadja az i-edik szomszédot, ha nincs ilyen elem, akkor null-al tér vissza.
     * @param i a lekérdezni kívánt szomszéd sorszáma a neighbours listában.
     */
    public INeighbour getNeighbourAt(int i) {
        if (i < neighbours.size())
        	return neighbours.get(i);
        return null;
    }

    /**
     * Kiveszi a kapott utazót a travellers listából.
     * @param traveller az eltávolítani kívánt traveller
     */
    public void removeTraveller(Traveller traveller) {
        travellers.remove(traveller);
    }

    /**
     * @return
     */
    public Mineral getCore() {
        // TODO implement here
        return null;
    }

    /**
     * A megvalósított interfész függvénye, elhelyezi a traveller-t a listájában és meghívja
     * erre az utazóra a setAsteroid függvényét saját magát paraméterül adva.
     * @param traveller az elhelyezni kívánt utazó
     */
    public void placeTraveller(Traveller traveller){
    	travellers.add(traveller);
    	traveller.setAsteroid(this);
    }

    /**
     * A megvalósított interfész függvénye, kiveszi a paraméterül kapott szomszédot a neighbours listából.
     * @param neighbour az eltávolítani kívánt szomszéd
     */
    public void removeNeighbour(INeighbour neighbour) {
    	neighbours.remove(neighbour);
    }

    /**
     * a megvalósított interfész függvénye, hozzáadja a neighbours listájához a szomszédot.
     * @param neighbour a hozzáadni kívánt szomszéd
     */
    public void addNeighbour(INeighbour neighbour){
    	neighbours.add(neighbour);
    }

}