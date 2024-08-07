package edu.survey.controller;
import edu.survey.util.DBCon;
import edu.survey.dao.MemberDAO;
import edu.survey.dao.SurveyDAO;
import edu.survey.dao.VoteDAO;
import edu.survey.vo.MemberVO;
import edu.survey.vo.VoteVO;

import java.util.List;
import java.util.Scanner;

public class MemberMain {
    public static Scanner sc;
    private static MemberDAO mdao;
    private static SurveyDAO sdao;
    private static VoteDAO vdao;

    public void menu(){
        //1. 회원 여부 확인하기
        //1.1 회원이면 로그인
        //1.2 회원이 아니면 회원가입
        //2. 로그인
        //3. 회원 정보 조회
        //4. 회원 목록 조회
        //5. 관리자 계정 로그인
        //5. 시스템 종료
        while(true){
            System.out.println("----------------------------------------");
            System.out.println("ㅁ MEMBER only SYSTEM ㅁㅁㅁㅁㅁ MAIN MENU");
            System.out.println("----------------------------------------");
            System.out.println(" 1. 회원 여부 확인하기");
            System.out.println(" 2. 회원가입");
            System.out.println(" 3. 로그인");
            System.out.println(" 4. 회원 정보 수정");
            System.out.println(" 5. 회원 정보 삭제");
            System.out.println(" 6. 회원 정보 조회");
            System.out.println(" 7. 화원 목록 조회");
            System.out.println(" 8. 시스템 종료");

            System.out.print("선택 : ");
            switch (sc.nextLine()){ // 키보드 입력받기
                case "1":
                    check(); //회원 여부 확인 메서드 호출
                    break;
                case "2":
                    join(); //회원가입 메서드 호출
                    break;
                case "3":
                    login(); //회원 로그인 메서드 호출
                    break;
                case "4":
                    modify(); //회원정보 수정 메서드 호출
                    break;
                case "5":
                    remove(); //회원정보 삭제 메서드 호출
                    break;
                case "6":
                    view(); //회원정보 조회 메서드 호출
                    break;
                case "7":
                    list(); //회원목록 조회 메서드 호출
                    break;
                case "8":
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

    public void check(){
        System.out.println("----------------------------------------");
        System.out.println(" 회원 여부 확인하기");
        System.out.println("----------------------------------------");
        System.out.print("아이디 입력하세요\t: ");
        String mid=sc.nextLine();

        if(mdao.checkM(mid)){// t_member 테이블에 레코드 추가하는 메서드 호출하고 반환되는 값을 화면에 표시
            System.out.println("> 회원 아이디가 존재합니다.");
            System.out.println("> 로그인 후 이용해주세요.");
            System.out.print("> 로그인으로 이동하시겠습니까?(Y/N): ");
            if(sc.nextLine().toLowerCase().equals("y")){login();}
            else{exit();}
        }
        else{
            System.out.println("> 회원이 아닙니다.");
            System.out.print("> 회원가입 하시겠습니까?(Y/N): ");
            if(sc.nextLine().toLowerCase().equals("y")){join();}
            else{
                System.out.print("> 회원 메뉴로 이동하시겠습니까?(Y/N): ");
                if(sc.nextLine().toLowerCase().equals("y")){menu();}
                else{ exit();}
            }
        }
    }

    public void login(){
        System.out.println("----------------------------------------");
        System.out.println(" 로그인 하기");
        System.out.println("----------------------------------------");
        System.out.print("아이디 입력하세요\t: "); String mid=sc.nextLine();
        System.out.print("비밀번호 입력하세요\t: "); String mpw=sc.nextLine();

        if(mdao.loginM(mid,mpw)){// t_member 테이블에 레코드 추가하는 메서드 호출하고 반환되는 값을 화면에 표시
            System.out.println("> 로그인에 성공하였습니다.");
            VoteVO vvo=new VoteVO();
            vvo.setId(mid);

            System.out.print("> 설문참여 시스템으로 이동하시겠습니까?(Y/N): ");
            if(sc.nextLine().toLowerCase().equals("y")){
                VoteMainCopy vote = new VoteMainCopy(sc);  // Scanner 객체 전달
                vote.menu();}
            else{
                System.out.print("> 회원 메뉴로 이동하시겠습니까?(Y/N):");
                if(sc.nextLine().toLowerCase().equals("n")){exit();}

            }
        }
        else{
            System.out.println("> 로그인에 실패했습니다.");
            System.out.println();
            System.out.print("> 다시 로그인을 하시겠습니까?(Y/N):");
            if(sc.nextLine().toLowerCase().equals("y")){login();}
            else{
                System.out.print("> 회원 메뉴로 이동하시겠습니까?(Y/N):");
                if(sc.nextLine().toLowerCase().equals("n")){exit();}

            }
        }
    }

    public void join(){
        MemberVO mvo = new MemberVO();

        System.out.println("----------------------------------------");
        System.out.println("회원 가입");
        System.out.println("----------------------------------------");
        System.out.print("아이디\t: "); mvo.setMid(sc.nextLine());
        System.out.print("회원이름\t: "); mvo.setMname(sc.nextLine());
        System.out.print("비밀번호\t: ");  mvo.setMpw(sc.nextLine());
        System.out.print("이메일\t: "); mvo.setEmail(sc.nextLine());
        System.out.print("성별(M/F)\t: "); mvo.setGender(sc.nextLine());
        System.out.print("사진\t: "); mvo.setPhoto(sc.nextLine());
        System.out.print("생년월일(yyyy-MM-dd)\t: "); mvo.setBirth_date(sc.nextLine());

        if(mdao.insert(mvo)){// t_member 테이블에 레코드 추가하는 메서드 호출하고 반환되는 값을 화면에 표시
            System.out.println("> 회원 가입이 완료되었습니다.");
            System.out.println();
            System.out.print("> 로그인으로 이동하시겠습니까?(Y/N): ");
            if(sc.nextLine().toLowerCase().equals("y")){login();}
            else{
                System.out.print("> 회원 메뉴로 이동하시겠습니까?(Y/N):");
                if(sc.nextLine().toLowerCase().equals("n")){exit();}

            }
        }
        else{
            System.out.println("> 회원 가입에 실패했습니다.");
            System.out.println();
            System.out.print("> 다시 회원가입을 하시겠습니까?(Y/N):");
            if(sc.nextLine().toLowerCase().equals("y")){join();}
            else{
                System.out.print("> 회원 메뉴로 이동하시겠습니까?(Y/N):");
                if(sc.nextLine().toLowerCase().equals("n")){exit();}

            }
        }
    }

    //아이디와 비밀번호가 일치하면 이름, 이메일, 사진 수정
    public void modify(){
        System.out.println("----------------------------------------");
        System.out.println("회원정보 수정하기");
        System.out.println("----------------------------------------");
        System.out.print("아이디\t: ");
        String mid=sc.nextLine(); //수정하려는 레코드의 아이디를 입력받아
        MemberVO mvo = mdao.select(mid);//t_member 테이블에서 mvo 객체를 받아오고
        //수정하려는 테이터들의 입력된 mvo에 값을 저장하여 변경 처리
        System.out.print("비밀번호 : ");  String mpw=sc.nextLine();

        if(mdao.loginM(mid,mpw)){// t_member 테이블에 레코드 추가하는 메서드 호출하고 반환되는 값을 화면에 표시
            System.out.println("> 아이디와 비밀번호가 일치합니다. ");

            System.out.print("이름 : "); String input =sc.nextLine();
            if(!input.isEmpty()) mvo.setMname(input);

            System.out.print("이메일 : "); input =sc.nextLine();
            if(!input.isEmpty()) mvo.setEmail(input);

            System.out.print("사진 : "); input =sc.nextLine();
            if(!input.isEmpty()) mvo.setPhoto(input);


            if(mdao.update(mvo)){ //t_member 테이블에 레코드를 변경하는 메서드 호출 및 반환값을 화면에 표시
                System.out.println("> 회원정보 수정이 완료되었습니다.");
            }else{
                System.out.println("> 회원정보 수정이 실패하였습니다.");
                System.out.println();
                System.out.print("> 다시 회원 수정을 시도하시겠습니까?(Y/N):");
                if(sc.nextLine().toLowerCase().equals("y")){modify();}
                else{
                    System.out.print("> 회원 메뉴로 이동하시겠습니까?(Y/N):");
                    if(sc.nextLine().toLowerCase().equals("n")){exit();}

                }
            }

        }else{
            System.out.println("> 아이디와 비밀번호가 일치하지 않습니다.");
            System.out.println();
            System.out.print("> 다시 회원 수정을 시도하시겠습니까?(Y/N):");
            if(sc.nextLine().toLowerCase().equals("y")){modify();}
            else{
                System.out.print("> 회원 메뉴로 이동하시겠습니까?(Y/N):");
                if(sc.nextLine().toLowerCase().equals("n")){exit();}
            }
        }

    }

    public void remove(){ //삭제하려는 회원아이디를 입력받아
        // t_member 테이블의 레코드를 삭제하는 메서드 호출 및 반환값 화면 표시
        System.out.println("----------------------------------------");
        System.out.println("ㅁ MEMBER only SYSTEM ㅁㅁㅁㅁㅁㅁㅁ REMOVE");
        System.out.println("----------------------------------------");
        System.out.print("아이디 : ");
        String mid=sc.nextLine(); //탈퇴하려는 레코드의 아이디를 입력받아

        if(mdao.delete(mid)){ //t_member 테이블에 레코드를 삭제하는 메서드 호출 및 반환값을 화면에 표시
            System.out.println("> 회원 탈퇴가 완료되었습니다.");
        }else{
            System.out.println("> 회원 탈퇴에 실패하였습니다.");
            System.out.println();
            System.out.print("> 다시 회원 탈퇴를 시도하시겠습니까?(Y/N):");
            if(sc.nextLine().toLowerCase().equals("y")){remove();}
            else{
                System.out.print("> 회원 메뉴로 이동하시겠습니까?(Y/N):");
                if(sc.nextLine().toLowerCase().equals("n")){exit();}
            }
        }
    }

    public void view(){//조회하려는 회원아이디를 입력받아
        // t_member 테이블의 레코드를 삭제하는 메서드 호출 및 반환값 화면 표시
        System.out.println("----------------------------------------");
        System.out.println("ㅁ MEMBER only SYSTEM ㅁㅁㅁㅁㅁㅁㅁㅁ VIEW");
        System.out.println("----------------------------------------");
        System.out.print("아이디\t: ");
        String mid=sc.nextLine(); //수정하려는 레코드의 아이디를 입력받아
        MemberVO mvo = mdao.select(mid);//t_member 테이블에서 mvo 객체를 받아오고

        if(mvo != null){ // 읽어올 값이 있는지 확인
            System.out.println("---- 회원 정보 ----");
            System.out.println("회원 아이디: "+mvo.getMid());
            System.out.println("회원 이름: "+mvo.getMname());
            System.out.println("이메일: "+mvo.getEmail());
            System.out.println("성별: "+mvo.getGender());
            System.out.println("사진: "+mvo.getPhoto());
            System.out.println("생년월일: "+mvo.getBirth_date());
            System.out.println("가입일자: "+mvo.getJoin_date());
            System.out.println();

        }else{ System.out.println("- 일치하는 회원 정보가 없습니다.");}


        if(mdao.checkM(mid)){
            System.out.println("> 회원정보 조회가 완료되었습니다.");
        }else{
            System.out.println("> 회원정보 조회를 실패하였습니다.");
            System.out.println();
            System.out.print("> 다시 회원정보 조회를 시도하시겠습니까?(Y/N):");
            if(sc.nextLine().toLowerCase().equals("y")){view();}
            else{
                System.out.print("> 회원 메뉴로 이동하시겠습니까?(Y/N):");
                if(sc.nextLine().toLowerCase().equals("n")){exit();}
            }
        }


    }
    public void list(){ // t_member 테이블의 모든 레코드를 조회하는 메서드 호출 및 반환값 화면 표시
        // 단, 등록된 회원이 없는 경우 적절한 메시지 출력
        System.out.println("----------------------------------------");
        System.out.println("ㅁ MEMBER only SYSTEM ㅁㅁㅁㅁㅁㅁㅁㅁ LIST");
        System.out.println("----------------------------------------");
        System.out.println("아이디 | 이름 | 이메일 | 가입일자");
        System.out.println("----------------------------------------");

        List<MemberVO> mvoList=mdao.selectAll(); //t_memeber 테이블에서 List객체를 받아오고
        //foreach
        for(MemberVO mvo:mvoList){
            System.out.println(mvo.getMid()+" | "+mvo.getMname()+" | "+mvo.getEmail()+" | "+mvo.getJoin_date());
        }

        System.out.println();
        //람다식
        //mvoList.forEach(mvo->System.out.println(mvo.getMid()+" | "+mvo.getMname()+" | "+mvo.getEmail()+" | "+mvo.getJoin_date()));

    }

    public static void main(String[] args) {

        sc = new Scanner(System.in);
        mdao = new MemberDAO();
        MemberMain m=new MemberMain();
        VoteMain v=new VoteMain();
        SurveyMain s=new SurveyMain();

        m.menu();



    }


}
