import java.io.*;
import java.util.*;


/**
 * Kezeli a felhasználóval való interakciókat és a menüt.
 * Az egyes use-case-ekhez megcsinálja az inicializálást majd meghívja a megfelelő tesztelni kívánt metódust.
 */
public class Skeleton {

    private static Scanner input = new Scanner(System.in);
    private static PrintWriter output = new PrintWriter(System.out);
    private static File inputfile = null;
    private static File outputfile = null;
    public static boolean random = false;
    public static Game game = new Game();

    /**
     * Az objektumok és a kiírandó nevüknek az összerendelése.
     */
    public static HashMap<Object, String> names = new HashMap<Object, String>();
    public static HashMap<String, Object> IDs = new HashMap<String, Object>();

    /**
     * Interfész, amely a parancsok számára készült. A parancsok ezt implementálják.
     */
    private interface Command{
        public void execute(String[] args);
    }

    /**
     * A load parancshoz tartozó osztály.
     */
    private static class loadCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A save parancshoz tartozó osztály.
     */
    private static class saveCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A input parancshoz tartozó osztály.
     */
    private static class inputCommand implements Command{
        /**
         * A paraméterként megadott fájlra állítja a bemenetet.
         * Ha nincs elég argumentum, akkor hibát jelez.
         * @param args a parancs argumentumainak tömbje
         */
        public void execute(String[] args) {
            if (args.length < 2) {
                output.println("load unsuccessful");
                return;
            }
            File file = new File(args[1]);
            Scanner temp;
            try {
                temp = new Scanner(file);
            } catch (FileNotFoundException e) {
                output.println("load unsuccessful");
                return;
            }
            input.close();
            input = temp;
        }
    }
    /**
     * A output parancshoz tartozó osztály.
     */
    private static class outputCommand implements Command{
        /**
         * A paraméterként megadott fájlra irányítja a kimenetet.
         * Ha nincs elég argumentum, akkor hibát jelez.
         * @param args a parancs argumentumainak tömbje
         */
        public void execute(String[] args) {
            if (args.length < 2) {
                output.println("load unsuccessful");
                return;
            }
            File file = new File(args[1]);
            PrintWriter temp;
            try {
                temp = new PrintWriter(new FileWriter(file));
            } catch (Exception e) {
                output.println("load unsuccessful");
                return;
            }
            output.close();
            output = temp;
        }
    }
    /**
     * A setrandom parancshoz tartozó osztály.
     */
    private static class setrandomCommand implements Command{
        /**
         * Beállítja a random értékét a felhasználó által megadott értékre.
         * Ha nincs elég argumentum, akkor hibaüzenetet ad és visszatér
         * @param args a parancs argumentumainak tömbje
         */
        public void execute(String[] args) {
            if (args.length < 2) {
                output.println("all details must be specified");
                return;
            }
            if ("0".equals(args[1]))
                random = false;
            else if ("1".equals(args[0]))
                random = true;
            output.println("random events " + (random ? "on" : "off"));
        }
    }
    /**
     * A addsettler parancshoz tartozó osztály.
     */
    private static class addsettlerCommand implements Command{

