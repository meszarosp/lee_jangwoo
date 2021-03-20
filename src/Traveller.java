
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

    /**
     * 
     */
    protected Asteroid asteroid;

    /**
     * 
     */
    protected Game game;

    public void setGame(Game game){
    	Skeleton.startMethod(this, "setGame", game);
        this.game = game;
        Skeleton.endMethod(this, null);
    }

    /**
     * átmegy az aszteroidájáról egy másikra, vagy egy teleportkapuba
     * @param number hányadik szomszédjára megy az utazó az aszteroidának
     */
    public void move(int number) {
    	Skeleton.startMethod(this, "move", number);
        INeighbour b = asteroid.getNeighbourAt(number);
        asteroid.removeTraveller(this);
        b.placeTraveller(this);
        Skeleton.endMethod(this, null);
    }

    /**
     * megfúrja az aszteroidát
     */
    public void drill() {
    	Skeleton.startMethod(this, "drill", null);
    	asteroid.onDrill();
    	Skeleton.endMethod(this, null);
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
    	Skeleton.startMethod(this, "setAsteroid", a);
    	asteroid = a;
    	Skeleton.endMethod(this, null);
    }

    /**	 visszaadja az aszteroidát, amin az utazó áll
     * @return az az aszteroida amin az utazó áll
     */
    public Asteroid getAsteroid() {
    	Skeleton.startMethod(this, "getAsteroid", null);
        Skeleton.endMethod(this, asteroid);
        return asteroid;
    }

}