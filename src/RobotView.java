
import java.awt.*;
import java.util.*;

/**
 * Az osztály felelőssége, hogy az általa mutatott robot objektumhoz tartozó képernyő koordinátákat eltárolja,
 * és az általa mutatott robot grafikus megjelenítésével kapcsolatos feladatokat végezze/menedzselje.
 * A TravellerView leszármazottja, így a View interfészt ő is megvalósítja.
 */
public class RobotView extends TravellerView {

    /**
     * Az osztály konstruktora, bemeneti paraméter az r, amit a mutatott Robot attribútumának ad értékül.
     * Meghívja az ős (TravellerView) konstruktorát. (az x y koordináta az Update hívással lesz inicializálva)
     * @param r a mutatott Robot objektum.
     */
    public RobotView(Robot r, LevelView lv) {
        super(lv);
        robot = r;
    }

    /**
     * A mutatott robot objektum, akinek a rajzolásáért felel.
     */
    private Robot robot;

    /**
     * Négyzetet rajzol Robot-nak megfelelő módon (szürke) az örökölt x,y attribútumok szerinti koordinátákra.
     * @param g Graphics típusú objektum a rajzoláshoz.
     */
    public void draw(Graphics g) {
    	g.setColor(new Color(102, 102, 102));
        g.fillRect(x, y, 10, 10);
    }

    /**
     * A getAsteroid metódussal elkéri a robot-tól az aszteroidáját. 
     * Az örökölt levelView-tól elkéri az aszteroida View objektumát a getAsteroidView metódussal. 
     * A kapott AsteroidView objektumra meghívja a getTravellerX és Y metódusokat magát adva paraméterül,
     * majd a kapott értékeket beírja az x és y attribútumaiba.
     */
    public void Update() {
        Asteroid a = robot.getAsteroid();
        AsteroidView av = levelView.getAsteroidView(a);
        x = av.getTravellerX(this.robot);
        y = av.getTravellerY(this.robot);
    }

    /**
     * A paraméterben megadott Traveller t objektummal összehasonlítja robot-tal,
     * és ha megegyeznek akkor true, ha nem, akkor false a visszatérési értéke.
     * @param t a Traveller, amivel összehasonlítjuk.
     * @return igaz vagy hamis aszerint, hogy a paraméterben megadott traveller megegyezik-e this objektummal.
     */
    public boolean identify(Traveller t) {
        return t == robot;
    }

}