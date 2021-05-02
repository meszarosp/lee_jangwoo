
import java.awt.*;
import java.util.*;

/**
 * Az osztály felelőssége, hogy az általa mutatott UFO objektumhoz tartozó képernyő koordinátákat eltárolja,
 * és az általa mutatott UFO grafikus megjelenítésével kapcsolatos feladatokat végezze/menedzselje.
 * A TravellerView leszármazottja, így a View interfészt ő is megvalósítja.
 */
public class UFOView extends TravellerView {

    /**
     * Az osztály konstruktora, bemeneti paraméter az u, amit a mutatott UFO attribútumának ad értékül.
     * Meghívja az ős (TravellerView) konstruktorát. (az x y koordináta az Update hívással lesz inicializálva)
     * @param u a mutatott Robot objektum.
     */
    public UFOView(UFO u) {
    	super(); //ez kb tökéletesen fölösleges.--------------------------------------
    	ufo = u;
    }

    /**
     * A mutatott ufo objektum.
     */
    private UFO ufo;

    /**
     * A getAsteroid metódussal elkéri az ufo-tól az aszteroidáját. 
     * Az örökölt levelView-tól elkéri az aszteroida View objektumát a getAsteroidView metódussal. 
     * A kapott AsteroidView objektumra meghívja a getTravellerX és Y metódusokat magát adva paraméterül, 
     * majd a kapott értékeket beírja az x és y attribútumaiba.
     */
    public void Update() {
        Asteroid a = ufo.getAsteroid();
        AsteroidView av = levelView.getAsteroidView(a);
        x = av.getTravellerX(this.ufo);
        y = av.getTravellerY(this.ufo);
    }

    /**
     * A paraméterben megadott Traveller t objektummal összehasonlítja ufo-val,
     * és ha megegyeznek akkor true, ha nem, akkor false a visszatérési értéke.
     * @param t a Traveller, amivel összehasonlítjuk.
     * @return igaz vagy hamis aszerint, hogy a paraméterben megadott traveller megegyezik-e this objektummal.
     */
    public boolean identify(Traveller t) {
        return t == ufo;
    }

    /**
     * Négyzetet rajzol UFO-nak megfelelő módon (zöld) az örökölt x,y attribútumok szerinti koordinátákra.
     * @param g Graphics típusú objektum a rajzoláshoz.
     */
    public void draw(Graphics g){
    	g.setColor(new Color(1, 255, 55));
        g.fillRect(x, y, 10, 10);
    }


}