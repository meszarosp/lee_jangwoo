import java.util.*;

/**
 * Felelõssége, hogy az aszteroidák napközeliségét szabályozza és a véletlen idõközönként
 * napviharral sújtsa õket.
 */
public class Sun {

    /**
     * Default constructor
     */
    public Sun() {
        asteroids = new ArrayList<Asteroid>();
    }

    /**
     * A játékban szerplõ összes aszteroidát tartalmazó lista.
     */
    private List<Asteroid> asteroids;

    /**
     * Ha azt a választ kapja, hogy legyen npvihar, akkor minden aszteroidára meghívja a solarWind metódust. Ez után végigmegyünk az összes aszteroidán, megkérdezzük, hogy az legyen-e napközelben, ha azt a választ kapja, hogy igen, akkor meghívja rajta a setCloseToSun metódust
     */
    public void makeAction() {
    	Random rand = new Random();     //napvihar/setclosetosun
        if(rand.nextInt() % 5 == 0) {
        	asteroids.get(rand.nextInt(asteroids.size())).solarWind(rand.nextInt()%5+1);
        }
        for(Asteroid a : asteroids) {
        	if(rand.nextInt() % 2 == 1) {
        		a.setCloseToSun();
        	}
        }
    }

    /**
     * kiveszi a paraméterként kapott aszteroidát az asteroids listából
     * @param a az eltávolítandó aszteroida
     */
    public void removeAsteroid(Asteroid a) {
        asteroids.remove(a);
    }

    /**
     * A saját asteroids listáját felülírja a paraméterül kapott asteroids listával
     * @param asteroids aszteroidákból álló lista, amit beállít a saját asteroids listájának
     */
    public void addAsteroids(List<Asteroid> asteroids) {
    	this.asteroids=asteroids;
    }

    /**
     *
     * @param asteroid
     */
    // TODO Ezt ledokumentálni
    public void addAsteroid(Asteroid asteroid) {
        this.asteroids.add(asteroid);
    }

    /**
     * Visszaadja az asteroids listát.
     */
    public List<Asteroid> getAsteroids() {
        return this.asteroids;
    }

}