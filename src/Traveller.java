
import java.util.*;

/**
 * Absztrakt oszt�ly. Felel�ss�ge nyilv�ntartani a jelenlegi aszteroid�j�t, 
 * �s egy Game objektumot. Biztos�tania kell virtu�lis f�ggv�nyeket a t�le 
 * �r�kl� oszt�lyoknak. Mozognia kell tudni aszteroid�r�l aszteroid�ra, 
 * ak�r k�ztes teleportkapuval, ak�r an�lk�l. Tudnia kell aszteroid�t f�rni.
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
     * �tmegy az aszteroid�j�r�l egy m�sikra, vagy egy teleportkapuba
     * @param number h�nyadik szomsz�dj�ra megy az utaz� az aszteroid�nak
     */
    public void move(int number) {
    	Skeleton.startMethod(this, "move", number);
        INeighbour b = asteroid.getNeighbourAt(number);
        asteroid.removeTraveller(this);
        b.placeTraveller(this);
        Skeleton.endMethod(this, null);
    }

    /**
     * megf�rja az aszteroid�t
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

    /**  be�ll�tja az utaz� aszteroid�j�t
     * @param a az az aszteroida amin az utaz� �ll
     */
    public void setAsteroid(Asteroid a) {
    	Skeleton.startMethod(this, "setAsteroid", a);
    	asteroid = a;
    	Skeleton.endMethod(this, null);
    }

    /**	 visszaadja az aszteroid�t, amin az utaz� �ll
     * @return az az aszteroida amin az utaz� �ll
     */
    public Asteroid getAsteroid() {
    	Skeleton.startMethod(this, "getAsteroid", null);
        Skeleton.endMethod(this, asteroid);
        return asteroid;
    }

}