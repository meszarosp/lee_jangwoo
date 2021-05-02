import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class MineralButton extends JButton {

    private Mineral mineral;

    public MineralButton(Mineral m) {
        super();
        mineral = m;
        setBackground(LevelView.mineralColor(m));
        if (mineral == null) {
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        } else {
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
        }
        setSize(10, 10);
        setOpaque(true);
    }

    public Mineral getMineral() {
        return mineral;
    }
}
