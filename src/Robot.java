import java.util.*;

/**
 * Folyamatosan f�r, vagy mozog. Felel�ss�ge meghalni, ha napvihar �ri, 
 * vagy m�sik aszteroid�ra ker�lni, ha radioakt�v robban�s t�rt�nik az 
 * aszteroid�j�n. Ha meghal, el kell t�vol�ttatnia mag�t a Game 
 * list�j�b�l, valamint a saj�t aszteroid�j�r�l.
 */
public class Robot extends Traveller {

    /**
     * Default constructor
     */
    public Robot() {
    }

    public Robot(Asteroid a) {
        super(a);
    }

    /**
     * az aszteroida egy random szomsz�dj�ra �tmozgatja a robotot. 
     * amennyiben nincs szomsz�d, a robot meghal
     */
    public void hitByBlast() {
        Random rand = new Random();
        int randNeighbour = rand.nextInt(asteroid.getNeighbourCount());
        INeighbour neighbour = asteroid.getNeighbourAt(randNeighbour);
        if(neighbour != null){
            neighbour.placeTraveller(this);
        } else {
        	die();
        }
    }

    /**
     * robot meghal
     */
    public void die() {
    	asteroid.removeTraveller(this);
        game.removeRobot(this);
    }

    /**
     * megf�rja az aszteroid�t
     */
    public boolean drill() {
    	return asteroid.onDrill();
    }

    /**
     * itt vagy mozog, vagypedig f�r a robot
     * ha a gener�lt random igaz, akkor f�r, egy�bk�nt pedig morog az aszteroid�j�nak egy random szomsz�dj�ra
     */
    public boolean makeAction() {
        Random rand = new Random();
        boolean randDecision = rand.nextBoolean();
    	if (randDecision) {
    	    if (drill()) {
    	        return true;
            }
    	}
    	if (asteroid.getNeighbourCount() == 0)
    	    return false;
        int randNeighbour = rand.nextInt(asteroid.getNeighbourCount());
        return move(randNeighbour);
    }

}