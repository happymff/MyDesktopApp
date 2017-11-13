package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Planet implements Star {

    private int x;//行星的位置
    private int y;//行星的位置
    private int planet_width;//行星的大小
    private int planet_height;//行星的大小
    private int longXpis; //离恒星的距离，就是椭圆的长轴
    private int shortXpis;//离恒星的距离，就是椭圆的短轴
    private Star fixedStar;//恒星
    private double degree = 1;//角度
    private Image image = null;//行星的图片，由于每个行星不同，所以定义为非静态的
    private double speed = 0.01;//默认的改变角度的速度
    private List<Planet> moon = new ArrayList<Planet>();//定义行星的子行星，如地球的月亮
    private boolean satellite = false;

    public Planet(int x, int y) {
        this.x = x;
        this.y = y;

    }
    /**
     *
     * @param longXpis 长轴
     * @param shortXpis 短轴
     * @param planet_width 行星的宽度
     * @param planet_height 行星的高度
     * @param fixedStar 中心星体，即恒星
     * @param path 图片的位置
     * @param speed 角度改变的速度
     */
    public Planet(int longXpis, int shortXpis, int planet_width, int planet_height, Star fixedStar, String path,
                  double speed) {
        //定义离中心星体的初始距离
        this(fixedStar.getX() + fixedStar.getWidth() / 2 + longXpis - planet_width / 2,
                fixedStar.getY() + fixedStar.getHeight() / 2 - planet_height / 2);
        this.longXpis = longXpis;
        this.shortXpis = shortXpis;
        this.planet_height = planet_height;
        this.planet_width = planet_width;
        this.fixedStar = fixedStar;
        this.image = ImageLoadUtil.loadImage(path);
        this.speed = speed;
    }

    public Planet(int longXpis, int shortXpis, int planet_width, int planet_height, Star planetStar, String path,
                  double speed, boolean satellite) {
        this(longXpis, shortXpis, planet_width, planet_height, planetStar, path,
                speed);
        this.satellite = satellite;
    }

    /**
     * 绘画方法
     * @param g
     */
    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.gray);
        //画出行星图片
        g.drawImage(image, this.x, this.y, planet_width, planet_height, null);
        //画出行星的运行轨迹
        if(!satellite) {
            g.drawOval(fixedStar.getX() - (longXpis - this.planet_width / 2),
                    fixedStar.getY() - (shortXpis - this.planet_height / 2), this.longXpis * 2, this.shortXpis * 2);

        }
        g.setColor(c);
        //以下的代码使行星做椭圆运动
        this.x = (int) (fixedStar.getX() + longXpis * Math.cos(degree));
        this.y = (int) (fixedStar.getY() + shortXpis * Math.sin(degree));
        if(degree < 2 * Math.PI) {
            degree += speed;

        } else {
            degree = 0;
        }
        //画子行星
        for(int i = 0; i < moon.size(); i++) {
            moon.get(i).draw(g);
        }
    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    @Override
    public int getWidth() {
        return planet_width;
    }
    @Override
    public int getHeight() {
        return planet_height;
    }



}