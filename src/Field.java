import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Field extends JPanel implements ActionListener {
    private static int width;//Ширина игрового поля
    private static int height;//Высота игрового поля
    private final int pixel_size = 64;//Размер одной ячейки игрового поля
    private Image Snake_dot;//Изображение змейки
    private Image Apple;//изображение яблока
    private Image Head;//избражение головы змейки
    private int AppleX;//
    private int AppleY;// Текущие координаты яблока
    private final int[] snakeX; //
    private final int[] snakeY; //массивы координат каждого отсека змейки
    private int snakeSize = new Random().nextInt(4);//количество ячеек змейки
    private boolean UP = false;     //
    private boolean DOWN = false;   //
    private boolean LEFT = false;   //
    private boolean RIGHT = true;  //Направления движения змейки
    private boolean IN_GAME = true;    //состояние игры (в игре/вне игры)
    private int score = 0;//количество очков

    public Field(int width, int height) {
        Field.width = width;
        Field.height = height;
        int size = width * height;//Размер игрового поля
        int count_pixels = size / pixel_size;//количество ячеек в игровом поле
        snakeX = new int[count_pixels - 1];
        snakeY = new int[count_pixels - 1];
        setBackground(Color.GRAY);//Заливка фона цветом
        loadImages();
        initGame();
        addKeyListener(new KeyListener());
        addMouseListener(new MouseListener());
        setFocusable(true);
    }

    /**Начальная инициализация игровыз данных (отрисовка первой змейки, первого яблока, запуск таймера)*/
    public void initGame() {
        if (snakeSize == 0)
            snakeSize = 1;
        for (int i = snakeSize; i > 0; i--) {
            snakeX[i] = snakeSize * pixel_size - i * pixel_size;
            snakeY[i] = 0;
        }
        Timer timer = new Timer(128, this);
        timer.start();
        createApple();
    }

    /**Генерирует новое яблоко на поле*/
    public void createApple() {
        AppleX = new Random().nextInt((width / pixel_size) - 2) * pixel_size;
        AppleY = new Random().nextInt((height / pixel_size) - 2) * pixel_size; //В некоторых разрешениях на 64 нацело не делиться, поэтому последние два варианта (первый - это граница, второй - это половина картинки) я убрал
        for (int i = 0; i < snakeSize; i++)
        {
            if (AppleX == snakeX[i] && AppleY == snakeY[i])
                createApple();
        }
        score++;
    }

    /**Загрузка картинок*/
    public void loadImages() {
        ImageIcon head = new ImageIcon("snake.png");
        ImageIcon apple = new ImageIcon("Qieli.png");
        ImageIcon snake = new ImageIcon("head.png");
        Head = head.getImage();
        Apple = apple.getImage();
        Snake_dot = snake.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (IN_GAME)
        {
            g.drawImage(Apple, AppleX, AppleY, this);
            for (int i = 1; i < snakeSize; i++)
            {
                g.drawImage(Snake_dot, snakeX[i], snakeY[i], this);
            }
            g.drawImage(Head, snakeX[0], snakeY[0],this);
            g.setFont(new Font("Comic Sans", Font.ITALIC, width/45));
            g.setColor(Color.RED);
            g.drawString("Score: " + score , 0, 45);
        }
        else
        {
            setBackground(Color.BLACK);
            g.setFont(new Font("Comic Sans", Font.ITALIC, width/35));
            g.setColor(Color.WHITE);
            g.drawString("DIED FROM CRINGE" , width/3, height/2);
            g.setColor(Color.RED);
            g.drawString("Your score : " + score , width/3, height/2 + width/35);
        }
    }

    /**Меняет координаты змейки*/
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

    /**Проверка, схвачено ли яблоко*/
    public void checkApple() {
        if (snakeX[0] == AppleX && snakeY[0] == AppleY) {
            snakeSize++;
            createApple();
        }
    }

    /**проверка на наличие столкновения с собой либо с границей экрана0*/
    public void checkCollisions()
    {
        if (snakeX[0] < 0 || snakeX[0] > (width - pixel_size) || snakeY[0] < 0 || snakeY[0] > (height - pixel_size))
            IN_GAME = false;
        for (int i = 1; i < snakeSize; i++)
        {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i])
            {
                IN_GAME = false;
                break;
            }
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

    class MouseListener extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            int x = e.getX();
            int y = e.getY();
            if (y < height/10 && !DOWN)
            {
                UP = true;
                LEFT = false;
                RIGHT = false;
            }
            if (x < width/10 && !RIGHT)
            {
                UP = false;
                LEFT = true;
                DOWN = false;
            }
            if (x > width - width/10 && !LEFT)
            {
                UP = false;
                DOWN = false;
                RIGHT = true;
            }
            if (y > height - height/10 && !UP)
            {
                DOWN = true;
                LEFT = false;
                RIGHT = false;
            }
        }
    }

    class KeyListener extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_W || key == KeyEvent.VK_UP)&& !DOWN)
            {
                UP = true;
                LEFT = false;
                RIGHT = false;
            }
            if ((key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) && !RIGHT)
            {
                UP = false;
                LEFT = true;
                DOWN = false;
            }
            if ((key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) && !LEFT)
            {
                UP = false;
                DOWN = false;
                RIGHT = true;
            }
            if ((key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) && !UP)
            {
                DOWN = true;
                LEFT = false;
                RIGHT = false;
            }
        }
    }
}