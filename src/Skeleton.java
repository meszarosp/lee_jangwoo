import java.io.*;
import java.util.*;


/**
 * Kezeli a felhasználóval való interakciókat és a menüt.
 * Az egyes use-case-ekhez megcsinálja az inicializálást majd meghívja a megfelelő tesztelni kívánt metódust.
 */
public class Skeleton {

    private static Scanner input = new Scanner(System.in);
    private static PrintStream output = System.out;
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

    private static void resetIDs(){
        IDs.clear();
        reverseIDs.clear();
    }
    public static boolean getRandom() {
    	return random;
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

        private Scanner fileInput;

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
            Sun sun = game.getSun();
            activeSettler = null;
            IDs.clear();
            reverseIDs.clear();
            try {
                fileInput = new Scanner(file);
                readAsteroidsTeleports(sun);
                readTravellers();
            }catch (Exception e){
                e.printStackTrace();
                output.println("load unsuccessful");
                return;
            }
            output.println("loaded " + args[1]);
        }

        private void readTravellers() throws Exception{
            String[] pieces = fileInput.nextLine().split(" ");
            int nSettlers = Integer.parseInt(pieces[1]);
            int nRobots = Integer.parseInt(pieces[3]);
            int nUFOs = Integer.parseInt(pieces[5]);
            for (int i = 0; i < nSettlers; i++){
                pieces = fileInput.nextLine().split(" ");
                Asteroid a = (Asteroid)IDs.getOrDefault(pieces[1], null);
                if (a == null)
                    throw new Exception();
                Settler s = new Settler(a);
                addID(pieces[0].substring(0, pieces[0].length()-2), s);
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
                pieces = fileInput.nextLine().split(" ");
                Asteroid a = (Asteroid)IDs.getOrDefault(pieces[1], null);
                if (a == null)
                    throw new Exception();
                Robot r = new Robot(a);
                addID(pieces[0].substring(0, pieces[0].length()-2), r);
                game.addRobot(r);
            }
            for (int i = 0; i < nUFOs; i++){
                pieces = fileInput.nextLine().split(" ");
                Asteroid a = (Asteroid)IDs.getOrDefault(pieces[1], null);
                if (a == null)
                    throw new Exception();
                UFO ufo = new UFO(a);
                addID(pieces[0].substring(0, pieces[0].length()-2), ufo);
                game.addUFO(ufo);
            }
        }

        private void updateMaxID(String type, String ID){
            int number = Integer.parseInt(ID.substring(1));
            if (number > maxIDs.get(type))
                maxIDs.replace(type, number);
        }

