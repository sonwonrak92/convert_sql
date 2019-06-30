package com.team4.webservice.common;

import com.team4.webservice.common.syntaxEnum.OperatorsSyntax;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryConvertUtil {

    static final String OPERATOR_REGEX = "(\\+|-|\\*|\\/|=|>|<|>=|<=|<>|&|\\||%|!|\\^|\\(|\\)";
    static final String FIND_SPACE_REGEX = "(\\s{2,})";
    static final String BETWEEN_QUOTES_REGEX = "(\"([^\"]|\"\")*\")|(\'([^\']|\'\')*\')";
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

    public static ArrayList<String> GetQueryText(String query) {
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

	public static String SetQueryText(String newQuery, ArrayList<String> oldQueryText){ 
		
		StringBuffer sb = new StringBuffer();
		String[] newQuerySplitArry = newQuery.split(BETWEEN_QUOTES_REGEX);
		
	  
	  for(int i = 0; i < oldQueryText.size(); i++){ 
		  newQuerySplitArry[i] =  newQuerySplitArry[i] + oldQueryText.get(i);
	  }
	  
	  for(String str : newQuerySplitArry) {
		  System.out.println(str);
		  sb.append(str);
	  }
	
	 return sb.toString(); 
	}

	// ""없는 AS의 경우 일괄 ""적용

	//채유진 2019.06.23
	public static boolean valCheck (String str) {
		boolean chk = true;

		if(str.contains("SELECT ") && str.contains(" FROM ")) {
			for(OperatorsSyntax op : OperatorsSyntax.values()) {
				String chr = op.character;
				if(str.contains(chr)) {
					if(str.contains(" WHERE ") || str.contains(" ON ")) {
						return chk;
					}else chk=false;
				};
			}
		}else chk=false;

		return chk;
	}
}
