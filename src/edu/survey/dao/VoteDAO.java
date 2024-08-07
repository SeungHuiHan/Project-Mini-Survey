package edu.survey.dao;

import edu.survey.util.DBCon;
import edu.survey.vo.VoteVO;
import edu.survey.vo.VoteVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoteDAO {
    public Connection con;  //mysql Database 접속 -Connection 객체 필요
    private Statement stmt; // 쿼리(변수 포함 X) 실행 객체- 실행하고 반드시 닫아야 한다.
    private PreparedStatement pstmt; // 쿼리(변수를 ?로 바인딩하는 경우) 실행 객체
    private ResultSet rs; //쿼리 실행 결과 레코드 집합 저장

    public VoteDAO() {this.con= DBCon.getConnection();}

    //설문 참여
    public boolean insertVote(VoteVO vvo){
        String query=" INSERT INTO t_vote(survey_no,id,one_two,vote_date) VALUES(?,?,?,NOW()) ";
        try{
            pstmt=con.prepareStatement(query);

            pstmt.setInt(1, vvo.getSurveyNo());
            pstmt.setString(2, vvo.getId());
            pstmt.setInt(3, vvo.getOneTwo());
            //pstmt.setDate(4, (Date) vvo.getVoteDate());
            int result=pstmt.executeUpdate();
            if(result==1){ return true; }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBCon.close(pstmt);
        }

        return false;
    }

//    //설문 응답 항목 업데이트 항목 1에 참여 '1', 항목 2에 참여 '2',
//    public boolean updatVote(VoteVO vvo){
//        String query=" UPDATE t_vote SET  one_two=? WHERE id=? ";
//
//        try {
//            pstmt=con.prepareStatement(query);// 쿼리 실행 객체 생성- 미리 준비
//            // 쿼리 물음표에 해당하는 값 바인딩 -> 알아보기
//            pstmt.setInt(1,vvo.getOneTwo()); // 쿼리 물음표에 해당하는 값 바인딩 -> 알아보기
//            pstmt.setString(2,vvo.getId());
//
//            //DML(insert, delete, update)쿼리 모두 .executeUpdate ,sql의 ctrl+enter 기능을 함!!
//            int result=pstmt.executeUpdate(); // DML 쿼리 실행 결과 행수 저장
//            if(result==1){
//                DBCon.close(pstmt);
//                return true;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e); //이 코드를 실행하거나 아래 코드를 실행하거나 둘 중 하나
//        }finally { //예외 발생 여부 관계없이 항상 실행
//            DBCon.close(pstmt);
//        }
//        return false; //레코드 추가에 실패하면 false 반환
//
//    }

//private int surveyNo;//  `SURVEY_NO` int NOT NULL,
//    private String id;//  `ID` varchar(20) NOT NULL,
//    private int oneTwo;//  `ONE_TWO` int DEFAULT NULL,
//    private Date voteDate

    // 설문 참여 여부 확인
    public VoteVO select(String id,int no ) {
        String query= " SELECT * FROM t_vote WHERE survey_no= ? and id= ? " ;
        VoteVO  vvo=null;
        try{
            pstmt=con.prepareStatement(query); //쿼리문 미리 준비
            pstmt.setInt(1, no); //쿼리의 물음표에 해당하는 값 바인딩
            pstmt.setString(2, id);
            rs=pstmt.executeQuery(); // 쿼리 실행하고 결과 레코드 받기 SELECT는 ..executeQuery()!!

            //int result = pstmt.executeQuery(); // DML 쿼리 실행 결과 행 수 저장
            if( rs.next()) {
                vvo=new VoteVO();
                vvo.setSurveyNo(rs.getInt("survey_no"));
                vvo.setId(rs.getString("id"));
                vvo.setOneTwo(rs.getInt("one_two"));
                vvo.setVoteDate(rs.getDate("vote_date"));

            }
        } catch (SQLException e) {  throw new RuntimeException(e);
        } finally {                 DBCon.close(pstmt);             }
        return vvo;
    }

    //설문참여 조회
//    public VoteVO select(String id){//매개변수로 넘겨받은 회원아이디가 레코드 하나 조회 ---------
//        String query=" SELECT * FROM t_member WHERE mid= ? ";
//        VoteVO vvo=null;
//
//        try{
//            pstmt=con.prepareStatement(query); //쿼리문 미리 준비
//            pstmt.setString(1, id); //쿼리의 물음표에 해당하는 값 바인딩
//            rs=pstmt.executeQuery(); // 쿼리 실행하고 결과 레코드 받기 SELECT는 ..executeQuery()!!
//
//            if(rs.next()){
//                vvo=new VoteVO(); //vvo 객체를 생성하여 읽어온 값들을 각 필드에 저장
//                vvo.setMid(rs.getString("mid"));
//                vvo.setMname(rs.getString("mname"));
//                vvo.setMpw(rs.getString("mpw"));
//                vvo.setEmail(rs.getString("email"));
//                vvo.setGender(rs.getString("gender"));
//                vvo.setPhoto(rs.getString("photo"));
//                vvo.setBirth_date(rs.getString("birth_date"));
//                vvo.setJoin_date(rs.getDate("join_date"));
//
//            }
//        } catch (SQLException e) {throw new RuntimeException(e);
//        }finally { DBCon.close(pstmt); }
//        return vvo;
//    }

    //모든 설문 참여 여부 확인
    public List<VoteVO> selectAll(String id){//t_board 테이블의 모든 레코드 조회 ---List로 모든 목록 반환
        String query=" SELECT * FROM t_vote WHERE id= ? ";
        List<VoteVO> list=new ArrayList<>();
        VoteVO vvo=null;

        try{
            pstmt=con.prepareStatement(query);
            pstmt.setString(1,id);
            rs=pstmt.executeQuery(); // 쿼리 실행하고 결과 레코드 받기 SELECT는 ..executeQuery()!!

            while(rs.next()){ //더이상 읽을 값이 없을 떄까지 반복
                vvo =new VoteVO(); //bvo 객체를 생성하여 읽어온 값들을 각 필드에 저장

                vvo.setId(rs.getString("id"));
                vvo.setSurveyNo(rs.getInt("survey_no"));
                vvo.setOneTwo(rs.getInt("one_two"));
                //vvo.setVoteDate(rs.getDate("voteDate"));
                list.add(vvo);  //생성된 bvo 객체를 list에 저장
            }
        } catch (SQLException e) { throw new RuntimeException(e);
        }finally {DBCon.close(pstmt);}
        return list;
    }


}
