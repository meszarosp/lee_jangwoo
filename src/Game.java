
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
     * Default constructor
     */
    public Game() {
    }

    /**
     * A játékban lévõ nap.
     */
    private Sun sun;

    /**
     * A játékban lévõ telepesek.
     */
    private List<Settler> settlers = new ArrayList<Settler>();

    /**
     * A játékban lévõ robotok
     */
    private List<Robot> robots = new ArrayList<Robot>();

    /**
     * Hozzáad egy telepest a telepesek listájához.
     * @param s A hozzáadni kívánt telepes.
     */



    private List<Teleport> gates = new ArrayList<Teleport>();

    public void addSettler(Settler s){
        settlers.add(s);
    }

    /**
     * Kivesz egy robotot a robotok listájából.
     * @param r A kivenni kívánt robot.
     */
    public void removeRobot(Robot r) {
        Skeleton.startMethod(this, "removeRobot", r);
        Skeleton.endMethod(this, null);
    }

    /**
     * Kivesz egy telepest a telepesek listájából.
     * @param s A kivenni kívánt telepes.
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
     * Hozzáad egy robotot a robotok listájához.
     * @param r A hozzáadni kívánt robot.
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