package com.team4.webservice.service.ansi;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.team4.webservice.common.QueryConvertUtil;
import com.team4.webservice.common.syntaxEnum.CommonSyntax;

@Service
public class AnsiToOracle implements Ansi{
	
	public String exec(String query) {
		
        query = QueryConvertUtil.replaceAllSingleSpace(query);

        //2019.06.30 이상훈 코드 추가(split 후 최준우 파라미터 전송)
        AnsiToOracle ansiToOracle = new AnsiToOracle();
        ArrayList<ArrayList<String>> list = ansiToOracle.parseStrToArr(query);
        StringBuffer sb = ansiToOracle.moveToFrom(list);
        String convertedQuery =  QueryConvertUtil.setConvertJoinQuery(query, sb.toString());
        //유진이 옵션들어가는 부분
        return convertedQuery;
	}



    public StringBuffer moveToFrom(ArrayList<ArrayList<String>> list) {
    	System.out.println(list.toString());
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

        for(int i=0;i<result.size();i++){
            sb.append(result.get(i));
            sb.append(" ");
        }
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
        String[] arrSql =  targetSql.split("\\s+");
        for (int i = 0; i < arrSql.length; i++) {
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
            ArrayList<String> temp = new ArrayList<>();
            temp.add(flag);
            if ("".equals(flag)) {
                continue;
            }
            for (j = j; j < arrSql.length; j++) {
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
