import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTest {
    private Connection con; //DB 연결 객체
    private Statement stmt; // 쿼리 실행 객체

    public JDBCTest() {
        String url="jdbc:mysql://localhost:3306/modeldb"; //mysql 접속 정보 및 스키마(DB) 연결
        String username="root"; //mysql 접속 계정
        String password="1250"; //mysql 접속 계정의 비밀번호

        try {
            //Connection 객체 생성 - DriverManager이용
            con= DriverManager.getConnection(url,username,password);
            System.out.println("con ok!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        JDBCTest jt=new JDBCTest();

    }
}
