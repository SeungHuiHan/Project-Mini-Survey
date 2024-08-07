package edu.survey.controller;

import edu.survey.dao.MemberDAO;
import edu.survey.dao.SurveyDAO;
import edu.survey.dao.VoteDAO;

import java.util.Scanner;

public class AppMain {
    public static Scanner sc;
    private static MemberDAO mdao;
    private static SurveyDAO sdao;
    private static VoteDAO vdao;

    public static void main(String[] args) {
        sc= new Scanner(System.in);
        mdao= new MemberDAO();
        sdao= new SurveyDAO();
        vdao= new VoteDAO();

    }
}
