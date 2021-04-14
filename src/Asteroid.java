
import java.util.*;

/**
 * Az aszteroida felel a rajta lévő utazók nyilvántartásáért, értesítenie kell őket amennyiben az aszteroida 
 * felrobban, valamint, ha napszél éri el ezt az aszteroidát. Felelőssége nyilvántartani a kérgének vastagságát,
 * a magjában tartott nyersanyagot, valamint azt, hogy közel van-e a naphoz.
 * Felelőssége lehetővé tenni a telepesnek, hogy az üreges aszteroidába tegyen egy nyersanyagot. 
 * Ha megfúrják, csökkentenie kell a kérgének vastagságát, ha bányásszák, oda kell adnia a benne lévő nyersanyagot.
 * Nyilván kell tartania szomszédait (ezek lehetnek aszteroidák és teleportkapuk), és vissza is kell adnia
 * egy szomszédjának objektumát, ha az utazó mozogni szeretne. Ha napközelben van és felszínre kerül a nyersanyag,
 * akkor ezt jeleznie kell a nyersanyag felé.
 * Megvalósítja az INeighbour interfészt (ahogy a Teleport osztály is).
 */
public class Asteroid implements INeighbour {

    /**
     * Default constructor
     */
    public Asteroid() {
    	neighbours = new ArrayList<INeighbour>();
    	travellers = new ArrayList<Traveller>();
    }

    /**
     * Constructor, mely a paraméterben kapott nyersanyagot az aszteroida magjába helyezi.
     * @param c a leendő mag (nyersanyag típus)
     */
    public Asteroid(Mineral c){
        core = c;
        neighbours = new ArrayList<INeighbour>();
    	travellers = new ArrayList<Traveller>();
    }

    /**
     * Az aszteroida kérgének vastagságát jellemző szám.
     */
    private int shell;

    /**
     * Az aszteroida naphoz közeli állapotát tárolja el. Ha értéke igaz (true), 
     * akkor az aszteroida napközelben van, különben ha false, akkor naptávolban van.
     */
    private boolean closeToSun;

    /**
     * Az aszteroida szomszédainak listája, ezen keresztül tudja értesíteni őket felrobbanásakor.
     * Valamint létezése lehetővé teszi, hogy az utazó lekérje egy szomszédját.
     */
    private List<INeighbour> neighbours = new ArrayList<INeighbour>();

    /**
     * Az aszteroida magjában lévő nyersanyag, ez lehet üres is, ekkor üreges az aszteroida.
     */
    private Mineral core;

    /**
     * Az aszteroidán lévő utazók listája. Ezen keresztül értesíti őket az azteroida, ha napszél éri, vagy felrobban.
     */
    private List<Traveller> travellers = new ArrayList<Traveller>();

    /**
     * Mielőtt az aszteroida felrobban értesíti a napot, hogy fel fog robbanni.
     */
    private Sun sun;

    /**
     * Az aszteroida "meg lett fúrva", ez csökkenti a shell-t, amennyiben az nem nulla.
     * Ha nulla lesz a shell, megnézi a closeToSun változót, amennyiben ez igaz, 
     * meghívja a core exposedToSun metódusát, magát adva paraméterként.
     */
    public void onDrill() {
        Skeleton.startMethod(this, "onDrill", null);
        shell = Skeleton.intQuestion("How thick is the shell before reduce by one?(int)");
        if (shell > 0)
        	shell--;
        if (shell == 0 && Skeleton.yesnoQuestion("Is the asteroid close to sun?(yes/no)") && !Skeleton.yesnoQuestion("Is the asteroiod hollow?(yes/no)"))
        	core.exposedToSun(this);
        Skeleton.endMethod(this, null);

    }

    /**
     * ha a shell 0, a nyersanyag, amely van, vagy nincs az aszteroida magjában "kibányászódik".
     * Ha volt nyersanyag, akkor visszaadja visszatérési értékként a core-t, amennyiben nem is volt,
     *  vagy a shell nem 0, akkor null-al tér vissza. Ha volt benne nyersanyag, akkor azt null-ra állítja.
     * @return core ha van és hozzáférhető, különben null
     */
    public Mineral onMine() {
    	Skeleton.startMethod(this, "onMine", null);
        if (Skeleton.intQuestion("How thick is the shell?(int)") <= 0) {
            if (Skeleton.yesnoQuestion("Is the asteroid hollow?(yes/no)")){
                Skeleton.endMethod(this, null);
                return null;
            }else{
                core = Skeleton.mineralQuestion();
                Skeleton.endMethod(this, core);
                return core;
            }
        }
        Skeleton.endMethod(this, null);
        return null;
    }

    /**
     * Ha a core nem üres, vagy a shell nem 0, az összes Traveller-re a travellers listában meghívja
     * a Traveller die metódusát. Egyébként csak visszatér.
     */
    public void solarWind() {
    	Skeleton.startMethod(this, "solarWind", null);
        if (!Skeleton.yesnoQuestion("Is the asteroid hollow?(yes/no)") || Skeleton.intQuestion("How thick is the shell?(int)") != 0)
            for (int startingSize = travellers.size(); startingSize > 0; startingSize--)
                travellers.get(0).die();
        Skeleton.endMethod(this, null);
    }

