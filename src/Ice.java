
import java.util.*;

/**
 * A vízjég nyersanyagot reprezentálja, a nyersanyagból származik le. Használható teleportkapuk
 * építésére, valamint még bővíthető további funkciókkal vagy attribútumokkal (pl.:
 * hőmérséklet). Ha egy napközelben lévő kéregtelen aszteroidán van, meg kell semmisülnie (elszublimál).
 */
public class Ice extends Mineral {

    /**
     * Default constructor
     */
    public Ice() {
    }

    /**
     * Ez egy örökölt, ám felülírt metódus. A paraméterül kapott aszteroidára meghívja
     * a removeMineral függvényt, ezzel eltávolítva annak magját.
     * @param a a nyersanyag aszteroidája, amely magját eltávolítja.
     */
    public void exposedToSun(Asteroid a) {
        a.removeMineral();
    }

}