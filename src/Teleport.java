
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
    }

    public Teleport(boolean bamboozled) {
        this.bamboozled = bamboozled;
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

    /**
     * A teleport párját null-ra állítja, és ha a neighbour nem null (azaz már le van rakva a teleportkapu), akkor a neighbour removeNeighbour metódusát meghívja
     */
    public void perish() {
    	//Skeleton.startMethod(this, "perish", null);
        pair = null;
        /*if(!Skeleton.yesnoQuestion("Has the pair been blown up (perished)? (yes/no)")) {
        	neighbour.removeNeighbour(this);
        }
        Skeleton.endMethod(this,  null);*/
    }

    /**
     * Beállítja a kapu párját a paraméterül kapott teleportkapura.
     * @param t teleportkapu párja
     */
    public void setPair(Teleport t) {
    	//Skeleton.startMethod(this,  "setPair", t);
    	pair = t;
        //Skeleton.endMethod(this,  null);
    }

    /**
     * Ha a neighbour nem null, akkor meghívja rá a placeTraveller metódust és true-val tér vissza, egyébként false-al tér vissza
     * @param traveller az áthelyezendõ traveller
     * @return bool a traveller áthelyezésének sikerességérõl
     */
    public boolean teleportTraveller(Traveller traveller) {
    	/*Skeleton.startMethod(this,  "teleportTraveller", traveller);
      	if(Skeleton.yesnoQuestion("Has the pair been placed? (yes/no)")) {
    		neighbour.placeTraveller(traveller);
    		Skeleton.endMethod(this, true);
    		return true;
    	}
      	Skeleton.endMethod(this, false);*/
          ///TODO
        return false;
    }

    /**
     * Meghívja a pair teleportTraveller metódusát. Abban az esetben, ha az hamissal tér vissza, meghívja a neighbour placeTraveller metódusát.
     * @param traveller
     */
    public void placeTraveller(Traveller traveller){
    	//Skeleton.startMethod(this,  "placeTraveller", traveller);
    	boolean b = pair.teleportTraveller(traveller);
    	if(!b) {
    		neighbour.placeTraveller(traveller);
    	}
    	//Skeleton.endMethod(this,  null);
    }

    /**
     * Meghívja a pair-nek a perish metódusát.
     * @param neighbour
     */
    public void removeNeighbour(INeighbour neighbour){
    	//Skeleton.startMethod(this, "removeNeighbour", neighbour);
    	if (pair != null)
    	    pair.perish();
    	//Skeleton.endMethod(this,  null);
    }

    public void solarWind(int i) {

    }

    public boolean moveTeleport(Teleport t) {
        return false;
    }

    /**
     *Abban az esetben, ha a pair nem null (azaz már le van rakva a kapu párja), a paraméterül kapott neighbour-nek meghívja az addNeighbour metódusát, aminek saját magát adja paraméterül.
     * @param neighbour
     */
    public void setNeighbour(Asteroid a){
    	/*Skeleton.startMethod(this,  "addNeighbour", neighbour);
    	if (!Skeleton.init) {
            if (!Skeleton.yesnoQuestion("Has the pair been blown up (perished)? (yes/no)")) {
                neighbour.addNeighbour(this);
                this.neighbour = neighbour;
            }
        }else{
    	    this.neighbour = neighbour;
        }
    	Skeleton.endMethod(this,  null);*/
        ///TODO
    }

}