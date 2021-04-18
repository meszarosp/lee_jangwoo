import java.util.*;

/**
 * Az aszteroida felel a rajta l�v� utaz�k nyilv�ntart�s��rt, �rtes�tenie kell �ket amennyiben az aszteroida 
 * felrobban, valamint, ha napsz�l �ri el ezt az aszteroid�t. Felel�ss�ge nyilv�ntartani a k�rg�nek vastags�g�t,
 * a magj�ban tartott nyersanyagot, valamint azt, hogy k�zel van-e a naphoz.
 * Felel�ss�ge lehet�v� tenni a telepesnek, hogy az �reges aszteroid�ba tegyen egy nyersanyagot. 
 * Ha megf�rj�k, cs�kkentenie kell a k�rg�nek vastags�g�t, ha b�ny�ssz�k, oda kell adnia a benne l�v� nyersanyagot.
 * Nyilv�n kell tartania szomsz�dait (ezek lehetnek aszteroid�k �s teleportkapuk), �s vissza is kell adnia
 * egy szomsz�dj�nak objektum�t, ha az utaz� mozogni szeretne. Ha napk�zelben van �s felsz�nre ker�l a nyersanyag,
 * akkor ezt jeleznie kell a nyersanyag fel�.
 * Megval�s�tja az INeighbour interf�szt (ahogy a Teleport oszt�ly is).
 */
public class Asteroid implements INeighbour {

	/**
     * Az aszteroida k�rg�nek vastags�g�t jellemz� sz�m.
     */
    private int shell;
    
    /**
     * Az aszteroida naphoz k�zeli �llapot�t t�rolja el. Ha �rt�ke igaz (true), 
     * akkor az aszteroida napk�zelben van, ha false, akkor napt�volban van.
     */
    private boolean closeToSun;
	
    /**
     * Az aszteroida szomsz�dainak list�ja, ezen kereszt�l tudja �rtes�teni �ket felrobban�sakor.
     * Valamint l�tez�se lehet�v� teszi, hogy az utaz� lek�rje egy szomsz�dj�t.
     */
    private List<INeighbour> neighbours = new ArrayList<INeighbour>();

    /**
     * Az aszteroid�n l�v� utaz�k list�ja. Ezen kereszt�l �rtes�ti �ket az azteroida, ha napsz�l �ri, vagy felrobban.
     */
    private List<Traveller> travellers = new ArrayList<Traveller>();
    
    /**
     * Miel�tt az aszteroida felrobban �rtes�ti a napot, hogy fel fog robbanni.
     */
    private Sun sun;
    
    /**
     * Az aszteroida magj�ban l�v� nyersanyag, ez lehet �res is, ekkor �reges az aszteroida.
     */
    private Mineral core;
    
    /**
     * Default constructor
     */
    public Asteroid() {}

    /**
     * Konstruktor, ami a param�terben kapott �rt�kek alapj�n inicializ�lja az aszteroida objektumot.
     * @param shell az aszteroida k�regvastags�ga
     * @param closeToSun az aszteroida k�zel van-e a naphoz?
     * @param core az aszteroida magja
     * @param sun az aszteroid�hoz tartoz� Nap
     */
    public Asteroid(int shell, boolean closeToSun, Mineral core, Sun sun) {
        this.shell = shell;
        this.closeToSun = closeToSun;
        this.core = core;
        this.sun = sun;
    }

    /**
     * Az aszteroida "meg lett f�rva", ez cs�kkenti a shell-t, amennyiben az nem nulla.
     * Ha nulla lesz a shell, megn�zi a closeToSun v�ltoz�t, amennyiben ez igaz, 
     * megh�vja a core exposedToSun met�dus�t, mag�t adva param�terk�nt.
     */
    public boolean onDrill() {
        if (shell > 0) {
            shell--;
            if (shell == 0 && getCloseToSun())
                core.exposedToSun(this);
            return true;
        }else{
            return false;
        }

    }

    /**
     * Ha a shell 0, a nyersanyag, amely van, vagy nincs az aszteroida magj�ban "kib�ny�sz�dik".
     * Ha volt nyersanyag, akkor visszaadja visszat�r�si �rt�kk�nt a core-t, amennyiben nem is volt,
     *  vagy a shell nem 0, akkor null-al t�r vissza. Ha volt benne nyersanyag, akkor azt null-ra �ll�tja.
     * @return
     */
    public Mineral onMine() {
        if (shell == 0) {
        	Mineral tmp = core;
        	core = null;
        	return tmp;
        }
        return null;
    }

    /**
     * Megh�vja minden Traveller-re a travellers list�ban a hitByBlast met�dusukat, 
     * ezut�n minden szomsz�dj�ra a removeNeighbour f�ggv�nyt mag�val param�terk�nt. 
     * Ezut�n a sun-nak megh�vja a removeAsteroid met�dus�t mag�val param�terk�nt.
     */
    public void radioactiveBlast() {
    	for (int i = 0; i < travellers.size(); i++)
    		travellers.get(i).hitByBlast();
    	for (int i = 0; i < neighbours.size(); i++)
    		neighbours.get(i).removeNeighbour(this);
    	sun.removeAsteroid(this);
    }

