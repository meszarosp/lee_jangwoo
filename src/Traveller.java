
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
     * �tmegy az aszteroid�j�r�l egy m�sikra, vagy egy teleportkapuba
     * @param number h�nyadik szomsz�dj�ra megy az utaz� az aszteroid�nak
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

    /**  be�ll�tja az utaz� aszteroid�j�t
     * @param a az az aszteroida amin az utaz� �ll
     */
    public void setAsteroid(Asteroid a) {
    	asteroid = a;
    }

    /**	 visszaadja az aszteroid�t, amin az utaz� �ll
     * @return az az aszteroida amin az utaz� �ll
     */
    public Asteroid getAsteroid() {
        return asteroid;
    }

}