
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
        ButtonPanel.setLayout(new GridLayout(2,2));
        TeleportPanel = new JPanel();
        TeleportPanel.setLayout(new FlowLayout());
        MineralPanel = new JPanel();
        MineralPanel.setLayout(new GridLayout(2, 5));

        setLayout(new FlowLayout());
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