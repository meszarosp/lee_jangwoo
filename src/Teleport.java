
import java.util.*;

/**
 * Nyilv�ntartani a teleportkapu p�rj�t, valamint egy INeighbour interf�szt megval�s�t�
 * objektumot. Ez a szomsz�dja, mellyel az utaz�k �thalad�s�t biztos�tja a m�sik teleportkapun
 * �t. Ha a szomsz�d aszteroid�n robban�s t�rt�nik, azaz megsz�nik ez a szomsz�d, akkor a rajta
 * l�v� kapunak is meg kell sz�nnie. Ha egy kapu megsz�nik, a p�rj�nak is meg kell.
 * Felel�ss�ge nem �tengedni utaz�kat, amennyiben a kapup�rja m�g nincs let�ve.
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
     * A teleportkapu p�rja.
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
     * A teleport p�rj�t null-ra �ll�tja, �s ha a neighbour nem null (azaz m�r le van rakva a teleportkapu), akkor a neighbour removeNeighbour met�dus�t megh�vja
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
     * Be�ll�tja a kapu p�rj�t a param�ter�l kapott teleportkapura.
     * @param t teleportkapu p�rja
     */
    public void setPair(Teleport t) {
    	pair = t;
    }

    /**
     * Ha a neighbour nem null, akkor megh�vja r� a placeTraveller met�dust �s true-val t�r vissza, egy�bk�nt false-al t�r vissza
     * @param traveller az �thelyezend� traveller
     * @return bool a traveller �thelyez�s�nek sikeress�g�r�l
     */
    public boolean teleportTraveller(Traveller traveller) {
         if(neighbour != null) {
    		neighbour.placeTraveller(traveller);
    		return true;
    	}
        return false;
    }

    /**
     * Megh�vja a pair teleportTraveller met�dus�t. Abban az esetben, ha az hamissal t�r vissza, megh�vja a neighbour placeTraveller met�dus�t.
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
     * Megh�vja a pair-nek a perish met�dus�t.
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
     *Abban az esetben, ha a pair nem null (azaz m�r le van rakva a kapu p�rja), a param�ter�l kapott neighbour-nek megh�vja az addNeighbour met�dus�t, aminek saj�t mag�t adja param�ter�l.
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