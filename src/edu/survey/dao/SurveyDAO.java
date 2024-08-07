package edu.survey.dao;

import edu.survey.util.DBCon;
import edu.survey.vo.SurveyVO;

import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class SurveyDAO {
    public Connection con;  //mysql Database 접속 -Connection 객체 필요
    private Statement stmt; // 쿼리(변수 포함 X) 실행 객체- 실행하고 반드시 닫아야 한다.
    private PreparedStatement pstmt; // 쿼리(변수를 ?로 바인딩하는 경우) 실행 객체
    private ResultSet rs; //쿼리 실행 결과 레코드 집합 저장

    public SurveyDAO() {this.con= DBCon.getConnection();}

    //관리자 설문조사 등록
    public boolean insertAdmin(SurveyVO svo){
        String query=" INSERT INTO t_survey(title,one,two,start_date,end_date,reg_date) VALUES(?,?,?,?,?,NOW()) ";
        try{
            pstmt=con.prepareStatement(query);
            pstmt.setString(1, svo.getTitle());
            pstmt.setString(2, svo.getOne());
            pstmt.setString(3, svo.getTwo());
            pstmt.setString(4, svo.getStartDate());
            pstmt.setString(5, svo.getEndDate());
            int result=pstmt.executeUpdate();
            if(result==1){ return true; }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBCon.close(pstmt);
        }

        return false;
    }

    //관리자 설문조사 수정
    public boolean updateAdmin(SurveyVO svo){
        String query=" UPDATE t_survey SET  title=?,one=?,two=?,mod_date=NOW() WHERE survey_no=?";

        try {
            pstmt=con.prepareStatement(query);// 쿼리 실행 객체 생성- 미리 준비
            // 쿼리 물음표에 해당하는 값 바인딩 -> 알아보기
            pstmt.setString(1,svo.getTitle()); // 쿼리 물음표에 해당하는 값 바인딩 -> 알아보기
            pstmt.setString(2,svo.getOne());
            pstmt.setString(3,svo.getTwo());
            pstmt.setInt(4, svo.getSurveyNo());

            //DML(insert, delete, update)쿼리 모두 .executeUpdate ,sql의 ctrl+enter 기능을 함!!
            int result=pstmt.executeUpdate(); // DML 쿼리 실행 결과 행수 저장
            if(result==1){
                DBCon.close(pstmt);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //이 코드를 실행하거나 아래 코드를 실행하거나 둘 중 하나
        }finally { //예외 발생 여부 관계없이 항상 실행
            DBCon.close(pstmt);
        }
        return false; //레코드 추가에 실패하면 false 반환

    }

    //항목 투표수 업데이트
    public boolean updateCnt(int onetwo,int surveyNo){
        String query=" ";
        if(onetwo==1)
            query=" UPDATE t_survey SET  one_cnt=one_cnt+1 WHERE survey_no=?";
        else if(onetwo==2)
            query=" UPDATE t_survey SET  two_cnt=two_cnt+1 WHERE survey_no=?";

        try (Connection con = DBCon.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            // 바인딩: survey_no 값을 쿼리의 물음표에 설정
            pstmt.setInt(1, surveyNo);

            // DML 쿼리 실행
            int result = pstmt.executeUpdate();

            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }


    //관리자 설문 삭제
    public boolean deleteAdmin(int no){   //게시글 번호를 매개변수로 받아 레코드 삭제 ----
        String query = " DELETE FROM t_survey WHERE survey_no = ? ";

        try {
            pstmt = con.prepareStatement(query); // 쿼리 실행 객체 생성- 미리 준비
            pstmt.setInt(1, no); //쿼리의 물음표에 해당하는 값 바인딩

            int result = pstmt.executeUpdate(); // DML 쿼리 실행 결과 행 수 저장
            if( result == 1) {
                DBCon.close(pstmt);
                return true;         //레코드 변경에 성공하면 true,
            }
        } catch (SQLException e) {  throw new RuntimeException(e);
        } finally {                 DBCon.close(pstmt);             }
        return false;
    }
    
    //관리자 설문 조회
    public SurveyVO selectAdmin(int no){//매개변수로 넘겨받은 게시글번호 레코드 하나 조회 ---------
        String query=" SELECT * FROM t_survey WHERE survey_no= ? ";
        SurveyVO svo=null;

        try{
            pstmt=con.prepareStatement(query); //쿼리문 미리 준비
            pstmt.setInt(1, no); //쿼리의 물음표에 해당하는 값 바인딩
            rs=pstmt.executeQuery(); // 쿼리 실행하고 결과 레코드 받기 SELECT는 ..executeQuery()!!

            if(rs.next()){
                svo=new SurveyVO(); //svo 객체를 생성하여 읽어온 값들을 각 필드에 저장
                svo.setSurveyNo(rs.getInt("survey_no"));
                svo.setTitle(rs.getString("title"));
                svo.setOne(rs.getString("one"));
                svo.setTwo(rs.getString("two"));
                svo.setOneCnt(rs.getInt("one_cnt"));
                svo.setTwoCnt(rs.getInt("two_cnt"));
                svo.setStartDate(rs.getString("start_date"));
                svo.setEndDate(rs.getString("end_date"));
                svo.setRegDate(rs.getDate("reg_date"));
                svo.setModDate(rs.getDate("mod_date"));
                
            }
        } catch (SQLException e) {throw new RuntimeException(e);
        }finally { DBCon.close(pstmt); }
        return svo;
    }

    //회원 설문 조회
    public SurveyVO select(int no){//매개변수로 넘겨받은 게시글번호 레코드 하나 조회 ---------
        String query=" SELECT * FROM t_survey WHERE survey_no= ? ";
        SurveyVO svo=null;

        try{
            pstmt=con.prepareStatement(query); //쿼리문 미리 준비
            pstmt.setInt(1, no); //쿼리의 물음표에 해당하는 값 바인딩
            rs=pstmt.executeQuery(); // 쿼리 실행하고 결과 레코드 받기 SELECT는 ..executeQuery()!!

            if(rs.next()){
                svo=new SurveyVO(); //svo 객체를 생성하여 읽어온 값들을 각 필드에 저장
                svo.setSurveyNo(rs.getInt("survey_no"));
                svo.setTitle(rs.getString("title"));
                svo.setOne(rs.getString("one"));
                svo.setTwo(rs.getString("two"));

            }
        } catch (SQLException e) {throw new RuntimeException(e);
        }finally { DBCon.close(pstmt); }
        return svo;
    }
    //관리자 설문 목록
    public List<SurveyVO> selectAll(){//t_board 테이블의 모든 레코드 조회 ---List로 모든 목록 반환
        String query=" SELECT * FROM t_survey ";
        List<SurveyVO> list=new ArrayList<>();

        try{
            pstmt=con.prepareStatement(query);
            rs=pstmt.executeQuery(); // 쿼리 실행하고 결과 레코드 받기 SELECT는 ..executeQuery()!!

            while(rs.next()){ //더이상 읽을 값이 없을 떄까지 반복
                SurveyVO svo =new SurveyVO(); //bvo 객체를 생성하여 읽어온 값들을 각 필드에 저장
                svo.setSurveyNo(rs.getInt("survey_no"));
                svo.setTitle(rs.getString("title"));
                svo.setOne(rs.getString("one"));
                svo.setTwo(rs.getString("two"));
                svo.setOneCnt(rs.getInt("one_cnt"));
                svo.setTwoCnt(rs.getInt("two_cnt"));


                list.add(svo);  //생성된 bvo 객체를 list에 저장
            }
        } catch (SQLException e) { throw new RuntimeException(e);
        }finally {DBCon.close(rs,pstmt);}
        return list;
    }

}
