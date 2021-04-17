
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
     * Default constructor
     */
    public Settler() {
    }

    /**
     * A telepesnél található nyersanyagok listája
     */
    private List<Mineral> minerals = new ArrayList<Mineral>();

    /**
     * A telepesnél található teleportkapuk listája
     */
    private List<Teleport> teleportgates = new ArrayList<Teleport>();

    public Settler(Asteroid a) {
        super(a);
    }

    /**
     * A telepes listájához hozzááad egy teleportkaput
     * @param t Az új teleportkapu
     */
    public void addTeleport(Teleport t){
        teleportgates.add(t); 
    }

    /**
     * A telepes listájához hozzáad egy egy nyersanyagot.
     * Ha volt hely igazzal, ha nem akkor hamissal tér viszza
     * @param m Az új nyersanyag
     * @return a hozzáadás sikerességét jelzi
     */
    public boolean addMineral(Mineral m){
        if (minerals.size() < 10){
            minerals.add(m);
            return true;
        }else{
            return false;
        }

    }

    /**
     * A telepest radioaktív robbanás éri, amitõl meghal
     */
    public void hitByBlast() {
        die();
    }

    /**
     * megfúrja az aszteroidát
     */
    public boolean drill() {
    	return asteroid.onDrill();
    }

    /**
     * A telepes az aktuális aszteroidáján bányaszni próbál
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
     * A telepes a nála lévõ nyersanyagokból egy robotot készít
     * @return
     */
    public boolean craftRobot() {
        if (minerals.size()>=3) {

                //Kellõ nyersanyagok meglétének ellenõrzése, illetve kigyûjtése
                List<Mineral> temp = new ArrayList<Mineral>();
                int coalCount = 0;
                int ironCount = 0;
                int uraniumCount = 0;
                int i = 0;
                while(coalCount<1 || ironCount<1 || uraniumCount<1){
                    if(coalCount<1 && minerals.get(i).getClass() == Coal.class){
                        temp.add(minerals.get(i));
                        ++coalCount;
                    }
                    if(ironCount<1 && minerals.get(i).getClass() == Iron.class){
                        temp.add(minerals.get(i));
                        ++ironCount;
                    }
                    if(uraniumCount<1 && minerals.get(i).getClass() == Uranium.class){
                        temp.add(minerals.get(i));
                        ++uraniumCount;
                    }
                    ++i;
                    if(i>=minerals.size())
                        break;
                }

                if(temp.size()==3) {
                    //Építéskor felhasznált nyersanyagok eltávolítása a minerals listából
                    int j = 0;
                    while (temp.size() != 0) {
                        if (minerals.get(j).getClass() == temp.get(0).getClass()) {
                            temp.remove(0);
                            minerals.remove(j);
                            /////
                            j = 0;
                            /////
                        }
                        ++j;
                    }
                    Robot r = new Robot();
                    asteroid.placeTraveller(r);
                    game.addRobot(r);
                    return true;
                }
        }
        return false;
    }

    /**
     * A telepes a nála lévõ nyersanyagokból teleportkaput készít
     */
    public boolean craftTeleport() {
        if (teleportgates.size() < 2) {

            if (minerals.size()>=4) {

                //Kellõ nyersanyagok meglétének ellenõrzése, illetve kigyûjtése
                List<Mineral> temp = new ArrayList<Mineral>();
                int ironCount = 0;
                int iceCount = 0;
                int uraniumCount = 0;
                int i = 0;
                while (ironCount < 2 || iceCount < 1 || uraniumCount < 1) {
                    if (ironCount < 2 && minerals.get(i).getClass() == Iron.class) {
                        temp.add(minerals.get(i));
                        ++ironCount;
                    }
                    if (iceCount < 1 && minerals.get(i).getClass() == Ice.class) {
                        temp.add(minerals.get(i));
                        ++iceCount;
                    }
                    if (uraniumCount < 1 && minerals.get(i).getClass() == Uranium.class) {
                        temp.add(minerals.get(i));
                        ++uraniumCount;
                    }
                    ++i;
                    if (i >= minerals.size())
                        break;
                }

                if (temp.size() == 4) {
                    //Építéskor felhasznált nyersanyagok eltávolítása a minerals listából
                    int j = 0;
                    while (temp.size() != 0) {
                        if (minerals.get(j).getClass() == temp.get(0).getClass()) {
                            temp.remove(0);
                            minerals.remove(j);
                            //////
                            j = 0;
                            /////
                        } else
                            ++j;
                    }

                    Teleport t1 = new Teleport();
                    Teleport t2 = new Teleport();
                    t1.setPair(t2);
                    t2.setPair(t1);
                    teleportgates.add(t1);
                    teleportgates.add(t2);
                    return true;
                }
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
     * A telepes meghal
     */
    public void die() {
        asteroid.removeTraveller(this);
        game.removeSettler(this);
    }

    /**
     * A telepes egy nála lévõ nyersanyagot elhelyez
     * az éppen aktuális aszteroida magjában
     * @param m A visszahelyezendõ nyersanyag
     */
    public boolean putMineralBack(Mineral m) {
        return asteroid.putMineralBack(m);
    }

    /**
     * A telepesnél lévõ nyersanyagokat kérdezi le
     * @return A nyersanyagok
     */
    public List<Mineral> getMinerals() {
        return minerals;
    }

}