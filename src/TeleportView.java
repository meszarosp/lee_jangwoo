
import java.awt.*;
import java.util.*;

/**
 * 
 */
public class TeleportView implements View {

    /**
     * A teleportkapu x koordinátája
     */
    private int x;

    /**
     *A teleportkapu y koordinátája 
     */
    private int y;

    /**
     * A teleportkapu színe
     */
    private Color teleportColor;

    /**
     * A teleportkapu párja
     */
    private Teleport teleport;

    /**
     * Konstruktor
     * beállítja az x, y, teleportColor és teleport értékét a paraméterül kapott dolgokra
     */
    public TeleportView(Teleport t, Color c, int x2, int  y2) {
        x = x2;
        y = y2;
        teleportColor = c;
        teleport = t;
    }

    /**
     * Kirajzolja a teleportkaput a megfelelõ színnel
     * @param g
     */
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(x, y, 20, 36);
        g.setColor(teleportColor);
        g.fillRect(x, y, 15, 31);

    }

    /**
     * Visszaadja a teleport tagváltozót
     * @return
     */
    public Teleport getTeleport() {
        return teleport;
    }

    /**
     * Visszaadja ax x tagváltozót
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Visszaadja az y tagváltozót
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * A paraméterként megadott a aszteroidát összeveti a teleport
     * getNeighbour-el lekért aszteroidájával. Ha megegyeznek true,
     * ha nem akkor false a visszatérési érték.
     * @param a 
     * @return
     */
    public boolean isThisYourNeighbour(Asteroid a) {
        ArrayList<INeighbour> neighbour = teleport.getNeighbour();
        if(neighbour==a){
            return true;
        }
        return false;
    }

    /**
     * Visszaadja a teleportColor tagváltozót
     * @return
     */
    public Color getColor() {
        return teleportColor;
    }

    /**
     * Ellenõrzi, hogy a paraméterül kapott koordináták által meghatározott pont a teleportkapu területén belül van-e
     * @param xClicked
     * @param yClicked
     * @return igaz, ha belül van, egyébként hamis
     */
    public boolean clicked(int xClicked, int yClicked) {
        if((xClicked<x+10 || xClicked>x-10)&&(yClicked<y+18 || yClicked>y-18)){
            return true;
        }
        return false;
    }

    /**
     * Meghívja a teleport getPair metódusát, és a visszakapott teleportkaput összehasonlítja a paraméterül kapottal.
     * @param t 
     * @return ha egyezik igaz, ha nem akkor hamis
     */
    public boolean isPair(Teleport t) {
        if(teleport.getPair() == t){
            return true;
        }
        return false;
    }

}