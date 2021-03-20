
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
     * A teleportkapu p�rja.
     */
    private Teleport pair;

    /**
     * Az aszteroida, amin a teleortkapu van.
     */
    private INeighbour neighbour;

    /**
     * A teleport p�rj�t null-ra �ll�tja, �s ha a neighbour nem null (azaz m�r le van rakva a teleportkapu), akkor a neighbour removeNeighbour met�dus�t megh�vja
     */
    public void perish() {
    	Skeleton.startMethod(this, "perish", null);
        pair = null;
        if(Skeleton.yesnoQuestion("Le van m�r rakva a teleportkapu?")) {
        	neighbour.removeNeighbour(this);
        }
        Skeleton.endMethod(this,  null);
    }

    /**
     * Be�ll�tja a kapu p�rj�t a param�ter�l kapott teleportkapura.
     * @param a teleportkapu p�rja
     */
    public void setPair(Teleport t) {
    	Skeleton.startMethod(this,  "setPair", t);
        pair = t;
        Skeleton.endMethod(this,  null);
    }

    /**
     * Ha a neighbour nem null, akkor megh�vja r� a placeTraveller met�dust �s true-val t�r vissza, egy�bk�nt false-al t�r vissza
     * @param traveller az �thelyezend� traveller
     * @return bool a traveller �thelyez�s�nek sikeress�g�r�l
     */
    public boolean teleportTraveller(Traveller traveller) {
    	Skeleton.startMethod(this,  "teleportTraveller", traveller);
      	if(Skeleton.yesnoQuestion("Le van m�r rakva a teleportkapu?")) {
    		neighbour.placeTraveller(traveller);
    		Skeleton.endMethod(this, true);
    		return true;
    	}
      	Skeleton.endMethod(this, false);
        return false;
    }

    /**
     * Megh�vja a pair teleportTraveller met�dus�t. Abban az esetben, ha az hamissal t�r vissza, megh�vja a neighbour placeTraveller met�dus�t.
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
     * Megh�vja a pair-nek a perish met�dus�t.
     * @param neighbour
     */
    public void removeNeighbour(INeighbour neighbour){
    	Skeleton.startMethod(this, "removeNeighbour", neighbour);
    	pair.perish();
    	Skeleton.endMethod(this,  null);
    }

    /**
     *Abban az esetben, ha a pair nem null (azaz m�r le van rakva a kapu p�rja), a param�ter�l kapott neighbour-nek megh�vja az addNeighbour met�dus�t, aminek saj�t mag�t adja param�ter�l.
     * @param neighbour
     */
    public void addNeighbour(INeighbour neighbour){
    	Skeleton.startMethod(this,  "addNeighbour", neighbour);
    	if(Skeleton.yesnoQuestion("Le van m�r rakva a kapu p�rja?")) {
    		neighbour.addNeighbour(this);
    	}
    	Skeleton.endMethod(this,  null);
    }

}