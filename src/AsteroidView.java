
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.math.*;

/**
 * 
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
     * 
     */
    private int x;

    /**
     * 
     */
    private int y;

    /**
     *
     */
    private int radius=30;

    /**
     * 
     */
    private Asteroid asteroid;

    /**
     * Elkéri az asteroid-tól a travellers listáját a getTravellers metódussal. Visszaadja a paraméterként
     * megadott traveller x koordinátáját a képernyõn, kiszámolva a travellers listában elfoglalt helyébõl,
     * és az aszteroida középpontjából
     * @param t 
     * @return
     */
    public int getTravellerX(Traveller t) {
        List<Traveller> travellers
        for(int i = 0; i< travellers.length(); ++i){
            if(travellers[i]==t){
                if(i==0){
                    return ((x-radius+5);
                }else{
                    return ((x-radius+5)+((i+1)*13));        //10 az oldalhosszúsága a traveller-t jelölõ négyzetnek, 3 pixel hely van két négyzet között
                }
            }
        }
        return 0;
    }

    /**
     * A paraméterként megadott traveller y koordinátáját a képernyõn, kiszámolja az aszteroida középpontja alapján
     * @param t 
     * @return
     */
    public int getTravellerY(Traveller t) {
        return x+radius+10;
    }

    /**
     * Visszaadja az asteroid tagváltozót
     * @return
     */
    public Asteroid getAsteroid() {
        return asteroid;
    }

    /**
     * Végignézi az asteroid szomszédait, és ha valamelyik megegyezik a paraméterül
     * kapott aszteroidával, akkor igazzal, egyébként meg hamissal tér vissza
     * @param a 
     * @return
     */
    public boolean isThisYourNeighbour(Asteroid a) {
        ArrayList<INeighbour> neighbours = asteroid.getNeighbours();
        for(int i=0; i<neighbours.length(); ++i){
            if(neighbours[i]==a){
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
     * Ellenõrzi, hogy a paraméterül kapott koordináták által meghatározott pont az aszteroida körén belül van-e
     * @param int xClicked
     * @param int yClicked
     * @return igaz, ha belül van, egyébként hamis
     */
    public boolean clicked(int xClicked, int yClicked) {
        if((pow((xClicked-x), 2)*pow((yClicked-y), 2))<pow(radius, 2){
            return true;
        }
        return false;
    }

    /**
     * kirajzolja az aszteroidát úgy hogy megfeleljen az állapotának
     * @param g
     */
    public void draw(Graphics g) {
        int aShell = asteroid.getShell();
        bool aCloseToSun = asteroid.getCloseToSun();
        if(aShell!=0){
            if(aCloseToSun){
                g.setColor(new Color(255, 201, 14));      //sárga
                g.fillOval(x, y, radius*2, radius*2);
                g.setColor(new Color(255, 255, 255));       //fehér
                g.fillOval(x, y, radius*2-4, radius*2-4);
                g.setColor(new Color(0, 0, 0));     //fekete
                g.drawString(aShell, x-(fontMetrics.stringWidth(aShell)/2), y-(fontMetrics.getHeight()/2);

            }else{
                Mineral aCore = asteroid.getCore();
                g.setColor(new Color(0, 0, 0));       //fekete
                g.fillOval(x, y, radius*2, radius*2);
                g.setColor(mineralColor(aCore));
                g.fillOval(x, y, radius*2-4, radius*2-4);
            }
        }

    }

}