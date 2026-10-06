package ai1006;

import center.CenterFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckBoxTest extends JFrame {

    CheckBoxTest(){
        setTitle("CheckBox 컴포넌트 연습");

        // 레이아웃: 컴포넌트를 보기 좋게 배치
        // JFrame 기본 레이아웃: BorderLayout
        // 동, 서, 남, 북, 가운데 5개의 위치에 배치
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JCheckBox check1 = new JCheckBox("선택하세요.");
        check1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (check1.isSelected()) {
                    JOptionPane.showMessageDialog(null, "체크 ON");
                } else {
                    JOptionPane.showMessageDialog(null, "체크 OFF");
                }
            }
        });

        add(check1);

        int w = 300;
        int h = 200;

        setSize(w,h);
        int[] location = CenterFrame.getLocation(w,h);
        setLocation(location[0],location[1]);

        setVisible(true);

    }
    public static void main(String[] args){
        new CheckBoxTest();
    }
}
