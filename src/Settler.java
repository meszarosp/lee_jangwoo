
import java.util.*;

/**
 * A telepes a játékos által irányított entitás,
 * követnie kell az utasításait. Felelõssége nyilvántartani
 * a jelenlegi pozícióját, nyersanyagait.
 * Képes bányászni, ezzel eltenni a kibányászott nyersanyagot,
 * új dolgokat építeni, például teleportkaput és robotot,
 * valamint teleportkaput elhelyezni. Ha napvihar vagy robbanás éri,
 * felelõssége értesíteni a Game-et és a saját aszteroidáját,
 * hogy vegyék ki a nyilvántartásból, aztán meghalni.
 */
public class Settler extends Traveller {
    /**
     * A telepesnél található nyersanyagok listája.
     */
    private List<Mineral> minerals = new ArrayList<Mineral>();

    /**
     * A telepesnél található teleportkapuk listája.
     */
    private List<Teleport> teleportgates = new ArrayList<Teleport>();

    /**
     * Konstruktor amely a traveller ősre meghívja a konstruktort
     * @param a az aszteroida ahol az objektum lesz.
     */
    public Settler(Asteroid a, Game g) {
        super(a, g);
    }

    /**
     * A telepes listájához hozzááad egy teleportkaput, amennyiben az még nem része a listájának.
     * @param t Az új teleportkapu
     */
    public void addTeleport(Teleport t){
    	if (!teleportgates.contains(t))
    		teleportgates.add(t); 
    }

    /**
     * A telepes listájához hozzáad egy nyersanyagot.
     * Ha sikeresen hozzáadta igazzal, ha null volt a paraméter vagy nem volt hely hozzáadni hamissal tér vissza.
     * @param m az új nyersanyag
     * @return a hozzáadás sikerességét jelzi
     */
    public boolean addMineral(Mineral m){
        if (minerals.size() < 10 && m != null){
            minerals.add(m);
            return true;
        }else{
            return false;
        }

    }

    /**
     * A telepest radioaktív robbanás éri, amitõl meghal.
     */
    @Override
    public void hitByBlast() {
        die();
    }

    /**
     * megfúrja az aszteroidát meghívva annak onDrill metódusát.
     */
    public boolean drill() {
    	return asteroid.onDrill();
    }

    /**
     * A telepes az aktuális aszteroidáján bányaszni próbál. 
     * Ha sikeres trueval ha sikertelen falseval tér vissza.
     * @return bányászás sikerességét jelzi.
     */
    public boolean mine() {
        Mineral temp = asteroid.onMine();
        if (temp == null)
            return false;
        if(!addMineral(temp)){
            asteroid.putMineralBack(temp);
            return false;
        }
        return true;
    }

    /**
     * A telepes a nála lévõ nyersanyagokból egy robotot készít. Ha nincs megfelelõ mennyiség és típus akkor nem készíti el.
     * @return a robot készítésének sikeressége.
     */
    public boolean craftRobot() {
        //Kellõ nyersanyagok meglétének ellenõrzése
        int coalCount = 0;
        int ironCount = 0;
        int uraniumCount = 0;
        int i = 0;
        while((coalCount < 1 || ironCount < 1 || uraniumCount < 1) && i < minerals.size()){
            if(minerals.get(i).toString().contains("uranium") && uraniumCount < 1){
            	++uraniumCount;
            }
            else if(minerals.get(i).toString() == "iron" && ironCount < 1){
                ++ironCount;
            }
            else if(minerals.get(i).toString() == "coal"  && coalCount < 1){
                ++coalCount;
            }
            ++i;
        }
        if(coalCount >= 1 && ironCount >= 1 && uraniumCount >= 1) {
            //Építéskor felhasznált nyersanyagok eltávolítása a minerals listából
            i = 0;
            while (coalCount >= 0 || ironCount >= 0 || uraniumCount >= 0) {
            	if(minerals.get(i).toString().contains("uranium") && uraniumCount > 0){
                	--uraniumCount;
                	minerals.remove(i);
                }
                else if(minerals.get(i).toString() == "iron" && ironCount > 0){
                    --ironCount;
                    minerals.remove(i);
                }
                else if(minerals.get(i).toString() == "coal" && coalCount > 0){
                    --coalCount;
                    minerals.remove(i);
                }
                else ++i;
            }
            Robot r = new Robot(asteroid, game);
            game.addRobot(r);
            return true;
        }
        return false;
    }

