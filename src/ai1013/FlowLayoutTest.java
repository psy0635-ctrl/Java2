
package ai1013;

import center.CenterFrame2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FlowLayoutTest extends JFrame {

    public FlowLayoutTest() {
        setTitle("FlowLayout 연습");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));

        ArrayList<JButton> list = new ArrayList<JButton>(5);

        for (int i = 0; i < 5; i++) {

            JButton btn = new JButton("버튼" + (i + 1));

            // 버튼의 기본 색상
            btn.setBackground(Color.LIGHT_GRAY);
            btn.setOpaque(true);
            btn.setContentAreaFilled(true);

            // 버튼 클릭 이벤트
            btn.addActionListener(e -> {

                if (btn.getBackground().equals(Color.YELLOW)) {
                    btn.setBackground(Color.LIGHT_GRAY);
                } else {
                    btn.setBackground(Color.YELLOW);
                }

            });

            list.add(btn);
            add(btn);
        }

        int w = 250;
        int h = 250;

        Point point = CenterFrame2.getLocation(w, h);
        setBounds(point.x, point.y, w, h);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FlowLayoutTest::new);
    }
}