    /**
     * Neg�lja a closeToSun-t, �s ha a closeToSun true �s a shell 0 megh�vja a core-ra (ha van)
     *  az exposedToSun met�dus�t �nmag�t adva param�terk�nt.
     */
    public void setCloseToSun() {
        closeToSun = !closeToSun;
        if (closeToSun && shell == 0 && core != null)
        	core.exposedToSun(this);
    }

    /**
     * Megpr�b�lja betenni a core-ba a param�ter�l kapott Mineral-t. Ez akkor siker�l, ha a core �res,
     * �s az aszteroid�nak ki van f�rva a k�rge. Ha siker�l, akkor true-val t�r vissza, egy�bk�nt false-al,
     * nem m�dos�tja a core-ban l�v� Mineral-t.
     * @param m az aszteroida magj�ba (core) betenni k�v�nt nyersanyag
     * @return bool a nyersanyag viszzatev�s�nek sikeress�g�r�l
     */
    public boolean putMineralBack(Mineral m) {
        if (core == null && shell == 0) {
        	core = m;
        	if (closeToSun)
        	    core.exposedToSun(this);
        	return true;
        }
        return false;
    }

    /**
     * A f�ggv�nyt megh�vva az aszteroida magja null �rt�ket vesz fel.
     */
    public void removeMineral() {
        core = null;
    }

    
    /**
     * A neighbours list�b�l visszaadja az i-edik szomsz�dot, ha nincs ilyen elem, akkor null-al t�r vissza.
     * @param i a lek�rdezni k�v�nt szomsz�d sorsz�ma a neighbours list�ban.
     */
    public INeighbour getNeighbourAt(int i) {
        if (i < neighbours.size() && i >= 0)
        	return neighbours.get(i);
        return null;
    }

    public int getShell() {
        return shell;
    }

    /**
     * A megval�s�tott interf�sz f�ggv�nye, elhelyezi a traveller-t a list�j�ban �s megh�vja
     * erre az utaz�ra a setAsteroid f�ggv�ny�t saj�t mag�t param�ter�l adva.
     * @param traveller az elhelyezni k�v�nt utaz�
     */
    @Override
    public void placeTraveller(Traveller traveller){
    	if (!travellers.contains(traveller)) {
    		travellers.add(traveller);
    		traveller.setAsteroid(this);
    	}
    }
    
    /**
     * Kiveszi a kapott utaz�t a travellers list�b�l.
     * @param traveller az elt�vol�tani k�v�nt traveller
     */
    public void removeTraveller(Traveller traveller) {
        travellers.remove(traveller);
    }

    /**
     * A megval�s�tott interf�sz f�ggv�nye, kiveszi a param�ter�l kapott szomsz�dot a neighbours list�b�l.
     * @param neighbour az elt�vol�tani k�v�nt szomsz�d
     */
    @Override
    public void removeNeighbour(INeighbour neighbour) {
    	neighbours.remove(neighbour);
    }
    
    /**
     * Visszaadja az aszteroida szomsz�dainak sz�m�t.
     * @return a szomsz�dok sz�ma
     */
    public int getNeighbourCount() {
    	return neighbours.size();
    }
    
    /**
     * A teleport mozgat�s��rt felel�s met�dus. Megh�vja a param�ter�l kapott teleporton a
     * setNeighbour met�dust mag�t adva param�ter�l. Beteszi a teleportot a neighbours list�j�ba �s igazzal t�r vissza.
     * @param t a mozgatand� teleport
     * @return a mozgat�s sikeress�ge
     */
    @Override
	public boolean moveTeleport(Teleport t) {
		t.setNeighbour(this);
		neighbours.add(t);
		return true;
	}
    
    /**
     * Ha a core nem �res, vagy a shell nem 0, az �sszes Traveller-re a travellers list�ban megh�vja
     * a Traveller die met�dus�t. 
     * Ha az i nem egyenl� 0-val, akkor az �sszes szomsz�dj�ra megh�vja a solarWind met�dust eggyel 
     * cs�kkentett i-vel. (ez egy sz�less�gi bej�r�s).
     * @param i napsz�l m�lys�ge (hogy mekkora ter�letet �r majd el)
     */
    @Override
    public void solarWind(int i) {
        if (core != null || shell != 0)
        	for (int j = 0; j < travellers.size(); j++)
        		travellers.get(j).die();
        if (i > 0)
        	for (int j = 0; j < neighbours.size(); j++)
        		neighbours.get(j).solarWind(i - 1);
    }

    /**
     * a megval�s�tott interf�sz f�ggv�nye, hozz�adja a neighbours list�j�hoz a szomsz�dot,
     * amennyiben az m�g nem r�sze.
     * @param neighbour a hozz�adni k�v�nt szomsz�d
     */
    public void addNeighbour(INeighbour neighbour){
    	if (!neighbours.contains(neighbour))
    		neighbours.add(neighbour);
    }
    
    /** Visszaadja az aszteroida magj�t.
     * @return az aszteroida magja
     */
    public Mineral getCore() {
        return core;
    }
    
    /**
     * Lek�rdezi hogy napk�zel van-e?
     * @return boolean aszerint hogy napk�zel (true) vagy napt�vol (false) van
     */
    public boolean getCloseToSun(){
    	return closeToSun;
    }
}