
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
    private List<Mineral> minerals;

    /**
     * A telepesnél található teleportkapuk listája
     */
    private List<Teleport> teleportgates;

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
    public void drill() {
    	asteroid.onDrill();
    }

    /**
     * A telepes az aktuális aszteroidáján bányaszni próbál
     */
    public boolean mine() {
        Mineral temp = asteroid.onMine();
        if(!addMineral(temp)){
            putMineralBack(temp);
            return false;
        }
        return true;
    }

    /**
     * A telepes a nála lévõ nyersanyagokból egy robotot készít
     */
    public void craftRobot() {
        if (minerals.size()>=3)) {

                //Kellõ nyersanyagok meglétének ellenõrzése, illetve kigyûjtése
                List<minerals> temp;
                int coalCount = 0;
                int ironCount = 0;
                int uraniumCount = 0;
                int i;
                while(ironCount<2 || iceCount<1 || uraniumCount<1){
                    if(coalCount<1 && minerals[i].getClass() == Coal.class){
                        temp.add(minerals[i]);
                        ++coalCount;
                    }
                    if(ironCount<1 && minerals[i].getClass() == Iron.class){
                        temp.add(minerals[i]);
                        ++ironCount;
                    }
                    if(uraniumCount<1 && minerals[i].getClass() == Uranium.class){
                        temp.add(minerals[i]);
                        ++ironCount;
                    }
                    ++i;
                    if(i>=minerals.size())
                        break;
                }

                if(temp.size()==3){
                    //Építéskor felhasznált nyersanyagok eltávolítása a minerals listából
                    int j = 0;
                    while(temp.size()!=0){
                        if(minerals[j].getClass() == temp[0]){
                            temp.remove(0);
                            minerals.remove(j);
                        }
                        ++j;
                    }
            Robot r = new Robot();
            asteroid.placeTraveller(r);
            game.addRobot(r);
        }
    }

    /**
     * A telepes a nála lévõ nyersanyagokból teleportkaput készít
     */
    public void craftTeleport() {
        if (teleportgates.size() < 2) {

            if (minerals.size()>=4)) {

                //Kellõ nyersanyagok meglétének ellenõrzése, illetve kigyûjtése
                List<minerals> temp;
                int ironCount = 0;
                int iceCount = 0;
                int uraniumCount = 0;
                int i;
                while(ironCount<2 || iceCount<1 || uraniumCount<1){
                    if(ironCount<2 && minerals[i].getClass() == Iron.class){
                        temp.add(minerals[i]);
                        ++ironCount;
                    }
                    if(iceCount<1 && minerals[i].getClass() == Ice.class){
                        temp.add(minerals[i]);
                        ++iceCount;
                    }
                    if(uraniumCount<1 && minerals[i].getClass() == Uranium.class){
                        temp.add(minerals[i]);
                        ++ironCount;
                    }
                    ++i;
                    if(i>=minerals.size())
                        break;
                }

                if(temp.size()==4){
                    //Építéskor felhasznált nyersanyagok eltávolítása a minerals listából
                    int j = 0;
                    while(temp.size()!=0){
                        if(minerals[j].getClass() == temp[0]){
                            temp.remove(0);
                            minerals.remove(j);
                        }
                        ++j;
                    }

                    Teleport t1 = new Teleport();
                    Teleport t2 = new Teleport();
                    Skeleton.names.put(t1, "t1");
                    Skeleton.endMethod(t1, null);
                    Skeleton.names.put(t2, "t2");
                    t1.setPair(t2);
                    t2.setPair(t1);
                    teleportgates.add(t1);
                    teleportgates.add(t2);
                }
            }
        }
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
    public void putMineralBack(Mineral m) {
        asteroid.putMineralBack(m);
    }

    /**
     * A telepesnél lévõ nyersanyagokat kérdezi le
     * @return A nyersanyagok
     */
    public List<Mineral> getMinerals() {
        return minerals;
    }

}