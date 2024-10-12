import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class GameField extends JPanel implements ActionListener{
    private final int SIZE = 370;
    private final int DOT_SIZE = 16; //размер ячейки
    private final int ALL_DOTS = 400; //ёмкость поля
    private Image dot;
    private Image food;
    private int foodX; //позиция еды по Х
    private int foodY;
    private int[] x = new int[ALL_DOTS]; //координаты змейки
    private int[] y = new int[ALL_DOTS];
    private int dots; //колличество элементов змейки
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true; //проверка, запущена ли игра или нет


    public GameField(){
        setBackground(Color.pink);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    //метод для конструирования исходных условий игры
    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(200,this);
        timer.start();
        createFood();
    }

    //метод для создания еды змейки
    public void createFood(){
        foodX = new Random().nextInt(20)*DOT_SIZE;
        foodY = new Random().nextInt(20)*DOT_SIZE;
    }

    //метод, загружающий спрайты змейки и еду
    public void loadImages(){
        ImageIcon iia = new ImageIcon("food.png");
        food = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    //метод для отрисовки спрайтов на поле
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(food,foodX,foodY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i],this);
            }
        } else{
            String str = "Game Over";
            g.setColor(Color.blue);
            g.drawString(str,125,SIZE/2);
        }
    }

    //логика для перемещения змейки
    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        } if(up){
            y[0] -= DOT_SIZE;
        } if(down){
            y[0] += DOT_SIZE;
        }
    }

    //метод для увеличения тела змейки
    public void checkFood(){
        if(x[0] == foodX && y[0] == foodY){
            dots++;
            createFood();
        }
    }

    //метод для проверки на коллизии с границами поля
    public void checkCollisions(){
        for (int i = dots; i >0 ; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }
        if(x[0]>SIZE){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(y[0]>SIZE){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
    }

    //запускает основные методы при старте игры
    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkFood();
            checkCollisions();
            move();
        }
        repaint();
    }

    //считывание клавиш
    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                right = false;
                down = true;
                left = false;
            }
        }
    }
}