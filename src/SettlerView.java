
import java.awt.*;
import java.util.*;

/**
 * Az osztály felelőssége, hogy az általa mutatott telepes objektumhoz tartozó képernyő koordinátákat eltárolja,
 * és az általa mutatott telepes grafikus megjelenítésével kapcsolatos feladatokat végezze/menedzselje.
 *  A TravellerView leszármazottja, így a View interfészt ő is megvalósítja.
 */
public class SettlerView extends TravellerView {

    /**
     * Az osztály konstruktora, bemeneti paraméter az s, amit a mutatott Settler attribútumának ad értékül,
     * és defaulban hamis az active attribútuma. 
     * Meghívja az ős (TravellerView) konstruktorát. (az x y koordináta az Update hívással lesz inicializálva)
     * @param s a mutatott Settler objektum.
     */
    public SettlerView(Settler s, LevelView lv) {
        super(lv);
        settler = s;
    }

    /**
     * Értéke true, ha az általa mutatott objektum a jelenleg aktív telepes, különben false.
     */
    private boolean active;

    /**
     * A mutatott telepes objektum, akinek a rajzolásáért felel.
     */
    private Settler settler;

    /**
     * Négyzetet rajzol Settler-nek megfelelő módon (narancssárga) az örökölt x,y attribútumok szerinti koordinátákra. 
     * Amennyiben az active attribútuma true, egy sárga keretet rajzol a négyzete köré.
     * @param g Graphics típusú objektum a rajzoláshoz.
     */
    public void draw(Graphics g) {
    	//ez eleg fapados igy tudom, atirhatjuk de igy h nem fut a kod meg nem mertem borderrel------------------------------
    	if (active) {
    		g.setColor(new Color(233, 233, 13));
    		g.fillRect(x, y, 10, 10);
    		g.setColor(new Color(227, 164, 97));
    		g.fillRect(x + 2, y + 2, 8, 8);
    	} else {
    		g.setColor(new Color(227, 164, 97));
    		g.fillRect(x, y, 10, 10);
    	}
    }

    /**
     * A getAsteroid metódussal elkéri a settler-től az aszteroidáját. 
     * Az örökölt levelView-tól elkéri az aszteroida View objektumát a getAsteroidView metódussal. 
     * A kapott AsteroidView objektumra meghívja a getTravellerX és Y metódusokat magát adva paraméterül, 
     * majd a kapott értékeket beírja az x és y attribútumaiba.
     */
    public void Update() {
        Asteroid a = settler.getAsteroid();
        AsteroidView av = levelView.getAsteroidView(a);
        x = av.getTravellerX(this.settler);
        y = av.getTravellerY(this.settler);
    }

    /**
     * Beállítja az active változót a paraméterül kapottra.
     * @param active az új active érték
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * A paraméterben megadott Traveller t objektummal összehasonlítja settlerrel,
     * és ha megegyeznek akkor true, ha nem, akkor false a visszatérési értéke.
     * @param t a Traveller, amivel összehasonlítjuk.
     * @return igaz vagy hamis aszerint, hogy a paraméterben megadott traveller megegyezik-e this objektummal.
     */
    public boolean identify(Traveller t) {
        return t == settler;
    }

}