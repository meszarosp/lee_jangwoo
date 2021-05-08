import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class GameFrame extends JFrame {
    private LevelView lv;
    private InventoryView iv;
    private JMenuBar menuBar = new JMenuBar();
    private ActionListener actionListener;

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
    public LevelView getLevelView(){
        return lv;
    }
}
