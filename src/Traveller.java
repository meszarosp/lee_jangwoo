
import java.util.*;

/**
 * Absztrakt osztály. Felelõssége nyilvántartani a jelenlegi aszteroidáját, 
 * és egy Game objektumot. Biztosítania kell virtuális függvényeket a tõle 
 * öröklõ osztályoknak. Mozognia kell tudni aszteroidáról aszteroidára, 
 * akár köztes teleportkapuval, akár anélkül. Tudnia kell aszteroidát fúrni.
 */
public abstract class Traveller {
    /**
     * Traveller konstruktora, ahol paraméterben kapja azt az aszteroidát ahol elhelyezzük.
     * @param asteroid a traveller helye
     */
    public Traveller(Asteroid a, Game g) {
        a.placeTraveller(this);
        setGame(g);
    }

    /**
     * A traveller helyét jelképezõ aszteroida típusú attribútum.
     */
    protected Asteroid asteroid;

    /**
     * A game objektumot jelképező attibútum.
     */
    protected Game game;

    /**
     * A game settere
     * @param game az új game objektum
     */
    public void setGame(Game game){
        this.game = game;
    }

    /**
     * Átmegy az aszteroidájáról egy másikra, vagy egy teleportkapuba
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
     * Absztrakt hitByBlast függvény, amely a leszármazottakban lehet felülírva, ott kifejtve.
     */
    public abstract void hitByBlast();

    /**
     * Absztrakt die függvény, amely a leszármazottakban lesz felülírva, ott kifejtve.
     */
    public abstract void die();

    /**  
     * beállítja az utazó aszteroidáját
     * @param a az az aszteroida amin az utazó áll
     */
    public void setAsteroid(Asteroid a) {
    	asteroid = a;
    }

    /**	 
     * visszaadja az aszteroidát, amin az utazó áll
     * @return az az aszteroida amin az utazó áll
     */
    public Asteroid getAsteroid() {
        return asteroid;
    }
}