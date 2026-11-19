package book_management.controller;

import book_management.domain.Book;
import book_management.repository.BookRepository;
import book_management.view.BookInsertPan;
import book_management.view.BookSearchPan;
import book_management.view.center.SetFrame;

import javax.swing.*;
import java.util.ArrayList;

public class BookController extends JFrame {
    private static final System.Logger LOGGER = System.getLogger(BookController.class.getName());
    private final BookRepository repository = new BookRepository();
    private final BookSearchPan searchPan = new BookSearchPan();
    private final BookInsertPan insertPan = new BookInsertPan();

    public BookController() {
        ArrayList<Book> bookList = loadSearchResults();
        searchPan.setBookList(bookList);
        searchPan.initView();

        insertPan.setOptions(repository.selectPublishNames(), repository.selectCategoryNames());
        insertPan.setBookList(bookList);
        insertPan.initView();

        searchPan.getBtnSearch().addActionListener(e -> searchBooks());
        insertPan.getBtnInsert().addActionListener(e -> insertBook());

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.add("도서 검색", searchPan);
        tabbedPane.add("도서 추가", insertPan);
        add(tabbedPane);

        SetFrame.setFrame(this, "도서관리 시스템", 700, 600);
    }

    private ArrayList<Book> loadSearchResults() {
        return repository.select(searchPan.getSearchWord(), searchPan.getCombo().getSelectedIndex());
    }

    private void refreshSearchResults() {
        searchPan.setBookList(loadSearchResults());
        searchPan.putSearchResult();
    }

    private void searchBooks() {
        try {
            refreshSearchResults();
        } catch (IllegalStateException e) {
            LOGGER.log(System.Logger.Level.ERROR, e.getMessage(), e);
            JOptionPane.showMessageDialog(this, e.getMessage(), "조회 오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertBook() {
        try {
            Book book = insertPan.getBook();
            repository.insert(book);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "등록 확인", JOptionPane.WARNING_MESSAGE);
            return;
        } catch (IllegalStateException e) {
            LOGGER.log(System.Logger.Level.ERROR, e.getMessage(), e);
            JOptionPane.showMessageDialog(this, e.getMessage(), "등록 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        insertPan.clearForm();
        try {
            insertPan.setBookList(repository.select("", 0));
            insertPan.putSearchResult();
            refreshSearchResults();
        } catch (IllegalStateException e) {
            LOGGER.log(System.Logger.Level.ERROR, e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "도서는 등록되었지만 목록을 새로 고치지 못했습니다.",
                    "조회 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "도서가 등록되었습니다.", "등록 완료", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new BookController();
            } catch (IllegalStateException e) {
                LOGGER.log(System.Logger.Level.ERROR, e.getMessage(), e);
                JOptionPane.showMessageDialog(null, e.getMessage(), "실행 오류", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
