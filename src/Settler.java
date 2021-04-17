
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
    private List<Mineral> minerals = new ArrayList<Mineral>();

    /**
     * A telepesn�l tal�lhat� teleportkapuk list�ja
     */
    private List<Teleport> teleportgates = new ArrayList<Teleport>();

    public Settler(Asteroid a) {
        super(a);
    }

    /**
     * A telepes list�j�hoz hozz��ad egy teleportkaput
     * @param t Az �j teleportkapu
     */
    public void addTeleport(Teleport t){
        teleportgates.add(t); 
    }

    /**
     * A telepes list�j�hoz hozz�ad egy egy nyersanyagot.
     * Ha volt hely igazzal, ha nem akkor hamissal t�r viszza
     * @param m Az �j nyersanyag
     * @return a hozz�ad�s sikeress�g�t jelzi
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
     * A telepest radioakt�v robban�s �ri, amit�l meghal
     */
    public void hitByBlast() {
        die();
    }

    /**
     * megf�rja az aszteroid�t
     */
    public boolean drill() {
    	return asteroid.onDrill();
    }

    /**
     * A telepes az aktu�lis aszteroid�j�n b�nyaszni pr�b�l
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
     * A telepes a n�la l�v� nyersanyagokb�l egy robotot k�sz�t
     * @return
     */
    public boolean craftRobot() {
        if (minerals.size()>=3) {

                //Kell� nyersanyagok megl�t�nek ellen�rz�se, illetve kigy�jt�se
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
                    //�p�t�skor felhaszn�lt nyersanyagok elt�vol�t�sa a minerals list�b�l
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
     * A telepes a n�la l�v� nyersanyagokb�l teleportkaput k�sz�t
     */
    public boolean craftTeleport() {
        if (teleportgates.size() < 2) {

            if (minerals.size()>=4) {

                //Kell� nyersanyagok megl�t�nek ellen�rz�se, illetve kigy�jt�se
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
                    //�p�t�skor felhaszn�lt nyersanyagok elt�vol�t�sa a minerals list�b�l
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
     * A telepes lehelyez egy n�la l�v� teleportkaput
     * @param t A lehelyezend� kapu
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
     * A telepes egy n�la l�v� nyersanyagot elhelyez
     * az �ppen aktu�lis aszteroida magj�ban
     * @param m A visszahelyezend� nyersanyag
     */
    public boolean putMineralBack(Mineral m) {
        return asteroid.putMineralBack(m);
    }

    /**
     * A telepesn�l l�v� nyersanyagokat k�rdezi le
     * @return A nyersanyagok
     */
    public List<Mineral> getMinerals() {
        return minerals;
    }

}