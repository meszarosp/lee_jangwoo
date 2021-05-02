import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    LevelView levelView = new LevelView();

    GameFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Asteroid Game");
        init();
        pack();
        setVisible(true);
    }
    private void init(){
        levelView.setMinimumSize(new Dimension(1000, 600));
        levelView.setPreferredSize(new Dimension(1000, 600));
        add(levelView);
    }
    public static void main(String[] args){
        GameFrame gf = new GameFrame();
    }
}
