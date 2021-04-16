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
    }

    /**
     * A játékban szerplõ összes aszteroidát tartalmazó lista.
     */
    private List<Asteroid> asteroids;

    /**
     * Ha azt a választ kapja, hogy legyen npvihar, akkor minden aszteroidára meghívja a solarWind metódust. Ez után végigmegyünk az összes aszteroidán, megkérdezzük, hogy az legyen-e napközelben, ha azt a választ kapja, hogy igen, akkor meghívja rajta a setCloseToSun metódust
     */
    public void makeAction() {
        //Skeleton.init = false;
    	//Skeleton.startMethod(this, "makeAction", null);
       /* int option = Skeleton.sunQuestion(this);
        if (option == 1){
            asteroids.get(0).solarWind();
        } else if (option == 2 || option == 3) {
            asteroids.get(0).setCloseToSun();
        }*/
    	/*if(Skeleton.yesnoQuestion("Legyen napvihar?")) {
        	for(Asteroid a : asteroids){
        		a.solarWind();
        	}
        }
        for(Asteroid a : asteroids){
        	if(Skeleton.yesnoQuestion("Legyünk napközelben?")) {
        		a.setCloseToSun();
        	}
        }*/
        //Skeleton.endMethod(this,  null);
        //Skeleton.init = true;
        
    }

    /**
     * kiveszi a paraméterként kapott aszteroidát az asteroids listából
     * @param a az eltávolítandó aszteroida
     */
    public void removeAsteroid(Asteroid a) {
    	//Skeleton.startMethod(this, "removeAsteroid", a);
        asteroids.remove(a);
        //Skeleton.endMethod(this, null);
    }

    /**
     * A saját asteroids listáját felülírja a paraméterül kapott asteroids listával
     * @param asteroids aszteroidákból álló lista, amit beállít a saját asteroids listájának
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
    // TODO Ezt ledokumentálni
    public void addAsteroid(Asteroid asteroid) {
        this.asteroids.add(asteroid);
    }

    /**
     * Visszaadja az asteroids listát.
     */
    public List<Asteroid> getAsteroids() {
        //Skeleton.startMethod(this,  "getAsteroids", null);
        //Skeleton.endMethod(this, this.asteroids);
        return this.asteroids;
    }

}