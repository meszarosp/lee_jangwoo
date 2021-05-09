import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A levelview és az inventoryview paneleket tartalmazza. A menüt is tartalmazza.
 */
public class GameFrame extends JFrame {
    /**
     * A levelview objektum, ami használatban van.
     */
    private LevelView lv;

    /**
     * Az inventoryview objektum, ami használatban van.
     */
    private InventoryView iv;

    /**
     * A menü.
     */
    private JMenuBar menuBar = new JMenuBar();

    /**
     * Az eseménykezelõ, akit ismer.
     */
    private ActionListener actionListener;

    /**
     * Inicializálja a menüt a megfelelõmenüpontokkal.
     * Beállítja a hozzájuk tartozó actionCommand-ot.
     */
    private void initMenu(){
        JMenu file = new JMenu("File");
        JMenuItem temp = new JMenuItem("Load");
        temp.setActionCommand("load");
        temp.addActionListener(actionListener);
        file.add(temp);
        temp = new JMenuItem("Save");
        temp.setActionCommand("save");
        temp.addActionListener(actionListener);
        file.add(temp);
        temp = new JMenuItem("New Game");
        temp.setActionCommand("newgame");
        temp.addActionListener(actionListener);
        file.add(temp);
        temp = new JMenuItem("Give up");
        temp.setActionCommand("giveup");
        temp.addActionListener(actionListener);
        file.add(temp);
        menuBar.add(file);

        JMenu check = new JMenu("Check");
        temp = new JMenuItem("Check Win");
        temp.setActionCommand("checkwin");
        temp.addActionListener(actionListener);
        check.add(temp);
        temp = new JMenuItem("Check Lose");
        temp.setActionCommand("checklose");
        temp.addActionListener(actionListener);
        check.add(temp);
        menuBar.add(check);
    }

    /**
     * Beállítja az ablak méretét, elkészíti a levelview-t és az inventoryviewt.
     * @param c A kontroller.
     * @param game A játák.
     */
    public GameFrame(Control c, Game game) {
        super();
        actionListener = c;
        setTitle("Asteroid Game");
        initMenu();
        setJMenuBar(menuBar);
        setMinimumSize(new Dimension(1000, 600));
        setPreferredSize(new Dimension(1000, 600));
        lv = new LevelView(game);
        iv = new InventoryView(lv, c);
        lv.addMouseListener(c);
        add(lv, BorderLayout.CENTER);
        add(iv, BorderLayout.SOUTH);
        pack();
    }

    /**
     * Getter a levelview attríbútumhoz.
     * @return A levelview.
     */
    public LevelView getLevelView(){
        return lv;
    }
}
