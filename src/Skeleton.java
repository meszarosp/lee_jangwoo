import java.io.*;
import java.util.*;


/**
 * Kezeli a felhasználóval való interakciókat és a menüt.
 * Az egyes use-case-ekhez megcsinálja az inicializálást majd meghívja a megfelelő tesztelni kívánt metódust.
 */
public class Skeleton {

    private static Scanner input = new Scanner(System.in);
    private static PrintWriter output = new PrintWriter(System.out);
    private static boolean random = false;
    private static Game game = new Game();
    private static Settler activeSettler = null;
    private static HashMap<String, Integer> maxIDs = new HashMap<String, Integer>();

    /**
     * Az objektumok és a kiírandó nevüknek az összerendelése.
     */
    public static HashMap<Object, String> names = new HashMap<Object, String>();
    public static HashMap<String, Object> IDs = new HashMap<String, Object>();
    public static HashMap<Object, String> reverseIDs = new HashMap<Object, String>();

    private static void addID(String s, Object o){
        IDs.put(s, o);
        reverseIDs.put(o, s);
    }

    private static void removeID(String s, Object o){
        IDs.remove(s);
        reverseIDs.remove(o);
    }

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
            if (args.length < 2) {
                output.println("load unsuccessful");
                return;
            }
            File file = new File(args[1]);
            if (!file.exists()){
                output.println("load unsuccessful");
                return;
            }
            game = new Game();
            Sun sun = new Sun();
            game.setSun(sun);
            activeSettler = null;
            IDs.clear();
            try {
                readAsteroidsTeleports(sun);
                readTravellers();
            }catch (Exception e){
                output.println("load unsuccessful");
                return;
            }
        }

        private void readTravellers() throws Exception{
            String[] pieces = input.next().split(" ");
            int nSettlers = Integer.parseInt(pieces[1]);
            int nRobots = Integer.parseInt(pieces[3]);
            int nUFOs = Integer.parseInt(pieces[5]);
            for (int i = 0; i < nSettlers; i++){
                pieces = input.next().split(" ");
                Asteroid a = (Asteroid)IDs.getOrDefault(pieces[1], null);
                if (a == null)
                    throw new Exception();
                Settler s = new Settler(a);
                IDs.put(pieces[0].substring(0, pieces[0].length()-2), s);
                game.addSettler(s);
                int k = Integer.parseInt(pieces[2]);
                for (int j = 0; j < k; j++){
                    Mineral m = parseMineral(pieces[3+j]);
                    if (m == null)
                        throw new Exception();
                    s.addMineral(m);
                }
                int t = Integer.parseInt(pieces[3+k]);
                for (int j = 0; j < k; j++){
                    Teleport teleport = (Teleport)IDs.getOrDefault(pieces[3+k+1+j], null);
                    if (teleport == null)
                        throw new Exception();
                    s.addTeleport(teleport);
                }
            }
            for (int i = 0; i < nRobots; i++){
                pieces = input.next().split(" ");
                Asteroid a = (Asteroid)IDs.getOrDefault(pieces[1], null);
                if (a == null)
                    throw new Exception();
                Robot r = new Robot(a);
                IDs.put(pieces[0].substring(0, pieces[0].length()-2), r);
                game.addRobot(r);
            }
            for (int i = 0; i < nUFOs; i++){
                pieces = input.next().split(" ");
                Asteroid a = (Asteroid)IDs.getOrDefault(pieces[1], null);
                if (a == null)
                    throw new Exception();
                UFO ufo = new UFO(a);
                IDs.put(pieces[0].substring(0, pieces[0].length()-2), ufo);
                game.addUFO(ufo);
            }
        }

        private void readAsteroidsTeleports(Sun sun) throws Exception {
            String[] pieces = input.next().split(" ");
            int nAsteroids=Integer.parseInt(pieces[1]);
            int nTeleports=Integer.parseInt(pieces[3]);
            ArrayList<String[]> lines = new ArrayList<>();
            List<Asteroid> asteroids = new ArrayList<Asteroid>();
            for (int i = 0; i < nAsteroids; i++){
                pieces = input.next().split(" ");
                lines.add(pieces);
                Mineral m = parseMineral(pieces[pieces.length-1]);
                boolean closeToSun = !"0".equals(pieces[pieces.length-2]) && "1".equals(pieces[pieces.length-2]);
                int shell = Integer.parseInt(pieces[pieces.length-3]);
                Asteroid a = new Asteroid(shell, closeToSun, m, sun);
                asteroids.add(a);
                IDs.put(pieces[0].substring(0, pieces[0].length()-2), a);
            }
            sun.addAsteroids(asteroids);
            for (int i = 0; i < nTeleports; i++){
                pieces = input.next().split(" ");
                lines.add(pieces);
                Teleport t = new Teleport();
                game.addTeleport(t);
                IDs.put(pieces[0].substring(0, pieces[0].length()-2), t);
            }
            for (int i = 0; i < nAsteroids; i++){
                pieces = lines.get(i);
                int k = Integer.parseInt(pieces[1]);
                for (int j = 0; j < k; j++){
                    Asteroid a = (Asteroid) IDs.getOrDefault(pieces[0].substring(0, pieces[0].length()-2), null);
                    INeighbour neighbour = (INeighbour)IDs.getOrDefault(pieces[2+j], null);
                    a.addNeighbour(neighbour);
                }
            }
            for (int i = nAsteroids; i < nAsteroids+nTeleports; i++){
                pieces = lines.get(i);
                Teleport t = (Teleport)IDs.getOrDefault(pieces[0].substring(0, pieces[0].length()-2), null);
                if (!"0".equals(pieces[1])) {
                    Asteroid a = (Asteroid) IDs.getOrDefault(pieces[1], null);
                    Teleport t2 = (Teleport) IDs.getOrDefault(pieces[2], null);
                    if (a == null || t2 == null)
                        throw new Exception();
                    t.setNeighbour(a);
                    t.setPair(t2);
                }
            }
        }
    }
    /**
     * A save parancshoz tartozó osztály.
     */
    // TODO Annának
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
                output.println("unsuccessful");
                return;
            }
            File file = new File(args[1]);
            Scanner temp;
            try {
                temp = new Scanner(file);
            } catch (FileNotFoundException e) {
                output.println("unsuccessful");
                return;
            }
            input.close();
            input = temp;
            output.println("input set to " + args[1]);
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
                output.println("unsuccessful");
                return;
            }
            File file = new File(args[1]);
            PrintWriter temp;
            try {
                temp = new PrintWriter(new FileWriter(file));
            } catch (Exception e) {
                output.println("unsuccessful");
                return;
            }
            output.close();
            output = temp;
            output.println("output set to " + args[1]);
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
            Sun sun = game.getSun();
            List<Asteroid> asteroids = sun.getAsteroids();
            if (asteroid == null || !asteroids.contains((Asteroid) asteroid)){
                output.println("couldn’t complete request\n" +
                        "    selected ID not available\n");
            }else{
                Settler s = new Settler((Asteroid) asteroid);
                int n = maxIDs.get("settler");
                maxIDs.replace("settler", n+1);
                IDs.put("s" + (n+1), s);
                ((Asteroid) asteroid).placeTraveller(s);
                output.println("settler s" + (n+1) + " added to asteroid: " + args[1]);
            }
        }
    }
    /**
     * A addasteroid parancshoz tartozó osztály.
     */
    private static class addasteroidCommand implements Command{

        public void execute(String[] args) {
            if (args.length < 4) {
                output.println("all details must be specified");
                return;
            }
            int shell = Integer.parseInt(args[1]);
            boolean cts = false;
            if (args[2].equals("1"))
                cts = true;
            Mineral m = parseMineral(args[3]);
            Asteroid asteroid = new Asteroid(shell, cts, m, game.getSun());
            game.getSun().addAsteroid(asteroid);
            int n = maxIDs.get("asteroid");
            maxIDs.replace("asteroid", n+1);
            IDs.put("a" + (n+1), asteroid);
            output.println("asteroid a" + (n+1) + " added");
            output.println("shell: " + shell);
            output.println("closetosun: " + (cts? "yes" : "no"));
            output.println("core: " + args[3]);
        }
    }
    /**
     * A addrobot parancshoz tartozó osztály.
     */
    private static class addrobotCommand implements Command{

        public void execute(String[] args) {
            if (args.length < 2) {
                output.println("all details must be specified");
                return;
            }
            Object asteroid = IDs.getOrDefault(args[1], null);
            Sun sun = game.getSun();
            List<Asteroid> asteroids = sun.getAsteroids();
            if (asteroid == null || !asteroids.contains((Asteroid) asteroid)){
                output.println("couldn’t complete request\n" +
                        "    selected ID not available\n");
            }else{
                Robot r = new Robot((Asteroid) asteroid);
                int n = maxIDs.get("robot");
                maxIDs.replace("robot", n+1);
                IDs.put("r" + (n+1), r);
                ((Asteroid) asteroid).placeTraveller(r);
                output.println("robot r" + (n+1) + " added to asteroid: " + args[1]);
            }
        }
    }
    /**
     * A addufo parancshoz tartozó osztály.
     */
    private static class addufoCommand implements Command{

        public void execute(String[] args) {
            if (args.length < 2) {
                output.println("all details must be specified");
                return;
            }
            Object asteroid = IDs.getOrDefault(args[1], null);
            Sun sun = game.getSun();
            List<Asteroid> asteroids = sun.getAsteroids();
            if (asteroid == null || !asteroids.contains((Asteroid) asteroid)){
                output.println("couldn’t complete request\n" +
                        "    selected ID not available\n");
            }else{
                UFO ufo = new UFO((Asteroid) asteroid);
                int n = maxIDs.get("ufo");
                maxIDs.replace("ufo", n+1);
                IDs.put("r" + (n+1), ufo);
                ((Asteroid) asteroid).placeTraveller(ufo);
                output.println("ufo u" + (n+1) + " added to asteroid: " + args[1]);
            }
        }
    }
    /**
     * A connectasteroid parancshoz tartozó osztály.
     */
    private static class connectasteroidCommand implements Command{

        public void execute(String[] args) {
            if (args.length < 3) {
                output.println("all details must be specified");
                return;
            }
            Asteroid a1 = (Asteroid) IDs.getOrDefault(args[1], null);
            Asteroid a2 = (Asteroid) IDs.getOrDefault(args[2], null);
            if (a1 == null || a2 == null){
                output.println("couldn’t complete request\n" +
                        "    selected ID not available\n");
                return;
            }
            a1.addNeighbour(a2);
            a2.addNeighbour(a1);
            output.println(args[1] + " and " + args[2] + " are neighbouring asteroids");
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
            if (!settlerCommandCheck(args, 2))
                return;
            activeSettler.move(Integer.parseInt(args[1]));
        }
    }
    /**
     * A drill parancshoz tartozó osztály.
     */

    private static class drillCommand implements Command{
        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 1))
                return;
            activeSettler.drill();
        }
    }
    /**
     * A mine parancshoz tartozó osztály.
     */
    private static class mineCommand implements Command{

        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 1))
                return;
            activeSettler.mine();
        }
    }
    /**
     * A putmineralback parancshoz tartozó osztály.
     */
    private static class putmineralbackCommand implements Command{

        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 2))
                return;
            activeSettler.putMineralBack(parseMineral(args[1]));
        }
    }
    /**
     * A craftrobot parancshoz tartozó osztály.
     */
    private static class craftrobotCommand implements Command{

        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 1))
                return;
            activeSettler.craftRobot();
        }
    }
    /**
     * A craftteleport parancshoz tartozó osztály.
     */
    private static class craftteleportCommand implements Command{

        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 1))
                return;
            activeSettler.craftTeleport();
        }
    }
    /**
     * A placeteleport parancshoz tartozó osztály.
     */
    // TODO teleportot kitalálni
    private static class placeteleportCommand implements Command{

        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 2))
                return;
            activeSettler.placeTeleport();
        }
    }
    /**
     * Az addmineral parancshoz tartozó osztály.
     */
    private static class addmineralCommand implements Command{

        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 2))
                return;
            Mineral mineral = parseMineral(args[1]);
            if (mineral == null)
                output.println("all details must be specified");
            int n = activeSettler.getMinerals().size();
            activeSettler.addMineral(mineral);
            if (n == activeSettler.getMinerals().size())
                output.println("settler " + IDs.get(activeSettler) + " received one unit of " + args[1]);
            else
                output.println("settler inventory too full");
        }
    }
    /**
     * A addteleportpair parancshoz tartozó osztály.
     */
    private static class addteleportpairCommand implements Command{

        public void execute(String[] args) {
            if (args.length < 3) {
                output.println("all details must be specified");
                return;
            }
            Asteroid a1 = (Asteroid) IDs.getOrDefault(args[1], null);
            Asteroid a2 = (Asteroid) IDs.getOrDefault(args[2], null);
            if (a1 == null || a2 == null){
                output.println("couldn’t complete request\n" +
                        "    selected ID not available\n");
                return;
            }
            Teleport t1 = new Teleport(false);
            Teleport t2 = new Teleport(false);
            t1.setPair(t2);
            t2.setPair(t1);
            t1.setNeighbour(a1);
            t2.setNeighbour(a2);
            a1.addNeighbour(t1);
            a2.addNeighbour(t2);
            int id = maxIDs.get("teleport");
            IDs.put("t" + (id+1), t1);
            IDs.put("t" + (id+2), t2);
            maxIDs.replace("teleport", id+2);
            game.addTeleport(t1);
            game.addTeleport(t2);
            output.println("connected teleportgates " + ("t" + (id+1)) +" " + ("t" + (id+2)) " placed by " + args[1] + " " + args[2]);
        }
    }
    /**
     * A nextturn parancshoz tartozó osztály.
     */
    // TODO Annának
    private static class nextturnCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A robotaction parancshoz tartozó osztály.
     */
    // TODO Annának
    private static class robotactionCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A sunaction parancshoz tartozó osztály.
     */
    // TODO Annának
    private static class sunactionCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A solarwind parancshoz tartozó osztály.
     */
    // TODO Annának
    private static class solarwindCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A checkwin parancshoz tartozó osztály.
     */
    private static class checkwinCommand implements Command{
        /**
         * Értesíti a felhasználót arról, hogy megnyerte-e a játékot.
         * @param args a paraméterek tömbje
         */
        public void execute(String[] args) {
            if (game.checkWin())
                output.println("game won");
            else
                output.println("win conditions not met");
        }
    }
    /**
     * A chechlose parancshoz tartozó osztály.
     */
    private static class checkloseCommand implements Command{
        /**
         * Értesíti a felhasználót arról, hogy elvesztette-e a játékot.
         * @param args a paraméterek tömbje
         */
        public void execute(String[] args) {
            if (game.checkLose())
                output.println("game lost");
            else
                output.println("losing conditions not met");
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
        /**
         * A paraméterként megkapott aszteroidának a closeToSun változóját állítja be a megadott értékre.
         * Ha nincs elég argumentum, vagy nem létezik ilyen aszteroida, akkor hibát jelez.
         * @param args a paraméterek tömbje
         */
        public void execute(String[] args) {
            if (args.length < 3 || (!"0".equals(args[2]) && !"1".equals(args[2]))) {
                output.println("all details must be specified");
                return;
            }
            Asteroid asteroid = (Asteroid)IDs.getOrDefault(args[1], null);
            Sun sun = game.getSun();
            List<Asteroid> asteroids = sun.getAsteroids();
            if (asteroid == null || !asteroids.contains(asteroid)){
                output.println("couldn’t complete request\n" +
                        "    selected ID not available\n");
            }else{
                boolean oldCloseToSun = asteroid.getCloseToSun();
                boolean newCloseToSun = !"0".equals(args[2]) && ("1".equals(args[2]));
                if (oldCloseToSun == newCloseToSun){
                    output.println(args[1] + "already " + (oldCloseToSun ? "close to " : "far from ") + "sun, no change");
                }else{
                    asteroid.setCloseToSun();
                    output.println(args[1] + "set " + (newCloseToSun ? "close to " : "far from ") + "sun");
                }
            }
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
            if (args.length < 3 || (!"0".equals(args[2]) && !"1".equals(args[2]))) {
                output.println("all details must be specified");
                return;
            }
            Teleport teleport = (Teleport)IDs.getOrDefault(args[1], null);
            if (game.getGates().contains(teleport)){
                boolean bamboozled = !"0".equals(args[2]) && ("1".equals(args[2]));
                output.println(args[1] + "teleportgate" + (bamboozled ? "" : "not") + "bamboozled");
            }else{
                output.print("couldn’t complete request\n" +
                        "    selected ID not available\n");
            }
        }
    }

    private static boolean settlerCommandCheck(String[] args, int argscnt){
        if (args.length < argscnt){
            output.println("all details must be specified");
            return false;
        }
        if (activeSettler == null){
            output.println("couldn’t complete request\n" +
                    "    no active settler selected\n");
            return false;
        }
        if (game.getSettlers().contains((Settler)IDs.getOrDefault(args[1], null))){
            output.println("active settler died");
            return false;
        }
        return true;
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
        commands.put("checkwin", new checkwinCommand()); commands.put("checklose", new checkloseCommand());
        commands.put("newgame", new newgameCommand()); commands.put("setclosetosun", new setclosetosunCommand());
        commands.put("giveup", new giveupCommand()); commands.put("ufoaction", new ufoactionCommand());
        commands.put("bamboozleteleport", new bamboozleteleportCommand());
    }

    private static Mineral parseMineral(String arg){
        if ("iron".equals(arg))
            return new Iron();
        else if ("coal".equals(arg))
            return new Coal();
        else if ("ice".equals(arg))
            return new Ice();
        else if (arg.startsWith("uranium")){
            try{
                int exposedToSunCounter = Integer.parseInt(arg.subSequence(7, arg.length()-2));
                return new Uranium(exposedToSunCounter);
            }catch (Exception e) {
                return null;
            }
        }else
            return null;
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
