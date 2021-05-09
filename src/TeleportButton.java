import javax.swing.*;
import java.awt.*;

/**
 * Egy teleportkapuhoz tartozó gomb. A teleportkapu-pároknak megfelelõ színû.
 */
public class TeleportButton extends JButton {

    /**
     * A teleportkapu, akit ismer.
     */
    private Teleport teleport;

    /**
     * Inicializálja a gomb méreteit.
     */
    public TeleportButton() {
        super();
        setSize(30, 50);
        setPreferredSize(new Dimension(30 , 50));
        setMaximumSize(new Dimension(30 , 50));
        setMinimumSize(new Dimension(30 , 50));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 12));
        setBackground(new Color(120, 45, 76));
        setOpaque(true);
        setActionCommand("set down");
    }

    /**
     * Visszaadja az ismert teleportkaput
     * @return A teleportkapu
     */
    public Teleport getTeleport() {
        return teleport;
    }

    /**
     * Beállítja az ismert teleportkaput.
     * @param t Az új teleportkapu.
     */
    public void setTeleport(Teleport t) {
        this.teleport = t;
        if (teleport == null)
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        else
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 12));
        // TODO hogyan tudja meg a színt?
        setBackground(new Color(120, 45, 76));
    }
}
