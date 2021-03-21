
import java.util.*;

/**
 * Interfészt biztosít az Aszteroida és Teleport osztályoknak. Ezzel valósul meg a telepes vagy
 * robot mozgatása aszteroidáról aszteroidára akár teleporton keresztül, vagy anélkül. Ennek
 * segítségével van kiépítve az aszteroidák és teleportkapuk szomszédsági hálója. Segítségével el
 * lehet távolítani szomszédokat.
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