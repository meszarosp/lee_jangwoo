
import java.util.*;

/**
 * Nyilvántartani a teleportkapu párját, valamint egy INeighbour interfészt megvalósító
 * objektumot. Ez a szomszédja, mellyel az utazók áthaladását biztosítja a másik teleportkapun
 * át. Ha a szomszéd aszteroidán robbanás történik, azaz megszûnik ez a szomszéd, akkor a rajta
 * lévõ kapunak is meg kell szûnnie. Ha egy kapu megszûnik, a párjának is meg kell.
 * Felelõssége nem átengedni utazókat, amennyiben a kapupárja még nincs letéve.
 */
public class Teleport implements INeighbour {

    /**
     * Default constructor
     */
    public Teleport() {
    	bamboozled = false;
        pair = null;
        neighbour = null;
    }

    public Teleport(boolean bamboozled) {
        this.bamboozled = bamboozled;
        pair = null;
        neighbour = null;
    }

    /**
     * A teleportkapu párja.
     */
    private Teleport pair;

    /**
     * Az aszteroida, amin a teleortkapu van.
     */
    private Asteroid neighbour;

    private boolean bamboozled;

    public void setBamboozled(boolean bamboozled) {
        this.bamboozled = bamboozled;
    }

    public boolean getBamboozled() { return bamboozled; }

    public Teleport getPair() {
        return pair;
    }

    public Asteroid getNeighbour() {
        return neighbour;
    }

    public boolean isBamboozled() {
        return bamboozled;
    }

    /**
     * A teleport párját null-ra állítja, és ha a neighbour nem null (azaz már le van rakva a teleportkapu), akkor a neighbour removeNeighbour metódusát meghívja
     */
    public void perish() {
        pair = null;
        if(neighbour != null) {
        	neighbour.removeNeighbour(this);
        	neighbour = null;
        }
        return;
    }

    /**
     * Beállítja a kapu párját a paraméterül kapott teleportkapura.
     * @param t teleportkapu párja
     */
    public void setPair(Teleport t) {
    	pair = t;
    }

    /**
     * Ha a neighbour nem null, akkor meghívja rá a placeTraveller metódust és true-val tér vissza, egyébként false-al tér vissza
     * @param traveller az áthelyezendõ traveller
     * @return bool a traveller áthelyezésének sikerességérõl
     */
    public boolean teleportTraveller(Traveller traveller) {
         if(neighbour != null) {
    		neighbour.placeTraveller(traveller);
    		return true;
    	}
        return false;
    }

    /**
     * Meghívja a pair teleportTraveller metódusát. Abban az esetben, ha az hamissal tér vissza, meghívja a neighbour placeTraveller metódusát.
     * @param traveller
     */
    public void placeTraveller(Traveller traveller){
    	if(pair == null) {
    		return;
    	}
    	if(!pair.teleportTraveller(traveller))
    		neighbour.placeTraveller(traveller);
    }


    /**
     * Meghívja a pair-nek a perish metódusát.
     * @param neighbour
     */
    public void removeNeighbour(INeighbour neighbour){
    	if (pair != null)
    	    pair.perish();
    }

    public void solarWind(int i) {
        bamboozled = true;
    }

    public boolean moveTeleport(Teleport t) {
        return false;
    }

    /**
     *Abban az esetben, ha a pair nem null (azaz már le van rakva a kapu párja), a paraméterül kapott neighbour-nek meghívja az addNeighbour metódusát, aminek saját magát adja paraméterül.
     * @param neighbour
     */
    public void setNeighbour(Asteroid a){
    	neighbour = a;
        if(pair == null){
            removeNeighbour(this);
        }
    }
    public void makeAction() {
        if(neighbour == null){
            return;
        }
    	if(bamboozled) {
    		Asteroid old = neighbour;
    		boolean placed = false;
    		int i = 1;
    		while(!placed) {
    			INeighbour temp = old.getNeighbourAt(i);
    			if(temp==null) {
    				return;
    			}
    			if(temp.moveTeleport(this)) {
    				neighbour = (Asteroid)temp;
    				old.removeNeighbour(this);
    				placed = true;
    			}
    			i++;
    		}
    	}
    }

}