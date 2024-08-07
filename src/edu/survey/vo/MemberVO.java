package edu.survey.vo;

import java.sql.Date;

public class MemberVO {
    private String mid; //회원아이디 mid vatchar(20) PK
    private String mname; //회원이름 mname varchar(20)
    private String mpw; //비밀번호 mpw varchar(20)
    private String email; //이메일 email varchar(50)
    private String gender; //성별 gender char(1)
    private String photo; //사진 photo varchar(50)
    private String birth_date; //생년월일 birth_date date
    private Date join_date; //가입일자 join_date date

    public String getMname() { return mname; }

    public void setMname(String mname) {this.mname = mname;}

    public String getMid() {return mid;}

    public void setMid(String mid) {this.mid = mid;}

    public String getMpw() {return mpw;}

    public void setMpw(String mpw) {this.mpw = mpw;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getGender() {return gender;}

    public void setGender(String gender) {this.gender = gender;}

    public String getPhoto() {return photo;}

    public void setPhoto(String photo) {this.photo = photo;}

    public String getBirth_date() {return birth_date;}

    public void setBirth_date(String birth_date) {this.birth_date = birth_date;}

    public Date getJoin_date() {return join_date;}

    public void setJoin_date(Date join_date) {this.join_date = join_date;}
}
