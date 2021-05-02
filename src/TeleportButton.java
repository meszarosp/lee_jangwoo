import javax.swing.*;

/**
 *
 */
public class TeleportButton extends JButton {

    private Teleport teleport;

    public TeleportButton(Teleport t) {
        super();
        teleport = t;

    }

    public Teleport getTeleport() {
        return teleport;
    }
}
