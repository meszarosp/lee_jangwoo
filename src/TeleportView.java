
import java.awt.*;
import java.util.*;

/**
 * 
 */
public class TeleportView implements View {

    /**
     * A teleportkapu x koordin�t�ja
     */
    private int x;

    /**
     *A teleportkapu y koordin�t�ja 
     */
    private int y;

    /**
     * A teleportkapu sz�ne
     */
    private Color teleportColor;

    /**
     * A teleportkapu p�rja
     */
    private Teleport teleport;

    /**
     * Konstruktor
     * be�ll�tja az x, y, teleportColor �s teleport �rt�k�t a param�ter�l kapott dolgokra
     */
    public TeleportView(Teleport t, Color c, int x2, int  y2) {
        x = x2;
        y = y2;
        teleportColor = c;
        teleport = t;
    }

    /**
     * Kirajzolja a teleportkaput a megfelel� sz�nnel
     * @param g
     */
    public void draw(Graphics g) {
        if(x == -1 && y == -1)          //ha a nyomi settler zsebében van, ne rajzoljuk ki
            return;
        g.setColor(new Color(0, 0, 0));
        g.fillRect(x-10, y-18, 20, 36);
        g.setColor(teleportColor);
        g.fillRect(x-7, y-15, 15, 31);

    }

    /**
     * Visszaadja a teleport tagv�ltoz�t
     * @return
     */
    public Teleport getTeleport() {
        return teleport;
    }

    /**
     * Visszaadja ax x tagv�ltoz�t
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Visszaadja az y tagv�ltoz�t
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * A param�terk�nt megadott a aszteroid�t �sszeveti a teleport
     * getNeighbour-el lek�rt aszteroid�j�val. Ha megegyeznek true,
     * ha nem akkor false a visszat�r�si �rt�k.
     * @param a 
     * @return
     */
    public boolean isThisYourNeighbour(Asteroid a) {
        Asteroid neighbour = teleport.getNeighbour();
        if(neighbour == null){
            return false;
        }
        return neighbour.equals(a);
    }

    /**
     * Visszaadja a teleportColor tagv�ltoz�t
     * @return
     */
    public Color getColor() {
        return teleportColor;
    }

    /**
     * Ellen�rzi, hogy a param�ter�l kapott koordin�t�k �ltal meghat�rozott pont a teleportkapu ter�let�n bel�l van-e
     * @param xClicked
     * @param yClicked
     * @return igaz, ha bel�l van, egy�bk�nt hamis
     */
    public boolean clicked(int xClicked, int yClicked) {
        if((xClicked<x+10 && xClicked>x-10)&&(yClicked<y+18 && yClicked>y-18)){
            return true;
        }
        return false;
    }

    /**
     * Megh�vja a teleport getPair met�dus�t, �s a visszakapott teleportkaput �sszehasonl�tja a param�ter�l kapottal.
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