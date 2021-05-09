import javax.swing.*;
import java.awt.*;

/**
 * Egy teleportkapuhoz tartoz� gomb. A teleportkapu-p�roknak megfelel� sz�n�.
 */
public class TeleportButton extends JButton {

    /**
     * A teleportkapu, akit ismer.
     */
    private Teleport teleport;

    /**
     * Inicializ�lja a gomb m�reteit.
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
     * Be�ll�tja az ismert teleportkaput.
     * @param t Az �j teleportkapu.
     */
    public void setTeleport(Teleport t) {
        this.teleport = t;
        if (teleport == null)
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        else
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 12));
        // TODO hogyan tudja meg a sz�nt?
        setBackground(new Color(120, 45, 76));
    }
}
