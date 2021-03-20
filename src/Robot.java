import java.util.*;

/**
 * 
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
        if(Skeleton.yesnoQuestion("Is there a neighbour I can go to?(yes/no)")) {
        	int i = Skeleton.intQuestion("Which is the index of the neighbour I can go to?(int)");
        	asteroid.getNeighbourAt(i).placeTraveller(this);
        	while(currAst.equals(asteroid)) {		//ha még rendesen le nem tett teleportot mondott a felhasználó
        		i = Skeleton.intQuestion("Tell me a valid index of a good neighbpur!(int)");
        		asteroid.getNeighbourAt(i).placeTraveller(this);
        	}
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
     * 
     */
    public void die() {
        // TODO implement here
    }

    /**
     * 
     */
    public void makeAction() {
        // TODO implement here
    }

}