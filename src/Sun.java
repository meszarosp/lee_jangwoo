import java.util.*;

/**
 * 
 */
public class Sun {

    /**
     * Default constructor
     */
    public Sun() {
    }

    /**
     * A j�t�kban szerpl� �sszes aszteroid�t tartalmaz� lista.
     */
    private List<Asteroid> asteroids;

    /**
     * Ha azt a v�laszt kapja, hogy legyen npvihar, akkor minden aszteroid�ra megh�vja a solarWind met�dust. Ez ut�n v�gigmegy�nk az �sszes aszteroid�n, megk�rdezz�k, hogy az legyen-e napk�zelben, ha azt a v�laszt kapja, hogy igen, akkor megh�vja rajta a setCloseToSun met�dust
     */
    public void makeAction() {
    	Skeleton.startMethod(this, "makeAction", null);
        if(Skeleton.yesnoQuestion("Legyen napvihar?")) {
        	for(Asteroid a : asteroids){
        		a.solarWind();
        	}
        }
        
        for(Asteroid a : asteroids){
        	if(Skeleton.yesnoQuestion("Legy�nk napk�zelben?")) {
        		a.setCloseToSun();
        	}
        }
        Skeleton.endMethod(this,  null);
    }

    /**
     * kiveszi a param�terk�nt kapott aszteroid�t az asteroids list�b�l
     * @param az elt�vol�tand� aszteroida
     */
    public void removeAsteroid(Asteroid a) {
    	Skeleton.startMethod(this, "removeAsteroid", a);
        asteroids.remove(a);
        Skeleton.endMethod(this, null);
    }

    /**
     * A saj�t asteroids list�j�t fel�l�rja a param�ter�l kapott asteroids list�val
     * @param aszteroid�kb�l �ll� lista, amit be�ll�t a saj�t asteroids list�j�nak
     */
    public void addAsteroids(List<Asteroid> asteroids) {
    	Skeleton.startMethod(this, "addAsteroids", asteroids);
    	this.asteroids=asteroids;
    	Skeleton.endMethod(this,  null);
    }

    /**
     * Visszaadja az asteroids list�t.
     */
    public List<Asteroid> getAsteroids() {
        Skeleton.startMethod(this,  "getAsteroids", null);
        Skeleton.endMethod(this, this.asteroids);
        return this.asteroids;
    }

}