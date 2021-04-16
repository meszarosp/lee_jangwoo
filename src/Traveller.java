
import java.util.*;

/**
 * Absztrakt osztály. Felelõssége nyilvántartani a jelenlegi aszteroidáját, 
 * és egy Game objektumot. Biztosítania kell virtuális függvényeket a tõle 
 * öröklõ osztályoknak. Mozognia kell tudni aszteroidáról aszteroidára, 
 * akár köztes teleportkapuval, akár anélkül. Tudnia kell aszteroidát fúrni.
 */
public abstract class Traveller {

    /**
     * Default constructor
     */
    public Traveller() {
    }

    public Traveller(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    /**
     * 
     */
    protected Asteroid asteroid;

    /**
     * 
     */
    protected Game game;

    public void setGame(Game game){
        this.game = game;
    }

    /**
     * átmegy az aszteroidájáról egy másikra, vagy egy teleportkapuba
     * @param number hányadik szomszédjára megy az utazó az aszteroidának
     */
    public boolean move(int number) {
        INeighbour b = asteroid.getNeighbourAt(number);
        if (b == null)
            return false;
        asteroid.removeTraveller(this);
        b.placeTraveller(this);
        return true;
    }

    /**
     * 
     */
    public abstract void hitByBlast();

    /**
     * 
     */
    public abstract void die();

    /**  beállítja az utazó aszteroidáját
     * @param a az az aszteroida amin az utazó áll
     */
    public void setAsteroid(Asteroid a) {
    	asteroid = a;
    }

    /**	 visszaadja az aszteroidát, amin az utazó áll
     * @return az az aszteroida amin az utazó áll
     */
    public Asteroid getAsteroid() {
        return asteroid;
    }

}