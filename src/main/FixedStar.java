package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class FixedStar implements Star {

    private int x;//x轴位置
    private int y;//y轴位置
    private int fixed_width;//物体的宽度
    private int fixed_height;//物体的高度
    private List<Star> planets = new ArrayList<Star>();//用于存储行星的容器
    private static Image image = ImageLoadUtil.loadImage("/Users/mengfeifei/Desktop/workspace/IDEA_Project/MyDesktopApp/src/Resources/earth_net.png");////恒星的图片


    public FixedStar(int fixed_width, int fixed_height) {
        //计算出xy的值，是它居中显示
        this.x = (Constant.GAME_WIDTH - fixed_width)/2;
        this.y = (Constant.GAME_HEIGHT - fixed_height)/2;
        this.fixed_width = fixed_width;
        this.fixed_height = fixed_height;
        this.createPlanet();


    }
    /**
     * 创建围绕它转的所有行星
     */
    private void createPlanet() {
        Star earth= new Planet(100, 50, 30, 30, this, "/Users/mengfeifei/Desktop/workspace/IDEA_Project/MyDesktopApp/src/Resources/earth_net.png", 0.09);
        planets.add(earth);
        planets.add(new Planet(20, 15, 15, 15, earth, "/Users/mengfeifei/Desktop/workspace/IDEA_Project/MyDesktopApp/src/Resources/noteBook.png", 0.3, true));
        planets.add(new Planet(200, 100, 30, 30, this, "/Users/mengfeifei/Desktop/workspace/IDEA_Project/MyDesktopApp/src/Resources/paper_plane.png", 0.08));
        planets.add(new Planet(250, 150, 30, 30, this, "/Users/mengfeifei/Desktop/workspace/IDEA_Project/MyDesktopApp/src/Resources/paper_plane.png", 0.05));
        planets.add(new Planet(350, 200, 30, 30, this, "/Users/mengfeifei/Desktop/workspace/IDEA_Project/MyDesktopApp/src/Resources/compute.png", 0.03));
    }

    /**
     * 绘画方法
     * @param g
     */
    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.red);
        //g.fillOval(this.x, this.y, fixed_width, fixed_height);
        g.drawImage(image, this.x, this.y, fixed_width, fixed_height, null);
        g.setColor(c);
        //画出每个行星
        for(int i = 0; i < this.planets.size(); i++) {
            planets.get(i).draw(g);

        }
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setFixed_width(int fixed_width) {
        this.fixed_width = fixed_width;
    }
    public void setFixed_height(int fixed_height) {
        this.fixed_height = fixed_height;
    }
    @Override
    public int getWidth() {
        return fixed_height;
    }
    @Override
    public int getHeight() {
        return fixed_height;
    }




}
