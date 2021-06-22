import javax.swing.*;
import java.awt.*;

public class Main extends JFrame
{
    public Main()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        setTitle("snake");
        setDefaultCloseOperation(3);//EXIT_ON_CLOSE == 3
        setSize(width, height);
        add(new Field(width, height));
        setVisible(true);
        setResizable(false);
    }
    public static void main(String[] args)
    {
        Main mw = new Main();
    }
}