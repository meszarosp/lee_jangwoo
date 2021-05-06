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

    private void initMenu(){
        JMenu file = new JMenu("File");
        file.add(new JMenuItem("Load"));
        file.add(new JMenuItem("Save"));
        file.add(new JMenuItem("New Game"));
        menuBar.add(file);

        JMenu check = new JMenu("Check");
        check.add(new JMenuItem("Check Win"));
        check.add(new JMenuItem("Check Lose"));
        menuBar.add(check);
    }
    public GameFrame(Control c, Game game) {
        super();
        setTitle("Asteroid Game");
        setJMenuBar(menuBar);
        initMenu();
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
