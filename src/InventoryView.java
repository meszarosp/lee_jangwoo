
import javax.swing.*;
import java.awt.*;
import java.util.*;

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
    /**
     * Default constructor
     */
    public InventoryView() {
        super();
        Drill = new JButton("Drill");
        Mine = new JButton("Mine");
        CraftTeleport = new JButton("Craft Teleport");
        CraftRobot = new JButton("Craft Robot");

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
        ButtonPanel.add(Mine);
        ButtonPanel.add(CraftTeleport);
        ButtonPanel.add(CraftRobot);

    }

    /**
     * 
     */
    private LevelView levelView;

    /**
     * @param g
     */
    public void draw(Graphics g) {
        // TODO implement here
    }

    /**
     * @param x
     * @param y
     */
    public void click(int x, int y) {
        // TODO implement here
    }


}