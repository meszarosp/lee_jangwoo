import java.util.*;

/**
 * Interfészt biztosít az Aszteroida és Teleport osztályoknak. Ezzel valósul meg a telepes vagy robot 
 * mozgatása aszteroidáról aszteroidára akár teleporton keresztül, vagy anélkül. Ennek segítségével van kiépítve az 
 * aszteroidák és teleportkapuk szomszédsági hálója. Segítségével el lehet távolítani szomszédokat. Tudja napszél érni.
 */
public interface INeighbour {

    /**
     * Áthelyezi a traveller-t egy másik aszteroidára, kifejtése az egyes interfész megvalósításoknál.
     * @param traveller - az áthelyezendő traveller
     */
    void placeTraveller(Traveller traveller);

    /**
     * Egy szomszéd megszűnéséről értesít, kifejtése az egyes interfész megvalósításoknál.
     * @param neighbour a megszűnő neighbour	
     */
    void removeNeighbour(INeighbour neighbour);
    
    /**
     * A napszélről értesítik ezzel egymást a megvalósított interfészek.
     * @param i napszél mélysége (hogy mekkora területet ér majd el)
     * @param neighbour aki meghívta a függvényt (rá nem kell visszahívni)
     */
    void solarWind(int i);

    /**
     * A teleport mozgatásáért felelős metódus
     * @param t a mozgatandó teleport
     * @return a mozgatás sikeressége
     */
    boolean moveTeleport(Teleport t);
}