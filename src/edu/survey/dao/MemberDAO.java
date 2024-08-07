package edu.survey.dao;

import edu.survey.util.DBCon;
import edu.survey.vo.MemberVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    public Connection con;  //mysql Database 접속 -Connection 객체 필요
    private Statement stmt; // 쿼리(변수 포함 X) 실행 객체- 실행하고 반드시 닫아야 한다.
    private PreparedStatement pstmt; // 쿼리(변수를 ?로 바인딩하는 경우) 실행 객체
    private ResultSet rs; //쿼리 실행 결과 레코드 집합 저장

    //기본생성자에서
    //DBCon의 Connection 객체를 반환하는메서드를 이용하여 con객체 받아오기
    public MemberDAO() {this.con= DBCon.getConnection(); }

    //회원 여부 -- 아이디만 필요
    //아이디 있으면 -->true, 아이디 없으면 --> false
    public boolean checkM(String id) {
        String query= " SELECT COUNT(*) FROM t_member WHERE mid='"+id+"'" ;
        boolean flag=false;
        MemberVO mvo=null;

        try{
            stmt=con.createStatement();
            rs=stmt.executeQuery(query); // 쿼리 실행하고 결과 레코드 받기 SELECT는 ..executeQuery()!!

            if(rs.next()){
                if(rs.getInt(1)>0)  flag=true;
            }
        } catch (SQLException e) {throw new RuntimeException(e);
        }finally { DBCon.close(pstmt); }
        return flag;
    }

    //로그인한 회원 여부 -- 아이디, 패스워드
    //로그인성공 --> true, 로그인 실패 --> false
    public boolean loginM(String id,String pw ) {
        String query= " SELECT * FROM t_member WHERE mid= ? and mpw= ? " ;

        try{
            pstmt=con.prepareStatement(query); //쿼리문 미리 준비
            pstmt.setString(1, id); //쿼리의 물음표에 해당하는 값 바인딩
            pstmt.setString(2, pw);
            rs=pstmt.executeQuery(); // 쿼리 실행하고 결과 레코드 받기 SELECT는 ..executeQuery()!!

            //int result = pstmt.executeQuery(); // DML 쿼리 실행 결과 행 수 저장
            if( rs.next()) {
                DBCon.close(pstmt);
                return true;         //레코드 변경에 성공하면 true,
            }
        } catch (SQLException e) {  throw new RuntimeException(e);
        } finally {                 DBCon.close(pstmt);             }
        return false;
    }


    //회원 가입

    // 레코드 추가에 성공하면 true, 그렇지 않으면 fasle 반환
    public boolean insert(MemberVO mvo){ //t_member 테이블에 레코드 추가 ---------

        String query= " INSERT INTO t_member VALUES(?,?,?,?,?,?,?,NOW()) " ;

        try {
            pstmt = con.prepareStatement(query); // 쿼리 실행 객체 생성- 미리 준비
            pstmt.setString(1,mvo.getMid()); // 쿼리 물음표에 해당하는 값 바인딩 -> 알아보기
            pstmt.setString(2,mvo.getMname());
            pstmt.setString(3, mvo.getMpw());
            pstmt.setString(4, mvo.getEmail());
            pstmt.setString(5,mvo.getGender());
            pstmt.setString(6,mvo.getPhoto());
            pstmt.setString(7,mvo.getBirth_date());

            int result = pstmt.executeUpdate(); // DML 쿼리 실행 결과 행 수 저장
            if( result == 1) {
                DBCon.close(pstmt);
                return true;         //레코드 변경에 성공하면 true,
            }
        } catch (SQLException e) {  throw new RuntimeException(e);
        } finally {                 DBCon.close(pstmt);             }
        return false;

    }

    //회원 수정
    public boolean update(MemberVO mvo){ //t_member 테이블의 회원아이디가 bbb인 레코드 변경---------
        //이메일: bbb@bbb.com , 사진 : default.png
        String query=" UPDATE t_member SET  mname=?, email=? , photo=? WHERE mid=?";
       // MemberVO mvo=null;
        try {
            pstmt=con.prepareStatement(query);// 쿼리 실행 객체 생성- 미리 준비
            // 쿼리 물음표에 해당하는 값 바인딩 -> 알아보기
            pstmt.setString(1,mvo.getMname());
            pstmt.setString(2,mvo.getEmail()); // 쿼리 물음표에 해당하는 값 바인딩 -> 알아보기
            pstmt.setString(3,mvo.getPhoto());
            pstmt.setString(4, mvo.getMid());

            //DML(insert, delete, update)쿼리 모두 .executeUpdate ,sql의 ctrl+enter 기능을 함!!
            int result=pstmt.executeUpdate(); // DML 쿼리 실행 결과 행수 저장
            if(result==1){
                DBCon.close(pstmt); // 왜 여기서 닫지???
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //이 코드를 실행하거나 아래 코드를 실행하거나 둘 중 하나
        }finally { //예외 발생 여부 관계없이 항상 실행
            DBCon.close(pstmt);
        }
        return false; //레코드 추가에 실패하면 false 반환


    }

    //회원 삭제
    public boolean delete(String id){   //회원아이디를 매개변수로 받아 레코드 삭제 ----
        //query=" DELETE FROM t_member WHERE mid="+id;<- 이거는 id가 문자가 된다. 변수로 만들어야 함!!!
        //DELETE FROM t_member WHERE mid=bbb 이렇게 만들어짐
        //DELETE FROM t_member WHERE mid='bbb' 우리는 이렇게 만들어야 함!!
        String query = " DELETE FROM t_member WHERE mid = ? ";

        try {
            pstmt = con.prepareStatement(query); // 쿼리 실행 객체 생성- 미리 준비
            pstmt.setString(1, id); //쿼리의 물음표에 해당하는 값 바인딩

            int result = pstmt.executeUpdate(); // DML 쿼리 실행 결과 행 수 저장
            if( result == 1) {
                DBCon.close(pstmt);
                return true;         //레코드 변경에 성공하면 true,
            }
        } catch (SQLException e) {  throw new RuntimeException(e);
        } finally {                 DBCon.close(pstmt);             }
        return false;
    }
    //회원 조회
    //    private String mid; //회원아이디 mid vatchar(20) PK
    //    private String mname; //회원이름 mname varchar(20)
    //    private String mpw; //비밀번호 mpw varchar(20)
    //    private String email; //이메일 email varchar(50)
    //    private String gender; //성별 gender char(1)
    //    private String photo; //사진 photo varchar(50)
    //    private String birth_date; //생년월일 birth_date date
    //    private Date join_date;
    public MemberVO select(String id){//매개변수로 넘겨받은 회원아이디가 레코드 하나 조회 ---------
        String query=" SELECT * FROM t_member WHERE mid= ? ";
        MemberVO mvo=null;

        try{
            pstmt=con.prepareStatement(query); //쿼리문 미리 준비
            pstmt.setString(1, id); //쿼리의 물음표에 해당하는 값 바인딩
            rs=pstmt.executeQuery(); // 쿼리 실행하고 결과 레코드 받기 SELECT는 ..executeQuery()!!

            if(rs.next()){
                mvo=new MemberVO(); //mvo 객체를 생성하여 읽어온 값들을 각 필드에 저장
                mvo.setMid(rs.getString("mid"));
                mvo.setMname(rs.getString("mname"));
                mvo.setMpw(rs.getString("mpw"));
                mvo.setEmail(rs.getString("email"));
                mvo.setGender(rs.getString("gender"));
                mvo.setPhoto(rs.getString("photo"));
                mvo.setBirth_date(rs.getString("birth_date"));
                mvo.setJoin_date(rs.getDate("join_date"));

            }
        } catch (SQLException e) {throw new RuntimeException(e);
        }finally { DBCon.close(pstmt); }
        return mvo;
    }

    //회원 목록

    public List<MemberVO> selectAll(){//t_member 테이블의 모든 레코드 조회 ---List로 모든 목록 반환
        String query=" SELECT * FROM t_member ";
        List<MemberVO> list=new ArrayList<>();

        try{
            pstmt=con.prepareStatement(query);
            rs=pstmt.executeQuery(); // 쿼리 실행하고 결과 레코드 받기 SELECT는 ..executeQuery()!!

            while(rs.next()){ //더이상 읽을 값이 없을 떄까지 반복
                MemberVO mvo=new MemberVO(); //bvo 객체를 생성하여 읽어온 값들을 각 필드에 저장
                mvo.setMid(rs.getString("mid"));
                mvo.setMname(rs.getString("mname"));
                mvo.setEmail(rs.getString("email"));
                mvo.setJoin_date(rs.getDate("join_date"));

                list.add(mvo);  //생성된 mvo 객체를 list에 저장
            }
        } catch (SQLException e) { throw new RuntimeException(e);
        }finally {DBCon.close(rs,pstmt);}
        return list;
    }
}
