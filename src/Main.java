import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int)screenSize.getWidth();
        final int height = (int)screenSize.getHeight();
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(width, height);
        add(new Field(width, height));
        setVisible(true);
    }
    public static void main(String[] arg)
    {
        Main main = new Main();
    }
}