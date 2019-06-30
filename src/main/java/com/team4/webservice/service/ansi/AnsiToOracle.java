package com.team4.webservice.service.ansi;

import com.team4.webservice.common.syntaxEnum.AnsiSyntax;
import com.team4.webservice.common.syntaxEnum.CommonSyntax;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AnsiToOracle implements Ansi{


    @Override
    public void moveToFrom() {

    }

    @Override
    public void onToWhere() {

    }

    @Override
    public void attachPlus() {

    }

    @Override
    public void attachStr() {
        String str = "";
        //2) char 배열으로 넘길때
        char[] arr;
        arr = str.toCharArray();

        for(int i=0;i<arr.length;i++){
            if(97<=arr[i] && arr[i] <=122)
                arr[i] = (char)(arr[i]-32);
        }

        //1) string 값으로 넘길때
        //str.toUpperCase();

    }

    @Override
    public void splitStr() {

    }

    //@Override
    public void removeSpaces() {
        //1
        String str = "";
        str.replaceAll("\\p{Z}","");
    }

    /**
     * String sql을 ArrayList형태로 전달
     *
     *
     */

    public ArrayList<ArrayList<String>> parseStrToArr (String sql) {
        Pattern regex = Pattern.compile("(?<=FROM).+");    // 정규식 변수
        Matcher matcher = regex.matcher(sql);
        String targetSql = "";

        if (matcher.find()) {
            targetSql = matcher.group(0);
        }

        // FROM절부터 맨 뒤 까지 자른 데이터 중 WHERE가 포함되어있는지 확인
        if (targetSql.indexOf(CommonSyntax.WHERE.getSyntex()) > -1) {
            regex = Pattern.compile(".+(?=WHERE)");    // 정규식 변수
            matcher = regex.matcher(targetSql);
            if (matcher.find()) {
                targetSql = matcher.group(0);
            }
        }
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        // FROM ~ WHERE OR 맨 뒤 까지 데이터가 담겨있음
        System.out.println("target sql :: " + targetSql);
        String[] arrSql =  targetSql.split("\\s+");
        for (int i = 0; i < arrSql.length; i++) {
            System.out.println("target :: " + arrSql[i]);
            int j = 0;
            String flag = "";
            if ("INNER".equals(arrSql[i])) {
                j = i + 2;
                flag = "INNER";
            }
            else if ("LEFT".equals(arrSql[i])) {
                j = i + 3;
                flag = "LEFT";
            }
            else if ("RIGHT".equals(arrSql[i])) {
                j = i + 3;
                flag = "RIGHT";
            }
            else if ("OUTER".equals(arrSql[i])) {
                j = i + 2;
                flag = "OUTER";
            }
            System.out.println("flag :: " + flag + ", i :: " + i);
            ArrayList<String> temp = new ArrayList<>();
            temp.add(flag);
            if ("".equals(flag)) {
                continue;
            }
            for (j = j; j < arrSql.length; j++) {
                System.out.println("flag :: " + flag + ", j :: " + arrSql[j]);
                if ("ON".equals(arrSql[j])) {
                    result.add(temp);
                    temp = new ArrayList<>();   // 초기화
                    temp.add("ON");
                    continue;
                }
                if (    "INNER".equals(arrSql[j])
                        || "LEFT".equals(arrSql[j])
                        || "RIGHT".equals(arrSql[j])
                        || "OUTER".equals(arrSql[j])
                        || "WHERE".equals(arrSql[j])
                        || ";".equals(arrSql[j])
                        || arrSql.length-1 == j) {
                    if (arrSql.length-1 == j) {
                        temp.add(arrSql[j]);
                    }
                    i = j - 1;
                    System.out.println("result"+ result.get(result.size() - 1));
                    result.add(temp);
                    break;
                }
                else {
                    temp.add(arrSql[j]);
                }
            }

        }

        // where 절
        regex = Pattern.compile("(?<=FROM).+");    // 정규식 변수
        matcher = regex.matcher(sql);
        targetSql = "";

        if (matcher.find()) {
            targetSql = matcher.group(0);
            System.out.println("targetSql :: " + targetSql);
        }




        return result;
    }
}
