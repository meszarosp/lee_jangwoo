
import java.util.*;

/**
 * 
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