    /**
     * Meghívja minden Traveller-re a travellers listában a hitByBlast metódusukat, 
     * ezután minden szomszédjára a removeNeighbour függvényt magával paraméterként. 
     * Ezután a sun-nak meghívja a removeAsteroid metódusát.
     */
    public void radioactiveBlast() {
    	Skeleton.startMethod(this, "radioactiveBlast", null);
        for (int startingSize = travellers.size(); startingSize > 0; startingSize--)
            travellers.get(0).hitByBlast();
    	//for (Traveller t : travellers)
    		//t.hitByBlast();
    	for (INeighbour n : neighbours)
    		n.removeNeighbour(this);
        //travellers.get(0).hitByBlast();
        //neighbours.get(0).removeNeighbour(this);
    	//sun.removeAsteroid(this);
    	Skeleton.endMethod(this, null);
    }

    /**
     * Negálja a closeToSun-t, és ha a closeToSun true és a shell 0 meghívja a core-ra (ha van)
     *  az exposedToSun metódusát önmagát adva paraméterként.
     */
    public void setCloseToSun() {
    	Skeleton.startMethod(this, "setCloseToSun", null);
        //closeToSun = !closeToSun;
        if (Skeleton.yesnoQuestion("Is the asteroid close to sun?(yes/no)") && Skeleton.intQuestion("How thick is the shell?(int)") == 0)
        	if (!Skeleton.yesnoQuestion("Is the asteroiod hollow?(yes/no)"))
        		core.exposedToSun(this);
        Skeleton.endMethod(this, null);
    }

    /**
     * Visszaadja az aszteorida closeToSun változóját.
     * @return a closeToSun tag értéke.
     */
    public boolean getCloseToSun() {
        return closeToSun;
    }

    /**
     * Megpróbálja betenni a core-ba a paraméterül kapott Mineral-t. Ez akkor sikerül, ha a core üres,
     * és az aszteroidának ki van fúrva a kérge. Ha sikerül, akkor true-val tér vissza, egyébként false-al,
     * nem módosítja a core-ban lévő Mineral-t.
     * @param m az aszteroida magjába (core) betenni kívánt nyersanyag
     * @return bool a nyersanyag viszzatevésének sikerességéről
     */
    public boolean putMineralBack(Mineral m) {
    	Skeleton.startMethod(this, "putMineralBack", m);
        if (Skeleton.yesnoQuestion("Is the asteroiod hollow?(yes/no)") && Skeleton.intQuestion("How thick is the shell?(int)") == 0) {
        	core = m;
        	Skeleton.endMethod(this, true);
        	return true;
        }
        Skeleton.endMethod(this, false);
        return false;
    }

    /**
     * A függvényt meghívva az aszteroida magja null értéket vesz fel.
     */
    public void removeMineral() {
    	Skeleton.startMethod(this, "removeMineral", null);
        core = null;
        Skeleton.endMethod(this, null);
    }

    
    //---------------itt a ha nincs olyan elem résszel bővítettem, és nem volt visszatérési érték, ugyh tettem------
    /**
     * A neighbours listából visszaadja az i-edik szomszédot, ha nincs ilyen elem, akkor null-al tér vissza.
     * @param i a lekérdezni kívánt szomszéd sorszáma a neighbours listában.
     */
    public INeighbour getNeighbourAt(int i) {
    	Skeleton.startMethod(this, "getNeighbourAt", i);
        if ( 0 <= i && i < neighbours.size()) {
        	Skeleton.endMethod(this, neighbours.get(i));
        	return neighbours.get(i);
        }
        Skeleton.endMethod(this, null);
        return null;
    }

    /**
     * Kiveszi a kapott utazót a travellers listából.
     * @param traveller az eltávolítani kívánt traveller
     */
    public void removeTraveller(Traveller traveller) {
    	Skeleton.startMethod(this, "removeTraveller", traveller);
        travellers.remove(traveller);
        Skeleton.endMethod(this, null);
    }

    /**Visszaadja az aszteroida magját (core)
     * @return Mineral az aszteroida magja (core)
     */
    public Mineral getCore() {
    	Skeleton.startMethod(this, "getCore", null);
    	Skeleton.endMethod(this, core);
        return core;
    }

    /**
     * A megvalósított interfész függvénye, elhelyezi a traveller-t a listájában és meghívja
     * erre az utazóra a setAsteroid függvényét saját magát paraméterül adva.
     * @param traveller az elhelyezni kívánt utazó
     */
    public void placeTraveller(Traveller traveller){
    	Skeleton.startMethod(this, "placeTraveller", traveller);
    	travellers.add(traveller);
    	traveller.setAsteroid(this);
    	Skeleton.endMethod(this, null);
    }

    /**
     * A megvalósított interfész függvénye, kiveszi a paraméterül kapott szomszédot a neighbours listából.
     * @param neighbour az eltávolítani kívánt szomszéd
     */
    public void removeNeighbour(INeighbour neighbour) {
    	Skeleton.startMethod(this, "removeNeighbour", neighbour);
    	neighbours.remove(neighbour);
    	Skeleton.endMethod(this, null);
    }

    @Override
    public void solarWind(int i) {

    }

    @Override
    public boolean moveTeleport(Teleport t) {
        return false;
    }

    /**
     * a megvalósított interfész függvénye, hozzáadja a neighbours listájához a szomszédot.
     * @param neighbour a hozzáadni kívánt szomszéd
     */
    public void addNeighbour(INeighbour neighbour){
    	Skeleton.startMethod(this, "addNeighbour", neighbour);
    	neighbours.add(neighbour);
    	Skeleton.endMethod(this, null);
    }

}