        public void execute(String[] args) {
            if (args.length < 2) {
                output.println("all details must be specified");
                return;
            }
            Object asteroid = IDs.getOrDefault(args[1], null);
            int n = game.getSettlers().size();
            Sun sun = game.getSun();
            List<Asteroid> asteroids = sun.getAsteroids();
            if (asteroid == null || asteroids.contains(asteroid)){
                output.println("couldn’t complete request\n" +
                        "    selected ID not available\n");
            }else{
                Settler s = new Settler();
                IDs.put("s" + (n+1), 2);
                ((Asteroid) asteroid).placeTraveller(s);
                output.println("settler s" + (n+1) + "added to asteroid: " + args[1]);
            }
        }
    }
    /**
     * A addasteroid parancshoz tartozó osztály.
     */
    private static class addasteroidCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A addrobot parancshoz tartozó osztály.
     */
    private static class addrobotCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A addufo parancshoz tartozó osztály.
     */
    private static class addufoCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A connectasteroid parancshoz tartozó osztály.
     */
    private static class connectasteroidCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A selectsettler parancshoz tartozó osztály.
     */
    private static class selectsettlerCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A move parancshoz tartozó osztály.
     */
    private static class moveCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A drill parancshoz tartozó osztály.
     */
    private static class drillCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A mine parancshoz tartozó osztály.
     */
    private static class mineCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A putmineralback parancshoz tartozó osztály.
     */
    private static class putmineralbackCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A craftrobot parancshoz tartozó osztály.
     */
    private static class craftrobotCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A craftteleport parancshoz tartozó osztály.
     */
    private static class craftteleportCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A placeteleport parancshoz tartozó osztály.
     */
    private static class placeteleportCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A addmineral parancshoz tartozó osztály.
     */
    private static class addmineralCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A addteleportpair parancshoz tartozó osztály.
     */
    private static class addteleportpairCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A nextturn parancshoz tartozó osztály.
     */
    private static class nextturnCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A robotaction parancshoz tartozó osztály.
     */
    private static class robotactionCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A sunaction parancshoz tartozó osztály.
     */
    private static class sunactionCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A solarwind parancshoz tartozó osztály.
     */
    private static class solarwindCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A checkwin parancshoz tartozó osztály.
     */
    private static class checkwinCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A chechlose parancshoz tartozó osztály.
     */
    private static class chechloseCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A newgame parancshoz tartozó osztály.
     */
    private static class newgameCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A setclosetosun parancshoz tartozó osztály.
     */
    private static class setclosetosunCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A giveup parancshoz tartozó osztály.
     */
    private static class giveupCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A ufoaction parancshoz tartozó osztály.
     */
    private static class ufoactionCommand implements Command{

        public void execute(String[] args) {

        }
    }

    /**
     * A bammboozleteleport parancshoz tartozó osztály.
     */
    private static class bamboozleteleportCommand implements Command{

        public void execute(String[] args) {

        }
    }

    private static HashMap<String, Command> commands;
    public static void initializeCommands(){
        commands = new HashMap<>();
        commands.put("load", new loadCommand()); commands.put("save", new saveCommand()); commands.put("input", new inputCommand());
        commands.put("output", new outputCommand()); commands.put("setrandom", new setrandomCommand());
        commands.put("addsettler", new addsettlerCommand()); commands.put("addasteroid", new addasteroidCommand());
        commands.put("addrobot", new addrobotCommand()); commands.put("addufo", new addufoCommand());
        commands.put("connectasteroid", new connectasteroidCommand()); commands.put("selectsettler", new selectsettlerCommand());
        commands.put("move", new moveCommand()); commands.put("drill", new drillCommand()); commands.put("mine", new mineCommand());
        commands.put("putmineralback", new putmineralbackCommand()); commands.put("craftrobot", new craftrobotCommand());
        commands.put("craftteleport", new craftteleportCommand()); commands.put("placeteleport", new placeteleportCommand());
        commands.put("addmineral", new addmineralCommand()); commands.put("addteleportpair", new addteleportpairCommand());
        commands.put("nextturn", new nextturnCommand()); commands.put("robotaction", new robotactionCommand());
        commands.put("sunaction", new sunactionCommand()); commands.put("solarwind", new solarwindCommand());
        commands.put("checkwin", new checkwinCommand()); commands.put("chechlose", new chechloseCommand());
        commands.put("newgame", new newgameCommand()); commands.put("setclosetosun", new setclosetosunCommand());
        commands.put("giveup", new giveupCommand()); commands.put("ufoaction", new ufoactionCommand());
        commands.put("bamboozleteleport", new bamboozleteleportCommand());
    }


    /**
     * Annak a száma, hogy aktuálisan hány tabulátorral kell beljebb kezdeni a sort.
     */
    private static int padding = 0;

    /**
     * Igaz, ha még az inicializáló fázisban vagyunk, ilyenkor nem írja ki a meghívott metódusokat.
     * Hamis, ha már a szekvenciadiagramon látható események történnek, ilyenkor naplózza a metódushívásokat
     * a konzolra a felhasználónak.
     */
    public static boolean init = true;


    /**
     * Az elérhető menüpontok.
     */
    private static String[] menuItems = {"Craft teleport", "Craft robot", "Place teleport",
            "Settler moves", "Robot moves", "Put mineral back", "Settler moves through teleport",
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
        init = false;
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
        init = false;
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
        init = false;
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
        init = false;
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
        init = false;
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
        init = false;
        settler.putMineralBack(m);
    }

