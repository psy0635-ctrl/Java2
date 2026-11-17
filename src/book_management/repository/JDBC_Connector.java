package book_management.repository;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** 공유용 연결 설정: 개인 계정은 config/db.properties에 직접 입력한다. */
public class JDBC_Connector {
    public static Connection getConnection() throws SQLException {
        Path configPath = Path.of(System.getProperty("book.db.config", "config/db.properties"));
        Properties config = new Properties();
        try (Reader reader = Files.newBufferedReader(configPath, StandardCharsets.UTF_8)) {
            config.load(reader);
        } catch (IOException e) {
            throw new SQLException("config/db.properties를 만들고 실행 작업 폴더를 프로젝트 루트로 지정하세요.", e);
        }
        String url = config.getProperty("db.url", "").trim();
        String user = config.getProperty("db.user", "").trim();
        String password = config.getProperty("db.password", "");
        if (url.isEmpty() || user.isEmpty() || password.isEmpty()) {
            throw new SQLException("db.url, db.user, db.password를 본인의 Oracle 접속 정보로 설정하세요.");
        }
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("lib/ojdbc11.jar를 프로젝트 라이브러리에 추가하세요.", e);
        }
        return DriverManager.getConnection(url, user, password);
    }
}