        private void readAsteroidsTeleports(Sun sun) throws Exception {
            String[] pieces = fileInput.nextLine().split(" ");
            int nAsteroids=Integer.parseInt(pieces[1]);
            int nTeleports=Integer.parseInt(pieces[3]);
            ArrayList<String[]> lines = new ArrayList<>();
            List<Asteroid> asteroids = new ArrayList<Asteroid>();
            for (int i = 0; i < nAsteroids; i++){
                pieces = fileInput.nextLine().split(" ");
                lines.add(pieces);
                Mineral m = parseMineral(pieces[pieces.length-1]);
                boolean closeToSun = !"0".equals(pieces[pieces.length-2]) && "1".equals(pieces[pieces.length-2]);
                int shell = Integer.parseInt(pieces[pieces.length-3]);
                Asteroid a = new Asteroid(shell, closeToSun, m, sun);
                asteroids.add(a);
                String ID = pieces[0].substring(0, pieces[0].length()-1);
                updateMaxID("asteroid", ID);
                addID(ID, a);
            }
            sun.addAsteroids(asteroids);
            for (int i = 0; i < nTeleports; i++){
                pieces = fileInput.nextLine().split(" ");
                lines.add(pieces);
                Teleport t = new Teleport();
                game.addTeleport(t);
                String ID = pieces[0].substring(0, pieces[0].length()-1);
                updateMaxID("teleport", ID);
                addID(ID, t);
            }
            for (int i = 0; i < nAsteroids; i++){
                pieces = lines.get(i);
                int k = Integer.parseInt(pieces[1]);
                for (int j = 0; j < k; j++){
                    Asteroid a = (Asteroid) IDs.getOrDefault(pieces[0].substring(0, pieces[0].length()-1), null);
                    INeighbour neighbour = (INeighbour)IDs.getOrDefault(pieces[2+j], null);
                    a.addNeighbour(neighbour);
                }
            }
            for (int i = nAsteroids; i < nAsteroids+nTeleports; i++){
                pieces = lines.get(i);
                Teleport t = (Teleport)IDs.getOrDefault(pieces[0].substring(0, pieces[0].length()-1), null);
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
            PrintStream temp;
            try {
                temp = new PrintStream(new FileOutputStream(file));
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
                addID("s" + (n+1), s);
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
            addID("a" + (n+1), asteroid);
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
                addID("r" + (n+1), r);
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
                addID("r" + (n+1), ufo);
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
            if (args.length < 2){
                output.println("all details must be specified");
                return;
            }
            Settler settler = (Settler)IDs.getOrDefault(args[1], null);
            if (settler == null){
                output.println("couldn’t complete request\n" +
                        "    selected ID not available\n");
                return;
            }
            activeSettler = settler;
            output.println("settler " + args[1] + " is now active");
        }
    }
    /**
     * A move parancshoz tartozó osztály.
     */
    private static class moveCommand implements Command{

        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 1)) {
                return;
            }
            if (args.length < 2) {
                for (int i = 0; i < activeSettler.getAsteroid().getNeighbourCount(); i++) {
                    INeighbour n = activeSettler.getAsteroid().getNeighbourAt(i);
                    String id = reverseIDs.get(n);
                    String type = "";
                    if (id.charAt(0) == 'a')
                        type = "asteroid";
                    if (id.charAt(0) == 't')
                        type = "teleportgate";
                    output.println(n + ": " + type);
                }
                return;
            }
            int index = Integer.parseInt(args[1]);
            if (activeSettler.move(index)) {
                INeighbour n = activeSettler.getAsteroid().getNeighbourAt(index);
                String id = reverseIDs.get(n);
                output.println("move to " + id + " successful");
            } else {
                output.println("move unsuccessful");
            };

        }
    }
    /**
     * A drill parancshoz tartozó osztály.
     */

    private static class drillCommand implements Command{
        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 1))
                return;
            if (activeSettler.drill()) {
                Asteroid a = activeSettler.getAsteroid();
                int shell = a.getShell();
                output.println("drilling successful");
                output.println("shell is now " + shell + " units thick");
            } else {
                output.println("drilling unsuccessful");
                output.println("shell has already been drilled through");
            }
        }
    }
    /**
     * A mine parancshoz tartozó osztály.
     */
    private static class mineCommand implements Command{

        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 1))
                return;
            Mineral m = activeSettler.getAsteroid().getCore();
            if (activeSettler.mine()) {
                output.println("mining successful");
                output.println("one unit of " + m.toString() + " acquired");
                output.println("asteroid is now empty");
            } else {
                output.println("mining unsuccessful");
                if (activeSettler.getAsteroid().getShell() > 0)
                    output.println("asteroid still has shell");
                if (m == null)
                    output.println("asteroid is already empty");
            }
        }
    }
    /**
     * A putmineralback parancshoz tartozó osztály.
     */
    private static class putmineralbackCommand implements Command{

        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 2))
                return;
            Mineral m = parseMineral(args[1]);
            Mineral core = activeSettler.getAsteroid().getCore();
            if (activeSettler.putMineralBack(m)) {
                output.println(m.toString() + " is now in the asteroid");
                // TODO ha felrobban
            } else {
                output.println("putting back mineral unsuccessful");
                if (activeSettler.getAsteroid().getShell() > 0)
                    output.println("asteroid still has shell");
                else if (core != null)
                    output.println("asteroid has other mineral");
                else
                    output.println("settler doesn't have specified mineral");
            }
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
            if (activeSettler.craftTeleport()) {
                output.println("new robot successfully crafted");
            } else {
                output.println("new robot couldn't be crafted, insufficient materials");
            }
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
            ///TODO megkérdezni a Petit
            //activeSettler.placeTeleport();
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
                output.println("settler " + reverseIDs.get(activeSettler) + " received one unit of " + args[1]);
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
            addID("t" + (id+1), t1);
            addID("t" + (id+2), t2);
            maxIDs.replace("teleport", id+2);
            game.addTeleport(t1);
            game.addTeleport(t2);
            output.println("connected teleportgates " + ("t" + (id+1)) +" " + ("t" + (id+2)) + " placed by " + args[1] + " " + args[2]);
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

    private static class robotactionCommand implements Command{

        public void execute(String[] args) {
            if (random) {
                if (args.length < 2) {
                    output.println("robot must be specified");
                    return;
                }
                // TODO robot stuff
            } else {
                if (args.length < 3) {
                    output.println("all details must be specified");
                    return;
                }
                Robot r = (Robot) IDs.get(args[1]);
                if (args[2].equals("drill")) {
                    if (r.drill())
                        output.println("robot " + args[1] + " drilled on " +
                                reverseIDs.get(r.getAsteroid()) + "shell is now" + r.getAsteroid().getShell());
                    else
                        output.println("robot " + args[1] + " couldn't drill");
                }
                if (args[2].equals("move")) {
                    if (args.length < 4) {
                        output.println("all details must be specified");
                        return;
                    }
                    int i = Integer.parseInt(args[3]);
                    if (r.move(i))
                        output.println("robot " + args[1] + " moved to " + reverseIDs.get(r.getAsteroid()));
                    else
                        output.println("robot couldn't move");
                }
            }
        }
    }
    /**
     * A sunaction parancshoz tartozó osztály.
     */
    private static class sunactionCommand implements Command{

        public void execute(String[] args) {
            game.getSun().makeAction();
            // TODO honnan a rákból tudjuk hogy mi történt?
        }
    }
    /**
     * A solarwind parancshoz tartozó osztály.
     */
    private static class solarwindCommand implements Command{

        public void execute(String[] args) {
            if (args.length < 3) {
                output.println("all details must be specified");
                return;
            }
            Asteroid a = (Asteroid) IDs.getOrDefault(args[1], null);
            if (a == null){
                output.println("couldn’t complete request\n" +
                        "    selected ID not available\n");
                return;
            }
            int radius = Integer.parseInt(args[2]);
            List<Robot> robots = game.getRobots();
            List<Settler> settlers = game.getSettlers();
            List<UFO> UFOs = game.getUFOs();
            List<Teleport> teleports = game.getGates();
            boolean[] b;
            b = new boolean[teleports.size()];
            for (int i = 0; i < teleports.size(); i++) {
                b[i] = teleports.get(i).getBamboozled();
            }
            a.solarWind(radius);
            output.println("solarwind created with asteroid " + args[1] + "in the middle");
            output.println("and a " + radius + " radius");
            output.println("events caused:");

            for (Robot r : robots) {
                if (!game.getRobots().contains(r))
                   output.println(reverseIDs.get(r) + " robot died");
            }
            for (Settler s : settlers) {
                if (!game.getSettlers().contains(s))
                    output.println(reverseIDs.get(s) + " settler died");
            }
            for (UFO u : UFOs) {
                if (!game.getUFOs().contains(u))
                    output.println(reverseIDs.get(u) + " ufo died");
            }
            for (int i = 0; i < teleports.size(); i++) {
                if (!b[i] && teleports.get(i).getBamboozled())
                    output.println(reverseIDs.get(teleports.get(i)) + " teleportgate gone mad");
            }
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
    //TODO Peti?
    private static class newgameCommand implements Command{

        public void execute(String[] args) {
            if (args.length < 3) {
                output.println("all details must be specified");
                return;
            }
            int nSettler, nAsteroid;
            try {
                nSettler = Integer.parseInt(args[1]);
                nAsteroid = Integer.parseInt(args[2]);

            }catch (Exception e){
                output.println("all details must be specified");
                return;
            }
            game.init(nSettler, nAsteroid);
            resetIDs();

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
    //TODO Peti
    private static class giveupCommand implements Command{

        public void execute(String[] args) {

        }
    }
    /**
     * A ufoaction parancshoz tartozó osztály.
     */
    //TODO Peti
    private static class ufoactionCommand implements Command{

        public void execute(String[] args) {
            if (args.length < 2 || (args.length == 3 && !"mine".equals(args[2])) || (args.length == 4 && !"move".equals(args[2]))){
                output.println("all details must be specified");
                return;
            }
            if (args.length == 2){
                UFO ufo = (UFO)IDs.getOrDefault(args[1], null);
                if (ufo == null)
                    output.print("couldn’t complete request\n" +
                            "    selected ID not available\n");
                else {
                }
            }
            if (args.length == 3){

            }
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

    /**
     * Az exit parancshoz tartozó osztály.
     */
    private static class exitCommand implements Command{

        public void execute(String[] args) {
            System.exit(0);
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
        if (!game.getSettlers().contains((Settler)IDs.getOrDefault(args[1], null))){
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
        commands.put("exit", new exitCommand());
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
                int exposedToSunCounter = Integer.parseInt(arg.substring(7, arg.length()-2));
                return new Uranium(exposedToSunCounter);
            }catch (Exception e) {
                return null;
            }
        }else
            return null;
    }

    private static void parseCommand(){
        String[] pieces = input.nextLine().split(" ");
        if (pieces.length == 0) {
            output.println("invalid command");
            return;
        }
        Command cmd = commands.getOrDefault(pieces[0], null);
        if (cmd == null){
            output.println("invalid command");
            return;
        }
        cmd.execute(pieces);
    }

    private static void initializeMaxIDs(){
        maxIDs.put("asteroid", 0);
        maxIDs.put("teleport", 0);
        maxIDs.put("settler", 0);
        maxIDs.put("robot", 0);
        maxIDs.put("ufo", 0);
    }


    /**
     * A program belépési pontja, kiírja a menüpontokat és bekéri a felhaszálótól a választott menüpontot a menu()
     * függvénnyel, amihez meghívja a megfelelő inicializáló függvényt.
     * Ezt addig ismétli, amíg a felhasználó ki nem lép a programból.
     * @param args parancssori argumentumok
     */
    public static void main(String[] args){
        initializeCommands();
        initializeMaxIDs();
        while (true){
            parseCommand();
            //output.flush();
        }
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
