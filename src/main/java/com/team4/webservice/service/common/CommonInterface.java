package com.team4.webservice.service.common;

import com.team4.webservice.common.syntaxEnum.CommonSyntax;
import com.team4.webservice.common.syntaxEnum.OperatorsSyntax;
import com.team4.webservice.common.syntaxEnum.SpecialCharactersSyntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

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
        for (String text: parseSql) {
            // Find select & from
            if (CommonSyntax.SELECT.getSyntex().equals(text)) {
                hasSelect = true;
                // select가 나오기 전에 from이 먼저 나옴
                if (!hasFrom) {
                    // TODO throw 문법이상
                }
            }
            else if (CommonSyntax.FROM.getSyntex().equals(text)) {
                hasFrom = true;
                // select가 나오기 전에 from이 먼저 나옴
                if (!hasSelect) {
                    // TODO throw 문법이상!!
                }
            }

            // hasSelect = true, hasFrom = false
            else if (hasSelect && !hasFrom) {
                String lastText = syntax.peek();
                System.out.println("last text :: " + lastText);

                // 이전 문자가 SELECT 거나 , 면 컬럼으로 인식
                if (lastText.equals(CommonSyntax.SELECT.getSyntex())
                        || lastText.equals(SpecialCharactersSyntax.COMMA.getSyntex())) {

                }
                //
//                else if (lastText.equals(CommonSyntax.AS.getSyntex())
//                        || ) {
//
//                }


                if (!CommonSyntax.AS.getSyntex().equals(text)) {
                    text = "AS " + text;
                }
            }



            syntax.push(text);
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        for (String str: syntax
        ) {
            System.out.println(str);
        }


        return "";
    }
}