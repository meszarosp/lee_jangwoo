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
        lv = new LevelView();
        iv = new InventoryView();
        add(lv, BorderLayout.CENTER);
        add(iv, BorderLayout.SOUTH);
    }
}