    /**
     * A Settler moves through teleport menüponthoz tartozó inicializáló függvény.
     */
    private static void settlerMovesThroughTeleport(){
        Settler settler = new Settler();
        names.put(settler, "settler");
        Teleport t = new Teleport();
        names.put(t, "t");
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
        init = false;
        settler.move(0);
    }

   /**
     * A Robot moves through teleport menüponthoz tartozó inicializáló függvény.
     */
   private static void robotMovesThroughTeleport(){
       Robot robot = new Robot();
       names.put(robot, "robot");
       Teleport t = new Teleport();
       names.put(t, "t");
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
       init = false;
       robot.move(0);
    }

    /**
     * A Settler drills asteroid menüponthoz tartozó inicializáló függvény.
     */
    private static void settlerDrillsAsteroid(){
        Coal core = new Coal();
        names.put(core, "core");
        Asteroid a = new Asteroid(core);
        names.put(a, "asteroid");
        Settler s = new Settler();
        names.put(s, "settler");
        a.placeTraveller(s);
        init = false;
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
        init = false;
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
        init = false;
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
        init = false;
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
        init = false;
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
        init = false;
        r.drill();
    }

    /**
     * Inicializáló metódus a Sun makes action menüponthoz.
     */
    private static void sunMakesAcion(){
        Sun sun = new Sun();
        names.put(sun, "sun");
        sun.makeAction();
    }
    /**
     * Inicializáló metódus a Settler mines menüponthoz.
     */
    private static void settlerMines(){
        Asteroid asteroid = new Asteroid();
        names.put(asteroid, "asteroid");
        Settler s = new Settler();
        names.put(s, "s");
        asteroid.placeTraveller(s);
        init = false;
        s.mine();
    }

    /**
     * Egy metódus elején kell meghívni, hogy naplózza az metódus meghívását.
     * @param o Az objektum, amin meghívták a megtódust.
     * @param methodName A metódus neve.
     * @param argument Az átadott argumentum, null, ha nem volt átadott argumentum.
     */
    public static void startMethod(Object o, String methodName, Object argument){
        if (!init) {
            for (int i = 0; i < padding; i++)
                System.out.print("\t");
            System.out.print("->" + names.getOrDefault(o, Integer.toString(o.hashCode())) + "." + methodName + "(");
            if (argument != null)
                //System.out.print(names.getOrDefault(argument, Integer.toString(o.hashCode())));
                System.out.print(names.getOrDefault(argument, argument.toString()));
            System.out.println(")");
            padding += 1;
        }
    }

    /**
     * Egy metódus végén kell meghívni, hogy naplózza a metódus visszatérését.
     * @param o Az objektum, amin meghívták a metódust.
     * @param returnvalue Az visszatérési érték, null, ha nincs visszatérési érték.
     */
    public static void endMethod(Object o, Object returnvalue){
        if (!init) {
            padding -= 1;
            for (int i = 0; i < padding; i++)
                System.out.print("\t");
            //System.out.print("<-" + names.getOrDefault(o, Integer.toString(o.hashCode())));
            System.out.print("<-");
            if (returnvalue != null)
                //System.out.print("return: " + names.getOrDefault(returnvalue, Integer.toString(returnvalue.hashCode())));
                System.out.print(names.getOrDefault(returnvalue, returnvalue.toString()));
            System.out.println();
        }
    }

