import java.util.*;

/**
 * Felel�ss�ge, hogy az aszteroid�k napk�zelis�g�t szab�lyozza �s a v�letlen id�k�z�nk�nt
 * napviharral s�jtsa �ket.
 */
public class Sun {

    /**
     * Default constructor
     */
    public Sun() {
        asteroids = new ArrayList<Asteroid>();
    }

    /**
     * A j�t�kban szerpl� �sszes aszteroid�t tartalmaz� lista.
     */
    private List<Asteroid> asteroids;

    /**
     * Ha azt a v�laszt kapja, hogy legyen npvihar, akkor minden aszteroid�ra megh�vja a solarWind met�dust. Ez ut�n v�gigmegy�nk az �sszes aszteroid�n, megk�rdezz�k, hogy az legyen-e napk�zelben, ha azt a v�laszt kapja, hogy igen, akkor megh�vja rajta a setCloseToSun met�dust
     */
    public void makeAction() {
        if(!Skeleton.getRandom()) {
    		return;
    	}
    	Random rand = new Random();
        for(Asteroid a : asteroids) {
        	if(rand.nextInt() % 2 == 1) {
        		SolarWind(a, rand.nextInt()%5+1);
        	}
        	if(rand.nextInt() % 2 == 1) {
        		SetCloseToSun(a);
        	}
        }
    }
    
    public void SolarWind(Asteroid a, int i) {
    	a.solarWind(i);
    }
    public void SetCloseToSun(Asteroid a) {
    	a.setCloseToSun();
    }


    /**
     * kiveszi a param�terk�nt kapott aszteroid�t az asteroids list�b�l
     * @param a az elt�vol�tand� aszteroida
     */
    public void removeAsteroid(Asteroid a) {
    	//Skeleton.startMethod(this, "removeAsteroid", a);
        asteroids.remove(a);
        //Skeleton.endMethod(this, null);
    }

    /**
     * A saj�t asteroids list�j�t fel�l�rja a param�ter�l kapott asteroids list�val
     * @param asteroids aszteroid�kb�l �ll� lista, amit be�ll�t a saj�t asteroids list�j�nak
     */
    public void addAsteroids(List<Asteroid> asteroids) {
    	//Skeleton.startMethod(this, "addAsteroids", asteroids);
    	this.asteroids=asteroids;
    	//Skeleton.endMethod(this,  null);
    }

    /**
     *
     * @param asteroid
     */
    // TODO Ezt ledokument�lni
    public void addAsteroid(Asteroid asteroid) {
        this.asteroids.add(asteroid);
    }

    /**
     * Visszaadja az asteroids list�t.
     */
    public List<Asteroid> getAsteroids() {
        //Skeleton.startMethod(this,  "getAsteroids", null);
        //Skeleton.endMethod(this, this.asteroids);
        return this.asteroids;
    }

}