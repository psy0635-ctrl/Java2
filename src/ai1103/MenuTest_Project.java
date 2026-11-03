package ai1103;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MenuTest_Project extends JFrame {

    private static final long serialVersionUID = 1L;

    private Path currentFile;
    private String savedText = "";

    JTextArea textArea = new JTextArea();
    JFileChooser fileChooser = new JFileChooser();

    public MenuTest_Project() {

        fileChooser.setFileFilter(
                new FileNameExtensionFilter("텍스트 파일 (*.txt)", "txt"));

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
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        JMenuItem closeMenuItem = new JMenuItem("Close");

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
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
        JButton newButton = createIconButton("new.png", "New");
        JButton openButton = createIconButton("open.png", "Open");
        JButton closeButton = createIconButton("close.png", "Close");

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
        whiteButton.setToolTipText("White");
        blueButton.setOpaque(true);
        blueButton.setToolTipText("Blue");
        magentaButton.setOpaque(true);
        magentaButton.setToolTipText("Magenta");

        toolBar.add(whiteButton);
        toolBar.add(blueButton);
        toolBar.add(magentaButton);

        // File 메뉴 기능
        ActionListener fileListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (e.getActionCommand()) {

                    case "New":
                        if (confirmSaveChanges()) {
                            textArea.setText("");
                            currentFile = null;
                            savedText = "";
                            updateTitle();
                        }
                        break;

                    case "Open":
                        openFile();
                        break;

                    case "Save":
                        saveFile(false);
                        break;

                    case "Save As":
                        saveFile(true);
                        break;

                    case "Close":
                        closeWindow();
                        break;
                }
            }
        };

        // File 메뉴에 기능 연결
        newMenuItem.addActionListener(fileListener);
        openMenuItem.addActionListener(fileListener);
        saveMenuItem.addActionListener(fileListener);
        saveAsMenuItem.addActionListener(fileListener);
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
        updateTitle();
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });

        setVisible(true);
    }

    // 실행 폴더 아래의 toolbar 폴더에서 아이콘을 불러옵니다.
    private JButton createIconButton(String fileName, String command) {
        ImageIcon icon = new ImageIcon("toolbar/" + fileName);
        JButton button;

        if (icon.getIconWidth() > 0) {
            Image image = icon.getImage().getScaledInstance(
                    32, 32, Image.SCALE_SMOOTH);
            button = new JButton(new ImageIcon(image));
        } else {
            // 경로가 잘못되었을 때는 기능 이름을 표시합니다.
            button = new JButton(command);
        }

        button.setToolTipText(command);
        return button;
    }

    private void updateTitle() {
        String name = currentFile == null
                ? "제목 없음" : currentFile.getFileName().toString();

        setTitle("간단한 메모장 - " + name);
    }

    private void openFile() {
        if (!confirmSaveChanges()) {
            return;
        }

        if (fileChooser.showOpenDialog(this)
                != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path selected = fileChooser.getSelectedFile().toPath();

        try {
            // 읽기에 성공한 다음에만 기존 문서를 바꿉니다.
            String content = Files.readString(
                    selected, StandardCharsets.UTF_8);

            textArea.setText(content);
            currentFile = selected;
            savedText = content;
            updateTitle();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "텍스트 파일을 열 수 없습니다.\n" + ex.getMessage(),
                    "파일 열기 오류",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // 저장 성공은 true, 취소하거나 실패하면 false를 반환합니다.
    private boolean saveFile(boolean saveAs) {
        Path target = currentFile;

        if (saveAs || target == null) {
            if (fileChooser.showSaveDialog(this)
                    != JFileChooser.APPROVE_OPTION) {
                return false;
            }

            target = fileChooser.getSelectedFile().toPath();
            String name = target.getFileName().toString();

            if (!name.toLowerCase(Locale.ROOT).endsWith(".txt")) {
                target = target.resolveSibling(name + ".txt");
            }

            if (Files.exists(target)) {
                int answer = JOptionPane.showConfirmDialog(
                        this,
                        "같은 이름의 파일이 있습니다. 덮어쓰시겠습니까?\n"
                                + target,
                        "덮어쓰기 확인",
                        JOptionPane.YES_NO_OPTION
                );

                if (answer != JOptionPane.YES_OPTION) {
                    return false;
                }
            }
        }

        try {
            Files.writeString(
                    target,
                    textArea.getText(),
                    StandardCharsets.UTF_8
            );

            currentFile = target;
            savedText = textArea.getText();
            updateTitle();
            return true;

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "파일을 저장할 수 없습니다.\n" + ex.getMessage(),
                    "파일 저장 오류",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    private boolean confirmSaveChanges() {
        if (textArea.getText().equals(savedText)) {
            return true;
        }

        int answer = JOptionPane.showConfirmDialog(
                this,
                "변경한 내용을 저장하시겠습니까?",
                "저장 확인",
                JOptionPane.YES_NO_CANCEL_OPTION
        );

        if (answer == JOptionPane.YES_OPTION) {
            return saveFile(false);
        }

        return answer == JOptionPane.NO_OPTION;
    }

    private void closeWindow() {
        if (confirmSaveChanges()) {
            dispose();
        }
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

            // 파란색 배경에서는 글자와 커서를 흰색으로 표시
            if (color.equals(Color.BLUE)) {
                textArea.setForeground(Color.WHITE);
                textArea.setCaretColor(Color.WHITE);
            } else {
                textArea.setForeground(Color.BLACK);
                textArea.setCaretColor(Color.BLACK);
            }
        }
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuTest_Project::new);
    }
}