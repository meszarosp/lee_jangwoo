
import java.util.*;

/**
 * A telepesek, robotok, UFO-k és a nap nyilvántartója.
 * A telepesek, robotok és UFO-k listáját menedzseli. Ha
 * új robot jön létre, fel kell vennie a nyilvántartásba.
 * Felelõssége inicializálni a telepeseket, UFO-kat, az
 * aszteroidákat. A játék végét ellenõrzõ metódusokért is õ felel.
 */
public class Game {
    /**
     * A játékban lévõ nap.
     */
    private Sun sun;

    private boolean gameEnded;

    /**
     * A játékban lévõ telepesek listája.
     */
    private List<Settler> settlers = new ArrayList<Settler>();

    /**
     * A játékban lévõ robotok listája.
     */
    private List<Robot> robots = new ArrayList<Robot>();

    /**
     * A játékban lévõ UFO-k listája.
     */
    private List<UFO> UFOs = new ArrayList<UFO>();

    private boolean gameEnd = false;
   /**
     * A játékban lévő lehelyezett teleportkapuk. Amik a játékos zsebében vannak, azokat is tárolja.
     */
    private List<Teleport> gates = new ArrayList<Teleport>();
    
    /**
     * Konstruktor, meghívja a Mineral osztály egy statikus függvényét,
     * amely azért fontos, mert ez inicializálja, hogy mely nyersanyagok
     * vesznek részt a játékban. A nyersanyagok típusai fontosak a Game
     * szempontjából, hogy el tudja dönteni, a játékosok megnyerték-e a játékot,
     * ezért mindig inicializáltnak kell lennie.
     * Létrehozza a napot is, amely elengedhetetlen egy játékhoz.
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
     * Új játék inicializálását végző segédfüggvény. Létrehozza a paraméterben
     * megkapott számú telepest, UFO-t, aszteroidát. Létrehoz egy új nap objektumot,
     * melynek odaadja az elkészített aszteroidákat. A telepeseket és az UFO-kat
     * elhelyezi randomzizált aszteroidákon. Az aszteroidáknak randomizált
     * nagyságú kérget ad, random nyersanyagot ad nekik (vagy üregest állít be),
     * és beállítja a szomszédságukat is.
     * Robotot és teleportkaput nem csinál, hiszen azokat a Telepesek craftolják.
     * @param nSettler	létrehozni kívánt Settlerek száma
     * @param nAsteroid	létrehozni kívánt aszteroidák száma
     * @param nUFO		létrehizni kívánt ufók száma
     */
    public void init(int nSettler, int nAsteroid, int nUFO) {
        sun = new Sun();
        List<Asteroid> asteroids = new ArrayList<Asteroid>();
        List<Mineral> allMinerals = Mineral.getAllMinerals();
        if(settlers.size() > 0) {
        	settlers = new ArrayList<Settler>();
        }
        if(UFOs.size() > 0) {
        	UFOs = new ArrayList<UFO>();
        }
        Random rand = new Random();
        for(int i = 0; i < nAsteroid; i++) {
        	asteroids.add(new Asteroid(rand.nextInt()% 6,rand.nextBoolean(),rand.nextInt() % 5 == 0 ? null : allMinerals.get(rand.nextInt(allMinerals.size())), sun));
        }
        for(int i = 0; i < 2*nAsteroid; i++) {
        	int neighbourIndex = rand.nextInt(nAsteroid);
        	asteroids.get(i%nAsteroid).addNeighbour(asteroids.get(neighbourIndex));
        	asteroids.get(neighbourIndex).addNeighbour(asteroids.get(i%nAsteroid));
        }
        for(int i = 0; i < nSettler; i++) {
        	settlers.add(new Settler(asteroids.get(rand.nextInt(asteroids.size())), this));
        }
        for(int i = 0; i < nSettler; i++) {
        	UFOs.add(new UFO(asteroids.get(rand.nextInt(asteroids.size())), this));
        }
        sun.addAsteroids(asteroids);
    }



    /**
     * Hozzáad egy robotot a robotok listájához.
     * @param r A hozzáadni kívánt robot.
     */
    public void addRobot(Robot r) {
        robots.add(r);
    }

    /**
     * Leellenőrzi, hogy a telepesek megnyerték-e a játékot.
     * Ezt úgy teszi, hogy megnézi telepesenként az adott telepes
     * aszteroidáján lévő nyersanyagokat. Ezt úgy kell érteni, hogy
     * az adott telepes hátizsákját, és az azon az aszteroidán lévő
     * másik telepesek hátizsákját is. Ha nincs meg a megfelelő számú
     * és típusú nyersanyag, megy a következő telepes aszteroidájára.
     * Az ellenőrzést a Mineral osztály statikus allMinerals listája
     * segíti. Ebben tárol a játék elejétől fogva elérhető
     * nyersanyagokból egy-egy példányt
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
                    gameEnd = true;
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
        gameEnd = true;
        return settlers.size() == 0;
    }

    public boolean getGameEnd(){
        return gameEnd;
    }
    public void setGameEnd(boolean end){
        gameEnd = end;
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