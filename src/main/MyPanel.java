package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

/**
 * 飞机类
 * @author may
 *
 */
public class MyPanel extends Panel {

    private int x;//位置
    private int y;//位置
    private static final int WIDTH = 48;//飞机的大小
    private static final int HEIGHT = 48;//飞机的大小
    private static final int WAR_WIDTH = 500;//游戏窗口的大小
    private static final int WAR_HEIGHT = 500;//游戏窗口的大小
    private boolean up = false, right = false, down = false, left = false;//飞机的移动方向
    private boolean enemy = true;//默认为敌机
    private static Image[] images = new Image[2];//敌机与我方的飞机图片
    private List<Shell> shells = null;//子弹容器
    private int step = 1;//移动的步数
    private boolean isLive = true;//默认飞机生存
    private int oldX = x;//记录飞机上一次的位置
    private int oldY = y;//记录飞机上一次的位置
    private int keep = 0;//敌机自动发射子弹的间隔
    private Random random = new Random();//定义随机数，用于定义敌机的子弹发射的时间间隔
    private Director dir = Director.STOP;//飞机默认是不动的
    private PanelGame game;//游戏主类
    public int lifeValue = 90;//飞机的生命值

    static {
        for(int i = 0; i < images.length; i++) {
            images[i] = ImageLoadUtil.loadImage("/lib/plane" + (i + 1) + ".png");
            images[i].getWidth(null);

        }

    }


    public MyPanel(PanelGame game, boolean enemy) {
        this.game = game;
        this.enemy = enemy;
        //如果是敌机，定义它初始位置
        if(this.enemy) {
            this.x = - random.nextInt(3 * WIDTH) ;
            this.y = random.nextInt(WAR_HEIGHT - 2 * HEIGHT) + HEIGHT;
        }
        this.shells = game.getShells();
    }

    public MyPanel(int x, int y, boolean enemy, PanelGame game) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.game = game;
        this.shells = game.getShells();
    }



    public void draw(Graphics g) {
        //如果飞机还活着，就画它
        if(this.isLive) {

            Color c = g.getColor();
            if(enemy) {
                g.drawImage(images[1], x, y, WIDTH, HEIGHT, null);

            } else {
                g.drawImage(images[0], x, y, WIDTH, HEIGHT, null);
            }
            g.setColor(c);
            g.setColor(c);

            this.move();
            if(enemy) {
                this.hit(game);
            }
        }


    }

    /**
     * 创建子弹
     */
    public void createShells() {
        if(!enemy) {
            this.getShells().add(new Shell(x - 10, y + 20, this, false, game));

        } else {

            this.getShells().add(new Shell(x + 50, y + 20, this, true, game));
        }

    }



    public boolean isEnemy() {
        return enemy;
    }

    public int getX() {
        return x;
    }



    public List<Shell> getShells() {
        //shells = game.getShells();
        return shells;
    }
    //如果飞机被击毁，飞机在飞机战场上消失（主类的容器）
    public void removedeadPlane() {
        this.isLive = false;
        if(!this.isLive && this.enemy) {
            game.getPanels().remove(this);
        }
        if(!this.isLive && !this.enemy) {
            game.getPanel().remove(this);
        }

    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean isLive) {
        this.isLive = isLive;
    }

    public int getY() {
        return y;
    }
    //确定方向，这些方向的确定是通过按键监听来确定
    private void directer() {
        if( left && !down && !right && !up ) {
            dir = Director.L;
        }

        else if( left && up && !right && !down ) {
            dir = Director.LU;
        }

        else if( up && !left && !right && !down ) {
            dir = Director.U;
        }

        else if( up && right && !left && !down ) {
            dir = Director.RU;
        }

        else if( right && !up && !left && !down ) {
            dir = Director.R;
        }

        else if( right && down && !up && !left ) {
            dir = Director.RD;
        }

        else if( down && !up && !right && !left ) {
            dir = Director.D;
        }

        else if( left && down && !right && !up ) {
            dir = Director.LD;
        }

        else if( !left && !up && !right && !down ) {
            dir = Director.STOP;
        }
    }

    //根据方向的移动方法
    public void move() {
        oldX = x;
        oldY = y;

        switch(dir) {
            case L:
                x -= step;
                break;
            case LU:
                x -= step;
                y -= step;
                break;
            case U:
                y -= step;
                break;
            case RU:
                x += step;
                y -= step;
                break;
            case R:
                x += step;
                break;
            case RD:
                x += step;
                y += step;
                break;
            case D:
                y += step;
                break;
            case LD:
                x -= step;
                y += step;
                break;
            case STOP:
                break;
        }
        //如果不是敌机，不允许它跑出战场
        if(!enemy) {

            if(x > (WAR_WIDTH - 48) || x < 0) {
                x = oldX;

            }
            if(y > (WAR_HEIGHT - 48) || y < 30) {
                y = oldY;

            }

        }
        //如果是敌机，确定它的方向
        if(enemy) {
            dir = Director.R;
            //每隔50个刷新周期，发射一枚子弹
            if(keep == 0) {
                keep = 50;
                this.createShells();
            }
            keep --;
            //如果敌机逃出战场，摧毁它
            if(x > WAR_WIDTH) {
                game.getPanels().remove(this);

            }

        }

    }

    //键盘按下监听事件，由主类调用
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_UP  :
                this.up = true;
                break;
            case KeyEvent.VK_RIGHT :
                this.right = true;
                break;
            case KeyEvent.VK_DOWN :
                this.down = true;
                break;
            case KeyEvent.VK_LEFT  :
                this.left = true;
                break;
        }
        this.directer();

    }

    //键盘抬起监听事件，由主类调用
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_UP :
                this.up = false;
                break;
            case KeyEvent.VK_RIGHT :
                this.right = false;
                break;
            case KeyEvent.VK_DOWN :
                this.down = false;
                break;
            case KeyEvent.VK_LEFT :
                this.left = false;
                break;
            case KeyEvent.VK_CONTROL :
                this.createShells();
                break;
        }
        this.directer();
    }
    //矩形碰撞检测
    public Rectangle getRectangle() {

        return new Rectangle(this.x, this.y, WIDTH, HEIGHT);

    }

    //撞到了就爆炸
    public void createExplode() {

        game.getExplodes().add(new Explode(this.x, this.y, game));
    }

    public void hit(PanelGame game) {

        if(this.enemy) {
            List<MyPanel> p = game.getGoodpanels();
            for(int i = 0; i < p.size(); i++) {
                if(this.getRectangle().intersects(p.get(i).getRectangle())) {
                    game.getPanels().remove(this);

                    p.get(i).lifeValue -= 30;


                    if(p.get(i).lifeValue <= 0) {

                        game.getGoodpanels().remove(p.get(i));

                    }
                    this.createExplode();


                }
            }

        }

    }

}
