package com.team4.webservice.service.ansi;

import com.team4.webservice.common.syntaxEnum.AnsiSyntax;
import com.team4.webservice.common.syntaxEnum.CommonSyntax;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
//모든 코드는 데이터를 받아왔다는 전제로 한다.
//받아야하는 데이터 범위 : INNER부터  끝까지
//1) INNER JOIN OUTER RIGHT JOIN은
//현재 ,로 받아왔다는 전제로 코딩하였음
//만약 ,로 치환이 아니라면 list 하나에 [INNER JOIN] 통째로 넣어 주길 바람

//2) 데이터를 받아오는 형식은 ArrayList안에 ArrayList를 제네릭으로 받는 것으로 구성하였음
//	ex) ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

//3) 값을 넘길때는 밑에와같이 잘라서 넘겨주길 바람
//**************************************
//INNER JOIN DEPT1 AS 2    (0)
//**************************************
//
//**************************************
//ON A.ID = B.ID			(1)
//AND C.ID = D.ID
//**************************************
//
//**************************************
//LEFT OUTER JOIN DEPT2 AS 3	(2)
//**************************************
//
//**************************************
//ON A.ID = B.ID			(3)
//**************************************
//
//**************************************
//WHERE A.ID > 1000			(4)
// AND C.ID < 2000
//**************************************
import java.util.ArrayList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AnsiToOracle implements Ansi{



    public StringBuffer moveToFrom(ArrayList<ArrayList<String>> list) {

        System.out.println("최준우 > moveToFrom");
         //데이터 확인
        System.out.println("상훈상훈");
        System.out.println(list);
        // TODO Auto-generated method stub
        /****************************************변수선언****************************************/
        ArrayList<String> innerOuter = new ArrayList<>();   //from으로 갈것들
        ArrayList<String> where = new ArrayList<>();        //where부터 끝까지
        ArrayList<String> on    = new ArrayList<>();        //on~where전까지
        ArrayList<String> result = new ArrayList<>();       //결과값
        StringBuffer sb = new StringBuffer();               //결과값을 String으로 캐스팅
        boolean where_yn = false;                           // where가 있을시 true로 전환
        /****************************************변수선언****************************************/


        //첫번째 list 사이즈만큼
        for(int i=0;i < list.size();i++) {
            ArrayList<String> inList = list.get(i);

            //where가 있으면 true 없으면 false
            if (inList.get(0).equals("WHERE")) where_yn = true;

            //첫 번째 들어온값이 "LEFT"  "RIGHT"  "ON"  "WHERE"인지 CHECK!
            switch (inList.get(0)) {

                case "LEFT":
                    //해당 다음 번째 인덱스의 2 번째 번지에 (+) 추가   "*i+1은 ON 절이 있는 부분"
                    list.get(i + 1).add(2, "(+)");

                    for (int k = 0; k < inList.size(); k++) {
                        if(inList.get(k).equals(";"))     inList.remove(k);
                        if(k==0)                          innerOuter.add(",");
                        if(!inList.get(k).equals("LEFT")) innerOuter.add(inList.get(k));
                    }
                    break;

                case "RIGHT":
                    //해당 다음 번째 인덱스의 4 번째 번지에 (+) 추가
                    list.get(i + 1).add(4, "(+)");
                    for (int k = 0; k < inList.size(); k++) {
                        if(inList.get(k).equals(";"))      inList.remove(k);
                        if(k==0)                           innerOuter.add(",");
                        if(!inList.get(k).equals("RIGHT")) innerOuter.add(inList.get(k));
                    }

                    break;

                case "INNER":
                    //System.out.println("(3)"+inList.get(0));
                    for (int k = 0; k < inList.size(); k++) {
                        if(inList.get(k).equals(";"))      inList.remove(k);
                        if(k==0)                           innerOuter.add(",");
                        if(!inList.get(k).equals("INNER")) innerOuter.add(inList.get(k));
                    }

                    break;

                case "WHERE":
                    //System.out.println("(4)"+inList.get(0));
                    for (int k = 0; k < inList.size(); k++) {
                        if(inList.get(k).equals(";")) inList.remove(k);

                        where.add(inList.get(k));
                    }

                    break;

                case "ON":
                    for (int k = 0; k < inList.size(); k++) {

                        if(inList.get(k).equals(";"))   inList.remove(k);
                        if(k==0) on.add("AND");
                        if(!inList.get(k).equals("ON")) on.add(inList.get(k));

                    }

                    break;
            }


//            System.out.println("*********************데이터출력*********************");
//            System.out.println("innerOuter"+innerOuter);
//            System.out.println("where" + where);
//            System.out.println("on"+on);


        }
        //where가 있을시 where arraylist에  innerOuter list 추가
        if (where_yn == true) {

            innerOuter.addAll(where);
            innerOuter.addAll(on);

            result = innerOuter;
        }else { //where가 없을때
            on.remove(0);
            on.add(0,"WHERE");

            innerOuter.addAll(on);
            result = innerOuter;
        }
//        System.out.println("결과");
//        System.out.println(result);

        for(int i=0;i<result.size();i++){
            sb.append(result.get(i));
            sb.append(" ");
        }
        sb.append(";");
        System.out.println("*********************데이터출력*********************");
        System.out.println(sb);
        System.out.println("*********************데이터출력*********************");
        return  sb;
    }

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
        // FROM절부터 맨 뒤 까지 자른 데이터 중 GROUP BY가 포함되어있는지 확인
        else if (targetSql.indexOf(CommonSyntax.GROUP_BY.getSyntex()) > -1) {
            regex = Pattern.compile(".+(?=GROUP)");    // 정규식 변수
            matcher = regex.matcher(targetSql);
            if (matcher.find()) {
                targetSql = matcher.group(0);
            }
        }
        // FROM절부터 맨 뒤 까지 자른 데이터 중 ORDER BY가 포함되어있는지 확인
        else if (targetSql.indexOf(CommonSyntax.ORDER_BY.getSyntex()) > -1) {
            regex = Pattern.compile(".+(?=ORDER)");    // 정규식 변수
            matcher = regex.matcher(targetSql);
            if (matcher.find()) {
                targetSql = matcher.group(0);
            }
        }


        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        // FROM ~ WHERE OR 맨 뒤 까지 데이터가 담겨있음
        //System.out.println("target sql :: " + targetSql);
        String[] arrSql =  targetSql.split("\\s+");
        for (int i = 0; i < arrSql.length; i++) {
            //System.out.println("target :: " + arrSql[i]);
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
            //System.out.println("flag :: " + flag + ", i :: " + i);
            ArrayList<String> temp = new ArrayList<>();
            temp.add(flag);
            if ("".equals(flag)) {
                continue;
            }
            for (j = j; j < arrSql.length; j++) {
                //System.out.println("flag :: " + flag + ", j :: " + arrSql[j]);
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
                    //System.out.println("result"+ result.get(result.size() - 1));
                    result.add(temp);
                    break;
                }
                else {
                    temp.add(arrSql[j]);
                }
            }

        }

        // where 절
        regex = Pattern.compile("(?<=WHERE ).+");    // 정규식 변수
        matcher = regex.matcher(sql);

        if (matcher.find()) {
            targetSql = matcher.group(0);
        }
        // GROUP BY 절이 있으면 그 전까지 자르기
        if (targetSql.indexOf(CommonSyntax.GROUP_BY.getSyntex()) > -1) {
            regex = Pattern.compile(".+(?=GROUP)");    // 정규식 변수
            matcher = regex.matcher(targetSql);
            if (matcher.find()) {
                targetSql = matcher.group(0);
            }
        }
        // ORDER BY 절이 있으면 그 전까지 자르기
        else if (targetSql.indexOf(CommonSyntax.ORDER_BY.getSyntex()) > -1) {
            System.out.println("주문");
            regex = Pattern.compile(".+(?=ORDER)");    // 정규식 변수
            matcher = regex.matcher(targetSql);
            if (matcher.find()) {
                targetSql = matcher.group(0);
            }
        }
        // 세미콜론 제거
        targetSql.replace(";", "");

        ArrayList<String> temp = new ArrayList<>();
        temp.add("WHERE");
        String[] arrStr = targetSql.split("\\s+");
        for (int i = 0; i < arrStr.length; i++) {
            temp.add(arrStr[i]);
        }
        result.add(temp);




        return result;
    }
}
