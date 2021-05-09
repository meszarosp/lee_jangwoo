
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Az osztály felelőssége hogy az éppen aktív telepesnél található nyersanyagok grafikus
 * megjelenítését és ezzel kapcsolatos feladatokat végezze/menedzselje.
 */
public class InventoryView extends JPanel {

    /**
     * A fúráshoz tartozó gomb.
     */
    private JButton Drill;

    /**
     * A bányászáshoz tartozó gomb.
     */
    private JButton Mine;

    /**
     * A teleportkapu építéshez tartozó gomb.
     */
    private JButton CraftTeleport;

    /**
     * A robot építéshez tartozó gomb.
     */
    private JButton CraftRobot;

    /**
     * A gombokat tartalmazó panel.
     */
    private JPanel ButtonPanel;

    /**
     * A teleportgombokat tartalmazó panel.
     */
    private JPanel TeleportPanel;

    /**
     * A nyersanyaggombokat tartalmazó panel.
     */
    private JPanel MineralPanel;

    /**
     * A telepesnél lévő teleportkapukhoz tartozó gombok.
     */
    private TeleportButton[] teleportButtons = new TeleportButton[3];

    /**
     * A telepesnél lévő nyersanyagokhoz tartozó gombok.
     */
    private MineralButton[] mineralButtons = new MineralButton[10];
    /**
     * Kontruktor.
     * Létrehozza a gombokat és a paneleket. Beállítja az actionCommand-okat
     * és az eseménykezelőt.
     * @param lv A levelview objektum
     * @param c A kontroller, aki kezeli az eseményeket
     */
    public InventoryView(LevelView lv, Control c) {
        super();
        setPreferredSize(new Dimension(1000, 100));
        levelView = lv;
        lv.setInventory(this);
        Drill = new JButton("Drill");
        Drill.addActionListener(c);
        Drill.setActionCommand("drill");
        Mine = new JButton("Mine");
        Mine.addActionListener(c);
        Mine.setActionCommand("mine");
        CraftTeleport = new JButton("Craft Teleport");
        CraftTeleport.addActionListener(c);
        CraftTeleport.setActionCommand("craftteleport");
        CraftRobot = new JButton("Craft Robot");
        CraftRobot.addActionListener(c);
        CraftRobot.setActionCommand("craftrobot");

        ButtonPanel = new JPanel();
        GridLayout forButtons = new GridLayout(2,2);
        forButtons.setVgap(20);
        forButtons.setHgap(20);
        ButtonPanel.setLayout(forButtons);
        TeleportPanel = new JPanel();
        TeleportPanel.setLayout(new GridLayout(1, 3, 30, 20));
        MineralPanel = new JPanel();
        GridLayout forMinerals = new GridLayout(2, 5);
        forMinerals.setHgap(20);
        forMinerals.setVgap(20);
        MineralPanel.setLayout(forMinerals);

        GridLayout grid = new GridLayout(1,3);
        grid.setHgap(80);
        setLayout(grid);
        add(ButtonPanel);
        add(TeleportPanel);
        add(MineralPanel);

        ButtonPanel.add(Drill);
        ButtonPanel.add(CraftTeleport);
        ButtonPanel.add(Mine);
        ButtonPanel.add(CraftRobot);

        for (int i = 0; i<3; i++) {
            teleportButtons[i] = new TeleportButton(lv);
            teleportButtons[i].addActionListener(c);
            teleportButtons[i].setActionCommand("placeteleport " + Integer.toString(i));
            TeleportPanel.add(teleportButtons[i]);
        }

        for (int i = 0; i<10; i++) {
            mineralButtons[i] = new MineralButton();
            mineralButtons[i].addActionListener(c);
            mineralButtons[i].setActionCommand("putmineralback " + Integer.toString(i+1));
            MineralPanel.add(mineralButtons[i]);
        }


    }

    /**
     * A levelview objektum, akit ismer.
     */
    private LevelView levelView;

    /**
     * Frissíti a paneleket és a gombokat.
     * Az aktív telepest lekéri a levelview-tól és az ő állapotának megfelelően
     * frissíti a gombokat.
     */
    public void Update() {
        if (levelView.getActiveSettler() == null)
            return;
        List<Teleport> teleportList = levelView.getActiveSettler().getTeleportgates();
        for (int i = 0; i < 3; i++) {
            if (i<teleportList.size())
                teleportButtons[i].setTeleport(teleportList.get(i));
            else
                teleportButtons[i].setTeleport(null);
        }

        List<Mineral> mineralList = levelView.getActiveSettler().getMinerals();
        for (int i = 0; i < 10; i++) {
            if (i<mineralList.size())
                mineralButtons[i].setMineral(mineralList.get(i));
            else
                mineralButtons[i].setMineral(null);
        }
        invalidate();
    }


}