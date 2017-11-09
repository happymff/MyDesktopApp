package main;

import javax.swing.*;
import java.awt.Desktop;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Created by mengfeifei on 2017/11/9.
 */
public class Notepad extends JInternalFrame {
    private static final long serialVersionUID = -6148113299360403243L;

    private JMenuBar menuBar = null;
    private JTextArea textArea = null;
    private JScrollPane scrollPane = null;
    private MyAction myAction = new MyAction();
    private String dir = null;
    private String fileDir = null;
    private boolean ctrlClick = false;
    private boolean sClick = false;

    public Notepad() {
        super("notepad");
        this.setSize(600, 500);
        menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("文件");
        JMenuItem menuItem2 = new JMenuItem("打开");
        JMenuItem menuItem4 = new JMenuItem("保存");
        menuItem2.addActionListener(myAction);
        menuItem4.addActionListener(myAction);
        JMenuItem menuItem3 = new JMenuItem("打开文件所在目录");
        menuItem3.addActionListener(myAction);
        menu1.add(menuItem2);
        menu1.add(menuItem3);
        menu1.add(menuItem4);

        JMenu menu2 = new JMenu("版本信息");
        menu2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String[] options =
                        {"确定"};
                JOptionPane.showOptionDialog(Notepad.this, "version:0.1-snapshoot", "关于", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, "确定");
            }
        });
        menuBar.add(menu1);
        menuBar.add(menu2);
        this.setJMenuBar(menuBar);
        textArea = new JTextArea();
        textArea.addKeyListener(new keyOpthon());
        textArea.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.getContentPane().add(scrollPane);
        this.setIconifiable(true);
        this.setClosable(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void openChooseDiag(String flag) {
        MyFileReader fileReader = null;
        JFileChooser fileChooser = new JFileChooser();
        if (dir != null) {
            fileChooser.setCurrentDirectory(new File(dir));
        }
        switch (flag) {
            case "打开":
                fileChooser.showOpenDialog(Notepad.this);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                File file = fileChooser.getSelectedFile();
                if (file != null) {
                    try {
                        dir = file.getAbsolutePath();
                        fileDir = dir;
                        dir = dir.substring(0, dir.lastIndexOf("/") + 1);
                        fileReader = new MyFileReader();
                        String str = null;
                        String str1 = file.toString().substring(file.toString().indexOf(".") + 1);
                        System.out.println(str1);
                        switch (str1) {
                            case "rtf":
                                str = fileReader.getTextFromRtf(file.toString());
                                break;
                            case "pdf":
                                str = fileReader.getTextFromPdf(file.toString());
                                break;
                            case "docx":
                                str = fileReader.getTextFromWord7(file.toString());
                                break;
                            case "doc":
                                str = fileReader.getTextFromWord3(file.toString());
                                break;
                            case "xlsx":
                                str = fileReader.getTextFromExcel(file.toString());
                                break;
                            case "txt":
                                str = fileReader.getTextFromTxt(file.toString());
                                break;
                            case "html":
                                str = fileReader.getTextFromHtml(file.toString());
                                break;
                        }

                        //System.out.println(str);
                        String[] strings = str.split("\n");
                        textArea.setText("");
                        for (String string : strings) {
                            textArea.append(string + "\n");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "保存":
                fileChooser.showSaveDialog(Notepad.this);
                File saveFile = fileChooser.getSelectedFile();
                if (saveFile != null) {
                    String absolutPath = saveFile.getAbsolutePath();
                    fileDir = absolutPath;
                    FileOutputStream out = null;
                    BufferedWriter buffOut = null;
                    dir = absolutPath.substring(0, absolutPath.lastIndexOf("/") + 1);
                    try {
                        out = new FileOutputStream(absolutPath);
                        buffOut = new BufferedWriter(new OutputStreamWriter(out,"utf-8"));
                        String text = textArea.getText();
                        if (text != null) {
                            buffOut.write(text);
                        }
                        buffOut.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (out != null) {
                                out.close();
                            }
                            if (buffOut != null) {
                                buffOut.close();
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                break;
            case "打开文件所在目录":
                if (dir != null) {
                    try {
                        Desktop.getDesktop().open(new File(dir));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private class MyAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem item = (JMenuItem) e.getSource();
            String flag = item.getText();
            switch (flag) {
                case "打开":
                    openChooseDiag(flag);
                    break;
                case "保存":
                    openChooseDiag(flag);
                    break;
                case "打开文件所在目录":
                    openChooseDiag(flag);
                    break;
            }
        }
    }

    private class keyOpthon extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (17 == keyCode) {
                ctrlClick = true;
            } else if (83 == keyCode) {
                sClick = true;
            }
            if (ctrlClick && sClick) {
                FileOutputStream out = null;
                BufferedWriter buffOut = null;
                try {
                    if (fileDir != null) {
                        out = new FileOutputStream(fileDir);
                        buffOut = new BufferedWriter(new OutputStreamWriter(out));
                        String text = textArea.getText();
                        if (text != null) {
                            buffOut.write(text);
                        }
                        buffOut.flush();
                    } else {
                        openChooseDiag("保存");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                        if (buffOut != null) {
                            buffOut.close();
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (17 == keyCode) {
                ctrlClick = false;
            } else if (83 == keyCode) {
                sClick = false;
            }
        }
    }
}