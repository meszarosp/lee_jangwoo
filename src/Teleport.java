
import java.util.*;

/**
 * 
 */
public class Teleport implements INeighbour {

    /**
     * Default constructor
     */
    public Teleport() {
    }

    /**
     * A teleportkapu párja.
     */
    private Teleport pair;

    /**
     * Az aszteroida, amin a teleortkapu van.
     */
    private INeighbour neighbour;

    /**
     * A teleport párját null-ra állítja, és ha a neighbour nem null (azaz már le van rakva a teleportkapu), akkor a neighbour removeNeighbour metódusát meghívja
     */
    public void perish() {
    	Skeleton.startMethod(this, "perish", null);
        pair = null;
        if(Skeleton.yesnoQuestion("Le van már rakva a teleportkapu?")) {
        	neighbour.removeNeighbour(this);
        }
        Skeleton.endMethod(this,  null);
    }

    /**
     * Beállítja a kapu párját a paraméterül kapott teleportkapura.
     * @param a teleportkapu párja
     */
    public void setPair(Teleport t) {
    	Skeleton.startMethod(this,  "setPair", t);
        pair = t;
        Skeleton.endMethod(this,  null);
    }

    /**
     * Ha a neighbour nem null, akkor meghívja rá a placeTraveller metódust és true-val tér vissza, egyébként false-al tér vissza
     * @param traveller az áthelyezendõ traveller
     * @return bool a traveller áthelyezésének sikerességérõl
     */
    public boolean teleportTraveller(Traveller traveller) {
    	Skeleton.startMethod(this,  "teleportTraveller", traveller);
      	if(Skeleton.yesnoQuestion("Le van már rakva a teleportkapu?")) {
    		neighbour.placeTraveller(traveller);
    		Skeleton.endMethod(this, true);
    		return true;
    	}
      	Skeleton.endMethod(this, false);
        return false;
    }

    /**
     * Meghívja a pair teleportTraveller metódusát. Abban az esetben, ha az hamissal tér vissza, meghívja a neighbour placeTraveller metódusát.
     * @param traveller
     */
    public void placeTraveller(Traveller traveller){
    	Skeleton.startMethod(this,  "placeTraveller", traveller);
    	boolean b = pair.teleportTraveller(traveller);
    	if(!b) {
    		neighbour.placeTraveller(traveller);
    	}
    	Skeleton.endMethod(this,  null);
    }

    /**
     * Meghívja a pair-nek a perish metódusát.
     * @param neighbour
     */
    public void removeNeighbour(INeighbour neighbour){
    	Skeleton.startMethod(this, "removeNeighbour", neighbour);
    	pair.perish();
    	Skeleton.endMethod(this,  null);
    }

    /**
     *Abban az esetben, ha a pair nem null (azaz már le van rakva a kapu párja), a paraméterül kapott neighbour-nek meghívja az addNeighbour metódusát, aminek saját magát adja paraméterül.
     * @param neighbour
     */
    public void addNeighbour(INeighbour neighbour){
    	Skeleton.startMethod(this,  "addNeighbour", neighbour);
    	if(Skeleton.yesnoQuestion("Le van már rakva a kapu párja?")) {
    		neighbour.addNeighbour(this);
    	}
    	Skeleton.endMethod(this,  null);
    }

}