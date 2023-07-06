import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int B_wight = 400;
    int B_height = 400;
    int Max_DOTS = 1600;
    int DOT_SIZE = 10;
    int DOTS;
    int x[] = new int[Max_DOTS];
    int y[] = new int[Max_DOTS];
    int apple_x;
    int apple_y;
    Image body, head, apple;
    Timer timer;
    int DELAY = 300;
    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;

    boolean inGame = true;


    Board() {
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_wight, B_height));
        setBackground(Color.BLACK);
        initGame();
        loadImages();

    }
    // initialize game
    public void initGame(){
        DOTS =3;
        //initialize snake position;
        x[0] = 250;
        y[0] =250;
        for(int i=1;i<DOTS;i++){
            x[i] = x[0] + DOT_SIZE * i;
            y[i] = y[0];
        }
        // initialize apple position
        locateApple();
//        apple_x = 150;
//        apple_y = 150;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    // load images form resources folder to image object.
    public void loadImages(){
        ImageIcon bodyIcon = new ImageIcon("C:/Users/DELL/Downloads/dot.png");
        body = bodyIcon.getImage();
        ImageIcon headIcon = new ImageIcon("C:/Users/DELL/Downloads/head.png");
        head = headIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("C:/Users/DELL/Downloads/apple.png");
        apple = appleIcon.getImage();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
    //draw image
    public void doDrawing(Graphics g){
        if(inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < DOTS; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else
                    g.drawImage(body, x[i], y[i], this);
            }
        }
        else {
            gameOver(g);
            timer.start();
        }
    }
    //randomize apple's position.
    public void locateApple(){
        apple_x = ((int)(Math.random()*39))*DOT_SIZE;
        apple_y = ((int)(Math.random()*39))*DOT_SIZE;
    }
    // check collision with Board and body
    public void checkCollision(){
        //collision with body;
        for(int i=1;i<DOTS;i++) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }
        if(x[0]<0){
            inGame = false;
        }
        if(x[0]>=B_wight){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
        if(y[0]>=B_height){
            inGame = false;
        }
    }
    //Display game over message;
    public void gameOver(Graphics g){
        String msg = "Game Over";
        int score = (DOTS - 3)*100;
        String scoremsg = "Score:" + Integer.toString(score);
        Font small = new Font("Helvetica",Font.BOLD,14);
        FontMetrics fontMetrics = getFontMetrics(small);
        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg,(B_wight-fontMetrics.stringWidth(msg))/2,B_height/4);
        g.drawString(scoremsg,(B_wight-fontMetrics.stringWidth(scoremsg))/2,3*(B_height/4));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    //Make snake move.
    public void move(){
        for(int i=DOTS-1;i>=1;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(leftDirection){
            x[0]-= DOT_SIZE;
        }
        if(rightDirection){
            x[0]+=DOT_SIZE;
        }
        if(upDirection){
            y[0]-=DOT_SIZE;
        }
        if(downDirection) {
            y[0] += DOT_SIZE;
        }
    }
    //snake eat food
    public void checkApple(){
        if(apple_x == x[0] && apple_y ==y[0]){
            DOTS++;
            locateApple();
        }
    }
    //implement control
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if(key == KeyEvent.VK_LEFT&&!rightDirection){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            } if(key == KeyEvent.VK_RIGHT&&!leftDirection){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_UP&&!downDirection){
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            if(key == KeyEvent.VK_DOWN&&!upDirection){
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }
        }
    }
}