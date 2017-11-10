package main;

import java.awt.*;
/**
 * 爆炸类
 * @author may
 *
 */
public class Explode {

    private int x;//爆炸位置
    private int y;//爆炸位置
    private static Image[] images = new Image[10];
    private int count = 0;//当前播放到的图片在数组中的下标
    private PanelGame game;//持有主窗口的引用

    //静态加载图片
    static {
        for(int i = 0; i < images.length; i++) {
            images[i] = ImageLoadUtil.loadImage("/lib/explode/" + (i) + ".gif");
            //避免Java的懒加载，让它一用到就可直接用，否则可能一开始的一两张图将看不到
            images[i].getHeight(null);
        }

    }

    /**
     *
     * @param x 位置
     * @param y 位置
     * @param game 游戏主类
     */
    public Explode(int x, int y, PanelGame game) {
        this.x = x;
        this.y = y;
        this.game = game;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        if(count == 3) {
            //播放完后将自己从主类容器中去掉
            game.getExplodes().remove(this);
        }
        g.drawImage(images[count ++], this.x - images[count ++].getWidth(null)/2 , this.y - images[count ++].getHeight(null)/2, 30, 30,  null);
        g.setColor(c);
    }

}