package edu.survey.controller;

import edu.survey.dao.MemberDAO;
import edu.survey.dao.SurveyDAO;
import edu.survey.dao.VoteDAO;
import edu.survey.util.DBCon;
import edu.survey.vo.SurveyVO;
import edu.survey.vo.VoteVO;

import java.util.List;
import java.util.Scanner;

public class VoteMain {
    public static Scanner sc;
    private static VoteDAO vdao;
    private static MemberDAO mdao;
    private static SurveyDAO sdao;
    public void menu(){

        while(true){
            System.out.println("----------------------------------------");
            System.out.println("ㅁ VOTE only SYSTEM ㅁㅁㅁㅁㅁ MAIN MENU");
            System.out.println("----------------------------------------");
            System.out.println(" 1. 설문 참여 여부 확인하기");
            System.out.println(" 2. 설문 참여");
            System.out.println(" 3. 설문 참여 목록 조회");
            System.out.println(" 4. 시스템 종료");

            System.out.print("선택 : ");
            switch (sc.nextLine()){ // 키보드 입력받기
                case "1":
                    check(); //회원 여부 확인 메서드 호출
                    break;
                case "2":
                    join(); //회원가입 메서드 호출
                    break;
                case "3":
                    list(); //회원목록 조회 메서드 호출
                    break;
                case "4":
                    exit();
                default:
                    System.out.println("> 1 ~ 5를 선택하세요");

            }
        }

    }

    //시스템 종료 메서드
    public void exit(){
        System.out.println("시스템을 종료합니다.");
        sc.close(); //스캐너 객체 닫기
        DBCon.close(); //DB연결 객체 닫기
        System.exit(0); //시스템 정상 종료
    }

    public void check(){
        System.out.println("----------------------------------------");
        System.out.println(" 설문 참여 목록 확인하기");
        System.out.println("----------------------------------------");

        //VoteVO vvo=new VoteVO();
        System.out.print("아이디 입력하세요\t: ");
        String vid=sc.nextLine();

        //System.out.print("회원 아이디 \t: "+vvo.getId());
        System.out.print("설문조사 번호를 입력하세요\t: ");
        int vno=sc.nextInt();sc.nextLine();
        VoteVO vvo=vdao.select(vid,vno);

        if(vvo!=null){
            System.out.println("---- 설문조사  정보 ----");
            System.out.println(vvo.getSurveyNo()+" | "+vvo.getId()
                    +" | "+vvo.getOneTwo()+" | "+vvo.getVoteDate());

            System.out.println("> 설문에 이미 참여하였습니다.");
            System.out.print("> 설문조사 메뉴로 이동하시겠습니까?(Y/N): ");
            if(sc.nextLine().toLowerCase().equals("y")){menu();}
            else{ exit();}
        }
        else{
            System.out.println("> 설문 미참여하였습니다.");
            System.out.print("> 설문에 참여 하시겠습니까?(Y/N): ");
            if(sc.nextLine().toLowerCase().equals("y")){join();}
            else{
                System.out.print("> 설문조사 메뉴로 이동하시겠습니까?(Y/N): ");
                if(sc.nextLine().toLowerCase().equals("y")){menu();}
                else{ exit();}
            }
        }
    }

    public void join(){

        System.out.println("----------------------------------------");
        System.out.println(" 설문 참여하기");
        System.out.println("----------------------------------------");
        VoteVO vvo=new VoteVO();
        System.out.print("설문조사 번호를 입력하세요\t: ");
        int no=sc.nextInt();sc.nextLine();
        vvo.setSurveyNo(no);

        SurveyVO svo=sdao.select(no);
        if(svo != null){ // 읽어올 값이 있는지 확인
            System.out.println("---- 설문조사 정보 ----");
            System.out.println(" 설문조사번호 | 제목 | 항목1 | 항목2 ");
            System.out.println(svo.getTitle()+" | "+svo.getOne()+" | "+svo.getTwo());
        }
        System.out.println();

        System.out.print("아이디 \t: ");

        String vid=sc.nextLine();
        vvo.setId(vid);
        System.out.println("> 항목1에 투표하시려면 1을, 항목2에 투표하시려면 2를 입력하세요: ");
        int onetwo=sc.nextInt(); sc.nextLine();
        vvo.setOneTwo(onetwo);


        if(vdao.insertVote(vvo)){ //t_member 테이블에 레코드를 변경하는 메서드 호출 및 반환값을 화면에 표시
            System.out.println("> 설문조사 참여가 완료되었습니다.");
            if(sdao.updateCnt(onetwo,no)) System.out.println("투표수 업데이트됨");
            else System.out.println("투표수 업데이트 실패");

        }else{
            System.out.println("> 설문조사 참여에 실패했습니다.");
            System.out.print("> 다시 설문에 참여 하시겠습니까?(Y/N): ");
            if(sc.nextLine().toLowerCase().equals("y")){join();}
            else{
                System.out.print("> 설문조사 메뉴로 이동하시겠습니까?(Y/N): ");
                if(sc.nextLine().toLowerCase().equals("y")){menu();}
                else{ exit();}
            }
        }
    }

    public void list(){
        System.out.println("----------------------------------------");
        System.out.println("ㅁ MEMBER only SYSTEM ㅁㅁㅁㅁㅁㅁㅁㅁ LIST");
        System.out.println("----------------------------------------");
        System.out.print("아이디\t: ");
        String id=sc.nextLine();

        System.out.println(" 설문조사 번호 | 투표항목 | 참여일자 ");
        System.out.println("----------------------------------------");
        List<VoteVO> vvoList=vdao.selectAll(id);
        for(VoteVO vvo:vvoList){
            System.out.println(vvo.getSurveyNo() +" | "+vvo.getOneTwo()+" | "+vvo.getVoteDate());
        }


    }


    public static void main(String[] args) {
        sc = new Scanner(System.in);
        vdao = new VoteDAO();
        sdao=new SurveyDAO();
        VoteMain v = new VoteMain();
       v.menu();

    }
}
