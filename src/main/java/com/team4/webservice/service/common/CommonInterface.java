package com.team4.webservice.service.common;

import com.team4.webservice.common.syntaxEnum.CommonSyntax;
import com.team4.webservice.common.syntaxEnum.OperatorsSyntax;
import com.team4.webservice.common.syntaxEnum.SpecialCharactersSyntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface CommonInterface {

    //(1) 문자열 합치기
    void attachStr();

    //(2) 문자열 자르기
    void splitStr();

    //(3) 문자열 공백 없애기

    /**
     * enum에 포함된 문자가 들어올때 제대로된 공백을 붙이는 메소드
     */
    static ArrayList<String> arr = new ArrayList<>(); //다시 담을 arraylist
    default  String removeSpaces(HashMap<String, ArrayList<String>> arrMap){
        //innerJoin에 해당하는 키값 받기
        Set key = arrMap.keySet();
        ArrayList<String> valueName = null;
        valueName = arrMap.get("INNERJOIN");

        CompareToOperation(valueName);

        return null;
    }

    default void CompareToOperation(ArrayList<String> str){
        for(int i=0;i<str.size();i++) {
            if(CompareToOperationDetail(str.get(i)) == true) {
                plusSpace(str.get(i));
            }else {
                plusSpace(str.get(i));
            }
        }
    }
    //enum에 있는 문자와 받아온 arraylist의 값 비교
    static boolean CompareToOperationDetail(String str){
        //System.out.println(str);
        for(OperatorsSyntax op : OperatorsSyntax.values()) {
            String chr = op.character;

            if(chr.equals(str)) {
                return true;
            }
        }
        return false;
    }
    //공백을 붙여서 저장
    static void plusSpace(String str) {
        arr.add(str);
        arr.add("\n");
    }


    /**
     * alias가 들어올 때 표준된 형태로 만들어주는 메소드
     */

    default String makeAlias(String sql) {
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
                text = matcher.group(0) + " " + CommonSyntax.AS.getSyntex() + " ";
            }
            // Alias가 있으면 붙여줌
            if (!alias.equals("")) {
                text += "\"" + alias + "\"";
            }
            // Alias가 없으면 컬럼명으로 붙여줌
            else {
                text += " AS \"" + text + "\"";
            }
            parseSql[i] = text;
        }

        return String.join(", ", parseSql);
    }
}