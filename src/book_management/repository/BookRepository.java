package book_management.repository;

import book_management.domain.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookRepository {
    public ArrayList<Book> select(String searchWord, int selectedIndex) {
        ArrayList<Book> bookList = new ArrayList<>();
        String[] columnName = {"b.name", "p.publish_name", "b.author"};

        String sql = "select b.isbn, b.name, b.author, b.price, b.inventory, "
                + "c.category_name, p.publish_name from book b, categroty c, publish p "
                + "where b.category_id = c.category_id and b.publish_id = p.publish_id "
                + "and " + columnName[selectedIndex] + " like ?";

        try (Connection con = JDBC_Connector.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchWord + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setIsbn(rs.getString("isbn"));
                    book.setName(rs.getString("name"));
                    book.setAuthor(rs.getString("author"));
                    book.setPrice(rs.getInt("price"));
                    book.setInventory(rs.getInt("inventory"));
                    book.setCategory_name(rs.getString("category_name"));
                    book.setPublish_name(rs.getString("publish_name"));
                    bookList.add(book);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("도서 목록을 조회하지 못했습니다.", e);
        }

        return bookList;
    }

    public String[] selectCategoryNames() {
        return selectNames("select distinct trim(category_name) from categroty order by 1");
    }

    public String[] selectPublishNames() {
        return selectNames("select distinct trim(publish_name) from publish order by 1");
    }

    private String[] selectNames(String sql) {
        ArrayList<String> names = new ArrayList<>();
        try (Connection con = JDBC_Connector.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString(1);
                if (name != null) {
                    names.add(name);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("출판사 또는 카테고리 목록을 조회하지 못했습니다.", e);
        }
        return names.toArray(new String[0]);
    }

    public void insert(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("등록할 도서 정보가 없습니다.");
        }
        String isbn = book.getIsbn();
        String name = book.getName();
        if (isbn == null || isbn.isBlank() || name == null || name.isBlank()) {
            throw new IllegalArgumentException("ISBN과 도서명을 입력해 주세요.");
        }
        if (book.getPrice() < 0 || book.getInventory() < 0) {
            throw new IllegalArgumentException("가격과 재고량에 0 이상의 정수를 입력해 주세요.");
        }

        String sql = "insert into book (isbn, name, author, price, inventory, category_id, publish_id) "
                + "values (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = JDBC_Connector.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            int categoryId = findId(con, "select category_id from categroty where trim(category_name) = ?",
                    book.getCategory_name(), "카테고리");
            int publishId = findId(con, "select publish_id from publish where trim(publish_name) = ?",
                    book.getPublish_name(), "출판사");

            pstmt.setString(1, isbn.strip());
            pstmt.setString(2, name.strip());
            pstmt.setString(3, book.getAuthor());
            pstmt.setInt(4, book.getPrice());
            pstmt.setInt(5, book.getInventory());
            pstmt.setInt(6, categoryId);
            pstmt.setInt(7, publishId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                case 1 -> throw new IllegalArgumentException("이미 등록된 ISBN입니다.", e);
                case 12899 -> throw new IllegalArgumentException("ISBN, 도서명 또는 저자명이 저장 가능한 길이를 초과했습니다.", e);
                case 2291 -> throw new IllegalArgumentException("선택한 출판사 또는 카테고리를 다시 확인해 주세요.", e);
                default -> throw new IllegalStateException("도서를 저장하지 못했습니다.", e);
            }
        }
    }

    private int findId(Connection con, String sql, String name, String field) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, name == null ? null : name.strip());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalArgumentException("등록된 " + field + "을(를) 선택해 주세요.");
                }
                int id = rs.getInt(1);
                if (rs.next()) {
                    throw new IllegalArgumentException("같은 이름의 " + field + "이(가) 여러 개 등록되어 있습니다.");
                }
                return id;
            }
        }
    }
}
