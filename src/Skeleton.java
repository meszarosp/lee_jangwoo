import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Skeleton {
    /**
     * Annak a száma, hogy aktuálisan hány tabulátorral kell beljebb kezdeni a sort.
     */
    private static int padding = 0;
    /**
     * Az objektumok és a kiírandó nevüknek az összerendelése.
     */
    private static HashMap<Object, String> names = new HashMap<Object, String>();
    /**
     * Az elérhető menüpontok.
     */
    private static String[] menuItems = {"Craft teleport", "Craft robot", "Place teleport",
            "Settler moves", "Robot moves", "Put mineral back", "Settler mvoes through teleport",
            "Robot moves through teleport", "Settler drills asteroid", "Settler drills ice asteroid",
            "Settler drills radioactive asteroid", "Robot drills asteroid", "Robot drills ice asteroid",
            "Robot drills radioactive asteroid", "Sun makes action", "Settler mines"};

    /**
     * A Craft teleport menüponthoz tartozó inicializáló függvény.
     * Készít egy telepest, elhelyezi a names szótárban majd meghívja rajta a craftTeleport metódust.
     */
    private static void craftTeleport(){
        Settler s = new Settler();
        names.put(s, "s");
        s.craftTeleport();
    }

    /**
     * A Craft robot menüponthoz tartozó inicializáló függvény.
     */
    private static void craftRobot(){
        Game game = new Game();
        names.put(game, "game");
        Settler s = new Settler();
        names.put(s, "s");
        Asteroid asteroid = new Asteroid();
        names.put(asteroid, "asteroid");
        asteroid.placeTraveller(s);
        s.setGame(game);
        s.craftRobot();
    }

    /**
     * A Place teleport menüponthoz tartozó inicializáló függvény.
     */
    private static void placeTeleport(){
        Settler settler = new Settler();
        names.put(settler, "settler");
        Asteroid asteroid = new Asteroid();
        names.put(asteroid, "asteroid");
        asteroid.placeTraveller(settler);
        Teleport t = new Teleport();
        names.put(t, "teleport");
        settler.addTeleport(t);
        settler.placeTeleport(t);
    }

    /**
     * A Settler moves menüponthoz tartozó inicializáló függvény.
     */
    private static void settlerMoves(){
        Asteroid asteroidA = new Asteroid();
        names.put(asteroidA, "asteroidA");
        Settler settler = new Settler();
        names.put(settler, "settler");
        Asteroid asteroidB = new Asteroid();
        names.put(asteroidB, "asteroidB");
        asteroidA.addNeighbour(asteroidB);
        asteroidB.addNeighbour(asteroidA);
        asteroidA.placeTraveller(settler);
        settler.move(0);
    }

    /**
     * A Robot moves menüponthoz tartozó inicializáló függvény.
     */
    private static void robotMoves(){
        Asteroid asteroidA = new Asteroid();
        names.put(asteroidA, "asteroidA");
        Robot robot = new Robot();
        names.put(robot, "robot");
        Asteroid asteroidB = new Asteroid();
        names.put(asteroidB, "asteroidB");
        asteroidA.addNeighbour(asteroidB);
        asteroidB.addNeighbour(asteroidA);
        asteroidA.placeTraveller(robot);
        robot.move(0);
    }

    /**
     * A Put mineral back menüponthoz tartozó inicializáló függvény.
     */
    private static void putMineralBack(){
        Settler settler = new Settler();
        names.put(settler, "settler");
        Asteroid asteroid = new Asteroid();
        names.put(asteroid, "asteroid");
        //Mineral m = mineralQuestion();
        Coal m = new Coal();
        names.put(m, "coal");
        asteroid.placeTraveller(settler);
        settler.addMineral(m);
        settler.putMineralBack(m);
    }

    /**
     * A Settler moves through teleport menüponthoz tartozó inicializáló függvény.
     */
    private static void settlerMovesThroughTeleport(){
        Settler settler = new Settler();
        names.put(settler, "settler");
        Teleport t = new Teleport();
        names.put(t, "teleport");
        Teleport pair = new Teleport();
        names.put(pair, "pair");
        t.setPair(pair);
        pair.setPair(t);
        Asteroid asteroid = new Asteroid();
        names.put(asteroid, "asteroid");
        asteroid.addNeighbour(t);
        Asteroid neighbour = new Asteroid();
        names.put(neighbour, "neighbour");
        neighbour.addNeighbour(pair);
        asteroid.placeTraveller(settler);
        pair.addNeighbour(neighbour);
        t.addNeighbour(asteroid);
        settler.move(0);
    }

   /**
     * A Robot moves through teleport menüponthoz tartozó inicializáló függvény.
     */
   private static void robotMovesThroughTeleport(){
       Robot robot = new Robot();
       names.put(robot, "robot");
       Teleport t = new Teleport();
       names.put(t, "teleport");
       Teleport pair = new Teleport();
       names.put(pair, "pair");
       t.setPair(pair);
       pair.setPair(t);
       Asteroid asteroid = new Asteroid();
       names.put(asteroid, "asteroid");
       asteroid.addNeighbour(t);
       Asteroid neighbour = new Asteroid();
       names.put(neighbour, "neighbour");
       neighbour.addNeighbour(pair);
       asteroid.placeTraveller(robot);
       pair.addNeighbour(neighbour);
       t.addNeighbour(asteroid);
       robot.move(0);
    }

    /**
     * A Settler drills asteroid menüponthoz tartozó inicializáló függvény.
     */
    private static void settlerDrillsAsteroid(){
        Coal core = new Coal();
        names.put(core, "core");
        Asteroid a = new Asteroid(core);
        names.put(a, "a");
        Settler s = new Settler();
        names.put(s, "s");
        a.placeTraveller(s);
        s.drill();
    }

    /**
     * A Settler ice drills asteroid menüponthoz tartozó inicializáló függvény.
     */
    private static void settlerDrillsIceAsteroid(){
        Ice core = new Ice();
        names.put(core, "core");
        Asteroid a = new Asteroid(core);
        names.put(a, "a");
        Settler s = new Settler();
        names.put(s, "s");
        a.placeTraveller(s);
        s.drill();
    }

    /**
     * A Settler radioactive asteroid menüponthoz tartozó inicializáló függvény.
     */
    private static void settlerDrillsRadioactiveAsteroid(){
        Uranium core = new Uranium();
        names.put(core, "core");
        Asteroid a = new Asteroid(core);
        names.put(a, "a");
        Settler s = new Settler();
        names.put(s, "s");
        Game g = new Game();
        names.put(g, "g");
        a.placeTraveller(s);
        s.setGame(g);
        g.addSettler(s);
        s.drill();
    }

    /**
     * A Robot drills asteroid menüponthoz tartozó inicializáló függvény.
     */
    private static void robotDrillsAsteroid(){
        Coal core = new Coal();
        names.put(core, "core");
        Asteroid a = new Asteroid(core);
        names.put(a, "a");
        Robot r = new Robot();
        names.put(r, "r");
        a.placeTraveller(r);
        r.drill();
    }

    /**
     * A Robot drills ice asteroid menüponthoz tartozó inicializáló függvény.
     */
    private static void robotDrillsIceAsteroid(){
        Ice core = new Ice();
        names.put(core, "core");
        Asteroid a = new Asteroid(core);
        names.put(a, "a");
        Robot r = new Robot();
        names.put(r, "r");
        a.placeTraveller(r);
        r.drill();
    }

    /**
     * A Robot drills radioctive asteroid menüponthoz tartozó inicializáló függvény.
     */
    private static void robotDrillsRadioactiveAsteroid(){
        Uranium core = new Uranium();
        names.put(core, "core");
        Asteroid a = new Asteroid(core);
        names.put(a, "a");
        Robot r = new Robot();
        names.put(r, "r");
        Game g = new Game();
        names.put(g, "g");
        a.placeTraveller(r);
        r.setGame(g);
        g.addRobot(r);
        r.drill();
    }

    private static void sunMakesAcion(){
        //TODO
    }

    private static void settlerMines(){
        //TODO
    }

    /**
     * Egy metódus elején kell meghívni, hogy naplózza az metódus meghívását.
     * @param o Az objektum, amin meghívták a megtódust.
     * @param methodName A metódus neve.
     * @param argument Az átadott argumentum, null, ha nem volt átadott argumentum.
     */
    public static void startMethod(Object o, String methodName, Object argument){
        for(int i = 0; i < padding; i++)
            System.out.print("\t");
        System.out.print("->" + names.getOrDefault(o, Integer.toString(o.hashCode())) + "." + methodName + "(");
        if (argument != null)
            System.out.print(names.getOrDefault(argument, Integer.toString(o.hashCode())));
        System.out.println(")");
        padding += 1;
    }

    /**
     * Egy metódus végén kell meghívni, hogy naplózza a metódus visszatérését.
     * @param o Az objektum, amin meghívták a metódust.
     * @param returnvalue Az visszatérési érték, null, ha nincs visszatérési érték.
     */
    public static void endMethod(Object o, Object returnvalue){
        padding -= 1;
        for(int i = 0; i < padding; i++)
            System.out.print("\t");
        //System.out.print("<-" + names.getOrDefault(o, Integer.toString(o.hashCode())));
        System.out.print("<-");
        if (returnvalue != null)
            System.out.print("return: " + names.getOrDefault(returnvalue, Integer.toString(returnvalue.hashCode())));
        System.out.println();
    }

    /**
     * Kiírja a paraméterként átadott kérdést, majd bekér a felhasználótól egy egész számot.
     * Addig kéri be a választ, amíg nem kap helyes bemenetet a felhasználótól.
     * @param message A kiírni kívánt üzenet/kérdés.
     * @return a beírt egész szám
     */
    public static int intQuestion(String message){
        System.out.println(message);
        Scanner sc = new Scanner(System.in);
        boolean again = true;
        int out = 0;
        while (again) {
            String s = sc.next();
            try {
                again = false;
                out = Integer.parseInt(s);
            } catch (Exception e) {
                again = true;
            }
        }
        return out;
    }

    /**
     * Kiírja a paraméterként átadott kérdést, majd bekér a felhasználótól egy yes/no választ.
     * Addig kéri be a választ, amíg nem kap helyes bemenetet a felhasználótól.
     * @param message A kiírni kívánt üzenet/kérdés.
     * @return true, ha yes volt a választ, false, ha no volt a válasz
     */
    public static boolean yesnoQuestion(String message){
        System.out.println(message);
        Scanner sc = new Scanner(System.in);
        while(true){
            String s = sc.next();
            if ("yes".equals(s)){
                return true;
            }else if("no".equals(s)){
                return false;
            }
        }
    }
    /**
     * Bekér a felhasználótól egy mineral fajtát, amelyet a felhasználó begépel.
     * Addig kéri be a választ, amíg nem kap helyes bemenetet a felhasználótól.
     * @return egy mineral objektum, amit a felhasználó kért.
     */
    public static Mineral mineralQuestion(){
        System.out.println("Please choose a mineral! (coal/ice/uranium/iron)");
        Mineral answer = null;
        Scanner sc = new Scanner(System.in);
        HashMap<String, Mineral> minerals = new HashMap<String, Mineral>();
        minerals.put("coal", new Coal());
        minerals.put("ice", new Ice());
        minerals.put("iron", new Iron());
        minerals.put("uranium", new Uranium());
        while (answer == null) {
            answer = minerals.getOrDefault(sc.next(), null);
        }
        return answer;
    }

    /**
     * Kiírja az elérhető menüpontokat.
     */
    private static void printMenu(){
        System.out.println("Menu:");
        for (int i = 0; i < menuItems.length; i++){
            System.out.println(i+1 +"\t" + menuItems[i]);
        }
        System.out.println("99\tExit");
    }

    /**
     *
     */
    private static void menu(){
        printMenu();
        Scanner sc = new Scanner(System.in);
        int option = 99;
        boolean again = true;
        while (again) {
            String s = sc.next();
            try {
                again = false;
                option = Integer.parseInt(s);
            } catch (Exception e) {
                again = true;
            }
        }

        switch (option){
            case 1: craftTeleport(); break;
            case 2: craftRobot(); break;
            case 3: placeTeleport(); break;
            case 4: settlerMoves(); break;
            case 5: robotMoves(); break;
            case 6: putMineralBack(); break;
            case 7: settlerMovesThroughTeleport(); break;
            case 8: robotMovesThroughTeleport(); break;
            case 9: settlerDrillsAsteroid(); break;
            case 10: settlerDrillsIceAsteroid(); break;
            case 11: settlerDrillsRadioactiveAsteroid(); break;
            case 12: robotDrillsAsteroid(); break;
            case 13: robotDrillsIceAsteroid(); break;
            case 14: robotDrillsRadioactiveAsteroid(); break;
            case 15: sunMakesAcion(); break;
            case 16: settlerMines(); break;
            case 99: System.exit(0); break;
        }
    }

    /**
     * A program belépési pontja, kiírja a menüpontokat és bekéri a felhaszálótól a választott menüpontot a menu()
     * föggvénnyel, amihez meghívja a megfelelő inicializáló függvnyt.
     * Ezt addig ismétli, amíg a felhasználó ki nem lép a programból.
     * @param args parancssori argumentumok
     */
    public static void main(String[] args){
        while (true){
            menu();
        }
       // mineralQuestion();
        //int a = intQuestion("Help");

        //boolean a = boolQuestion("Help me\n");
        //System.out.println(a);
//        Settler s = new Settler();
//        Asteroid a = new Asteroid();
//        names.put(s, "s");
//        names.put(a, "a");
//        startMethod(a, "placeTraveller", s);
//        startMethod(s, "setAsteroid", a);
//        endMethod(a, null);
//        endMethod(s, null);
    }
}
