import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class MineralButton extends JButton {

    private Mineral mineral;

    public MineralButton() {
        super();
        setSize(20, 20);
        setPreferredSize(new Dimension(20 , 20));
        setMaximumSize(new Dimension(20 , 20));
        setMinimumSize(new Dimension(20 , 20));
        setBackground(LevelView.mineralColor(mineral));
        if (mineral == null) {
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        } else {
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
        }
        setOpaque(true);
        setActionCommand("put back");
    }

    public Mineral getMineral() {
        return mineral;
    }

    public void setMineral(Mineral m) {
        this.mineral = m;
        setBackground(LevelView.mineralColor(mineral));
        if (mineral == null) {
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        } else {
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
        }
    }
}
