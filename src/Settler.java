
import java.util.*;

/**
 * A telepes a j�t�kos �ltal ir�ny�tott entit�s,
 * k�vetnie kell az utas�t�sait. Felel�ss�ge nyilv�ntartani
 * a jelenlegi poz�ci�j�t, nyersanyagait.
 * K�pes b�ny�szni, ezzel eltenni a kib�ny�szott nyersanyagot,
 * �j dolgokat �p�teni, p�ld�ul teleportkaput �s robotot,
 * valamint teleportkaput elhelyezni. Ha napvihar vagy robban�s �ri,
 * felel�ss�ge �rtes�teni a Game-et �s a saj�t aszteroid�j�t,
 * hogy vegy�k ki a nyilv�ntart�sb�l, azt�n meghalni.
 */
public class Settler extends Traveller {

    /**
     * Default constructor
     */
    public Settler() {
    }

    /**
     * A telepesn�l tal�lhat� nyersanyagok list�ja
     */
    private List<Mineral> minerals;

    /**
     * A telepesn�l tal�lhat� teleportkapuk list�ja
     */
    private Teleport[] teleportgates;

    public Settler(Asteroid a) {
        super(a);
    }

    /**
     * A telepes list�j�hoz hozz��ad egy teleportkaput
     * @param t Az �j teleportkapu
     */
    public void addTeleport(Teleport t){
    }

    /**
     * A telepes list�j�hoz hozz�ad egy egy nyersanyagot
     * @param m Az �j nyersanyag
     */
    public void addMineral(Mineral m){
        if (minerals.size() < 10)
            minerals.add(m);
    }

    /**
     * A telepest radioakt�v robban�s �ri, amit�l meghal
     */
    public void hitByBlast() {
        Skeleton.startMethod(this, "hitByBlast", null);
        die();
        Skeleton.endMethod(this, null);
    }

    /**
     * A telepes az aktu�lis aszteroid�j�n b�nyaszni pr�b�l
     */
    public void mine() {
        Skeleton.startMethod(this, "mine", null);
        asteroid.onMine();
        // El kell rakni a nyersanyagot? (Hely check?)
        Skeleton.endMethod(this, null);
    }

    /**
     * A telepes a n�la l�v� nyersanyagokb�l egy robotot k�sz�t
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
     * A telepes a n�la l�v� nyersanyagokb�l teleportkaput k�sz�t
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
     * A telepes lehelyez egy n�la l�v� teleportkaput
     * @param t A lehelyezend� kapu
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
     * A telepes egy n�la l�v� aszteroid�t elhelyez
     * az �ppen aktu�lis aszteroida magj�ban
     * @param m A visszahelyezend� nyersanyag
     */
    public void putMineralBack(Mineral m) {
        Skeleton.startMethod(this, "putMineralBack", m);
        asteroid.putMineralBack(m);
        Skeleton.endMethod(this, null);
    }

    /**
     * A telepesn�l l�v� nyersanyagokat k�rdezi le
     * @return A nyersanyagok
     */
    public List<Mineral> getMinerals() {
        return minerals;
    }

}