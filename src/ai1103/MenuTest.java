
package ai1103;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class MenuTest extends JFrame {

    JTextArea textArea = new JTextArea();
    JFileChooser fileChooser = new JFileChooser();

    public MenuTest() {

        // 텍스트 영역과 스크롤바
        JScrollPane scrollPane = new JScrollPane(
                textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        add(scrollPane, BorderLayout.CENTER);

        // 메뉴바 만들기
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        // File 메뉴
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem closeMenuItem = new JMenuItem("Close");

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(closeMenuItem);

        // Edit 메뉴
        JMenuItem whiteMenuItem = new JMenuItem("White");
        JMenuItem blueMenuItem = new JMenuItem("Blue");
        JMenuItem magentaMenuItem = new JMenuItem("Magenta");

        editMenu.add(whiteMenuItem);
        editMenu.add(blueMenuItem);
        editMenu.add(magentaMenuItem);

        // 상단 도구 모음
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.NORTH);

        // 파일 버튼
        JButton newButton = new JButton(new ImageIcon("toolbar/new.png"));
        JButton openButton = new JButton(new ImageIcon("toolbar/open.png"));
        JButton closeButton = new JButton(new ImageIcon("toolbar/close.png"));

        Font iconFont = new Font(
                "Segoe UI Emoji", Font.PLAIN, 26
        );

        newButton.setFont(iconFont);
        openButton.setFont(iconFont);
        closeButton.setFont(
                new Font("Segoe UI Symbol", Font.BOLD, 30)
        );
        closeButton.setForeground(Color.RED);

        toolBar.add(newButton);
        toolBar.add(openButton);
        toolBar.add(closeButton);

        toolBar.addSeparator();

        // 색상 버튼
        JButton whiteButton = new JButton("  ");
        JButton blueButton = new JButton("  ");
        JButton magentaButton = new JButton("  ");

        whiteButton.setBackground(Color.WHITE);
        blueButton.setBackground(Color.BLUE);
        magentaButton.setBackground(Color.MAGENTA);

        whiteButton.setOpaque(true);
        blueButton.setOpaque(true);
        magentaButton.setOpaque(true);

        toolBar.add(whiteButton);
        toolBar.add(blueButton);
        toolBar.add(magentaButton);

        // File 메뉴 기능
        ActionListener fileListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (e.getActionCommand()) {

                    case "New":
                        textArea.setText("");
                        break;

                    case "Open":
                        int result = fileChooser.showOpenDialog(
                                MenuTest.this
                        );

                        if (result == JFileChooser.APPROVE_OPTION) {
                            try {
                                String content = Files.readString(
                                        fileChooser.getSelectedFile().toPath(),
                                        StandardCharsets.UTF_8
                                );

                                textArea.setText(content);

                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(
                                        MenuTest.this,
                                        "텍스트 파일을 열 수 없습니다.\n"
                                                + ex.getMessage(),
                                        "파일 열기 오류",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        }
                        break;

                    case "Close":
                        dispose();
                        break;
                }
            }
        };

        // File 메뉴에 기능 연결
        newMenuItem.addActionListener(fileListener);
        openMenuItem.addActionListener(fileListener);
        closeMenuItem.addActionListener(fileListener);

        // 파일 버튼에 기능 연결
        newButton.setActionCommand("New");
        openButton.setActionCommand("Open");
        closeButton.setActionCommand("Close");

        newButton.addActionListener(fileListener);
        openButton.addActionListener(fileListener);
        closeButton.addActionListener(fileListener);

        // Edit 메뉴에 색상 변경 기능 연결
        whiteMenuItem.addActionListener(backgroundListener);
        blueMenuItem.addActionListener(backgroundListener);
        magentaMenuItem.addActionListener(backgroundListener);

        // 색상 버튼에 기능 연결
        whiteButton.setActionCommand("White");
        blueButton.setActionCommand("Blue");
        magentaButton.setActionCommand("Magenta");

        whiteButton.addActionListener(backgroundListener);
        blueButton.addActionListener(backgroundListener);
        magentaButton.addActionListener(backgroundListener);

        // 기본 창 설정
        setTitle("메뉴 만들기");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // 배경색 변경
    ActionListener backgroundListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            Color color = Color.WHITE;

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

            // 파란색 배경에서는 글자를 흰색으로 표시
            if (color.equals(Color.BLUE)) {
                textArea.setForeground(Color.WHITE);
            } else {
                textArea.setForeground(Color.BLACK);
            }
        }
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuTest::new);
    }
}
