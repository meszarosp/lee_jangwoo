import java.util.*;

/**
 * Folyamatosan fúr, vagy mozog. Felelõssége meghalni, ha napvihar éri, 
 * vagy másik aszteroidára kerülni, ha radioaktív robbanás történik az 
 * aszteroidáján. Ha meghal, el kell távolíttatnia magát a Game 
 * listájából, valamint a saját aszteroidájáról.
 */
public class Robot extends Traveller {

    /**
     * Konstruktor amely a traveller ősre meghívja a konstruktort.
     * @param a az aszteroida ahol az objektum lesz.
     * @param g a Game objektum, ami tartalmazza a robotot
     */
    public Robot(Asteroid a, Game g) {
        super(a, g);
    }

    /**
     * az aszteroida egy random szomszédjára átmozgatja a robotot. 
     * amennyiben nincs szomszéd, a robot meghal, mert léte értelmét vesztette.
     */
    @Override
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
     * A robot meghal. Ekkor eltávolítjuk az aszteroida és a game tárolóiból.
     */
    @Override
    public void die() {
    	asteroid.removeTraveller(this);
        game.removeRobot(this);
    }

    /**
     * megfúrja az aszteroidát: meghívja az aszteroidára az onDrill függvényt.
     * @return visszaadja, hogy sikeres volt-e a fúrás.
     */
    public boolean drill() {
    	return asteroid.onDrill();
    }

    /**
     * makeAction függvény: vagy mozog, vagypedig fúr a robot.
     * ha a generált random igaz, akkor fúr, egyébként pedig mozog az aszteroidájának egy random szomszédjára.
     * @return a visszaadott érték azt jelzi, hogy sikeresen fúrt/mozgott, vagy sem.
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