
import java.awt.*;
import java.util.*;

/**
 * Az absztrakt osztály felelőssége, hogy a belőle leszármazó objektumoknak egy közös őst biztosítson,
 * a hozzájuk tartozó képernyő koordinátákat eltárolja.
 * A View interfészt valósítja meg.
 */
public abstract class TravellerView implements View {

    /**
     * Default constructor
     */
    public TravellerView() {
    }

    /**
     * A mutatott teleportkapu x koordinátáját tárolja a képernyőn.
     */
    protected int x;

    /**
     * A mutatott teleportkapu y koordinátáját tárolja a képernyőn.
     */
    protected int y;

    /**
     * Az aszteroida nézetének lekérdezésére szolgál, melyen a telepes van.
     */
    protected LevelView levelView;

    /**
     * Kirajzolásra szolgál, bővebben a leszármazottakban kifejtve.
     * @param g Graphics típusú objektum a rajzoláshoz.
     */
    public void draw(Graphics g) {
    }

    /**
     * A leszármazottakban kifejtve.
     */
    public  void Update() {
    }

    /**
     * A leszármazottakban kifejtve.
     * @param t A traveller, akivel majd összehasonlítjuk a leszármazottakban.
     * @return bool az összehasonlítás eredményéről (majd a leszármazottakban).
     */
    public boolean identify(Traveller t){}

}