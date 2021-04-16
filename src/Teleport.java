
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
    }

    public Teleport(boolean bamboozled) {
        this.bamboozled = bamboozled;
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

    /**
     * A teleport p�rj�t null-ra �ll�tja, �s ha a neighbour nem null (azaz m�r le van rakva a teleportkapu), akkor a neighbour removeNeighbour met�dus�t megh�vja
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
     * Be�ll�tja a kapu p�rj�t a param�ter�l kapott teleportkapura.
     * @param t teleportkapu p�rja
     */
    public void setPair(Teleport t) {
    	//Skeleton.startMethod(this,  "setPair", t);
    	pair = t;
        //Skeleton.endMethod(this,  null);
    }

    /**
     * Ha a neighbour nem null, akkor megh�vja r� a placeTraveller met�dust �s true-val t�r vissza, egy�bk�nt false-al t�r vissza
     * @param traveller az �thelyezend� traveller
     * @return bool a traveller �thelyez�s�nek sikeress�g�r�l
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
     * Megh�vja a pair teleportTraveller met�dus�t. Abban az esetben, ha az hamissal t�r vissza, megh�vja a neighbour placeTraveller met�dus�t.
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
     * Megh�vja a pair-nek a perish met�dus�t.
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
     *Abban az esetben, ha a pair nem null (azaz m�r le van rakva a kapu p�rja), a param�ter�l kapott neighbour-nek megh�vja az addNeighbour met�dus�t, aminek saj�t mag�t adja param�ter�l.
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