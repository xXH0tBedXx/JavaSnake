import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Random;

public class Field extends JPanel implements ActionListener {
    private static int width;//Ширина игрового поля
    private static int height;//Высота игрового поля
    private static int size;//Размер игрового поля
    private static int count_pixels;//количество ячеек в игровом поле
    private final int pixel_size = 64;//Размер одной ячейки игрового поля
    private Image Snake_dot;//Изображение змейки
    private Image Apple;//изображение яблока
    private int AppleX;//
    private int AppleY;// Текущие координаты яблока
    private int[] snakeX; //
    private int[] snakeY; //массивы координат каждого отсека змейки
    private int snakeSize = new Random().nextInt(4);//количество ячеек змейки
    private Timer timer;
    private boolean UP = false;     //
    private boolean DOWN = false;   //
    private boolean LEFT = false;   //
    private boolean RIGHT = true;  //Направления движения змейки
    private boolean IN_GAME = true;    //состояние игры (в игре/вне игры)

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        size = width * height;
        count_pixels = size / pixel_size;
        snakeX = new int[count_pixels - 1];
        snakeY = new int[count_pixels - 1];
        setBackground(Color.GRAY);//Заливка фона цветом
        loadImages();
        initGame();
        addKeyListener(new KeyListener());
        setFocusable(true);
    }

    public void initGame() {
        for (int i = snakeSize; i > 0; i--) {
            snakeX[i] = snakeSize * pixel_size - i * pixel_size;
            snakeY[i] = 0;
        }
//        snakeX[0] = 0;
//        snakeY[0] = 0;
        timer = new Timer(128, this);
        timer.start();
        createApple();
    }

    public void createApple() {
        AppleX = new Random().nextInt((width / pixel_size) - 2) * pixel_size;
        AppleY = new Random().nextInt((height / pixel_size) - 2) * pixel_size; //В некоторых разрешениях на 64 нацело не делиться, поэтому последние два варианта (первый - это граница, второй - это половина картинки) я убрал
    }

    public void loadImages() {
        ImageIcon apple = new ImageIcon("matryoshka.png");
        ImageIcon snake = new ImageIcon("snake.png");
        Apple = apple.getImage();
        Snake_dot = snake.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (IN_GAME)
        {
            g.drawImage(Apple, AppleX, AppleY, this);
            for (int i = 0; i < snakeSize; i++)
            {
                g.drawImage(Snake_dot, snakeX[i], snakeY[i], this);
            }
        }
        else
        {
            setBackground(Color.BLACK);
            g.drawString("DIED FROM CRINGE", width/2, size/2);
        }
    }

    public void move() {
        for (int i = snakeSize; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        if (UP)
            snakeY[0] -= pixel_size;
        if (DOWN)
            snakeY[0] += pixel_size;
        if (LEFT)
            snakeX[0] -= pixel_size;
        if (RIGHT)
            snakeX[0] += pixel_size;
    }

    public void checkApple() {
        if (snakeX[0] == AppleX && snakeY[0] == AppleY) {
            snakeSize++;
            createApple();
        }
    }

    public void checkCollisions()
    {
        if (snakeX[0] < 0 || snakeX[0] > width || snakeY[0] < 0 || snakeY[0] > height)
            IN_GAME = false;
        for (int i = 1; i < snakeSize; i++)
        {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i])
                IN_GAME = false;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (IN_GAME)
        {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }
    class KeyListener extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_W && !DOWN)
            {
                UP = true;
                LEFT = false;
                RIGHT = false;
            }
            if (key == KeyEvent.VK_A && !RIGHT)
            {
                UP = false;
                LEFT = true;
                DOWN = false;
            }
            if (key == KeyEvent.VK_D && !LEFT)
            {
                UP = false;
                DOWN = false;
                RIGHT = true;
            }
            if (key == KeyEvent.VK_S && !UP)
            {
                DOWN = true;
                LEFT = false;
                RIGHT = false;
            }
        }
    }
}