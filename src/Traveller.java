
import java.util.*;

/**
 * Absztrakt oszt�ly. Felel�ss�ge nyilv�ntartani a jelenlegi aszteroid�j�t, 
 * �s egy Game objektumot. Biztos�tania kell virtu�lis f�ggv�nyeket a t�le 
 * �r�kl� oszt�lyoknak. Mozognia kell tudni aszteroid�r�l aszteroid�ra, 
 * ak�r k�ztes teleportkapuval, ak�r an�lk�l. Tudnia kell aszteroid�t f�rni.
 */
public abstract class Traveller {
    /**
     * Traveller konstruktora, ahol param�terben kapja azt az aszteroid�t ahol elhelyezz�k.
     * @param a traveller helye
     * @param g a j�t�k amelyben a traveller van
     */
    public Traveller(Asteroid a, Game g) {
        a.placeTraveller(this);
        asteroid = a;
        setGame(g);
    }

    /**
     * A traveller hely�t jelk�pez� aszteroida t�pus� attrib�tum.
     */
    protected Asteroid asteroid;

    /**
     * A game objektumot jelk�pez� attib�tum.
     */
    protected Game game;

    /**
     * A game settere
     * @param game az �j game objektum
     */
    public void setGame(Game game){
        this.game = game;
    }

    /**
     * �tmegy az aszteroid�j�r�l egy m�sikra, vagy egy teleportkapuba
     * @param number h�nyadik szomsz�dj�ra megy az utaz� az aszteroid�nak
     */
    public boolean move(int number) {   //0-tól indexelt
        INeighbour b = asteroid.getNeighbourAt(number);
        if (b == null)
            return false;
        asteroid.removeTraveller(this);
        Asteroid previousAsteroid = asteroid;
        b.placeTraveller(this);
        return previousAsteroid != asteroid;
    }

    /**
     * Absztrakt hitByBlast f�ggv�ny, amely a lesz�rmazottakban lehet fel�l�rva, ott kifejtve.
     */
    public abstract void hitByBlast();

    /**
     * Absztrakt die f�ggv�ny, amely a lesz�rmazottakban lesz fel�l�rva, ott kifejtve.
     */
    public abstract void die();

    /**  
     * be�ll�tja az utaz� aszteroid�j�t
     * @param a az az aszteroida, amin az utaz� �ll
     */
    public void setAsteroid(Asteroid a) {
    	asteroid = a;
    }

    /**	 
     * visszaadja az aszteroid�t, amin az utaz� �ll
     * @return az az aszteroida amin az utaz� �ll
     */
    public Asteroid getAsteroid() {
        return asteroid;
    }
}