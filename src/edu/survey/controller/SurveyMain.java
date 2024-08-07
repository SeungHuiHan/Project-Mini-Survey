package edu.survey.controller;

import edu.survey.dao.MemberDAO;
import edu.survey.dao.SurveyDAO;
import edu.survey.dao.VoteDAO;
import edu.survey.util.DBCon;
import edu.survey.vo.MemberVO;
import edu.survey.vo.SurveyVO;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SurveyMain {
    public static Scanner sc;
    private static SurveyDAO sdao;
    private static MemberDAO mdao;
    private static VoteDAO vdao;

    public void menu(){
        //1. 회원 여부 확인하기
        //1.1 회원이면 로그인
        //1.2 회원이 아니면 회원가입
        //2. 로그인
        //3. 회원 정보 조회
        //4. 회원 목록 조회
        //5. 시스템 종료
        while(true){
            System.out.println("----------------------------------------");
            System.out.println("ㅁ Memeber only SYSTEM ㅁㅁㅁㅁㅁ MAIN MENU");
            System.out.println("----------------------------------------");
            System.out.println(" 1. 설문조사 등록");
            System.out.println(" 2. 설문조사 수정");
            System.out.println(" 3. 설문조사 삭제");
            System.out.println(" 4. 설문조사 정보 조회");
            System.out.println(" 5. 설문조사 목록 조회");
            System.out.println(" 6. 시스템 종료");

            System.out.print("선택 : ");
            switch (sc.nextLine()){ // 키보드 입력받기
                case "1":
                    register(); //설문조사 등록 메서드 호출
                    break;
                case "2":
                    modify(); //설문조사 수정 호출
                    break;
                case "3":
                    remove(); //설문조사 삭제 메서드 호출
                    break;
                case "4":
                    view(); //설문조사 정보 조회 메서드 호출
                    break;
                case "5":
                    list(); //설문조사 목록 조회메서드 호출
                    break;
                case "6":
                    exit();
                default:
                    System.out.println("> 1 ~ 6를 선택하세요");

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

    public void register(){
        SurveyVO svo=new SurveyVO();

        System.out.println("----------------------------------------");
        System.out.println("설문조사 등록");
        System.out.println("----------------------------------------");
        System.out.print("제목\t: "); svo.setTitle(sc.nextLine());
        System.out.print("항목1\t: "); svo.setOne(sc.nextLine());
        System.out.print("항목2\t: ");  svo.setTwo(sc.nextLine());
        System.out.print("시작일(yyyy-MM-dd)\t: "); svo.setStartDate( sc.nextLine());
        System.out.print("마감일(yyyy-MM-dd)\t: "); svo.setEndDate(sc.nextLine());

        if(sdao.insertAdmin(svo)){// t_member 테이f블에 레코드 추가하는 메서드 호출하고 반환되는 값을 화면에 표시
            System.out.println("> 설문조사가 등록되었습니다.");
            System.out.println();
            System.out.print("> 설문조사 메뉴로 이동하시겠습니까?(Y/N):");
            if(sc.nextLine().toLowerCase().equals("n")){exit();}
        }
        else{
            System.out.println("> 설문조사 등록에 실패했습니다.");
            System.out.println();
            System.out.print("> 다시 설문조사 등록을 하시겠습니까?(Y/N):");
            if(sc.nextLine().toLowerCase().equals("y")){register();}
            else{
                System.out.print("> 설문조사 메뉴로 이동하시겠습니까?(Y/N):");
                if(sc.nextLine().toLowerCase().equals("n")){exit();}

            }
        }

    }
    
    public void modify(){
        System.out.println("----------------------------------------");
        System.out.println("설문조사 정보 수정하기");
        System.out.println("----------------------------------------");
        System.out.print("설문조사 번호\t: ");
        int surveyNo=sc.nextInt(); sc.nextLine();
        SurveyVO svo = sdao.selectAdmin(surveyNo);//t_member 테이블에서 svo 객체를 받아오고


        System.out.print("제목 : "); String input =sc.nextLine();
        if(!input.isEmpty()) svo.setTitle(input);

        System.out.print("항목1 : "); input =sc.nextLine();
        if(!input.isEmpty()) svo.setOne(input);

        System.out.print("항목2 : "); input =sc.nextLine();
        if(!input.isEmpty()) svo.setTwo(input);




        if(sdao.updateAdmin(svo)){ //t_member 테이블에 레코드를 변경하는 메서드 호출 및 반환값을 화면에 표시
            System.out.println("> 설문조사 수정이 완료되었습니다.");
        }else {
            System.out.println("> 설문조사 수정이 실패하였습니다.");
            System.out.println();
            System.out.print("> 다시 설문조사 수정을 시도하시겠습니까?(Y/N):");
            if (sc.nextLine().toLowerCase().equals("y")) {
                modify();
            } else {
                System.out.print("> 설문조사 메뉴로 이동하시겠습니까?(Y/N):");
                if (sc.nextLine().toLowerCase().equals("n")) {
                    exit();
                }
            }
        }

    }

    public void remove(){
        System.out.println("----------------------------------------");
        System.out.println("설문조사 정보 삭제하기");
        System.out.println("----------------------------------------");
        System.out.print("설문조사 번호\t: ");
        int surveyNo=sc.nextInt(); sc.nextLine();

        if(sdao.deleteAdmin(surveyNo)){ //t_member 테이블에 레코드를 삭제하는 메서드 호출 및 반환값을 화면에 표시
            System.out.println("> 설문조사 삭제가 완료되었습니다.");
        }else{
            System.out.println("> 설문조사 삭제에 실패하였습니다.");
            System.out.println();
            System.out.print("> 다시 설문조사 삭제를 시도하시겠습니까?(Y/N):");
            if(sc.nextLine().toLowerCase().equals("y")){remove();}
            else{
                System.out.print("> 설문조사 메뉴로 이동하시겠습니까?(Y/N):");
                if(sc.nextLine().toLowerCase().equals("n")){exit();}
            }
        }
    }
    
    public void view(){
        System.out.println("----------------------------------------");
        System.out.println("설문조사 정보 조회하기");
        System.out.println("----------------------------------------");
        System.out.print("설문조사 번호\t: ");
        int surveyNo=sc.nextInt(); sc.nextLine();
        SurveyVO svo=sdao.selectAdmin(surveyNo);

        if(svo != null){ // 읽어올 값이 있는지 확인
            System.out.println("---- 설문조사 정보 ----");
            System.out.println("제목: "+svo.getTitle());
            System.out.println("항목1 : "+svo.getOne());
            System.out.println("항목2 : "+svo.getTwo());
            System.out.println("항목1 투표수: "+svo.getOneCnt());
            System.out.println("항목2 투표수: "+svo.getTwoCnt());
            System.out.println("시작일: "+svo.getStartDate());
            System.out.println("마감일: "+svo.getEndDate());
            System.out.println("등록일: "+svo.getRegDate());
            System.out.println("수정일: "+svo.getModDate());

            System.out.println();
            System.out.println("설문조사 정보 조회가 완료되었습니다.");

        }else{ 
            System.out.println(" 일치하는 설문조사 정보가 없습니다.");
            System.out.print("> 다시 설문조사 정보 조회를 시도하시겠습니까?(Y/N):");
            if(sc.nextLine().toLowerCase().equals("y")){view();}
            else{
                System.out.print("> 설문조사 메뉴로 이동하시겠습니까?(Y/N):");
                if(sc.nextLine().toLowerCase().equals("n")){exit();}
            }
        }
    }
    
    public void list(){
        System.out.println("----------------------------------------");
        System.out.println("ㅁ SURVEY only SYSTEM ㅁㅁㅁㅁㅁㅁㅁㅁ LIST");
        System.out.println("----------------------------------------");
        System.out.println(" 설문조사번호 | 제목 | 항목1 | 항목2 | 항목1 투표수 | 항목2 투표수");
        System.out.println("----------------------------------------");
        List<SurveyVO> svoList=sdao.selectAll(); //t_memeber 테이블에서 List객체를 받아오고
        //foreach
        for(SurveyVO svo:svoList){
            System.out.println(svo.getSurveyNo()+" | "+svo.getTitle()
                    +" | "+svo.getOne()+" | "+svo.getTwo()+" | "+svo.getOneCnt()+" | "+svo.getTwoCnt());
        }
    }
    


    public static void main(String[] args) {
        sc = new Scanner(System.in);
        sdao = new SurveyDAO();
        SurveyMain s = new SurveyMain();
        s.menu();

    }

}
