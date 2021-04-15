
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
    private Teleport[] teleportgates;

    public Settler(Asteroid a) {
        super(a);
    }

    /**
     * A telepes listájához hozzááad egy teleportkaput
     * @param t Az új teleportkapu
     */
    public void addTeleport(Teleport t){
    }

    /**
     * A telepes listájához hozzáad egy egy nyersanyagot
     * @param m Az új nyersanyag
     */
    public void addMineral(Mineral m){
        if (minerals.size() < 10)
            minerals.add(m);
    }

    /**
     * A telepest radioaktív robbanás éri, amitõl meghal
     */
    public void hitByBlast() {
        Skeleton.startMethod(this, "hitByBlast", null);
        die();
        Skeleton.endMethod(this, null);
    }

    /**
     * A telepes az aktuális aszteroidáján bányaszni próbál
     */
    public void mine() {
        Skeleton.startMethod(this, "mine", null);
        asteroid.onMine();
        // El kell rakni a nyersanyagot? (Hely check?)
        Skeleton.endMethod(this, null);
    }

    /**
     * A telepes a nála lévõ nyersanyagokból egy robotot készít
     */
    public void craftRobot() {
        Skeleton.startMethod(this, "craftRobot", null);
        if (Skeleton.yesnoQuestion("Do I have the needed minerals? (yes/no)")) {
            Robot r = new Robot();
            Skeleton.names.put(r, "r");
            Skeleton.startMethod(r, "create", null);
            Skeleton.endMethod(r, null);
            asteroid.placeTraveller(r);
            game.addRobot(r);
        }
        Skeleton.endMethod(this, null);
    }

    /**
     * A telepes a nála lévõ nyersanyagokból teleportkaput készít
     */
    public void craftTeleport() {
        Skeleton.startMethod(this, "craftTeleport", null);
        if (Skeleton.yesnoQuestion("Is there space in my inventory for teleports? (yes/no)")) {
            if (Skeleton.yesnoQuestion("Do I have the needed minerals? (yes/no)")) {
                Teleport t1 = new Teleport();
                Teleport t2 = new Teleport();
                Skeleton.names.put(t1, "t1");
                Skeleton.startMethod(t1, "create", null);
                Skeleton.endMethod(t1, null);
                Skeleton.names.put(t2, "t2");
                Skeleton.startMethod(t2, "create", null);
                Skeleton.endMethod(t2, null);
                t1.setPair(t2);
                t2.setPair(t1);
            }
        }
        Skeleton.endMethod(this, null);
    }

    /**
     * A telepes lehelyez egy nála lévõ teleportkaput
     * @param t A lehelyezendõ kapu
     */
    public void placeTeleport(Teleport t) {
        Skeleton.startMethod(this, "placeTeleport", t);
        //t.addNeighbour(asteroid);
        t.setNeighbour(asteroid);
        Skeleton.endMethod(this, null);
    }

    /**
     * A telepes meghal
     */
    public void die() {
        Skeleton.startMethod(this, "die", null);
        asteroid.removeTraveller(this);
        game.removeSettler(this);
        Skeleton.endMethod(this, null);
    }

    /**
     * A telepes egy nála lévõ aszteroidát elhelyez
     * az éppen aktuális aszteroida magjában
     * @param m A visszahelyezendõ nyersanyag
     */
    public void putMineralBack(Mineral m) {
        Skeleton.startMethod(this, "putMineralBack", m);
        asteroid.putMineralBack(m);
        Skeleton.endMethod(this, null);
    }

    /**
     * A telepesnél lévõ nyersanyagokat kérdezi le
     * @return A nyersanyagok
     */
    public List<Mineral> getMinerals() {
        return minerals;
    }

}