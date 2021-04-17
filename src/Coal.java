
import java.util.*;

/**
 * A szén nyersanyagot reprezentálja, a nyersanyagból származik le. 
 * Használható Robotok építésére,valamint még bővíthető további funkciókkal vagy attribútumokkal (pl.: égéshő).
 */
public class Coal extends Mineral {

    /**
     * Default constructor
     */
    public Coal() {}
    
    /**
     * Az adott nyersanyag tipusát adja vissza Stringben. 
     * A mineralban lévőt írja felül.
     * @return a típus neve szövegként
     */
    @Override
    public String toString() { 
    	return "coal";
    }
}