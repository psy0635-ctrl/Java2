package ai1027;

import ai1027.SetFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseEventTest2 extends JFrame {
    JLabel lbl = new JLabel();

    public MouseEventTest2() {
        JPanel panel = new JPanel();
        JButton btnR = new JButton("red");
        JButton btnB = new JButton("blue");
        JButton btnG = new JButton("green");
        lbl.setOpaque(true);
        panel.add(btnR);
        panel.add(btnB);
        panel.add(btnG);
        add(panel);
        add("South",lbl);

        btnR.addMouseListener(btnmouseListener);
        btnB.addMouseListener(btnmouseListener);
        btnG.addMouseListener(btnmouseListener);
        lbl.addMouseListener(btnmouseListener);

        SetFrame.setFrame(this, "MouseEvent Programming",300,300);
    }

    MouseListener btnmouseListener = new MouseListener(){

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("버튼의 마우스가 클릭됨");
            lbl.setText("버튼의 마우스가 클릭됨");

            Color color = null;

            JButton selected = (JButton)e.getSource();
            switch (selected.getText()) {
                case "red":
                    color = Color.RED;
                    break;
                case "blue":
                    color = Color.BLUE;
                    break;
                case "green":
                    color = Color.GREEN;
                    break;
            }
            lbl.setBackground(color);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("버튼의 마우스가 눌러짐");
            lbl.setText("버튼의 마우스가 눌러짐");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("버튼의 마우스가 눌렀다가 해제됨");
            lbl.setText("버튼의 마우스가 눌렀다가 해제됨");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("버튼의 마우스가 올려짐");
            lbl.setText("버튼의 마우스가 올려짐");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            System.out.println("버튼의 마우스가 나감");
            lbl.setText("버튼의 마우스가 나감");
        }
    };

    public static void main(String[] args) {
        new MouseEventTest2();
    }
}
