package edu.survey.vo;

import java.util.Date;

public class VoteVO {
    private int surveyNo;//  `SURVEY_NO` int NOT NULL,
    private String id;//  `ID` varchar(20) NOT NULL,
    private int oneTwo;//  `ONE_TWO` int DEFAULT NULL,
    private Date voteDate;//  `VOTE_DATE` date NOT NULL,

    public int getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(int surveyNo) {
        this.surveyNo = surveyNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOneTwo() {
        return oneTwo;
    }

    public void setOneTwo(int oneTwo) {
        this.oneTwo = oneTwo;
    }

    public Date getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(Date voteDate) {
        this.voteDate = voteDate;
    }
}
