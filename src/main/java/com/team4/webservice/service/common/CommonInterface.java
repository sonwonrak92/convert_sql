package com.team4.webservice.service.common;

import com.team4.webservice.common.syntaxEnum.CommonSyntax;

import java.util.Stack;

public interface CommonInterface {

    //(1) 문자열 합치기
    void attachStr();

    //(2) 문자열 자르기
    void splitStr();

    //(3) 문자열 공백 없애기
    void removeSpaces();

    /**
     * alias가 들어올 때 표준된 형태로 만들어주는 메소드
     */

    default String makeAlias(String sql) {
        // TODO select, from 키워드가 포함되어있는지 확인
        // 셀렉트프로찾기(sql) == false ? throw exception;

        sql = "SELECT A.A AS text " +
                ", A.B B" +
                "       FROM TABLE_A A INNER JOIN TABLE_B AS B ON A.A = B.B;";
        // INPUT 형태 : SELECT A.A AS text FROM TABLE_A A INNER JOIN TABLE_B AS B ON A.A = B.B;
        // OUTPUT 형태 : SELECT A.A AS "text" FROM TABLE_A AS "A" INNER JOIN TABLE_B AS "B" ON A.A = B.B;
        String[] parseSql = sql.split("\\s+");
        for (String text : parseSql) {
            System.out.println(text);
        }

        Stack<String> syntax = new Stack<>();
        boolean hasSelect = false;
        boolean hasFrom = false;
        for (String text : parseSql) {
            // Find select & from
            if (CommonSyntax.SELECT.getSyntex().equals(text)) {
                hasSelect = true;
                // select가 나오기 전에 from이 먼저 나옴
                if (!hasFrom) {
                    // TODO throw 문법이상
                }
            } else if (CommonSyntax.FROM.getSyntex().equals(text)) {
                hasFrom = true;
                // select가 나오기 전에 from이 먼저 나옴
                if (!hasSelect) {
                    // TODO throw 문법이상!!
                }
            }
        }
        return "";
    }
}