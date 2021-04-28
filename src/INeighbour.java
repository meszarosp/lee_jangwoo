import java.util.*;

/**
 * Interfészt biztosít az Aszteroida és Teleport osztályoknak. Ezzel valósul meg a telepes vagy robot 
 * mozgatása aszteroidáról aszteroidára akár teleporton keresztül, vagy anélkül. Ennek segítségével van kiépítve az 
 * aszteroidák és teleportkapuk szomszédsági hálója. Segítségével el lehet távolítani szomszédokat. Tudja napszél érni.
 */
public interface INeighbour {

    /**
     * Áthelyezi a traveller-t egy másik aszteroidára, kifejtése az egyes interfész megvalósításoknál.
     * @param traveller - az áthelyezend? traveller
     */
    void placeTraveller(Traveller traveller);

    /**
     * Egy szomszéd megszûnésérõl értesít, kifejtése az egyes interfész megvalósításoknál.
     * @param neighbour a megsz?n? neighbour	
     */
    void removeNeighbour(INeighbour neighbour);
    
    /**
     * A napszélrõl értesítik ezzel egymást a megvalósított interfészek.
     * @param i napszél mélysége (hogy mekkora területet ér majd el)
     */
    void solarWind(int i);

    /**
     * A teleport mozgatásáért felelõs metódus
     * @param t a mozgatandó teleport
     * @return a mozgatás sikeressége
     */
    boolean moveTeleport(Teleport t);
}