    /**
     * Kiírja a paraméterként átadott kérdést, majd bekér a felhasználótól egy egész számot.
     * Addig kéri be a választ, amíg nem kap helyes bemenetet a felhasználótól.
     * @param message A kiírni kívánt üzenet/kérdés.
     * @return a beírt egész szám
     */
    public static int intQuestion(String message){
        for(int i = 0; i < padding; i++)
            System.out.print("\t");
        System.out.print(message + " ");
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
        for(int i = 0; i < padding; i++)
            System.out.print("\t");
        System.out.print(message + " ");
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
     * Metódus, amellyel a nap lekérdezheti a makeAction metódusában, hogy az almenü melyik menüpontját
     * kell végrehajtani. A választott opció alapján a megfelelő inicializáló függvényt meghívja. Addig kéri be a
     * felhasználótól a számokat, amíg nem 1,2,3 számok közül való számot választ.
     * @param sun A nap, amelyik kérdez
     * @return a felhasználó által választott menüpont száma
     */
    public static int sunQuestion(Sun sun){
        Skeleton.init = true;
        System.out.println("1:\tSun makes solar wind");
        System.out.println("2:\tRadioactive asteroid gets close to sun");
        System.out.println("3:\tIce asteroid gets close to sun");
        Scanner sc = new Scanner(System.in);
        boolean isNumber = false;
        int in = 0;
        while (!isNumber  || (in < 1 || in > 3)) {
            String s = sc.next();
            try {
                in = Integer.parseInt(s);
                isNumber= true;
            } catch (Exception ignored) {
                isNumber = false;
            }
        }
        if (in == 1){
            sunMakesSolarWind(sun);
        }else if (in == 2){
            radioactiveAsteroidGetsCloseToSun(sun);
        }else {
            iceAsteroidGetsCloseToSun(sun);
        }

        Skeleton.init = false;
        return in;
    }

    /**
     * A sun makes solar wind almenüponthoz tartozó inicializáló metódus.
     * @param sun A nap, amelyiknek oda kell adni az aszteoridákat
     */
    private static void sunMakesSolarWind(Sun sun){
        Asteroid a = new Asteroid();
        names.put(a, "a");
        Game game = new Game();
        names.put(game, "game");
        Settler s = new Settler();
        names.put(s, "s");
        Robot r = new Robot();
        names.put(r, "r");
        List<Asteroid> asteroids = new ArrayList<Asteroid>();
        asteroids.add(a);
        sun.addAsteroids(asteroids);
        a.placeTraveller(s);
        a.placeTraveller(r);
        game.addRobot(r);
        game.addSettler(s);
        r.setGame(game);
        s.setGame(game);
    }
    /**
     * A radioactive asteroid gets close to sun almenüponthoz tartozó inicializáló metódus.
     * @param sun A nap, amelyiknek oda kell adni az aszteoridákat
     */
    private static void radioactiveAsteroidGetsCloseToSun(Sun sun){
        Uranium core = new Uranium();
        names.put(core, "core");
        Asteroid a = new Asteroid(core);
        names.put(a, "a");
        Asteroid neighbour = new Asteroid();
        names.put(neighbour, "neighbour");
        Settler s = new Settler();
        names.put(s, "s");
        Robot r = new Robot();
        names.put(r, "r");
        Game game = new Game();
        names.put(game, "game");
        game.addSettler(s);
        game.addRobot(r);
        a.placeTraveller(s);
        a.placeTraveller(r);
        a.addNeighbour(neighbour);
        neighbour.addNeighbour(a);
        Teleport t = new Teleport();
        names.put(t, "t");
        Teleport pair = new Teleport();
        names.put(pair, "pair");
        t.setPair(pair);
        pair.setPair(t);
        t.addNeighbour(a);
        a.addNeighbour(t);
        r.setGame(game);
        s.setGame(game);
        List<Asteroid> asteroids = new ArrayList<Asteroid>();
        asteroids.add(a);
        sun.addAsteroids(asteroids);
    }
    /**
     * Az ice asteroid gets close to sun almenüponthoz tartozó inicializáló metódus.
     * @param sun A nap, amelyiknek oda kell adni az aszteoridákat
     */
    private static void iceAsteroidGetsCloseToSun(Sun sun){
        Ice core = new Ice();
        names.put(core, "core");
        Asteroid a = new Asteroid(core);
        names.put(a, "a");
        List<Asteroid> asteroids = new ArrayList<Asteroid>();
        asteroids.add(a);
        init = false;
        sun.addAsteroids(asteroids);
    }

    /**
     * Bekér a felhasználótól egy mineral fajtát, amelyet a felhasználó begépel.
     * Addig kéri be a választ, amíg nem kap helyes bemenetet a felhasználótól.
     * @return egy mineral objektum, amit a felhasználó kért.
     */
    public static Mineral mineralQuestion(){
        for(int i = 0; i < padding; i++)
            System.out.print("\t");
        System.out.print("Please choose a mineral! (coal/ice/uranium/iron) ");
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
        names.put(answer, "core");
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
     * Kiíratja a menüt és bekékéri a felhasználótól, hogy melyik menüpontot akarja elérni,
     * ezután meghívja a megfelelő inicializáló függvényt.
     */
    private static void menu(){
        names.clear();
        init = true;
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
     * függvénnyel, amihez meghívja a megfelelő inicializáló függvényt.
     * Ezt addig ismétli, amíg a felhasználó ki nem lép a programból.
     * @param args parancssori argumentumok
     */
    public static void main(String[] args){
        /*hile (true){
            menu();
        }*/
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
