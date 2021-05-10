import javax.swing.*;
import java.awt.*;

/**
 * Egy nyersanyaghoz tartozó gomb. A nyersanyagnak megfelelõ színû.
 */
public class MineralButton extends JButton {

    /**
     * A nyersanyag, amit ismer.
     */
    private Mineral mineral;

    /**
     * Inicializálja a gomb méreteit.
     */
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
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    /**
     * Visszaadja az ismert nyersanyagot.
     * @return A nyersanyag.
     */
    public Mineral getMineral() {
        return mineral;
    }

    /**
     * Beállítja az ismert nyersanyagot és frissíti a gomb színét.
     * @param m Az új nyersanyag.
     */
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
