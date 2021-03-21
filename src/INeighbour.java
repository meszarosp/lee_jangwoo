
import java.util.*;

/**
 * Interf�szt biztos�t az Aszteroida �s Teleport oszt�lyoknak. Ezzel val�sul meg a telepes vagy
 * robot mozgat�sa aszteroid�r�l aszteroid�ra ak�r teleporton kereszt�l, vagy an�lk�l. Ennek
 * seg�ts�g�vel van ki�p�tve az aszteroid�k �s teleportkapuk szomsz�ds�gi h�l�ja. Seg�ts�g�vel el
 * lehet t�vol�tani szomsz�dokat.
 */
public interface INeighbour {

    /**
     * @param traveller
     */
    void placeTraveller(Traveller traveller);

    /**
     * @param neighbour
     */
    void removeNeighbour(INeighbour neighbour);

    /**
     * @param neighbour
     */
    void addNeighbour(INeighbour neighbour);

}