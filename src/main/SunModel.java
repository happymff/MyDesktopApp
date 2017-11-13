package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SunModel extends MyFrame {

    private static final long serialVersionUID = -5330545593499050676L;
    private static Image image = ImageLoadUtil.loadImage("/Users/mengfeifei/Desktop/workspace/IDEA_Project/MyDesktopApp/src/Resources/paper_plane.png");
    //恒星对象
    private FixedStar fixedStar = new FixedStar(Constant.FIXED_WIDTH, Constant.FIXED_HEIGHT);
    //创建一个缓冲图，用于双缓冲，避免闪烁出现
    private BufferedImage buffImage = new BufferedImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);

    public SunModel() {
        super();
        this.setResizable(false);
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(new MyThread());
        //new Thread(new MyThread()).start();
    }

    @Override
    public void paint(Graphics g) {
        //将缓冲图直接画上去
        g.drawImage(buffImage, 0, 0, null);
    }

    //重写update方法，进行双缓冲，将需要显示的物体全画到缓冲图上，然后一次性显示出来
    @Override
    public void update(Graphics g) {
        Graphics sunG = buffImage.getGraphics();
        //将背景图画上
        sunG.drawImage(image, 0, 0, null);
        fixedStar.draw(sunG);
        paint(g);
    }

    public static void main(String[] args) {
        new SunModel();
    }

    private class MyThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                //调用重画方法
                repaint();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


    }

}