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
    public void drill() {
    	asteroid.onDrill();
    }

    /**
     * itt vagy mozog, vagypedig f�r a robot
     * ha a gener�lt random igaz, akkor f�r, egy�bk�nt pedig morog az aszteroid�j�nak egy random szomsz�dj�ra
     */
    public void makeAction() {
        Random rand = new Random();
        boolean randDecision = rand.nextBoolean();
    	if(randDecision) {
    		drill();
    	} else {
            int randNeighbour = rand.nextInt(asteroid.getNeighbourCount());
    		move(randNeighbour);
    	}
    }

}