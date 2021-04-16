
import java.util.*;

/**
 * Az urán nyersanyagot reprezentálja. Az aszteroidát fel kell robbantania, 
 * ha meghívják a függvényét és a számlálója eléri a 3-at. Használható Robotok és
 * teleportkapuk építésére, valamint még bővíthető további funkciókkal vagy attribútumokkal.
 */
public class Uranium extends RadioactiveMineral {

	/**
     * Az urán számlálója, ami 3-at elérve robban és az exposedToSun metódus hatására növekszik egyenként
     * a kezdeti konstruktor paraméterben átadott értékről, vagy annak hiányában 0-ról.
     */
    private int exposedToSunCounter = 0;

    /**
     * egy paraméteres konstruktor: a paraméter értékére állítja a számlálót, amennyiben az 0-3 között van.
     * Ha nincs 0-3 között akkor ha kisebb 0, ha nagyobb 3 lesz az értéke.
     * @param exposedToSunCounter a számláló kezdőértéke
     */
    public Uranium(int exposedToSunCounter) {
    	if (exposedToSunCounter > 3)
    		exposedToSunCounter = 3;
    	if (exposedToSunCounter < 0)
    		exposedToSunCounter = 0;
        this.exposedToSunCounter = exposedToSunCounter;
    }
    
    /**
     * Növeli az exposedToSunCounter-t, és ha ez 3 (vagy annál nagyobb, de ez elvileg nem lehetséges), 
     * akkor meghívja a paraméterül kapott aszteroidára a radioactiveBlast metódust,
     * @param a a nyersanyag aszteroidája, amelyet felrobbantunk, ha kell.
     */
    public void exposedToSun(Asteroid a) {
    	exposedToSunCounter++;
    	if (exposedToSunCounter >= 3)
    		a.radioactiveBlast();
    }

}