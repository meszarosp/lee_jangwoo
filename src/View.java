
import java.awt.*;
import java.util.*;

/**
 * Felelõssége, hogy közös interfészt biztosít a grafikus osztályoknak, és néhány közös
 * függvényt definiál (pl. draw)
 */
public interface View {

    /**
     * Kirajzolásért felelõs, a leszármazott osztályokban kifejtve
     * @param g A graphics objektum, amire a rajzolás történik.
     */
    public void draw(Graphics g);

}