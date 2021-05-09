

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.math.*;
import java.util.List;
import java.util.logging.Level;

import static java.lang.Math.pow;

/**
 * Az osztály felelőssége, hogy az általa mutatott aszteroida objektumhoz tartozó képernyő
 * koordinátákat eltárolja, és az általa mutatott aszteroida grafikus megjelenítésével kapcsolatos
 * feladatokat végezze/menedzselje.
 */
public class AsteroidView implements View {

    /**
     * Konstruktor
     * beállítja az x, y és a értékét a paraméterül kapott dolgokra
     */
    public AsteroidView(Asteroid a, int x2, int y2) {
        asteroid = a;
        x = x2;
        y = y2;
    }

    /**
     * A kör középpontjának x koordinátája
     */
    private int x;

    /**
     * A kör középpontjának y koordinátája
     */
    private int y;

    /**
     * A kör sugara
     */
    private int radius = 30;

    /**
     * A mutatott aszteroida objektum, akinek a rajzolásáért felel
     */
    private Asteroid asteroid;

    /**
     * Elkéri az asteroid-tól a travellers listáját a
     * getTravellers metódussal. Visszaadja a paraméterként megadott traveller x
     * koordinátáját a képernyőn, kiszámolva a travellers listában elfoglalt helyéből, és az
     * aszteroida középpontjából.
     * @param t Az utazó, akinek az x koordinátája kell
     * @return Az x koordináta
     */
    public int getTravellerX(Traveller t) {
        List<Traveller> travellers = asteroid.getTravellers();
        for(int i = 0; i < travellers.size(); ++i){
            if(travellers.get(i).equals(t)){
                return (x - radius - 5) + i * 16 + i * 5; //16 az oldalhossz�s�ga a traveller-t jel�l� n�gyzetnek, 5 pixel hely van k�t n�gyzet k�z�tt
            }
        }
        return 0;
    }


    /**
     * Elkéri az asteroid-tól a travellers listáját a
     * getTravellers metódussal. Visszaadja a paraméterként megadott traveller y
     * koordinátáját a képernyőn, kiszámolva a travellers listában elfoglalt helyéből, és az
     * aszteroida középpontjából.
     * @param t Az utazó, akinek az x koordinátája kell
     * @return Az y koordináta
     */
    public int getTravellerY(Traveller t) {
        return y - radius - 20;
    }

    /**
     * Visszaadja az asteroid tagváltozót
     * @return
     */
    public Asteroid getAsteroid() {
        return asteroid;
    }

    /**
     * Elkéri az asteroid-tól a szomszédait a
     * getNeighbours metódussal. Ezen az INeighbour listán végigiterál, és ha ez
     * megegyezik a paraméterül kapott a aszteroidával, akkor igazzal tér vissza, egyébként
     * hamissal.
     * @param a A kérdezett aszteroida.
     * @return IGAZ, ha szomszédos az aszteroida a paraméterként átadott aszteroidával, hamis, ha nem.
     */
    public boolean isThisYourNeighbour(Asteroid a) {
        List<INeighbour> neighbours = asteroid.getNeighbours();
        for(int i = 0; i < neighbours.size(); ++i){
            if(neighbours.get(i).equals(a)){
                return true;
            }
        }
        return false;
    }

    /**
     * Visszaadja az x tagváltozót
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
     * A koordináta attribútumaiból (x és y) kiszámolja, hogy
     * a paraméterül kapott x y koordináták az aszteroida sugarán belül vannak-e, és ezt a
     * boolean értéket adja vissza.
     * @param xClicked A kattintás x koordinátája.
     * @param yClicked A kattintás y koordinátája.
     * @return IGAZ, ha a körön belül van a kattintás, HAMIS, ha nem.
     */
    public boolean clicked(int xClicked, int yClicked) {
        if((pow((xClicked - x), 2)+pow((yClicked - y), 2))<pow(radius, 2)){
            return true;
        }
        return false;
    }

    /**
     * kirajzolja az aszteroidát úgy hogy megfeleljen az aszteroida állapotának
     * @param g A Graphics objektum, ahova a rajzolás történik.
     */

    public void draw(Graphics g) {
        int aShell = asteroid.getShell();
        boolean aCloseToSun = asteroid.getCloseToSun();
        Color border = aCloseToSun ? new Color(255, 201, 14) : new Color(0, 0, 0);
        g.setColor(border);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        if(aShell <= 0){

            Mineral aCore = asteroid.getCore();
            g.setColor(LevelView.mineralColor(aCore));
            g.fillOval(x - radius + 2, y - radius + 2, radius * 2 - 4, radius * 2 - 4);
            //if(aCloseToSun){
                //g.setColor(new Color(255, 201, 14));      //s�rga

               // g.setColor(new Color(255, 255, 255));       //feh�r

                //g.setColor(new Color(0, 0, 0));     //fekete
                //g.drawString(aShell, x-(fontMetrics.stringWidth(aShell)/2), y-(fontMetrics.getHeight()/2));

        }else{
            //Mineral aCore = asteroid.getCore();
            //g.setColor(new Color(0, 0, 0));       //fekete
            //g.fillOval(x, y, radius*2, radius*2);
            //g.setColor(mineralColor(aCore));
            g.setColor(Color.WHITE);
            //g.drawString(aShell, x-(fontMetrics.stringWidth(aShell)/2), y-(fontMetrics.getHeight()/2));
            g.fillOval(x - radius + 2, y - radius + 2, radius * 2 - 4, radius * 2 - 4);
            g.setColor(Color.BLACK);
            g.setFont(Font.getFont("Arial"));
            g.drawString(Integer.toString(aShell), x - 10, y);

        }

    }

}
