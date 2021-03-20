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

    /**
     * 
     */
    public void hitByBlast() {
    	Skeleton.startMethod(this, "hitByBlast", null);
        Asteroid currAst = asteroid;
        /*if(Skeleton.yesnoQuestion("Is there a neighbour I can go to?(yes/no)")) {
        	int i = Skeleton.intQuestion("Which is the index of the neighbour I can go to?(int)");
        	asteroid.getNeighbourAt(i).placeTraveller(this);
        	while(currAst.equals(asteroid)) {		//ha még rendesen le nem tett teleportot mondott a felhasználó
        		i = Skeleton.intQuestion("Tell me a valid index of a good neighbour!(int)");
        		asteroid.getNeighbourAt(i).placeTraveller(this);
        	}*/
        INeighbour neighbour = asteroid.getNeighbourAt(0);
        if(neighbour != null){
            neighbour.placeTraveller(this);
        } else {
        	die();
        }
        Skeleton.endMethod(this, null);
        /*while(currAst.equals(asteroid)) {
        	INeighbour neighbour = asteroid.getNeighbourAt(i);
        	if(neighbour==null) {
        		this.die();
        		return;
        	}
        	neighbour.placeTraveller(this);
        }*/
    }

    /**
     * robot meghal
     */
    public void die() {
    	Skeleton.startMethod(this, "die", null);
    	asteroid.removeTraveller(this);
        game.removeRobot(this);
        Skeleton.endMethod(this, null);
    }

    /**
     * itt vagy mozog, vagypedig fúr a robot
     */
    public void makeAction() {
    	Skeleton.startMethod(this, "makeAction", null);
    	if(Skeleton.yesnoQuestion("Should I drill?(yes/no)")) {
    		drill();
    	} else if (Skeleton.yesnoQuestion("Should I move?(yes/no)")) {
    		move(Skeleton.intQuestion("To which neighbour should I move?(int)"));
    	}
    	Skeleton.endMethod(this, null);
    }

}