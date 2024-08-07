package edu.survey.util;

import java.sql.*;

public class DBCon {
    //1. 데이터베이스 연결 공유 객체를 클래스 외부에서 접근할 수 없도록 선언
    private static Connection con;  //mysql Database 접속 -Connection 객체 필요
    private Statement stmt; // 쿼리 실행 객체- 실행하고 반드시 닫아야 한다.

    //2. 기본생성자를 선언하여 클래스 외부에서 접근할 수 없도록 처리
    private DBCon() {}

    //3. 필드로 선언된 객체를 반환하는 클래스 메서드 getConnection 작성
    // 단, 필드가 null인 경우에는 객체를 생성하여 반환하도록 처리

    // 반드시 static을 붙여야 Conntection 클래스를 사용할 수 있다.
    public static Connection getConnection() { // 클래스 메서드= 공유 메서드
        if(con == null) {
            String url = "jdbc:mysql://localhost:3306/modeldb"; //mysql 접속 정보 및 스키마(DB) 연결
            String username = "root"; //mysql 접속 계정
            String password = "1250"; //mysql 접속 계정의 비밀번호

            try {
                con = DriverManager.getConnection(url, username, password); // 객체 생성
                System.out.println("survey con ok!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return con;
    }

    //**4~7은 모두 공유 메서드로 작성
    //4. ResultSet 객체와 PreparedStatement 객체를 매개변수로 받아서 닫는 close 메서드 작성
    public static void close(ResultSet rs, PreparedStatement pstmt) {
        try { //객체 생성 역순으로 닫음
            if(rs != null)  rs.close(); //쿼리 실행 객체 닫기
            if(pstmt != null)  pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //5. ResultSet 객체와 Statement 객체를 매개변수로 받아서 닫는 close 메서드 작성
    public static void close(ResultSet rs, Statement stmt) {
        try {
            if(rs != null)  rs.close(); //쿼리 실행 객체 닫기
            if(stmt != null)  stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //6. PrepardeStatement 객체를 매개변수로 받아서 닫는 close 메서드 오버로딩
    public static void close(PreparedStatement pstmt) {
        try {
            //쿼리 실행 객체 닫기
            if(pstmt != null)  pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //7. 필드로 선언된 객체를 닫는 close 메서드 오버로딩
    public static void close() {
        try {
            if(con != null)  con.close(); //쿼리 실행 객체 닫기
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
