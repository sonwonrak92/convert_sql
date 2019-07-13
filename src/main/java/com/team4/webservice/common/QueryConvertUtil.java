package com.team4.webservice.common;

import com.team4.webservice.common.syntaxEnum.OperatorsSyntax;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryConvertUtil {

    static final String OPERATOR_REGEX = "(\\+|-|\\*|\\/|=|>|<|>=|<=|<>|&|\\||%|!|\\^|\\(|\\)";
    static final String FIND_SPACE_REGEX = "(\\s{2,})";
    static final String BETWEEN_QUOTES_REGEX = "(\"([^\"]|\"\")*\")|(\'([^\']|\'\')*\')";
    static final String BETWEEN_FROM_ORDER_BY = "(?<=FROM).+(?=ORDER BY)";
    static final String BETWEEN_FROM_GROUP_BY = "(?<=FROM).+(?=GROUP BY)";
    // 공백제거
    /*
     * 공백제거
     * 1. 쿼리의 개행문자를 공백으로 변경
     * 2. 쿼리의 모든 연산자에 공백 추가 ex) a<b => a < b 3. 연속된 공백에 대해
     * 4. >= , <= 와 같은 단일 연산자의 경우 사이공백 제거
     * 옵션처리(대소문자처리, )
     */
    public static String replaceLnToSpace(String query) {
        /*
         * 파라미터로 넘어오는 문자열의 모든 개행문자를 공백으로 변환
         * input  : he
         * 			llo
         * output : he llo
         */
        String result =  query.replaceAll("\n", " ");

        return result;
    }

    public static String replaceAllSingleSpace(String query) {
        /*
         * 파라미터로 넘어오는 문자열의 모든 연속된 공백문자를 단일 공백으로 변환
         * input  : h e   l   l   o
         * output : h e l l o
         */
        String result = query.replaceAll(FIND_SPACE_REGEX, " ");

        return result;
    }

    public static ArrayList<String> getQueryText(String query) {
        /*
         * quotes 사이 내용을 가져와 배열로 만든다.
         *  ex)SELECT NAME AS "이름" FROM USER A WHERE A.NAME = '김다미'
         *  "이름", '김다미'
         *  quotes 사이의 숫자 관련해서는 한번 더 확인해볼 필요가 있음.
         *
         */
        Pattern p = Pattern.compile(BETWEEN_QUOTES_REGEX);
        Matcher matcher = p.matcher(query);
        ArrayList<String> queryTextArry =  new ArrayList<>();
        int i = 0;

        while (matcher.find()) {
            queryTextArry.add(matcher.group(i).toString());
            i++;
        }

        return queryTextArry;
    }

    public static String setQueryText(String newQuery, ArrayList<String> oldQueryText){

        StringBuffer sb = new StringBuffer();
        String[] newQuerySplitArry = newQuery.split(BETWEEN_QUOTES_REGEX);


        for(int i = 0; i < oldQueryText.size(); i++){
            newQuerySplitArry[i] =  newQuerySplitArry[i] + oldQueryText.get(i);
        }

        for(String str : newQuerySplitArry) {
            sb.append(str);
        }

        return sb.toString();
    }

    // ""없는 AS의 경우 일괄 ""적용

    //채유진 2019.06.23
    //채유진 2019.06.30 유효성검사 항목 추가
    public static boolean valCheck (String str) {
        boolean chk = true;

        if(str.contains("FULL OUTER JOIN ")){ //FULL OUTER JOIN 은 오라클에서 사용불가
            chk = false;
        } else {
            if (str.contains("SELECT ") && str.contains(" FROM ")) { // SELECT FROM 없는지 체크
                for (OperatorsSyntax op : OperatorsSyntax.values()) {
                    String chr = op.character;
                    if (str.contains(chr)) {
                        if (str.contains(" WHERE ") || str.contains(" ON ")) { //조건절 있으면 WHERE나 ON 있어야함
                            return chk;
                        } else chk = false;
                    }
                    ;
                }
            } else chk = false;
        }
        return chk;
    }

    /*표준화를 거친 new쿼리 형태에 맞게 변환된 JOIN조건절을 세팅 */
    public static String setConvertJoinQuery(String newQuery, String convertedJoinSection){
    	String convertedNewQuery = null;
    	newQuery = newQuery.replaceAll(";", "");
    	if( !newQuery.contains(" GROUP BY ") && !newQuery.contains(" ORDER BY ") ) {
    		newQuery += ";";
    		return newQuery;
    	}
    	 
    	if(!newQuery.contains(" GROUP BY ")) {
    		convertedNewQuery = newQuery.replaceAll(BETWEEN_FROM_ORDER_BY, convertedJoinSection);
    	}else{
    		convertedNewQuery = newQuery.replaceAll(BETWEEN_FROM_GROUP_BY, convertedJoinSection);
    	}
    	convertedNewQuery += ";";
		return convertedNewQuery;
    }
    
    public static String defaultLnSetting(String query) {
    	//DB에 따라 재구성할 필요 있음. 현재 ORACLE 기준으로 작성    	

    	query = query.replaceAll(",","\n,")
    				 .replaceAll("FROM","\nFROM,")
    				 .replaceAll("WHERE","\nWHERE,")
    			  	 .replaceAll("AND","\nAND")
    				 .replaceAll("ORDER BY","\nORDER BY")
    			 	 .replaceAll("GROUP BY","\nGROUP BY");
    	
        return query;
    }

    public static String setQueryOption(String query, Map<String, Boolean> option) {
        /*
         *	comma : 콤마옵션
         *	newLine : 개행옵션
         *	upperCase : 대소문자옵션
         */ 
    	
        System.out.println("옵션쪽입니다............");
        System.out.println(option.get("comma"));
        System.out.println(option.get("newLine"));
        System.out.println(option.get("upperCase"));
        System.out.println("옵션쪽입니다............");
     	
        
    	boolean isComma = option.get("comma");
    	boolean isNewLine = option.get("newLine");
		boolean isUpperCase = option.get("upperCase");
		
	   	 
        System.out.println("옵션쪽입니다2222............");
        System.out.println(isComma);
        System.out.println("옵션쪽입니다2222............");
     	
    	
    	/* 콤마옵션 적용 */    	
		if( isComma ) {
			//query = query.setCommaOption(true);
		}else if( !isComma ){
			//query = query.setCommaOption(false);
		}
		
		/* 개행옵션 적용 */		
		if( isNewLine ) {
			//query = query.setNewLineOption(true);
		}else if( !isNewLine ){
			//query = query.setNewLineOption(false);
		}
		
		/* 대소문자 적용 */
		if( isUpperCase ) {
			query = query.toUpperCase();
		}else if( !isUpperCase ){
			query = query.toLowerCase();
		}

        return query;
    }


    	
}