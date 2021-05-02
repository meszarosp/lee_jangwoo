import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class GameFrame extends JFrame {
    private LevelView lv;
    private InventoryView iv;

    public GameFrame() {
        super();
        setTitle("Asteroid Game");
        setMinimumSize(new Dimension(1000, 600));
        setPreferredSize(new Dimension(1000, 600));
        lv = new LevelView();
        iv = new InventoryView();
        add(lv, BorderLayout.CENTER);
        add(iv, BorderLayout.SOUTH);
        pack();
    }
}
