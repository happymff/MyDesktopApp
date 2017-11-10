package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
/**
 * 炮弹类
 * @author may
 *
 */
public class Shell {

    private int x;
    private int y;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private boolean enemy = false;
    private boolean isLive = true;
    private MyPanel panel;
    private PanelGame game;
    private int step =2;


    public Shell(int x, int y, MyPanel panel, boolean enemy, PanelGame game) {
        this.x = x;
        this.y = y;
        this.panel = panel;
        this.enemy = enemy;
        this.game = game;
    }

    public void draw(Graphics g) {

        if(this.isLive) {

            Color c = g.getColor();
            if(enemy) {

                g.setColor(Color.red);
            } else {

                g.setColor(Color.yellow);
            }
            g.fillOval(this.x, this.y, WIDTH, HEIGHT);
            g.setColor(c);
            move();
            this.hit(game);
        }

    }

    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle(x, y, WIDTH, HEIGHT);

        return rectangle;
    }



    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean isLive) {
        this.isLive = isLive;
    }

    public void move() {
        if(!enemy) {
            x -= step;
        } else {
            x += step;
        }

        if(x < 10 || x > 500) {
            this.setLive(false);
            panel.getShells().remove(this);
        }

    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    //撞到了就爆炸
    public void createExplode() {

        game.getExplodes().add(new Explode(this.x, this.y, game));
    }
    //碰撞检测方法
    public void hit(PanelGame game) {
        List<MyPanel> enemys = game.getPanels();
        List<MyPanel> goods = game.getPanel();
        for(int i = 0; i < enemys.size(); i++) {
            if(this.enemy != enemys.get(i).isEnemy()) {

                if(this.getRectangle().intersects(enemys.get(i).getRectangle())) {
                    this.setLive(false);
                    panel.getShells().remove(this);
                    enemys.get(i).removedeadPlane();
                    this.createExplode();
                    game.killEnemyCount ++;
                }
            }

        }

        for(int i = 0; i < goods.size(); i++) {
            if(this.enemy != goods.get(i).isEnemy()) {

                if(this.getRectangle().intersects(goods.get(i).getRectangle())) {
                    this.setLive(false);
                    panel.getShells().remove(this);
                    goods.get(i).lifeValue -= 30;

                    if(goods.get(i).lifeValue <= 0) {
                        game.deadCount ++;
                        goods.get(i).removedeadPlane();

                    }
                    this.createExplode();
                }
            }

        }

    }



}