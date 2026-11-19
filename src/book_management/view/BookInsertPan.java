package book_management.view;

import book_management.domain.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;


public class BookInsertPan extends JPanel {
    JTable table;
    DefaultTableModel model;
    private ArrayList<Book> bookList = new ArrayList<>();
    String[] tableheader = {"ISBN", "도서명", "저자명", "출판사명", "가격", "재고량", "카테고리"};
    String[] header = {"ISBN", "도서명", "저자명", "가격", "재고량", "출판사명", "카테고리"};
    JLabel[] lbls = new JLabel[tableheader.length];
    JTextField[] tfs = new JTextField[tableheader.length-2];
    JComboBox<String> comboCategory, comboPublish;
    JButton btnInsert = new JButton("도서 추가");
    JPanel pans = new JPanel(new GridLayout(4, 4,20,2));

    public BookInsertPan() {
        setLayout(new BorderLayout());

        comboCategory = new JComboBox<>();
        comboPublish = new JComboBox<>();

        for(int i = 0; i < tableheader.length; i++){
            lbls[i] = new JLabel(header[i]);
            pans.add(lbls[i]);
            if(i < tableheader.length-2){
                tfs[i] = new JTextField();
                pans.add(tfs[i]);
            } else if (i == 5) {
                pans.add(comboPublish);
            } else{
                pans.add(comboCategory);
            }
        }

        JLabel emptyLbl = new JLabel("");
        pans.add(emptyLbl);
        pans.add(btnInsert);
    }

    public void initView() {
        model = new DefaultTableModel(tableheader, bookList.size()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        TableColumnModel tcm = table.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(50);
        tcm.getColumn(1).setPreferredWidth(200);
        tcm.getColumn(2).setPreferredWidth(150);
        tcm.getColumn(3).setPreferredWidth(100);
        tcm.getColumn(4).setPreferredWidth(50);
        tcm.getColumn(5).setPreferredWidth(100);
        tcm.getColumn(6).setPreferredWidth(50);

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);
        putSearchResult();

        add(pans, BorderLayout.SOUTH);
    }

    public void putSearchResult() {
        model.setRowCount(bookList.size());

        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            model.setValueAt(book.getIsbn(), i, 0);
            model.setValueAt(book.getName(), i, 1);
            model.setValueAt(book.getAuthor(), i, 2);
            model.setValueAt(book.getPublish_name(), i, 3);
            model.setValueAt(book.getPrice(), i, 4);
            model.setValueAt(book.getInventory(), i, 5);
            model.setValueAt(book.getCategory_name(), i, 6);
        }
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    public void setOptions(String[] publishNames, String[] categoryNames) {
        comboPublish.setModel(new DefaultComboBoxModel<>(publishNames));
        comboCategory.setModel(new DefaultComboBoxModel<>(categoryNames));
    }

    public JButton getBtnInsert() {
        return btnInsert;
    }

    public Book getBook() {
        Book book = new Book();
        book.setIsbn(tfs[0].getText().strip());
        book.setName(tfs[1].getText().strip());
        book.setAuthor(tfs[2].getText().strip());
        book.setPrice(readInt(3));
        book.setInventory(readInt(4));
        book.setPublish_name((String) comboPublish.getSelectedItem());
        book.setCategory_name((String) comboCategory.getSelectedItem());
        return book;
    }

    private int readInt(int fieldIndex) {
        try {
            return Integer.parseInt(tfs[fieldIndex].getText().strip());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(header[fieldIndex] + "에 0 이상의 정수를 입력해 주세요.", e);
        }
    }

    public void clearForm() {
        for (JTextField field : tfs) {
            field.setText("");
        }
        tfs[0].requestFocusInWindow();
    }
}
