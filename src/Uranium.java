
import java.util.*;

/**
 * Az urán nyersanyagot reprezentálja. Használható Robotok és
 * teleportkapuk építésére, valamint még bővíthető további funkciókkal vagy attribútumokkal
 * (pl.: felezési idő). Egy RadioactiveMineral, örökli őseinek felelősségeit.
 */
public class Uranium extends RadioactiveMineral {

    private int exposedToSunCounter = 0;

    public Uranium(int exposedToSunCounter) {
        this.exposedToSunCounter = exposedToSunCounter;
    }

    /**
     * Default constructor
     */
    public Uranium() {
    }

}