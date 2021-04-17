import java.util.*;

/**
 * Folyamatosan fúr, vagy mozog. Felelõssége meghalni, ha napvihar éri, 
 * vagy másik aszteroidára kerülni, ha radioaktív robbanás történik az 
 * aszteroidáján. Ha meghal, el kell távolíttatnia magát a Game 
 * listájából, valamint a saját aszteroidájáról.
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
     * az aszteroida egy random szomszédjára átmozgatja a robotot. 
     * amennyiben nincs szomszéd, a robot meghal
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
     * megfúrja az aszteroidát
     */
    public boolean drill() {
    	return asteroid.onDrill();
    }

    /**
     * itt vagy mozog, vagypedig fúr a robot
     * ha a generált random igaz, akkor fúr, egyébként pedig morog az aszteroidájának egy random szomszédjára
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