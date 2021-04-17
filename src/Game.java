
import java.util.*;

/**
 * A telepesek, robotok és a nap nyilvántartója.
 * A telepesek és a robotok listáját menedzseli. Ha
 * új robot jön létre, fel kell vennie a nyilvántartásba.
 * Felelõssége inicializálni a telepeseket, az
 * aszteroidákat. A játék végét ellenõrzõ metódusokért is õ felel.
 */
public class Game {
    /**
     * A játékban lévõ nap.
     */
    private Sun sun;

    /**
     * A játékban lévõ telepesek.
     */
    private List<Settler> settlers = new ArrayList<Settler>();

    /**
     * A játékban lévõ robotok.
     */
    private List<Robot> robots = new ArrayList<Robot>();

    /**
     * A játékban lévõ UFO-k.
     */
    private List<UFO> UFOs = new ArrayList<UFO>();


    /**
     * A játékban lévő lehelyezett teleportkapuk. Amik a játékosnál vannak azok is benne vannak.
     */
    private List<Teleport> gates = new ArrayList<Teleport>();

    /**
     * Konstruktor, létrehozza a napot is.
     */
    public Game() {
        Mineral.Init();
        sun = new Sun();
    }
    
    /**
     * Hozzáad egy telepest a telepesek listájához, ha még nem része.
     * @param s A hozzáadni kívánt telepes.
     */
    public void addSettler(Settler s){
    	if (!settlers.contains(s))
    		settlers.add(s);
    }

    /**
     * Kivesz egy robotot a robotok listájából.
     * @param r A kivenni kívánt robot.
     */
    public void removeRobot(Robot r) {
        robots.remove(r);
    }

    /**
     * Kivesz egy telepest a telepesek listájából.
     * @param s A kivenni kívánt telepes.
     */
    public void removeSettler(Settler s) {
        settlers.remove(s);
    }

    /**
     * 
     * @param nSettler
     * @param nAsteroid
     */
    public void init(int nSettler, int nAsteroid) {
        sun = new Sun();
        List<Asteroid> asteroids = new ArrayList<Asteroid>();
    }


    /**
     * Hozzáad egy robotot a robotok listájához.
     * @param r A hozzáadni kívánt robot.
     */
    public void addRobot(Robot r) {
        robots.add(r);
    }

    /**
     * @return
     */
    public boolean checkWin() {
    	List<Mineral> allMinerals = Mineral.getAllMinerals();
    	int allMineralCount = allMinerals.size();
    	int[] counter = new int[allMineralCount];
        for(Settler s1 : settlers) {
        	Asteroid currAsteroid = s1.getAsteroid();
        	List<Mineral> backpack = s1.getMinerals();
        	for(Mineral backPackItem : backpack) {
        		for(int i = 0; i < allMineralCount; i++) {
        			if(backPackItem.toString().equals(allMinerals.get(i).toString()) ||
        			(backPackItem.toString().contains("uranium")&&allMinerals.get(i).toString().contains("uranium"))) {
        				counter[i]++;
        			}
        		}
        	}
        	for(Settler s2 : settlers) {
        		if(!s1.equals(s2)) {
        			if(currAsteroid.equals(s2.getAsteroid())) {
        				backpack = s2.getMinerals();
        				for(Mineral backPackItem : backpack) {
        	        		for(int i = 0; i < allMineralCount; i++) {
        	        			if(backPackItem.toString().equals(allMinerals.get(i).toString()) ||
        	        			(backPackItem.toString().contains("uranium")&&allMinerals.get(i).toString().contains("uranium"))) {
        	        				counter[i]++;
        	        			}
        	        		}
        	        	}
        			}
        		}
        	}
        	for(int i = 0; i < allMineralCount; i++) {
        		if(counter[i] < 3) {
        			break;
        		}
        		if(i == allMineralCount-1) {
        			return true;
        		}
        	}
        	
        }
        return false;
    }

    /**
     * @return
     */
    public boolean checkLose() {
        return settlers.size() == 0;
    }

    public List<Settler> getSettlers() {
        return settlers;
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public List<UFO> getUFOs() { return UFOs;}

    public Sun getSun() {
        return sun;
    }

    public void setSun(Sun sun) {
        this.sun = sun;
    }

    public void addTeleport(Teleport t){
        if (!gates.contains(t))
            gates.add(t);
    }

    public void removeTeleport(Teleport t){
        gates.remove(t);
    }

    public List<Teleport> getGates(){
        return gates;
    }

    public void addUFO(UFO ufo) {
        if (!UFOs.contains(ufo))
            UFOs.add(ufo);
    }
    public void removeUFO(UFO ufo) {
    	UFOs.remove(ufo);
    }
}