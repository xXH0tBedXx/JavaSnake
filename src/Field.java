import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Field extends JPanel implements ActionListener {
    private final int width;
    private final int height;
    private final int SCREEN_SIZE = 640;
    private final int PIXEL_SIZE = 16;
    private final int COUNT_PIXELS = 1600;
    private Image SNAKE_PIXEL;
    private Image APPLE;
    private int APPLE_X;
    private int APPLE_Y;
    private int[] x = new int[COUNT_PIXELS - 1];
    private int[] y = new int[COUNT_PIXELS - 1];
    private int SNAKE_SIZE;
    private Timer timer;
    private Boolean[] direction = new Boolean[4];
    private Boolean IN_GAME = true;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        setBackground(Color.GRAY);
        loadImages();
    }

    public void initGame()
    {
        if (new Random().nextInt(2) > 0)
            direction[0] = true;
        else
            direction[1] = true;
        SNAKE_SIZE = new Random().nextInt(5);
        int static_variable = new Random().nextInt(64)%4;
        for (int i = 0; i < SNAKE_SIZE; i++)
        {
            x[i] = direction[0] ? static_variable : static_variable - i*PIXEL_SIZE;
            y[i] = direction[1] ? static_variable : static_variable - i*PIXEL_SIZE;
        }
        timer = new Timer(128,this);
        timer.start();
        createApple();
    }

    public void createApple()
    {
        APPLE_X = new Random().nextInt(40) * PIXEL_SIZE;
        APPLE_Y = new Random().nextInt(40) * PIXEL_SIZE;
    }
    public void loadImages()
    {
        ImageIcon apple = new ImageIcon("apple");
        ImageIcon snake = new ImageIcon("snake");
        apple.getImage();
        snake.getImage();
    }

    public void move()
    {
        for (int i = SCREEN_SIZE; i > 0 ; i--)
        {
            x[i] = x[i - 1];
            y[i] = x[i - 1];
        }
        if (direction[0])
            y[0] -= PIXEL_SIZE;
        if (direction[1])
            x[0] += PIXEL_SIZE;
        if (direction[2])
            y[0] += PIXEL_SIZE;
        if (direction[3])
            x[0] -= PIXEL_SIZE;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (IN_GAME)
        {
            move();
        }
        repaint();
    }
}
