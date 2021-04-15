
import java.util.*;

/**
 * A telepesek, robotok �s a nap nyilv�ntart�ja.
 * A telepesek �s a robotok list�j�t menedzseli. Ha
 * �j robot j�n l�tre, fel kell vennie a nyilv�ntart�sba.
 * Felel�ss�ge inicializ�lni a telepeseket, az
 * aszteroid�kat. A j�t�k v�g�t ellen�rz� met�dusok�rt is � felel.
 */
public class Game {

    /**
     * Default constructor
     */
    public Game() {
    }

    /**
     * A j�t�kban l�v� nap.
     */
    private Sun sun;

    /**
     * A j�t�kban l�v� telepesek.
     */
    private List<Settler> settlers = new ArrayList<Settler>();

    /**
     * A j�t�kban l�v� robotok
     */
    private List<Robot> robots = new ArrayList<Robot>();

    /**
     * Hozz�ad egy telepest a telepesek list�j�hoz.
     * @param s A hozz�adni k�v�nt telepes.
     */



    private List<Teleport> gates = new ArrayList<Teleport>();

    public void addSettler(Settler s){
        settlers.add(s);
    }

    /**
     * Kivesz egy robotot a robotok list�j�b�l.
     * @param r A kivenni k�v�nt robot.
     */
    public void removeRobot(Robot r) {
        Skeleton.startMethod(this, "removeRobot", r);
        Skeleton.endMethod(this, null);
    }

    /**
     * Kivesz egy telepest a telepesek list�j�b�l.
     * @param s A kivenni k�v�nt telepes.
     */
    public void removeSettler(Settler s) {
        Skeleton.startMethod(this, "removeSettler", s);
        Skeleton.endMethod(this, null);
    }

    /**
     * @param n
     */
    public void init(int n) {
        // TODO implement here
    }

    /**
     * Hozz�ad egy robotot a robotok list�j�hoz.
     * @param r A hozz�adni k�v�nt robot.
     */
    public void addRobot(Robot r) {
        Skeleton.startMethod(this, "addRobot", r);
        Skeleton.endMethod(this, null);
    }

    /**
     * @return
     */
    public boolean checkWin() {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public boolean checkLose() {
        // TODO implement here
        return false;
    }

    public List<Settler> getSettlers() {
        return settlers;
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public Sun getSun() {
        return sun;
    }

    public void setSun(Sun sun) {
        this.sun = sun;
    }

    public void addTeleport(Teleport t){
        gates.add(t);
    }

    public void removeTeleport(Teleport t){
        gates.remove(t);
    }

    public List<Teleport> getGates(){
        return gates;
    }

    public void addUFO(UFO ufo) {
    }
}