package main;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 定义一个父类，将常用的代码写到此处
 * @author may
 *
 */
public class MyFrame extends Frame {

    private static final long serialVersionUID = 8133309356114432110L;

    public MyFrame() {
        this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        this.setLocationRelativeTo(null);


        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                MyFrame.this.dispose();
            }
        });
        this.setVisible(true);

    }


}