    /**
     * A telepes a nála lévõ nyersanyagokból teleportkaput készít
     * @return a teleportkapu készítés sikeressége alapján true vagy false
     */
    public boolean craftTeleport() {
        if (teleportgates.size() < 2) {
            //Kellõ nyersanyagok meglétének ellenõrzése
            int iceCount = 0;
            int ironCount = 0;
            int uraniumCount = 0;
            int i = 0;
            ArrayList<Mineral> removeMinerals = new ArrayList<Mineral>();
            while ((iceCount < 1 || ironCount < 2 || uraniumCount < 1) && i < minerals.size()) {
                if (minerals.get(i).toString().contains("uranium") && uraniumCount < 1) {
                    ++uraniumCount;
                    removeMinerals.add(minerals.get(i));
                } else if ("iron".equals(minerals.get(i).toString()) && ironCount < 2) {
                    ++ironCount;
                    removeMinerals.add(minerals.get(i));
                } else if ("ice".equals(minerals.get(i).toString()) && iceCount < 1) {
                    ++iceCount;
                    removeMinerals.add(minerals.get(i));
                }
                ++i;
            }
            if (iceCount >= 1 && ironCount >= 2 && uraniumCount >= 1) {
                minerals.removeAll(removeMinerals);
                //Építéskor felhasznált nyersanyagok eltávolítása a minerals listából
                /*i = 0;
                while ((iceCount >= 0 || ironCount >= 0 || uraniumCount >= 0) && i < minerals.size()) {
                	if(minerals.get(i).toString().contains("uranium") && uraniumCount > 0){
                    	--uraniumCount;
                    	minerals.remove(i);
                    }
                    else if(minerals.get(i).toString() == "iron" && ironCount > 0){
                        --ironCount;
                        minerals.remove(i);
                    }
                    else if(minerals.get(i).toString() == "ice" && iceCount > 0){
                        --iceCount;
                        minerals.remove(i);
                    }
                    else ++i;
                }*/
                Teleport t1 = new Teleport();
                Teleport t2 = new Teleport();
                t1.setPair(t2);
                t2.setPair(t1);
                teleportgates.add(t1);
                teleportgates.add(t2);
                game.addTeleport(t1);
                game.addTeleport(t2);
                return true;
            }
        }
        return false;
    }

    /**
     * A telepes lehelyez egy nála lévõ teleportkaput
     * @param t A lehelyezendõ kapu
     */
    public void placeTeleport(Teleport t) {
        t.setNeighbour(asteroid);
    }

    /**
     * A telepes meghal. Ekkor kivesszük az aszteroida és a game tárolóiból.
     */
    @Override
    public void die() {
        asteroid.removeTraveller(this);
        game.removeSettler(this);
    }

    /**
     * A telepes egy nála lévõ nyersanyagot elhelyez
     * az éppen aktuális aszteroida magjában
     * @param m A visszahelyezendõ nyersanyag
     * @return bool aszerint, hogy a visszahelyezés sikeres volt-e
     */
    public boolean putMineralBack(int i) {
    	boolean success = false;
    	if (minerals.size() > i && i >= 0)
    		success = asteroid.putMineralBack(minerals.get(i));
    	if (success) {
    		minerals.remove(i);
    		return true;
    	}
    	return false;
    }

    /**
     * A telepesnél lévõ nyersanyagokat kérdezi le
     * @return minerals a nyersanyagok listája
     */
    public List<Mineral> getMinerals() {
        return minerals;
    }

    /**
     * A telepesnél lévõ teleportkapukat kérdezi le
     * @return teleportgates a teleportkapuk listája
     */
    public List<Teleport> getTeleportgates() {
        return teleportgates;
    }
}