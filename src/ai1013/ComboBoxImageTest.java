
package ai1013;

import center.CenterFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ComboBoxImageTest extends JFrame {

    public ComboBoxImageTest() {

        setTitle("동물 이미지 선택");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 콤보박스에 표시할 동물 이름
        String[] items = {"강아지", "고양이", "토끼", "미어캣", "코알라"};

        JComboBox<String> comboBox = new JComboBox<>(items);
        JLabel lbl = new JLabel();

        // 이미지를 가운데 정렬
        lbl.setHorizontalAlignment(JLabel.CENTER);

        // 콤보박스에서 동물을 선택했을 때
        comboBox.addActionListener(e -> {

            int index = comboBox.getSelectedIndex();
            String fileName = "";

            // 선택한 동물에 따라 이미지 변경
            switch (index) {
                case 0:
                    fileName = "Pet01.png";
                    break;

                case 1:
                    fileName = "Pet02.png";
                    break;

                case 2:
                    fileName = "Pet03.png";
                    break;

                case 3:
                    fileName = "Pet04.png";
                    break;

                case 4:
                    fileName = "Pet05.png";
                    break;

                default:
                    break;
            }

            // 선택한 이미지 화면에 표시
            if (!fileName.isEmpty()) {
                showImage(lbl, fileName);
            }
        });

        // 화면에 컴포넌트 추가
        add(comboBox, BorderLayout.NORTH);
        add(lbl, BorderLayout.CENTER);

        // 처음 실행할 때 강아지 이미지 표시
        showImage(lbl, "Pet01.png");

        int w = 400;
        int h = 350;

        int[] location = CenterFrame.getLocation(w, h);
        setBounds(location[0], location[1], w, h);

        setVisible(true);
    }

    // 이미지를 불러와 JLabel에 표시하는 메서드
    private void showImage(JLabel lbl, String fileName) {

        File file = new File("Pets", fileName);

        if (!file.isFile()) {
            lbl.setIcon(null);
            lbl.setText("이미지를 찾을 수 없습니다.");
            return;
        }

        ImageIcon icon = new ImageIcon(file.getAbsolutePath());

        if (icon.getIconWidth() <= 0 || icon.getIconHeight() <= 0) {
            lbl.setIcon(null);
            lbl.setText("이미지를 읽을 수 없습니다.");
            return;
        }

        // 이미지 비율을 유지하면서 크기 조절
        double scale = Math.min(
                320.0 / icon.getIconWidth(),
                260.0 / icon.getIconHeight()
        );

        int width = (int) (icon.getIconWidth() * scale);
        int height = (int) (icon.getIconHeight() * scale);

        Image image = icon.getImage().getScaledInstance(
                width, height, Image.SCALE_SMOOTH
        );

        lbl.setText("");
        lbl.setIcon(new ImageIcon(image));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ComboBoxImageTest::new);
    }
}
