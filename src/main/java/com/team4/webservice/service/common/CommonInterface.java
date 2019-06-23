package com.team4.webservice.service.common;

import com.team4.webservice.common.syntaxEnum.CommonSyntax;
import com.team4.webservice.common.syntaxEnum.SpecialCharactersSyntax;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//        sql = "A.TITLE   AS    \"   title\", A.name \"NAME \", a.age AGE, a.addr";
        // INPUT 형태 : A.TITLE   AS    "   title", A.name "NAME ", a.age AGE
        // OUTPUT 형태 : A.TITLE AS " title", A.NAME AS "NAME ", A.AGE AS "AGE"
        String[] parseSql = sql.split("(\\s|)+,(\\s)+");

        Pattern regex = null;    // 정규식 변수
        Matcher matcher = null;
        for (int i = 0; i < parseSql.length; i++) {
            String text = parseSql[i];
            String alias = "";

            /******************
            * alias 분리하기
            *******************/
            // 쌍따옴표로 감싸진 Alias 분리
            regex = Pattern.compile("(?<=\").+(?=\")+");
            matcher = regex.matcher(text);
//            System.out.println("Before  :: "  + text);
            while (matcher.find()) {
                alias = matcher.group(0);

            }
            // 쌍따옴표안에 없는 Alias의 경우 따로 분리
            if (alias.equals("")) {
                regex = Pattern.compile("(?<=\\s)\\w+$");
                matcher = regex.matcher(text);
                while (matcher.find()) {
                    alias = matcher.group(0);
                }
            }
//            System.out.println("Alias   :: " + alias);
            /*********************
             * 대문자 치환 & 공백 제거
             *********************/
            text = text.toUpperCase();                            // 대문자 치환
            text = text.replaceAll("\\s+", " "); // 공백 제거
            /*********************
             * AS 붙이기
             *********************/
            regex = Pattern.compile("^(\\w|\\.)+(?=\\s)");
            matcher = regex.matcher(text);
            while (matcher.find()) {
//                System.out.println("column  :: " + matcher.group(0));
                text = matcher.group(0) + " " + CommonSyntax.AS.getSyntex() + " ";
            }
            // Alias가 있으면 붙여줌(애초에 Alias가 없으면 생략)
            if (!alias.equals("")) {
                text += "\"" + alias + "\"";
            }
            parseSql[i] = text;
//            System.out.println("After   ::"  + parseSql[i]);
        }

//        for (int i = 0; i < parseSql.length; i++) {
//            System.out.println(parseSql[i]);
//        }
        return String.join(", ", parseSql);
    }
}