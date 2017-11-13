package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mengfeifei on 2017/11/9.
 */
public class Desktop extends JFrame {
    private static final long serialVersionUID =3899092629742973479L;
    private JDesktopPane desktop = null;
    private JLabel backgroundImage = null;
    private MouseOption mouseOption = new MouseOption();//鼠标监听对象
    public Desktop (String title) throws IOException {
        super(title);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setLayout(new BorderLayout());
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();
        this.setSize(width,height);
        desktop = new JDesktopPane();
        backgroundImage = new JLabel();
        BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g= image.createGraphics();
        BufferedImage ad = null;
        try{
            File imageFile = new File("/Users/mengfeifei/Desktop/workspace/IDEA_Project/MyDesktopApp/src/Resources/rapeFlower.jpg");
            ad = ImageIO.read(imageFile);
        }catch (IOException e){
            e.printStackTrace();
        }
        g.drawImage(ad,0,0,width,height,null);
        ImageIcon img = new ImageIcon(image);
        backgroundImage.setIcon(img);
        backgroundImage.setBounds(new Rectangle(0,0,width,height));

        JButton mycompute = new JButton();
        Icon computeIcon = new ImageIcon("/Users/mengfeifei/Desktop/workspace/IDEA_Project/MyDesktopApp/src/Resources/compute.png");
        mycompute.setIcon(computeIcon);
        mycompute.setBounds(20,20,48,48);
        mycompute.setContentAreaFilled(false);
        mycompute.setBorderPainted(false);
        mycompute.addMouseListener(mouseOption);
        mycompute.setText("compute");
        desktop.add(mycompute,Integer.MIN_VALUE+1);

        JButton myNotebook = new JButton();
        Icon noteBookIcon = new ImageIcon("/Users/mengfeifei/Desktop/workspace/IDEA_Project/MyDesktopApp/src/Resources/noteBook.png");
        myNotebook.setIcon(noteBookIcon);
        myNotebook.setBounds(20,88,48,48);
        myNotebook.setContentAreaFilled(false);
        myNotebook.setBorderPainted(false);
        myNotebook.addMouseListener(mouseOption);
        myNotebook.setText("notebook");
        desktop.add(myNotebook,Integer.MIN_VALUE+1);

        JButton myPanel = new JButton();
        Icon panelIcon = new ImageIcon("/Users/mengfeifei/Desktop/workspace/IDEA_Project/MyDesktopApp/src/Resources/paper_plane.png");
        myPanel.setIcon(panelIcon);
        myPanel.setBounds(20,156,48,48);
        myPanel.setContentAreaFilled(false);
        myPanel.setBorderPainted(false);
        myPanel.addMouseListener(mouseOption);
        myPanel.setText("panel");
        desktop.add(myPanel,Integer.MIN_VALUE+1);

        JButton mySunModel = new JButton();
        Icon sunModelIcon = new ImageIcon("/Users/mengfeifei/Desktop/workspace/IDEA_Project/MyDesktopApp/src/Resources/earth_net.png");
        mySunModel.setIcon(sunModelIcon);
        mySunModel.setBounds(20,224,48,48);
        mySunModel.setContentAreaFilled(false);
        mySunModel.setBorderPainted(false);
        mySunModel.addMouseListener(mouseOption);
        mySunModel.setText("sunModel");
        desktop.add(mySunModel,Integer.MIN_VALUE+1);

        desktop.add(backgroundImage, new Integer(Integer.MIN_VALUE));

        this.getContentPane().add(desktop,BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    private class MouseOption extends MouseAdapter{
        private int count;
        private Timer timer = new Timer();

        private String str = null;
        private class MyTimerTask extends TimerTask{
            JButton button = null;
            public MyTimerTask(JButton button){
                this.button = button;
            }

            @Override
            public void run() {
                if (count ==1){
                    count =0;
                }
                if (count ==2){
                    switch (str){
                        case "fileChooser":
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                            fileChooser.showOpenDialog(Desktop.this);
                            File file = fileChooser.getSelectedFile();
                            if (file != null){
                                System.out.println(file.getAbsolutePath());
                            }
                            break;
                        case "notebook":
                            Notepad notepad = new Notepad();
                            desktop.add(notepad);
                            notepad.toFront();
                            break;
                        case "compute":
                            try{
                                java.awt.Desktop.getDesktop().open(new File(System.getProperty("user.home")));
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                            break;
                        case "panel":
                            new PanelGame();
                            break;
                        case "sunModel":
//                            SunModel sunModel = new SunModel();
//                            desktop.add(sunModel);
//                            sunModel.toFront();
                            new SunModel();
                            break;
                    }
                    button.setContentAreaFilled(false);
                    count=0;
                }
            }
        }

            @Override
            public void mouseClicked(MouseEvent event){
                JButton button = (JButton) event.getSource();
                button.setContentAreaFilled(true);
                str = button.getText();
                count ++;
                timer.schedule(new MyTimerTask(button),400);
            }

    }
    public static void main(String [] args) throws Exception{
            new Desktop("桌面");
    }

}
