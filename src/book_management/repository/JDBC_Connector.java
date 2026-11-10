package book_management.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC_Connector {
    private static final String DRIVER_PATH ="oracle.jdbc.driver.OracleDriver";
    private static final String URL ="jdbc:oracle:thin:@//localhost:1521/xe";
    private static final String USER_NAME ="C##AISOFT";
    private static final String PASSWORD ="1234";
    private static Connection con;
    public static Connection getConnection(){

        try{
            // 1. JDBC DRIVER MEMORY LOADING
            Class.forName(DRIVER_PATH);
            System.out.println("JDBC Driver Class founded");
            // 2. CONNECTION
            con = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
            System.out.println("Connection succeeded");
        }catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver Class Not Found");
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
        return con;
    }
    public static void main(String[] args) {
        getConnection();
    }
}
