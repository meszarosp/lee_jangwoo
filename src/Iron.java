
import java.util.*;

/**
 * A vas nyersanyagot reprezentálja, a nyersanyagból származik le. Használható Robotok és
 * teleportkapuk építésére, valamint még bővíthető további funkciókkal vagy attribútumokkal (pl.: olvadáspont).
 */
public class Iron extends Mineral {

    /**
     * Default constructor
     */
    public Iron() {}
    
    /**
     * Az adott nyersanyag tipusát adja vissza Stringben. 
     * A mineralban lévőt írja felül.
     * @return a típus neve szövegként
     */
    @Override
    public String toString() { 
    	return "iron";
    }

}