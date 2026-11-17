package book_management.repository;

import book_management.domain.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookRepository {
    ArrayList<Book> bookList;

    public ArrayList<Book> select(String searchWord, int selectedIndex) {
        bookList = new ArrayList<>();
        String[] columnName = {"b.name", "p.publish_name", "b.author"};
        ResultSet rs = null;

        String sql = "select b.isbn, b.name, b.author, b.price, b.inventory, "
                + "c.category_name, p.publish_name from book b, categroty c, publish p "
                + "where b.category_id = c.category_id and b.publish_id = p.publish_id "
                + "and " + columnName[selectedIndex] + " like ?";

        try (Connection con = JDBC_Connector.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchWord + "%");
            rs = pstmt.executeQuery();

            Book book = null;
            while (rs.next()) {
                book = new Book();
                book.setIsbn(rs.getString("isbn"));
                book.setName(rs.getString("name"));
                book.setAuthor(rs.getString("author"));
                book.setPrice(rs.getInt("price"));
                book.setInventory(rs.getInt("inventory"));
                book.setCategory_name(rs.getString("category_name"));
                book.setPublish_name(rs.getString("publish_name"));
                bookList.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bookList;
    }
}
