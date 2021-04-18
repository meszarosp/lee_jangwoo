import java.util.ArrayList;
import java.util.List;
/**
 * Egy absztrakt osztály, összefogja az összes nyersanyagot, amely kibányászható. Felelőssége
 * egy függvény biztosítani az aszteroidának, amit meghívhat, ha az napközelbe ért.
 */
public abstract class Mineral {

	/**
	 * Az összes elérhető Mineral-t tartalmazó lista. Ennek segítségével
	 * ellenőrizhető, hogy megvannak-e a szükséges nyersanyagok a játék
	 * megnyeréséhez.
	 */
    static private List<Mineral> AllMinerals;

    /**
     * Felveszi a játékban elérhető Mineralokat. Ha új nyersanyagok jönnek,
     * bővíthető
     */
    static public void Init() {
    	AllMinerals = new ArrayList<Mineral>();
        AllMinerals.add(new Uranium(0));
        AllMinerals.add(new Iron());
        AllMinerals.add(new Ice());
        AllMinerals.add(new Coal());
    }
    /**
     * Default constructor
     */
        public Mineral() {}
    /**
     * Napközelbe kerüléskor meghívódó (jelenleg üres) függvény. A leszármazottak
     * újraimplementálhatják, ha szükséges(pl. jég, radioaktív nyersanyagok).
     * @param a az az aszteroida, amelynek ez a nyersanyag a magja.
     */
    public void exposedToSun(Asteroid a) {}

    /**
     * Az allMinerals statikus változó gettere.
     * @return visszaadja az allMinerals listát
     */
    public static List<Mineral> getAllMinerals(){
        return AllMinerals;
    }
    /**
     * A mineral tipusát adja vissza Stringben. 
     * Mivel a mineral abstract ezért erre a mineralban nincs szükség.
     * @return a típus neve szövegként
     */
    public abstract String toString();
}