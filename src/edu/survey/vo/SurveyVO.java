package edu.survey.vo;

import java.util.Date;

public class SurveyVO {
    private  int surveyNo;//  `SURVEY_NO` int
    private String title;//  `TITLE` varchar(100) NOT NULL,
    private  String one;//  `ONE` varchar(500) NOT NULL,
    private  String two;//  `TWO` varchar(500) NOT NULL,
    private  int oneCnt;//  `ONE_CNT` int DEFAULT NULL,
    private  int twoCnt;//  `TWO_CNT` int DEFAULT NULL,
    private String startDate;//  `START_DATE` date DEFAULT NULL,
    private String endDate;//  `END_DATE` date DEFAULT NULL,
    private Date regDate;//  `REG_DATE` date NOT NULL,
    private Date modDate;//  `MOD_DATE` date DEFAULT NULL,

    public int getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(int surveyNo) {
        this.surveyNo = surveyNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public int getOneCnt() {
        return oneCnt;
    }

    public void setOneCnt(int oneCnt) {
        this.oneCnt = oneCnt;
    }

    public int getTwoCnt() {
        return twoCnt;
    }

    public void setTwoCnt(int twoCnt) {
        this.twoCnt = twoCnt;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }
}
