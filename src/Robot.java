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

    /**
     * 
     */
    public void hitByBlast() {
    	Skeleton.startMethod(this, "hitByBlast", null);
        Asteroid currAst = asteroid;
        /*if(Skeleton.yesnoQuestion("Is there a neighbour I can go to?(yes/no)")) {
        	int i = Skeleton.intQuestion("Which is the index of the neighbour I can go to?(int)");
        	asteroid.getNeighbourAt(i).placeTraveller(this);
        	while(currAst.equals(asteroid)) {		//ha m�g rendesen le nem tett teleportot mondott a felhaszn�l�
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
     * itt vagy mozog, vagypedig f�r a robot
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