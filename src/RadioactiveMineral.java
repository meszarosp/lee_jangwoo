
import java.util.*;

/**
 * Absztrakt osztály. Az aszteroidát fel kell robbantania, ha meghívják a függvényét. A
 * radioaktív nyersanyagok csoportjának biztosít közös őst, és egy felülírt függvényt.
 * A nyersanyagból származik le.
 */
public abstract class RadioactiveMineral extends Mineral {

    /**
     * Default constructor
     */
    public RadioactiveMineral() {
    }

    /**
     * Meghívja a paraméterül kapott aszteroidára a radioactiveBlast függvényt, így felrobbantva azt.
     * @param a a nyersanyag aszteroidája, amelyet felrobbantunk.
     */
    public void exposedToSun(Asteroid a) {
    	Skeleton.startMethod(this, "exposedToSun", a);
        a.radioactiveBlast();
        Skeleton.endMethod(this, null);
    }

}