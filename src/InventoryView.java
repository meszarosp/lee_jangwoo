
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 
 */
public class InventoryView extends JPanel implements View {

    private JButton Drill;
    private JButton Mine;
    private JButton CraftTeleport;
    private JButton CraftRobot;

    private JPanel ButtonPanel;
    private JPanel TeleportPanel;
    private JPanel MineralPanel;

    private TeleportButton[] teleportButtons = new TeleportButton[3];
    private MineralButton[] mineralButtons = new MineralButton[10];
    /**
     * Default constructor
     */
    public InventoryView(LevelView lv) {
        super();
        setPreferredSize(new Dimension(1000, 100));
        levelView = lv;
        Drill = new JButton("Drill");
        Drill.setActionCommand("drill");
        Mine = new JButton("Mine");
        Mine.setActionCommand("mine");
        CraftTeleport = new JButton("Craft Teleport");
        CraftTeleport.setActionCommand("craft teleport");
        CraftRobot = new JButton("Craft Robot");
        CraftRobot.setActionCommand("craft robot");

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
            teleportButtons[i] = new TeleportButton();
            TeleportPanel.add(teleportButtons[i]);
        }

        for (int i = 0; i<10; i++) {
            mineralButtons[i] = new MineralButton();
            MineralPanel.add(mineralButtons[i]);
        }


    }

    /**
     * 
     */
    private LevelView levelView;

    /**
     * @param g
     */
    public void draw(Graphics g) {
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

    /**
     * @param x
     * @param y
     */
    public void click(int x, int y) {
        // TODO ide mi kéne egyáltalán?
    }


}