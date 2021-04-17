
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
     * Default constructor. Létrehozza a teleportkaput a megfelelo kiindulási értékekkel.
     */
    public Teleport() {
    	bamboozled = false;
        pair = null;
        neighbour = null;
    }

    /**
     * Egy paraméteres constructor. Létrehozza a teleportkaput a megfelelő kiindulási értékekkel.
     * Megadható kiindulási bamboozled érték
     * @param bamboozled értékét állítja be
     */
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

    /**
     * Meg van-e őrülve a teleportkapu (ha igen akkor mozoghat)
     */
    private boolean bamboozled;

    /**
     * beállíthatjuk a bamboozled értékét.
     * @param bamboozled bamboozled leendő értéke
     */
    public void setBamboozled(boolean bamboozled) {
        this.bamboozled = bamboozled;
    }

    /**
     * bamboozled változó gettere
     * @return bamboozled értékét adja vissza
     */
    public boolean getBamboozled() { return bamboozled; }

    /**
     * visszaadja a szomszédot.
     * @return a szomszéd aszteroida
     */
    public Asteroid getNeighbour() { return  neighbour; }

    /**
     * visszaadja a teleportkapu párját
     * @return a teleportkapu párja
     */
    public Teleport getPair() {
        return pair;
    }

    /**
     * Visszaadja, hogy igaz-e hogy a kapu bamboozled
     * @return bool aszerint hogy a kapu bamboozled vagy nem
     */
    public boolean isBamboozled() {
        return bamboozled;
    }

    /**
     * A teleport párját null-ra állítja, és ha a neighbour nem null (
     * azaz már le van rakva a teleportkapu), akkor a neighbour removeNeighbour metódusát meghívja
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
    @Override
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
    @Override
    public void removeNeighbour(INeighbour neighbour){
    	if (pair != null)
    	    pair.perish();
    }

    /**
     * Napvihar érkezik, ilyenkor a teleportkapu megõrül, ha már le van téve.
     */
    @Override
    public void solarWind(int i) {
        if(neighbour != null)
            bamboozled = true;
    }

     /**
     * A teleportkapun teleportkapu nem tud átmenni.
     * @return mindig false, mert nem lehet sikeres
     */
    @Override
    public boolean moveTeleport(Teleport t) {
        return false;
    }

    /**
     *Abban az esetben, ha a pair nem null (azaz már le van rakva a kapu párja), a paraméterül kapott 
     *neighbour-nek meghívja az addNeighbour metódusát, aminek saját magát adja paraméterül.
     * @param a itt nincs szerepe
     */
    public void setNeighbour(Asteroid a){
    	neighbour = a;
        if(pair == null){
            removeNeighbour(this);
        }
    }

    /**
     * ha le van téve és a kapu megkergült (bamboozled == true) akkor meghívja a szomszédjaira a getNeighbourAt függvénnyel a 
     * moveTeleport metódust magát adva paraméterként. Ha ez igazzal tér vissza, akkor meghívja a régi aszteroidájára
     * (ezt eltárolja) a removeNeighbour függvényt magát paraméterül adva, majd visszatér. Ha végigért az összes szomszédon,
     * és még nem tért vissza, akkor ebben a körben nem mozdul.
     */
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