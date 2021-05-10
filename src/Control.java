import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class Control implements ActionListener, MouseListener{

    /**
     * Default constructor
     */
    public Control() {}


    /**
     * Eseménykezelő. A levelview és az inventoryview eseményeit kezeli le.
     * @param e Az esemény leírója
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] actionCommand = e.getActionCommand().split(" ");
        System.out.print(actionCommand[0]);
        if (actionCommand.length > 1)
            System.out.print(" " + actionCommand[1]);
        System.out.println();
        commands.get(actionCommand[0]).execute(actionCommand);      //move még kérdéses
        /*if (actionCommand[0].equals("load")){
            activeSettler = game.getSettlers().get(0);
            LevelView lv = gameFrame.getLevelView();
            refreshActiveSettler();
            lv.setActiveSettler(activeSettler);
            lv.Update();
            lv.repaint();
            lv.getInventory().repaint();
        }*/
        /*if(actionCommand[0].equals("save") || actionCommand[0].equals("load") || actionCommand[0].equals("giveup")){

        } else {*/
        if (actionCommand[0].equals("save") || actionCommand[0].equals("giveup") ||
                actionCommand[0].equals("checkwin") || actionCommand[0].equals("checklose")|| actionCommand[0].equals("newgame")) {

        }else{
            if (refreshActiveSettler()) {
                commands.get("nextturn").execute(new String[]{"nextturn"});
                if (checkActiveSettlerDied())
                    refreshActiveSettler();
                JOptionPane.showMessageDialog(null, "Turn ended, next turn starts.");
            }
        }
            LevelView lv = gameFrame.getLevelView();
            lv.setActiveSettler(activeSettler);
            lv.Update();
            lv.repaint();
            lv.getInventory().Update();
            lv.getInventory().repaint();
        //}
    }

    /**
     * Egér esemény kezelő. A levelview-n történt kattintást kezeli le.
     * @param e Az egéresemény leírója.
     */
    @Override
    public void mouseClicked(MouseEvent e){
        LevelView lv = gameFrame.getLevelView();
        INeighbour neighbour = lv.click(e.getX(), e.getY());
        if(neighbour != null){
            List<INeighbour> neighbours = activeSettler.getAsteroid().getNeighbours();
            if(neighbours.contains(neighbour)){
                for(int i = 0; i < neighbours.size(); i++){
                    if(neighbours.get(i).equals(neighbour)){
                        commands.get("move").execute(new String[]{"move", Integer.toString(i)});
                        if(refreshActiveSettler()){
                            commands.get("nextturn").execute(new String[]{"nextturn"});
                            if(checkActiveSettlerDied())
                                refreshActiveSettler();
                            JOptionPane.showMessageDialog(null, "Turn ended, next turn starts.");
                        }
                        lv.setActiveSettler(activeSettler);
                        lv.Update();
                        lv.repaint();
                        lv.getInventory().repaint();
                        return;
                    }
                }
            }
        }
    }

    /**
     * Egér lenyomás eseménykezelő. Nincs használatban.
     * @param e Az esemény leírója.
     */
    @Override
    public void mousePressed(MouseEvent e){}

    /**
     * Egér felengedés eseménykezelő. Nincs használatban.
     * @param e Az esemény leírója.
     */
    @Override
    public void mouseReleased(MouseEvent e){}

    /**
     * Egér eseménykezelő. Nincs használatban.
     * @param e Az esemény leírója.
     */
    @Override
    public void mouseEntered(MouseEvent e){}

    /**
     * Egér eseménykezelő. Nincs használatban.
     * @param e Az esemény leírója.
     */
    @Override
    public void mouseExited(MouseEvent e){}

    /**
     * Frissíti az aktív telepest.
     * @return IGAZ, ha véget ért a kört és kell nextturn parancs, HAMIS, ha nem.
     */
    private static boolean refreshActiveSettler(){
        if(activeSettler == null){
            if(!game.getSettlers().isEmpty())
                activeSettler = game.getSettlers().get(0);
            ControlSettlers = new ArrayList<Settler>(game.getSettlers());
            return false;
        }
        if(game.getSettlers().contains(activeSettler)){
            ControlSettlers = new ArrayList<Settler>(game.getSettlers());
            for(int i = 0; i < ControlSettlers.size(); i++){
                if(ControlSettlers.get(i).equals(activeSettler)){
                    if(i == ControlSettlers.size()-1){
                        activeSettler = ControlSettlers.get(0);
                        return true;
                    }else {
                        activeSettler = ControlSettlers.get(i + 1);
                        return false;
                    }
                }
            }
        } else{
            int idxOfActive = 0;
            for(int i = 0; i < ControlSettlers.size(); i++){
                if(ControlSettlers.get(i).equals(activeSettler))
                    idxOfActive = i;
            }
            while(idxOfActive != 0){
                Settler settlerBeforeActive = ControlSettlers.get(idxOfActive-1);
                if(game.getSettlers().contains(settlerBeforeActive)){
                    ControlSettlers = new ArrayList<Settler>(game.getSettlers());
                    for(int i = 0; i < ControlSettlers.size(); i++){
                        if(ControlSettlers.get(i).equals(settlerBeforeActive)){
                            if(i == ControlSettlers.size()-1){
                                activeSettler = ControlSettlers.get(0);
                                return true;
                            } else{
                                activeSettler = ControlSettlers.get(i+1);
                                return false;
                            }
                        }
                    }
                }
                idxOfActive--;
            }
            if(!game.getSettlers().isEmpty())
                activeSettler = game.getSettlers().get(0);
            ControlSettlers = new ArrayList<Settler>(game.getSettlers());
            return false;
        }
        return false;
    }

    /**
     * Tárolja, hogy mi a legutóbbi tudomása a kontrollernek a játékban lévő telepesekről.
     */
    private static List<Settler> ControlSettlers;// = ... ArrayList ctor clone

    /**
     * GameFrame, ami tárolja a paneleket.
     */
    private static GameFrame gameFrame;
    /**
     * Az input, ahonnan a parancsokat olvassa. Alapesetben a standard bemenet.
     */
    private static Scanner input = new Scanner(System.in);

    /**
     * Az output, ahov? a parancsok kimenet?t ?rja. Alapesetben a standard kimenet.
     */
    private static PrintStream output = System.out;

    /**
     * Jelzi, hogy a v?letlenszer? t?rt?n?sek ki vannak-e kapcsolva.
     */
    private static boolean random = true;

    /**
     * A game objektum, amivel ?ppen t?rt?nik a j?t?k.
     */
    private static Game game = new Game();

    /**
     * Az a settler, amelyikkel ?ppen j?tszik a felhaszn?l?. Ez a settler kapja majd a settlereknek f?z?tt kommentek.
     */
    private static Settler activeSettler = null;

    /**
     * T?rolja, hogy a j?t?kban azonos?t?val ell?tott objektumok k?z?l,
     * mi a legnagyobb m?r kiosztott azonos?t?nak a sz?ma.
     * Azonos?t? a felhaszn?l? fel? kommunik?lt azonos?t?t jelenti.
     * T?rolt adatok pl.: settler, asteroid, ufo, robot.
     */
    private static HashMap<String, Integer> maxIDs = new HashMap<String, Integer>();

    /**
     * A j?t?kban l?v? objektumok ?s a felhaszn?l? fel? k?z?lt azonos?t?k ?sszerendel?se. A kulcs az azonos?t?.
     */
    public static HashMap<String, Object> IDs = new HashMap<String, Object>();

    /**
     * A j?t?kban l?v? objektumok ?s a felhaszn?l? fel? k?z?lt azonos?t?k ?sszerendel?se. A kulcs az objektum.
     */
    public static HashMap<Object, String> reverseIDs = new HashMap<Object, String>();

    /**
     * Hozz?ad egy ?j azonos?t?t az azonos?t? t?rol?khoz.
     * @param s A sz?veges azonos?t?
     * @param o Az objektum
     */
    private static void addID(String s, Object o){
        IDs.put(s, o);
        reverseIDs.put(o, s);
    }

    /**
     * Kit?r?l egy azonos?t?-objektum ?sszerendel?st az ezt t?rol?kb?l.
     * @param s A sz?veges azonos?t?
     * @param o Az objektum
     */
    private static void removeID(String s, Object o){
        IDs.remove(s);
        reverseIDs.remove(o);
    }

    /**
     * T?rli az azonos?t?-objektum ?sszerendel?seket.
     */
    private static void resetIDs(){
        IDs.clear();
        reverseIDs.clear();
    }

    /**
     * Interfész, amely a parancsok számára készült. A parancsok ezt implementálják.
     */
    private interface Command{
        /**
         * A parancsot v?grehajt? f?ggv?ny.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args);
    }
    private static class loadCommand implements Command{

        /**
         * Annak a megnyitott f?jlnak scannere, amib?l olvassa a bet?lteni k?v?nt p?ly?t.
         */
        private Scanner fileInput;

        /**
         * A beolvasott aszteroidák száma.
         */
        private int nAsteroids;


        /**
         * A beolvasott teleportkapuk száma.
         */
        private int nTeleports;

        /**
         * Egy JFileChooser dialógusablak segítségével lekéri a felhasználótól az útvonalat.
         * @return A megnyitni kívánt file.
         */
        public File showDialog(){
            JFileChooser fileChooser = new JFileChooser();
            int returnval = fileChooser.showDialog(null, "Open");
            if (returnval == fileChooser.APPROVE_OPTION)
                return fileChooser.getSelectedFile();
            else
                return null;
        }

        /**
         * L?trehoz egy ?j j?t?kot, amihez bet?lti a megadott f?jlb?l a p?ly?t.
         * Jelzi a felhaszn?l?nak a parancs sikeress?g?t.
         * Ha nincs el?g argumentum, vagy hiba t?rt?nt olvas?s k?zben, akkor jelzi a felhaszn?l?nak.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            File file;
            if (args.length < 2) {
                file = showDialog();
                //output.println("load unsuccessful");
                //return;
            }else{
                file = new File(args[1]);
            }
            if (!file.exists()){
                output.println("load unsuccessful");
                return;
            }
            game = new Game();
            gameFrame.getLevelView().setGame(game);
            Sun sun = game.getSun();
            activeSettler = null;
            IDs.clear();
            reverseIDs.clear();
            try {
                fileInput = new Scanner(file);
                readAsteroidsTeleports(sun);
                readTravellers();
                readCoordinates();
            }catch (Exception e){
                e.printStackTrace();
                output.println("load unsuccessful");
                return;
            }
            gameFrame.getLevelView().Update();
            //output.println("loaded " + args[1]);
        }

        /**
         * Beolvassa a f?jlb?l a settlereket, robotokat ?s az uf?kat.
         * Beolvassa az el?tt?k l?v? sort is, ami jelzi, hogy melyikb?l h?ny darab van.
         * Ha hiba van, akkor exceptiont dob.
         * @throws Exception Ha b?rmilyen hiba t?rt?nik olvas?s k?zben, akkor exceptiont dob.
         * Hiba lehet, ha nem megfelel? a form?tum vagy a f?jl olvas?sa k?zben hiba t?rt?nik.
         */
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
                Settler s = new Settler(a, game);
                String ID = pieces[0].substring(0, pieces[0].length()-1);
                updateMaxID("settler", ID);
                addID(ID, s);
                game.addSettler(s);
                int k = Integer.parseInt(pieces[2]);
                for (int j = 0; j < k; j++){
                    Mineral m = parseMineral(pieces[3+j]);
                    s.addMineral(m);
                }
                int t = Integer.parseInt(pieces[3+k]);
                for (int j = 0; j < t; j++){
                    Teleport teleport = (Teleport)IDs.getOrDefault(pieces[3+k+1+j], null);
                    if (teleport == null)
                        throw new Exception();
                    s.addTeleport(teleport);
                }
                gameFrame.getLevelView().addSettlerView(s);
            }
            for (int i = 0; i < nRobots; i++){
                pieces = fileInput.nextLine().split(" ");
                Asteroid a = (Asteroid)IDs.getOrDefault(pieces[1], null);
                if (a == null)
                    throw new Exception();
                Robot r = new Robot(a, game);
                String ID = pieces[0].substring(0, pieces[0].length()-1);
                updateMaxID("robot", ID);
                addID(ID, r);
                game.addRobot(r);

            }
            for (int i = 0; i < nUFOs; i++){
                pieces = fileInput.nextLine().split(" ");
                Asteroid a = (Asteroid)IDs.getOrDefault(pieces[1], null);
                if (a == null)
                    throw new Exception();
                UFO ufo = new UFO(a, game);
                String ID = pieces[0].substring(0, pieces[0].length()-1);
                updateMaxID("ufo", ID);
                addID(ID, ufo);
                game.addUFO(ufo);
                gameFrame.getLevelView().addUFOView(ufo);
            }
        }

        /**
         * A megadott típushoz tartozó ID-t frissíti a maxID összerendelésben.
         * Csak akkor frissít ha az ID-hez tartozó szám, nagyobb, mint az eddigi legnagyobb.
         * @param type A típus (pl.: settler)
         * @param ID Az ID, amit ellen?rizni kell, hogy a száma, nagyobb-e, mint az eddigi legnagyobb.
         */
        private void updateMaxID(String type, String ID){
            int number = Integer.parseInt(ID.substring(1));
            if (number > maxIDs.get(type))
                maxIDs.replace(type, number);
        }

        /**
         * Beolvassa a f?jlb?l az aszteroid?kat ?s a teleportkapukat.
         * Beolvassa a le?r?sok el?tti sort is, ami azt t?rolja, hogy melyikb?l h?ny darab van.
         * @param sun A j?t?kban l?v? nap.
         * @throws Exception Ha b?rmilyen hiba t?rt?nik olvas?s k?zben, akkor exceptiont dob.
         * Hiba lehet, ha nem megfelel? a form?tum vagy a f?jl olvas?sa k?zben hiba t?rt?nik.
         */
        private void readAsteroidsTeleports(Sun sun) throws Exception {
            String[] pieces = fileInput.nextLine().split(" ");
            nAsteroids=Integer.parseInt(pieces[1]);
            nTeleports=Integer.parseInt(pieces[3]);
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
                    if (a == null)
                        throw new Exception();
                    t.setNeighbour(a);
                }else{
                    t.setNeighbour(null);
                }
                if (!"0".equals(pieces[2])){
                    Teleport t2 = (Teleport) IDs.getOrDefault(pieces[2], null);
                    if (t2 == null)
                        throw new Exception();
                    t.setPair(t2);
                }else{
                    t.setPair(null);
                }
            }
        }

        /**
         * A fájl végéről beolvassa az aszteroidák és teleportkapuk koordinátáit és a teleportkapuk színeit.
         */
        private void readCoordinates(){
            for (int i = 0; i < nAsteroids; i++){
                String[] pieces = fileInput.nextLine().split(" ");
                String ID = pieces[0].substring(0, pieces[0].length()-1);
                Asteroid a = (Asteroid)IDs.get(ID);
                int x = Integer.parseInt(pieces[1]);
                int y = Integer.parseInt(pieces[2]);
                gameFrame.getLevelView().addAsteroidView(a, x, y);
            }
            for (int i = 0; i < nTeleports; i++){
                String[] pieces = fileInput.nextLine().split(" ");
                String ID = pieces[0].substring(0, pieces[0].length()-1);
                Teleport t = (Teleport)IDs.get(ID);
                if (t.getNeighbour() == null)
                    continue;
                int x = Integer.parseInt(pieces[1]);
                int y = Integer.parseInt(pieces[2]);
                Color c = new Color(Integer.parseInt(pieces[3]), Integer.parseInt(pieces[4]), Integer.parseInt(pieces[5]));
                if (t != null)
                    gameFrame.getLevelView().addTeleportView(t, c, x, y);
            }
        }
    }
    /**
     * A save parancshoz tartoz? oszt?ly. A param?terk?nt megadott f?jlba ki?rja a j?t?kban l?v? p?lya aktu?lis ?ll?s?t.
     */
    private static class saveCommand implements Command{
        /**
         * A PrintWriter, ami a megnyitott f?jlba ?r, ahova a p?ly?t ki kell menteni.
         */
        private PrintWriter fileOutput;

        /**
         * Egy JFileChooser dialógusablak segítségével lekéri a felhasználótól az útvonalat.
         * @return A megnyitni kívánt file.
         */
        public File showDialog(){
            JFileChooser fileChooser = new JFileChooser();
            int returnval = fileChooser.showDialog(null, "Save");
            if (returnval == fileChooser.APPROVE_OPTION)
                return fileChooser.getSelectedFile();
            else
                return null;
        }


        /**
         * A param?terk?nt megadott f?jlba kimenti a p?lya aktu?lis ?ll?s?t.
         * Jelzi a felhaszn?l?nak, hogy sikeres volt-e a parancs.
         * Ha hiba t?rt?nik a f?jlba ?r?s k?zben, akkor jelzi a felhaszn?l?nak.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            File file;
            if (args.length < 2) {
                file = showDialog();
                //output.println("save unsuccessful");
                //return;
            }else{
                file = new File(args[1]);
            }

            //File file = new File(args[1]);
            try {
                fileOutput = new PrintWriter(file);
                saveAsteroidTeleport();
                fileOutput.println("S: " + game.getSettlers().size() + " R: " + game.getRobots().size() + " U: " + game.getUFOs().size());
                saveSettlers();
                saverobotsUFOs();
                saveCoordinates();
            }catch (Exception e){
                e.printStackTrace();
                output.println("save unsuccessful");
                return;
            }
            fileOutput.close();
           // output.println("saved to " + args[1]);
        }

        /**
         * Kimenti a megadott f?jlba a settlereket.
         */
        private void saveSettlers() {
            List<Settler> settlers = game.getSettlers();
            for (Settler s : settlers) {
                List<Mineral> minerals = s.getMinerals();
                fileOutput.print(reverseIDs.get(s) + ": " + reverseIDs.get(s.getAsteroid()) + " " + minerals.size() + " ");
                for (Mineral m : minerals)
                    fileOutput.print(m.toString() + " ");

                List<Teleport> teleports = s.getTeleportgates();
                int t = teleports.size();
                fileOutput.print(t + (t > 0 ? " " : ""));
                for (int i = 0; i < t - 1; i++)
                    fileOutput.print(reverseIDs.get(teleports.get(i)) + " ");
                if (t > 0)
                    fileOutput.print(reverseIDs.get(teleports.get(t - 1)));
                fileOutput.print("\n");
            }
        }

        /**
         * Kimenti a f?jlba a robotokat ?s az uf?kat.
         */
        private void saverobotsUFOs(){
            for (Robot r : game.getRobots())
                fileOutput.println(reverseIDs.get(r) + ": " + reverseIDs.get(r.getAsteroid()));
            for (UFO ufo : game.getUFOs())
                fileOutput.println(reverseIDs.get(ufo) + ": " + reverseIDs.get(ufo.getAsteroid()));
        }

        /**
         * Kimenti a f?jlba az aszteorid?kat ?s a robotokat.
         */
        private void saveAsteroidTeleport(){
            List<Asteroid> asteroids = game.getSun().getAsteroids();
            List <Teleport> gates = game.getGates();
            fileOutput.println("A: " + asteroids.size() + " T: " + gates.size());

            for (Asteroid a : asteroids){
                int ncount = a.getNeighbourCount();
                fileOutput.print(reverseIDs.get(a) + ": " + ncount + " ");
                for (int i = 0; i < ncount; i++)
                    fileOutput.print(reverseIDs.get(a.getNeighbourAt(i)) + " ");
                fileOutput.print(a.getShell() + " " + (a.getCloseToSun() ? "1" : "0") + " ");
                fileOutput.println(a.getCore() == null ? "empty" : a.getCore().toString());
            }
            for (Teleport t : gates)
                fileOutput.println(reverseIDs.get(t) + ": " + reverseIDs.getOrDefault(t.getNeighbour(), "0")
                        + " " + reverseIDs.getOrDefault(t.getPair(), "0") + (t.getBamboozled() ? " 1" : " 0"));
        }

        /**
         * Elmenti a teleportkapuk nézeteinek koordinátáit és a színeit.
         * Elmenti az aszteroidák nézeteinek koordinátáit.
         */
        private void saveCoordinates(){
            LevelView lv = gameFrame.getLevelView();
            HashMap<Asteroid, AsteroidView> asteroidviews = lv.getAsteroidViews();
            HashMap<Teleport, TeleportView> teleportviews = lv.getTeleportViews();
            for (Asteroid a : asteroidviews.keySet()){
                if (a != null){
                    String ID = reverseIDs.get(a);
                    AsteroidView av = asteroidviews.get(a);
                    if (av == null) continue;
                    fileOutput.println(ID + ": " + av.getX() + " " + av.getY());
                }
            }
            for (Teleport t : teleportviews.keySet()){
                if (t != null){
                    String ID = reverseIDs.get(t);
                    TeleportView tv = teleportviews.get(t);
                    if (tv == null) continue;
                    Color c = tv.getColor();
                    fileOutput.println(ID + ": " + tv.getX() + " " + tv.getY() + " " + c.getRed() + " " + c.getGreen() + " " + c.getBlue());
                }
            }
        }
    }
    /**
     * A input parancshoz tartoz? oszt?ly. ?tir?ny?tja a bemenetet a param?terk?nt megadott f?jlra.
     */
    private static class inputCommand implements Command{
        /**
         * A param?terk?nt megadott f?jlra ?ll?tja a bemenetet.
         * Ha nincs el?g argumentum, akkor hib?t jelez.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
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
            System.out.println("input set to " + args[1]);
        }
    }
    /**
     * A output parancshoz tartoz? oszt?ly. ?tir?ny?tja a kimenetet a param?terk?nt megadott f?jlba.
     */
    private static class outputCommand implements Command{
        /**
         * A param?terk?nt megadott f?jlra ir?ny?tja a kimenetet.
         * Ha nincs el?g argumentum, akkor hib?t jelez.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
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
            System.out.println("output set to " + args[1]);
            output.close();
            output = temp;
        }
    }
    /**
     * A addsettler parancshoz tartoz? oszt?ly. ?j settlert ad a p?ly?ra.
     */
    private static class addsettlerCommand implements Command{

        /**
         * A param?terk?nt megadott aszteroid?ra teszt egy ?j telepest.
         * Ha nincs el?g argumentum, akkor hib?val jelez a felhaszn?l?nak.
         * A hiba fajt?j?t is ki?rja a felhaszn?l?nak.
         * Ha l?trej?tt a telepes, akkor ezt is jelzi a felhaszn?l?nak.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (args.length < 2) {
                output.println("all details must be specified");
                return;
            }
            Object asteroid = IDs.getOrDefault(args[1], null);
            Sun sun = game.getSun();
            List<Asteroid> asteroids = sun.getAsteroids();
            if (asteroid == null || !asteroids.contains((Asteroid) asteroid)){
                output.println("couldn't complete request\n" +
                        "selected ID not available\n");
            }else{
                Settler s = new Settler((Asteroid) asteroid, game);
                int n = maxIDs.get("settler");
                maxIDs.replace("settler", n+1);
                addID("s" + (n+1), s);
                game.addSettler(s);
                ((Asteroid) asteroid).placeTraveller(s);
                output.println("settler s" + (n+1) + " added to asteroid: " + args[1]);
            }
        }
    }
    /**
     * A addasteroid parancshoz tartoz? oszt?ly. Hozz?ad egy aszteroid?t a megadott param?terekkel a p?ly?hoz.
     */
    private static class addasteroidCommand implements Command{

        /**
         * A megadott a param?terekkel hozz?ad egy ?j aszteroid?t a p?ly?hoz.
         * Ha b?rmilyen hiba van, akkor jelzi a felhaszn?l?nak a hiba fajt?j?t.
         * Ha rendben volt minden, akkor ki?rja a felhaszn?l?nak az ?j aszteroida param?tereit.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
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
     * A addrobot parancshoz tartoz? oszt?ly. Hozz?ad egy ?j robotot a megadott aszteroid?ra.
     */
    private static class addrobotCommand implements Command{

        /**
         * A param?terk?nt megadott aszteroid?ra hozz?ad egy ?j robotot.
         * Ha nincs el?g param?ter, akkor hib?t jelez.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (args.length < 2) {
                output.println("all details must be specified");
                return;
            }
            Object asteroid = IDs.getOrDefault(args[1], null);
            Sun sun = game.getSun();
            List<Asteroid> asteroids = sun.getAsteroids();
            if (asteroid == null || !asteroids.contains((Asteroid) asteroid)){
                output.println("couldn't complete request\n" +
                        "    selected ID not available\n");
            }else{
                Robot r = new Robot((Asteroid) asteroid, game);
                int n = maxIDs.get("robot");
                maxIDs.replace("robot", n+1);
                addID("r" + (n+1), r);
                game.addRobot(r);
                ((Asteroid) asteroid).placeTraveller(r);
                output.println("robot r" + (n+1) + " added to asteroid: " + args[1]);
            }
        }
    }
    /**
     * A addufo parancshoz tartoz? oszt?ly. Hozz?ad egy ?j uf?t a param?terk?nt megadott aszteroid?ra.
     */
    private static class addufoCommand implements Command{

        /**
         * A param?terk?nt megadott aszteroid?ra elhelyez egy ?j uf?t.
         * Ha nincs el?g param?ter, akkor hib?t jelez.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (args.length < 2) {
                output.println("all details must be specified");
                return;
            }
            Object asteroid = IDs.getOrDefault(args[1], null);
            Sun sun = game.getSun();
            List<Asteroid> asteroids = sun.getAsteroids();
            if (asteroid == null || !asteroids.contains((Asteroid) asteroid)){
                output.println("couldn't complete request\n" +
                        "    selected ID not available\n");
            }else{
                UFO ufo = new UFO((Asteroid) asteroid, game);
                int n = maxIDs.get("ufo");
                maxIDs.replace("ufo", n+1);
                addID("u" + (n+1), ufo);
                ((Asteroid) asteroid).placeTraveller(ufo);
                game.addUFO(ufo);
                output.println("ufo u" + (n+1) + " added to asteroid: " + args[1]);
            }
        }
    }
    /**
     * A connectasteroid parancshoz tartoz? oszt?ly. A param?terk?nt megadott 2 aszteroid?t szomsz?dossa teszi egym?ssal.
     */
    private static class connectasteroidCommand implements Command{

        /**
         * A param?terk?nt megadott 2 aszteroid?t szomsz?dossa teszi egym?ssal.
         * Ha nincs el?g param?ter, vagy nem l?teznek az aszteroid?k, akkor hib?t jelez.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (args.length < 3) {
                output.println("all details must be specified");
                return;
            }
            Asteroid a1 = (Asteroid) IDs.getOrDefault(args[1], null);
            Asteroid a2 = (Asteroid) IDs.getOrDefault(args[2], null);
            if (a1 == null || a2 == null){
                output.println("couldn't complete request\n" +
                        "    selected ID not available\n");
                return;
            }
            a1.addNeighbour(a2);
            a2.addNeighbour(a1);
            output.println(args[1] + " and " + args[2] + " are neighbouring asteroids");
        }
    }
    /**
     * A move parancshoz tartoz? oszt?ly.
     * Ha param?ter n?lk?l h?vj?k meg, akkor ki?rja az akt?v telepes sz?m?ra el?rhet? szomsz?dokat.
     * Ha param?terrel h?vj?k meg, akkor meg kell adni a szomsz?dok list?j?ban l?v? sorsz?mot (1-t?l sz?mozva),
     * amelyre az akt?v telepest mozgatni akarja a felhaszn?l?.
     */
    private static class moveCommand implements Command{

        /**
         * Ha param?ter n?lk?l h?vj?k meg, akkor ki?rja az akt?v telepes sz?m?ra el?rhet? szomsz?dokat.
         * a param?terrel h?vj?k meg, akkor meg kell adni a szomsz?dok list?j?ban l?v? sorsz?mot (1-t?l sz?mozva),
         * amelyre az akt?v telepest mozgatni akarja a felhaszn?l?.
         * Ha a megadott param?terek valami?rt hib?sak, akkor ezt jelzi a felhaszn?l?nak.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
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
                    output.println(id + ": " + type);
                }
                return;
            }
            int index = Integer.parseInt(args[1]);
            INeighbour n = activeSettler.getAsteroid().getNeighbourAt(index);       //átírva 0-tól indexelőre
            String id = reverseIDs.getOrDefault(n, "");
            if (activeSettler.move(index)) {
                output.println("move to " + id + " successful");
            } else {
                output.println("move" + ("".equals(id) ? "" : " to ") + id + " unsuccessful");
            }

        }
    }
    /**
     * A drill parancshoz tartoz? oszt?ly. Az akt?v telepessel v?grehajt egy f?r?s m?veletet.
     */
    private static class drillCommand implements Command{
        /**
         * Az akt?v telepessel v?grehajt egy f?r?s m?veletet.
         * Ha valami t?rt?nt az akt?v telepessel, akkor jelzi a felhaszn?l?nak.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 1))
                return;
            if (activeSettler.drill()) {
                Asteroid a = activeSettler.getAsteroid();
                int shell = a.getShell();
                output.println("drilling successful");
                output.println("shell is now " + shell + " unit(s) thick");
            } else {
                output.println("drilling unsuccessful");
                output.println("the shell has already been drilled through");
            }
        }
    }
    /**
     * A mine parancshoz tartoz? oszt?ly. Az akt?v telepessel v?grehajt egy b?ny?sz?s m?veletet.
     */
    private static class mineCommand implements Command{
        /**
         * Az akt?v telepessel v?grehajt egy b?ny?sz?s m?veletet.
         * Jelzi a felhaszn?l?nak a m?velet eredm?ny?t.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
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
                if (activeSettler.getAsteroid().getShell() > 0) {
                    output.println("asteroid still has shell");
                    return;
                }
                if (m == null){
                    output.println("asteroid is already empty");
                    return;
                }
                if (activeSettler.getMinerals().size() == 10) {
                    output.println("settler inventory too full");
                    return;
                }
            }
        }
    }
    /**
     * A putmineralback parancshoz tartoz? oszt?ly.
     * Ha param?ter n?lk?l h?vj?k meg, akkor ki?rja az akt?v telepesn?l l?v? nyersanyagokat.
     * Ha param?terrel h?vj?k meg, akkor a megadott param?ternek megfelel? sorsz?m? (1-t?l sz?mozva)
     * a telepesn?l l?v? nyersanyagot a telepes nyersanyagai k?z?l kiv?lasztja ?s ezt a nyersanyagvisszatev?s
     * m?veletnek ?tadja.
     */
    private static class putmineralbackCommand implements Command{
        /**
         * Ha param?ter n?lk?l h?vj?k meg, akkor ki?rja az akt?v telepesn?l l?v? nyersanyagokat.
         * Ha param?terrel h?vj?k meg, akkor a megadott param?ternek megfelel? sorsz?m? (1-t?l sz?mozva)
         * a telepesn?l l?v? nyersanyagot a telepes nyersanyagai k?z?l kiv?lasztja ?s ezt a nyersanyagvisszatev?s
         * m?veletnek ?tadja.
         * A felhaszn?l?nak jelzi a m?velet eredm?ny?t.
         * Ha ez robban?st okozott, akkor jelzi a felhaszn?l?nak, hogy a robban?s k?vetkezt?ben mi t?rt?nt.
         * (Megvizsg?lja, hogy mely telepesek, robotok, teleportkapuk haltak meg a robban?s miatt.)
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 1))
                return;
            if (args.length == 1){
                List<Mineral> minerals = activeSettler.getMinerals();
                for (Mineral m : minerals)
                    output.println(m.toString());
                return;
            }
            int i = Integer.parseInt(args[1]) -1;
            Mineral core = activeSettler.getAsteroid().getCore();
            List<Robot> robots = new ArrayList<Robot>(game.getRobots());
            List<Settler> settlers = new ArrayList<Settler>(game.getSettlers());
            List<UFO> UFOs = new ArrayList<UFO>(game.getUFOs());
            List<Teleport> teleports = new ArrayList<Teleport>(game.getGates());
            if (activeSettler.putMineralBack(i)) {
                output.println(activeSettler.asteroid.getCore().toString() + " is now in the asteroid");
                if (!game.getSun().getAsteroids().contains(activeSettler.getAsteroid())) {
                    output.println("the returned uranium caused an explosion");
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
                    for (Teleport t : teleports) {
                        if (!game.getGates().contains(t))
                            output.println(reverseIDs.get(t) + " teleport perished");
                    }
                }

            } else {
                output.println("putting back mineral unsuccessful");
                if (activeSettler.getAsteroid().getShell() > 0){
                    output.println("asteroid still has shell");
                    JOptionPane.showMessageDialog(null, "The asteroid still has shell");
                } else if (core != null){
                    output.println("asteroid has other mineral");
                    JOptionPane.showMessageDialog(null, "The asteroid has other mineral");
                } else {
                    output.println("settler doesn't have the necessary mineral\n");
                    JOptionPane.showMessageDialog(null, "There's no mineral to place");
                }
            }
        }
    }
    /**
     * A craftrobot parancshoz tartoz? oszt?ly. Az akt?v telepessel v?grehajt egy robot?sz?t?s m?veletet.
     */
    private static class craftrobotCommand implements Command{
        /**
         * Az akt?v telepessel v?grehajt egy robot?sz?t?s m?veletet.
         * Jelzi a felhaszn?l?nak a m?velet eredm?ny?t. Jelzi az elk?sz?tett robot azonos?t?j?t.
         * Ha hiba t?rt?nik, azt is jelzi.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 1))
                return;
            if (activeSettler.craftRobot()) {
                Robot newrobot = game.getRobots().get(game.getRobots().size()-1);
                int n = maxIDs.get("robot");
                maxIDs.replace("robot", n+1);
                addID("r" + (n+1), newrobot);
                output.println("new robot r" + (n+1) + " successfully crafted");
            } else {
                output.println("new robot couldn't be crafted, insufficient minerals");
            }
        }
    }
    /**
     * A craftteleport parancshoz tartoz? oszt?ly. Az akt?v telepessel v?grehajt egy teleportk?sz?t?s m?veletet.
     */
    private static class craftteleportCommand implements Command{
        /**
         * Az akt?v telepessel v?grehajt egy teleportk?sz?t?s m?veletet.
         * Jelzi a felhaszn?l?nak a m?velet eredm?ny?t. Jelzi az elk?sz?tett teleportkapuk azonos?t?j?t.
         * Ha hiba t?rt?nik, azt is jelzi.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 1))
                return;
            if (activeSettler.craftTeleport()) {
                Teleport t1 = game.getGates().get(game.getGates().size()-2);
                Teleport t2 = game.getGates().get(game.getGates().size()-1);
                int n = maxIDs.get("teleport");
                addID("t" + (n+1), t1);
                addID("t" + (n+2), t2);
                maxIDs.replace("teleport", (n+2));
                output.println("new pair of teleportgates t" + (n+1) + " and t" + (n+2) + " successfully crafted");
            } else {
                if (activeSettler.getTeleportgates().size() < 2)
                    output.println("new pair of teleportgates couldn't be crafted, insufficient minerals");
                else
                    output.println("new pair of teleportgates couldn't be crafted, inventory too full");
            }
        }
    }
    /**
     * A placeteleport parancshoz tartoz? oszt?ly. Az akt?v telepessel v?grehajt egy teleportlehelyez?s m?veletet.
     * Param?ter n?lk?l kilist?zza az akt?v telepesn?l l?v? teleportkapukat.
     */
    private static class placeteleportCommand implements Command{

        /**
         * Az els? param?ter annak a teleportkapunak a sorsz?ma (1-t?l sz?mozva), amelyik teleportkaput le akarja
         * helyezni a felhaszn?l?. Param?ter n?lk?l kilist?zza a telepesn?l l?v? teleportkapukat.
         * Ha hiba t?rt?nik akkor jelzi a felhaszn?l?nak, k?l?nben ki?rja a lehelyez?s t?ny?t.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 2))
                return;
            List<Teleport> gates = activeSettler.getTeleportgates();
            if (gates.size() == 0){
                output.println("there's no teleport to place");
                JOptionPane.showMessageDialog(null, "There's no teleport to place");
                return;
            }
            if (args.length == 1){
                for (Teleport gate : gates)
                    output.println(reverseIDs.get(gate));
            }
            int i = Integer.parseInt(args[1]);      //ez mar nullatol varja a cimzest
            if (i < 0 || i >= gates.size()) {
                output.println("all details must be specified");
                return;
            }
            Teleport t = gates.get(i);
            activeSettler.placeTeleport(t);
            output.println("teleport " + reverseIDs.get(t) + " placed");

        }
    }
    /**
     * Az addmineral parancshoz tartoz? oszt?ly. Az akt?v telepesnek ad egy nyersanyagot.
     * A nyersanyagot param?terben kell megadni.
     */
    private static class addmineralCommand implements Command{

        /**
         * Az akt?v telepesnek ad egy, az els? param?terben meghat?rozott nyersanyagot.
         * Ha nem j?l adta meg a felhaszn?l?, akkor hib?t jelez.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (!settlerCommandCheck(args, 2))
                return;
            Mineral mineral = parseMineral(args[1]);
            if (mineral == null) {
                output.println("all details must be specified");
                return;
            }
            if (activeSettler.addMineral(mineral))
                output.println("settler " + reverseIDs.get(activeSettler) + " received one unit of " + mineral.toString());
            else
                output.println("settler inventory too full");
        }
    }
    /**
     * A addteleportpair parancshoz tartoz? oszt?ly. 2 param?tere van. A megadott 2 aszteroid?ra lehelyez egy ?j
     * teleportkaput p?r, amelyek nincsenek megkerg?lve.
     */
    private static class addteleportpairCommand implements Command{

        /**
         * Az els? ?s a m?sodik param?terben meghat?rozott aszteroid?ra lehelyez egy-egy teleportkaput, amelyek p?rt alkotnak.
         * Ha hiba t?rt?nik, jelez a felhaszn?l?nak, k?l?nben ki?rja a teleportkapuk l?trej?tt?nek t?ny?t.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (args.length < 3) {
                output.println("all details must be specified");
                return;
            }
            Asteroid a1 = (Asteroid) IDs.getOrDefault(args[1], null);
            Asteroid a2 = (Asteroid) IDs.getOrDefault(args[2], null);
            if (a1 == null || a2 == null){
                output.println("couldn't complete request\n" +
                        "    selected ID not available\n");
                return;
            }
            Teleport t1 = new Teleport(false);
            Teleport t2 = new Teleport(false);
            t1.setPair(t2);
            t2.setPair(t1);
            t1.setNeighbour(a1);
            t2.setNeighbour(a2);
            int id = maxIDs.get("teleport");
            addID("t" + (id+1), t1);
            addID("t" + (id+2), t2);
            maxIDs.replace("teleport", id+2);
            game.addTeleport(t1);
            game.addTeleport(t2);
            output.println("connected teleportgates " + ("t" + (id+1)) +" " + ("t" + (id+2)) + " placed by " + args[1] + " and " + args[2]);
        }
    }
    /**
     * A nextturn parancshoz tartoz? oszt?ly. A k?r v?g?n megh?vand? parancs. V?grehajtja a k?r v?gi l?p?seket.
     */
    private static class nextturnCommand implements Command{

        /**
         * A nextturn parancshoz tartoz? oszt?ly. A k?r v?g?n megh?vand? parancs. V?grehajtja a k?r v?gi l?p?seket.
         * Minden megkerg?lt teleportkapu l?p, minden robot ?s uf? l?p.
         * A nap tesz egy l?p?st.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            for (Teleport t : game.getGates()) {
                if (t.getBamboozled()) {
                    Asteroid a = t.getNeighbour();
                    t.makeAction();
                    if (a.equals(t.getNeighbour()))
                        output.println("teleport " + reverseIDs.get(t) + " couldn't move");
                    else
                        output.println("teleport " + reverseIDs.get(t) + " moved to " + reverseIDs.get(t.getNeighbour()));
                }
            }
            if (random) {
                for (Robot r : game.getRobots()) {
                    commands.get("robotaction").execute(new String[]{"robotaction", reverseIDs.get(r)});
                }
                for (UFO u : game.getUFOs()) {
                    commands.get("ufoaction").execute(new String[]{"ufoaction", reverseIDs.get(u)});
                }
                commands.get("sunaction").execute(new String[]{"sunaction"});
            } else {
                for (Robot r : game.getRobots()) {
                    output.println("enter a robotaction command for robot " + reverseIDs.get(r));
                    String[] pieces;
                    if (input.hasNextLine()) {
                        pieces = input.nextLine().split(" ");
                        if (pieces[0].equals("robotaction") && pieces[1].equals(reverseIDs.get(r))) {
                            commands.get("robotaction").execute(pieces);
                        } else {
                            output.println("invalid command");
                        }
                    }
                    else
                        return;
                }
                for (UFO u : game.getUFOs()) {
                    output.println("enter a ufoaction command for robot " + reverseIDs.get(u));
                    String[] pieces;
                    if (input.hasNextLine()) {
                        pieces = input.nextLine().split(" ");
                        if (pieces[0].equals("ufoaction") && pieces[1].equals(reverseIDs.get(u))) {
                            commands.get("ufoaction").execute(pieces);
                        } else {
                            output.println("invalid command");
                        }
                    }
                    else
                        return;
                }
                commands.get("sunaction").execute(new String[]{"sunaction"});
            }
        }
    }
    /**
     * A robotaction parancshoz tartoz? oszt?ly. A megadott param?terben l?v? robottal dolgozik.
     * Ha ezen fel?l nincs megadva param?ter, akkor egy makeAction m?veletet hajt v?gre a robottal.
     * Ha a m?sodik param?ter "drill" akkor f?r?st hajt v?gre a robottal.
     * Ha a m?sodik param?ter "move" akkor a harmadik param?terben megadott sorsz?m? (a robot jelenlegi aszteroid?j?nak
     * szomsz?dainak list?j?ban) szomsz?dra megy.
     */
    private static class robotactionCommand implements Command{

        /**
         * Az els? megadott param?terben l?v? robottal dolgozik.
         * Ha ezen fel?l nincs megadva param?ter, akkor egy makeAction m?veletet hajt v?gre a robottal.
         * Ha a m?sodik param?ter "drill" akkor f?r?st hajt v?gre a robottal.
         * Ha a m?sodik param?ter "move" akkor a harmadik param?terben megadott sorsz?m? (a robot jelenlegi aszteroid?j?nak
         * szomsz?dainak list?j?ban) szomsz?dra megy.
         * Ha valami hiba t?rt?nik, akkor jelzi a felhaszn?l? fel?.
         * Az esem?nyeket r?szletesen k?zli a felhaszn?l?val.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (random) {
                if (args.length < 2) {
                    output.println("robot must be specified");
                    return;
                }
                Robot r = (Robot) IDs.get(args[1]);
                Asteroid a = r.getAsteroid();
                int shell = a.getShell();
                if (args.length == 2) {
                    if (r.makeAction()) {
                        if (!a.equals(r.getAsteroid())) {
                            output.println("robot " + args[1] + " moved to " + reverseIDs.get(r.getAsteroid()));
                            return;
                        }
                        if (shell != r.getAsteroid().getShell()){
                            output.println("robot " + args[1] + " drilled on " + reverseIDs.get(a) + " shell is now " + r.getAsteroid().getShell());
                            return;
                        }
                    } else {
                        output.println("robot " + args[1] + " couldn't make action");
                    }
                    return;
                }
                if (args[2].equals("drill")) {
                    if (r.drill())
                        output.println("robot " + args[1] + " drilled on " +
                                reverseIDs.get(r.getAsteroid()) + "shell is now" + r.getAsteroid().getShell());
                    else
                        output.println("robot " + args[1] + " couldn't drill");
                }
                if (args[2].equals("move")) {
                    if (args.length < 4) {
                        if (a.getNeighbourCount() == 0) {
                            output.println("robot " + args[1] + " couldn't move");
                            return;
                        }
                        Random rand = new Random();
                        int randNeighbour = rand.nextInt(a.getNeighbourCount())-1;
                        if (r.move(randNeighbour)) {
                            output.println("robot " + args[1] + " moved to " + reverseIDs.get(r.getAsteroid()));
                        } else {
                            output.println("robot couldn't move");
                        }
                        return;
                    }
                    int i = Integer.parseInt(args[3]) - 1;
                    if (r.move(i))
                        output.println("robot " + args[1] + " moved to " + reverseIDs.get(r.getAsteroid()));
                    else
                        output.println("robot couldn't move");
                }
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
                    int i = Integer.parseInt(args[3]) - 1;
                    if (r.move(i))
                        output.println("robot " + args[1] + " moved to " + reverseIDs.get(r.getAsteroid()));
                    else
                        output.println("robot " + args[1] + " couldn't move");
                }
            }
        }
    }

    /**
     * A ufoaction parancshoz tartoz? oszt?ly. A megadott param?ter? uf?val dolgozik.
     * Ha ezen fel?l nincs megadva param?ter, akkor egy makeAction m?veletet hajt v?gre az uf?n.
     * Ha a m?sodik param?ter "mine" akkor b?ny?sz?st hajt v?gre az uf?val.
     * Ha a m?sodik param?ter "move" akkor a harmadik param?terben megadott sorsz?m? (az uf? jelenlegi aszteroid?j?nak
     * szomsz?dainak list?j?ban) szomsz?dra megy.
     */
    private static class ufoactionCommand implements Command{

        /**
         * A ufoaction parancshoz tartoz? oszt?ly. A megadott param?ter? uf?val dolgozik.
         * Ha ezen fel?l nincs megadva param?ter, akkor egy makeAction m?veletet hajt v?gre az uf?n.
         * Ha a m?sodik param?ter "mine" akkor b?ny?sz?st hajt v?gre az uf?val.
         * Ha a m?sodik param?ter "move" akkor a harmadik param?terben megadott sorsz?m? (az uf? jelenlegi aszteroid?j?nak
         * szomsz?dainak list?j?ban) szomsz?dra megy.
         * Az esetleges hib?kat a felhaszn?l?val k?zli.
         * A megt?rt?nt esem?nyeket r?szletesen k?zli a felhaszn?l? fel?.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (args.length < 2 || (args.length == 3 && !"mine".equals(args[2])) || (args.length == 4 && !"move".equals(args[2]))){
                output.println("all details must be specified");
                return;
            }
            UFO ufo = (UFO)IDs.getOrDefault(args[1], null);
            if (ufo == null) {
                output.print("couldn't complete request\n" +
                        "selected ID not available\n");
                return;
            }
            Asteroid a = ufo.getAsteroid();
            Mineral core = a.getCore();
            int shell = a.getShell();
            boolean mine = false;
            boolean move = false;
            if (args.length == 2){
                ufo.makeAction();
                if (a == ufo.getAsteroid() && core == a.getCore()){
                    output.println("UFO " + args[1] + " couldn't make action");
                    return;
                }
            }
            if (args.length == 3){
                ufo.mine();
                mine = true;
            }
            if (args.length == 4){
                int i = Integer.parseInt(args[3]);
                ufo.move(i-1);
                move = true;
            }
            if (a != ufo.getAsteroid()) {
                output.println("UFO " + args[1] + " moved to " + reverseIDs.get(ufo.getAsteroid()));
                return;
            }else if (move) {
                output.println("UFO " + args[1] + " couldn't move");
                return;
            }

            if (shell > 0){
                output.println("UFO " + args[1] + " couldn't mine");
                output.println("asteroid still has shell");
                return;
            }
            if (core == null){
                output.println("UFO " + args[1] + " couldn't mine");
                output.println("asteroid is already empty");
                return;
            }
            if (core != a.getCore()){
                output.println("UFO " + args[1] + " mined on " + reverseIDs.get(a));
                output.println("it got one unit of " + core.toString());
                output.println("asteroid is now empty");
            }
        }
    }


    /**
     * A sunaction parancshoz tartoz? oszt?ly. Ha a v?letlenszer?s?g be van kapcsolva, akkor a nappal v?grehajt
     * makeAction m?veletet. Ki?rja, hogy milyen esem?nyek k?vetkeztek be ennek hat?s?ra.
     */
    private static class sunactionCommand implements Command{

        /**
         * a a v?letlenszer?s?g be van kapcsolva, akkor a nappal v?grehajt
         * makeAction m?veletet. Ki?rja, hogy milyen esem?nyek k?vetkeztek be ennek hat?s?ra.
         * A robotok, telepesek, uf?k ?s a teleportkapuk v?ltoz?sait ellen?rzi ?s ezeket ki?rja.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (random) {
                List<Robot> robots = new ArrayList<Robot>(game.getRobots());
                List<Settler> settlers = new ArrayList<Settler>(game.getSettlers());
                List<UFO> UFOs = new ArrayList<UFO>(game.getUFOs());
                List<Teleport> teleports = new ArrayList<Teleport>(game.getGates());
                boolean[] b;
                b = new boolean[teleports.size()];
                for (int i = 0; i < teleports.size(); i++) {
                    b[i] = teleports.get(i).getBamboozled();
                }
                game.getSun().makeAction();
                for (Settler s : settlers) {
                    if (!game.getSettlers().contains(s))
                        output.println(reverseIDs.get(s) + " settler died");
                }
                for (Robot r : robots) {
                    if (!game.getRobots().contains(r))
                        output.println(reverseIDs.get(r) + " robot died");
                }
                for (UFO u : UFOs) {
                    if (!game.getUFOs().contains(u))
                        output.println(reverseIDs.get(u) + " ufo died");
                }
                for (int i = 0; i < teleports.size(); i++) {
                    if (!game.getGates().contains(teleports.get(i))) {
                        output.println(reverseIDs.get(teleports.get(i)) + " teleport perished");
                    } else {
                        if (!b[i] && teleports.get(i).getBamboozled()) {
                            output.println(reverseIDs.get(teleports.get(i)) + " teleportgate gone mad");
                        }
                    }
                }
            } else {
                Asteroid a = game.getSun().getAsteroids().get(0);
                if (a == null)
                    return;
                else {
                    commands.get("solarwind").execute(new String[] {"solarwind", reverseIDs.get(a), "0"});
                }
            }
        }
    }
    /**
     * A solarwind parancshoz tartoz? oszt?ly. Elind?t egy napvihart a megadott aszteroid?n a megadott m?rettel.
     * Ezut?n ki?rja a t?rt?nteket.
     */
    private static class solarwindCommand implements Command{
        /**
         * Ha nincs el?g param?ter, akkor hib?t jelez. K?l?nben elind?t az els? param?terk?nt megadott aszterod?n
         * egy a m?sodik param?terben ?tadott m?ret? napvihart.
         * A robotok, telepesek, ufok ?s teleportkapuk list?j?nak m?sol?s?val ellen?rzi, hogy a napvihar hat?s?ra
         * milyen esem?nyek t?rt?ntek.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (args.length < 3) {
                output.println("all details must be specified");
                return;
            }
            Asteroid a = (Asteroid) IDs.getOrDefault(args[1], null);
            if (a == null){
                output.println("couldn't complete request\n" +
                        "    selected ID not available\n");
                return;
            }
            int radius = Integer.parseInt(args[2]);
            List<Robot> robots = new ArrayList<Robot>(game.getRobots());
            List<Settler> settlers = new ArrayList<Settler>(game.getSettlers());
            List<UFO> UFOs = new ArrayList<UFO>(game.getUFOs());
            List<Teleport> teleports = new ArrayList<Teleport>(game.getGates());
            boolean[] b;
            b = new boolean[teleports.size()];
            for (int i = 0; i < teleports.size(); i++) {
                b[i] = teleports.get(i).getBamboozled();
            }
            a.solarWind(radius);
            output.println("solarwind created with asteroid " + args[1] + " in the middle");
            output.println("and a " + radius + " radius");
            output.println("events caused:");


            for (Settler s : settlers) {
                if (!game.getSettlers().contains(s))
                    output.println(reverseIDs.get(s) + " settler died");
            }
            for (Robot r : robots) {
                if (!game.getRobots().contains(r))
                    output.println(reverseIDs.get(r) + " robot died");
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
     * A checkwin parancshoz tartoz? oszt?ly. Ellen?rizteti a game-mel, hogy a j?t?kot megnyert?k-e m?r.
     */
    private static class checkwinCommand implements Command{
        /**
         * ?rtes?ti a felhaszn?l?t arr?l, hogy megnyerte-e a j?t?kot.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (game.checkWin()) {
                output.println("game won");
                JOptionPane.showMessageDialog(null, "Game won!");
            } else {
                output.println("win conditions not met");
                JOptionPane.showMessageDialog(null, "Win conditions not met!");
            }
        }
    }
    /**
     * A checklose parancshoz tartoz? oszt?ly. Ellen?rizteti a game-mel, hogy a j?t?kot elvesztett?k-e m?r.
     */
    private static class checkloseCommand implements Command{
        /**
         * ?rtes?ti a felhaszn?l?t arr?l, hogy elvesztette-e a j?t?kot.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (game.checkLose()) {
                output.println("game lost");
                JOptionPane.showMessageDialog(null, "Game lost!");
            }else {
                output.println("losing conditions not met");
                JOptionPane.showMessageDialog(null, "Losing conditions not met!");
            }
        }
    }
    /**
     * A newgame parancshoz tartoz? oszt?ly.
     * L?trehoz a felhaszn?l? ?ltal megadott
     * sz?m? telepest, aszteroid?t ?s UFO-t,
     * valamint egy napot a game init met?dusa
     * seg?ts?g?vel. ?j randomiz?lt p?lya k?sz?t?s?re
     * haszn?lhat?
     */
    private static class newgameCommand implements Command{

        /**
         * A newgame parancshoz tartoz? oszt?ly.
         * L?trehoz a felhaszn?l? ?ltal megadott
         * sz?m? telepest, aszteroid?t ?s UFO-t,
         * valamint egy napot a game init met?dusa
         * seg?ts?g?vel. ?j randomiz?lt p?lya k?sz?t?s?re
         * haszn?lhat?
         * A param?terekben a telepesek ?s az aszteroid?k sz?m?t is meg kell adni.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            int nSettler, nAsteroid, nUFO;

            String s = JOptionPane.showInputDialog("How many settlers?");
            if(s == null)
                return;
            nSettler = Integer.parseInt(s);
            if(nSettler <= 0){
                JOptionPane.showMessageDialog(null, "Invalid amount of settlers to start the game");
                return;
            }

            s = JOptionPane.showInputDialog("How many asteroids?");
            if(s == null)
                return;
            nAsteroid = Integer.parseInt(s);
            if(nAsteroid <= 0){
                JOptionPane.showMessageDialog(null, "Invalid amount of asteroids to start the game");
                return;
            }

            s = JOptionPane.showInputDialog("How many UFOs?");
            if(s == null)
                return;
            nUFO = Integer.parseInt(s);
            if(nUFO < 0){
                JOptionPane.showMessageDialog(null, "Invalid amount of UFOs");
                return;
            }
            //nUFO = 0;

            game = new Game();
            gameFrame.getLevelView().setGame(game);
            activeSettler = null;
            game.init(nSettler, nAsteroid, nUFO);

            resetIDs();         //ez nem tudom hogy jo-e

            List<Settler> allSettlers = game.getSettlers();
            List<UFO> allUFOs = game.getUFOs();
            List<Asteroid> allAsteroids = game.getSun().getAsteroids();
            LevelView lv = gameFrame.getLevelView();
            refreshActiveSettler();

            maxIDs.replace("settler", allSettlers.size());
            maxIDs.replace("ufo", allUFOs.size());
            maxIDs.replace("asteroid", allAsteroids.size());

            for(int i = 0; i < allSettlers.size(); i++) {
                addID("s" + (i+1), allSettlers.get(i));
                lv.addSettlerView(allSettlers.get(i));
            }
            for(int i = 0; i < allUFOs.size(); i++) {
                addID("u" + (i+1), allUFOs.get(i));
                lv.addUFOView(allUFOs.get(i));
            }
            int scale = 1 + (int)Math.sqrt(nAsteroid);
            int xborder = (int)(gameFrame.getSize().width*0.1);
            int yborder = (int)(gameFrame.getSize().height*0.1);
            for(int i = 0; i < allAsteroids.size(); i++) {
                addID("a" + (i+1), allAsteroids.get(i));
                lv.addAsteroidView(allAsteroids.get(i), i%scale*(gameFrame.getSize().width - 2*xborder)/scale+xborder, i/scale*(gameFrame.getSize().height-2*yborder)/scale+yborder);
            }
            lv.Update();

            output.println("new game created with " + allSettlers.size() + " settler" + (allSettlers.size() == 1 ? " " : "s ")
                    + allAsteroids.size() + " asteroid" + (allAsteroids.size() == 1 ? " " : "s ") + "and " + allUFOs.size() +
                    "UFO" + (allUFOs.size() == 1 ? " " : "s "));
        }
    }
    /**
     * A setclosetosun parancshoz tartoz? oszt?ly.
     * A param?terk?nt megkapott aszteroid?nak a closeToSun v?ltoz?j?t ?ll?tja be a m?sodik param?terben megadott ?rt?kre.
     */
    private static class setclosetosunCommand implements Command{
        /**
         * A param?terk?nt megkapott aszteroid?nak a closeToSun v?ltoz?j?t ?ll?tja be a megadott ?rt?kre.
         * Ha nincs el?g argumentum, vagy nem l?tezik ilyen aszteroida, akkor hib?t jelez.
         * Az ?j closeToSun ?rt?ket ?gy kell megadni, hogy "0" ha hamis, "1", ha igaz legyen.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
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
                output.println("couldn't complete request\n" +
                        "    selected ID not available\n");
            }else{
                boolean oldCloseToSun = asteroid.getCloseToSun();
                boolean newCloseToSun = !"0".equals(args[2]) && ("1".equals(args[2]));
                if (oldCloseToSun == newCloseToSun){
                    output.println(args[1] + " already " + (oldCloseToSun ? "close to " : "far from ") + "sun, no change");
                }else{
                    output.println(args[1] + " set " + (newCloseToSun ? "close to " : "far from ") + "sun");
                    List<Robot> robots = new ArrayList<Robot>(game.getRobots());
                    List<Settler> settlers = new ArrayList<Settler>(game.getSettlers());
                    List<UFO> UFOs = new ArrayList<UFO>(game.getUFOs());
                    List<Teleport> teleports = new ArrayList<Teleport>(game.getGates());
                    asteroid.setCloseToSun();
                    if (newCloseToSun && !game.getSun().getAsteroids().contains(asteroid)) {
                        output.println("events caused:");
                        output.println(args[1] + " exploded");
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
                        for (Teleport t : teleports) {
                            if (!game.getGates().contains(t))
                                output.println(reverseIDs.get(t) + " teleport perished");
                        }
                    }
                }
            }
        }
    }
    /**
     * A giveup parancshoz tartoz? oszt?ly.
     */
    private static class giveupCommand implements Command{
        /**
         * Feladja ?s befejezi a j?t?kot.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            game.setGameEnd(true);
            output.println("game given up");
        }
    }


    /**
     * A bammboozleteleport parancshoz tartoz? oszt?ly. A param?terk?nt kapott teleportkapu bamboozled mez?j?t a
     * m?sodik param?ternek megadott ?rt?kre be?ll?tja.
     */
    private static class bamboozleteleportCommand implements Command{

        /**
         * A bammboozleteleport parancshoz tartoz? oszt?ly. A param?terk?nt kapott teleportkapu bamboozled mez?j?t a
         * m?sodik param?ternek megadott ?rt?kre be?ll?tja.
         * Ha nincs el?g param?ter vagy nem j? az azonos?t?, akkor jelzi a felhaszn?l?nak.
         * Az ?j bamboozled ?rt?ket ?gy kell megadni, hogy "0" ha hamis, "1", ha igaz legyen.
         * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
         */
        public void execute(String[] args) {
            if (args.length < 3 || (!"0".equals(args[2]) && !"1".equals(args[2]))) {
                output.println("all details must be specified");
                return;
            }
            Teleport teleport = (Teleport)IDs.getOrDefault(args[1], null);
            if (game.getGates().contains(teleport)){
                boolean bamboozled = !"0".equals(args[2]) && ("1".equals(args[2]));
                teleport.setBamboozled(bamboozled);
                output.println(args[1] + " teleportgate " + (bamboozled ? "" : "not ") + "bamboozled");
            }else{
                output.print("couldn't complete request\n" +
                        "selected ID not available\n");
            }
        }
    }
    /**
     * Az akt?v telepessel kapcsolatos parancsok param?tereinek helyess?g?t ellen?rzi.
     * @param args A parancs parancssori argumentumai, a teljes sort meg kell adni, amely sz?k?z?kkel lett elv?lasztva.
     * @param argscnt H?ny parancssori argumentumot v?r a parancs.
     * @return Igaz, ha megfelel? sz?m? argumentum van ?s az akt?v telepes m?g nem halt meg. K?l?nben hamis.
     */
    private static boolean settlerCommandCheck(String[] args, int argscnt){
        if (args.length < argscnt){
            output.println("all details must be specified");
            return false;
        }
        if (activeSettler == null){
            output.println("couldn't complete request\n" +
                    "    no active settler selected\n");
            return false;
        }
        if (!game.getSettlers().contains(activeSettler)){
            output.println("active settler died");
            return false;
        }
        return true;
    }

    /**
     * A parancsok nev?nek ?s az ahhoz tartoz? parancsobjektumoknak az ?sszerendel?se.
     */
    private static HashMap<String, Command> commands;

    /**
     * Inicializ?lja a parancsokat. Hozz?adja az ?sszes el?rhet? parancsot a parancs n?v- parancsobjektum ?sszerendel?shez.
     */
    public static void initializeCommands(){
        commands = new HashMap<>();
        commands.put("load", new loadCommand()); commands.put("save", new saveCommand()); commands.put("input", new inputCommand());
        commands.put("output", new outputCommand()); //commands.put("setrandom", new setrandomCommand());
        commands.put("addsettler", new addsettlerCommand()); commands.put("addasteroid", new addasteroidCommand());
        commands.put("addrobot", new addrobotCommand()); commands.put("addufo", new addufoCommand());
        commands.put("connectasteroid", new connectasteroidCommand()); //commands.put("selectsettler", new selectsettlerCommand());
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
        //commands.put("exit", new exitCommand());
    }

    /**
     * A param?terk?nt megadott sztringb?l egy nyersanyagot pr?b?l meg beolvasni.
     * @param arg A sztring, amely egy nyersanyagot ?r le.
     * @return A beolvasott nyersanyagnak megfelel? nyersanyag objektum,
     * ha nem tudott nyersanyagot beolvasni, akkor null.
     */
    private static Mineral parseMineral(String arg){
        if ("iron".equals(arg))
            return new Iron();
        else if ("coal".equals(arg))
            return new Coal();
        else if ("ice".equals(arg))
            return new Ice();
        else if (arg.startsWith("uranium")){
            try{
                int exposedToSunCounter = Integer.parseInt(arg.substring(8, arg.length()-1));
                return new Uranium(exposedToSunCounter);
            }catch (Exception e) {
                return null;
            }
        }else
            return null;
    }

    /**
     * Ellen?rzi, hogy meghalt-e az akt?v telepes. Ha igen, akkor jelzi a felhaszn?l?nak.
     */
    private static boolean checkActiveSettlerDied(){
        if (activeSettler != null && !game.getSettlers().contains(activeSettler)){
            output.println("active settler died");
            return true;
        }
        return false;
    }

    /**
     * Megpr?b?l egy parancsot kiolvasni a bemenet k?vetkez? sor?b?l.
     * @return Hamis, ha a bemenet legutols? sor?t b?r beolvast?k. Igaz, ha m?g nem pr?b?ltak a legutols? sor ut?n olvasni.
     */
    private static boolean parseCommand(){
        String[] pieces;
        if (input.hasNextLine())
            pieces = input.nextLine().split(" ");
        else
            return false;
        if (pieces.length == 0) {
            output.println("invalid command");
            return true;
        }
        Command cmd = commands.getOrDefault(pieces[0], null);
        if (cmd == null){
            output.println("invalid command");
            return true;
        }
        if (!"newgame".equals(pieces[0]) && game.getGameEnd()){
            output.println("game ended");
            return true;
        }
        cmd.execute(pieces);
        checkActiveSettlerDied();
        return true;
    }

    /**
     * Inicializ?lja 0-val a maxID ?sszerendel?seket.
     */
    private static void initializeMaxIDs(){
        maxIDs.put("asteroid", 0);
        maxIDs.put("teleport", 0);
        maxIDs.put("settler", 0);
        maxIDs.put("robot", 0);
        maxIDs.put("ufo", 0);
    }
    /**
     * Inicializálja a ControlSettlers listát, és az első aktív settlert
     */
    public void init(){
        ControlSettlers = new ArrayList<Settler>(game.getSettlers());
        activeSettler = ControlSettlers.get(0);
    }
    /**
     * Inicializ?lja a parancsokat ?s a maxID-ket.
     * Ha van elegend? parancssori argumentum, akkor az els?re ?t?r?ny?tja a bemenetet, a m?sodikra a kimenetet.
     * A program bel?p?si pontja, ki?rja a men?pontokat ?s bek?ri a felhasz?l?t?l a v?lasztott men?pontot a menu()
     * f?ggv?nnyel, amihez megh?vja a megfelel? inicializ?l? f?ggv?nyt.
     * Ezt addig ism?tli, am?g a felhaszn?l? ki nem l?p a programb?l.
     * @param args parancssori argumentumok
     */
    public static void main(String[] args){
        Control control = new Control();                     //ez nem jóóóó kizárólag a Julcsi tesztje
        initializeCommands();
        initializeMaxIDs();
        gameFrame = new GameFrame(control, game);
        //commands.get("load").execute(new String[]{"load", "test.txt"});
        //control.init();
        //control.actionPerformed(new ActionEvent(control, 0, "load game1.txt"));

        gameFrame.getLevelView().setActiveSettler(activeSettler);
        gameFrame.pack();
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
        if (args.length >= 2){
            String[] cmdargs = new String[2];
            cmdargs[1] = args[0];
            commands.get("input").execute(cmdargs);
            cmdargs[1] = args[1];
            commands.get("output").execute(cmdargs);
        }
        boolean hasNext = true;
        while (hasNext){
            hasNext = parseCommand();
        }
    }
}