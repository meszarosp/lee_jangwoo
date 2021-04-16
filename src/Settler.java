
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
    private List<Teleport> teleportgates;

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
    public void drill() {
    	asteroid.onDrill();
    }

    /**
     * A telepes az aktu�lis aszteroid�j�n b�nyaszni pr�b�l
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
     * A telepes a n�la l�v� nyersanyagokb�l egy robotot k�sz�t
     */
    public void craftRobot() {
        if (minerals.size()>=3)) {

                //Kell� nyersanyagok megl�t�nek ellen�rz�se, illetve kigy�jt�se
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
                    //�p�t�skor felhaszn�lt nyersanyagok elt�vol�t�sa a minerals list�b�l
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
     * A telepes a n�la l�v� nyersanyagokb�l teleportkaput k�sz�t
     */
    public void craftTeleport() {
        if (teleportgates.size() < 2) {

            if (minerals.size()>=4)) {

                //Kell� nyersanyagok megl�t�nek ellen�rz�se, illetve kigy�jt�se
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
                    //�p�t�skor felhaszn�lt nyersanyagok elt�vol�t�sa a minerals list�b�l
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
    public void putMineralBack(Mineral m) {
        asteroid.putMineralBack(m);
    }

    /**
     * A telepesn�l l�v� nyersanyagokat k�rdezi le
     * @return A nyersanyagok
     */
    public List<Mineral> getMinerals() {
        return minerals;
    }

}