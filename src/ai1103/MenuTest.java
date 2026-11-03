
package ai1103;

import ai1027.SetFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuTest extends JFrame {
    JTextArea textArea = new JTextArea();
    JFileChooser fileChooser = new JFileChooser();
    JFrame frame;

    public MenuTest() {
        frame = this;
        JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem closeMenuItem = new JMenuItem("Close");

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(closeMenuItem);

        JMenuItem whiteMenuItem = new JMenuItem("White");
        JMenuItem blueMenuItem = new JMenuItem("Blue");
        JMenuItem magentaMenuItem = new JMenuItem("Magenta");

        editMenu.add(whiteMenuItem);
        editMenu.add(blueMenuItem);
        editMenu.add(magentaMenuItem);

        // 배경색 변경
        whiteMenuItem.addActionListener(backgroundListener);
        blueMenuItem.addActionListener(backgroundListener);
        magentaMenuItem.addActionListener(backgroundListener);

        // Open 클릭하면 새 창 열기
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (openFrame == null || !openFrame.isDisplayable()) {
                    openFrame = new JFrame("새 창");
                    openFrame.setSize(300, 300);
                    openFrame.setLocationRelativeTo(null);
                    openFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    openFrame.setVisible(true);
                } else {
                    openFrame.toFront();
                }
            }
        });

        // Close 클릭하면 창 닫기
        closeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (openFrame != null && openFrame.isDisplayable()) {
                    openFrame.dispose();
                    openFrame = null;
                } else {
                    dispose();
                }
            }
        });

        SetFrame.setFrame(this, "메뉴 만들기", 500, 500);
    }

    ActionListener backgroundListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Color color = null;

            switch (e.getActionCommand()) {
                case "White":
                    color = Color.WHITE;
                    break;

                case "Blue":
                    color = Color.BLUE;
                    break;

                case "Magenta":
                    color = Color.MAGENTA;
                    break;
            }

            textArea.setBackground(color);
        }
    };

    public static void main(String[] args) {
        new MenuTest();
    }
}
