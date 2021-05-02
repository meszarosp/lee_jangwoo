import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class TeleportButton extends JButton {

    private Teleport teleport;

    public TeleportButton() {
        super();
        setSize(30, 50);
        setPreferredSize(new Dimension(30 , 50));
        setMaximumSize(new Dimension(30 , 50));
        setMinimumSize(new Dimension(30 , 50));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 8));
        setBackground(new Color(120, 45, 76));
        setOpaque(true);
        setActionCommand("set down");
    }

    public Teleport getTeleport() {
        return teleport;
    }

    public void setTeleport(Teleport t) {
        this.teleport = t;
        if (teleport == null)
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        else
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 8));
        // TODO hogyan tudja meg a színt?
        setBackground(new Color(120, 45, 76));
    }
}
