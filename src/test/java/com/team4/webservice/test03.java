package com.team4.webservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class test03 {

	public static void main(String[] args) {
// TODO Auto-generated method stub
		String str = "SELECT A.REC_CD AS RESCD, A.DEP_DT AS DEPDT FROM MAIN AS A, DETAIL AS B WHERE A.ID = B.ID AND A.PASS = B.PASS GROUP BY A.REC_CD ORDER BY A.REC_CD";
		Map<String, String> qr = new HashMap<>();
		int maxKeySize = 0;
		boolean sortLeft = false;
		boolean commaLeft = false;


		// 쿼리 자르기
		qr = querySplit(str);
		// 쿼리 key set
		Set<String> qrKeySet = qr.keySet();
		// 키중 가장 긴 값
		for(String key : qrKeySet){
			if(maxKeySize < key.length()){
				maxKeySize = key.length();
			}
		}
		String blank = "";
		for(int i = 0; i <= maxKeySize ; i++){
			blank = blank + " ";
		}
		//comma Left or right 정렬
		if (commaLeft) {
			qr.put("SELECT", commaLeftSplit(qr.get("SELECT"), ",", blank));
			qr.put("FROM", commaLeftSplit(qr.get("FROM"), ",", blank));
			qr.put("GROUP BY", commaLeftSplit(qr.get("GROUP BY"),",", blank));
			qr.put("ORDER BY", commaLeftSplit(qr.get("ORDER BY"), ",", blank));
		} else {
			qr.put("SELECT", commaRightSplit(qr.get("SELECT"), ",", blank));
			qr.put("FROM", commaRightSplit(qr.get("FROM"), ",", blank));
			qr.put("GROUP BY", commaRightSplit(qr.get("GROUP BY"), ",", blank));
			qr.put("ORDER BY", commaRightSplit(qr.get("ORDER BY"), ",", blank));
		}

		//쿼리문 출력
		printMap(qr, sortLeft, blank);

		System.out.println(printMap(qr, sortLeft, blank));
	}

	public static Map<String, String> querySplit(String str) {
		Map<String, String> qr = new HashMap<>();

		int fromIndex = str.indexOf("FROM");
		int whereIndex = str.indexOf("WHERE");
		int groupByIndex = 0;
		int orderByIndex = 0;

		if(str.contains("GROUP BY")){
			groupByIndex = str.indexOf("GROUP BY");
		} else {
			groupByIndex = orderByIndex;
		}


		if(str.contains("ORDER BY")){
			orderByIndex = str.indexOf("ORDER BY");
		} else {
			groupByIndex = str.length();
			orderByIndex = str.length();
		}

		String selectStr = str.substring(6, fromIndex);
		String fromStr = str.substring(fromIndex + 4, whereIndex);
		String whereStr = str.substring(whereIndex + 5, groupByIndex);
		String groupByStr = "";
		String orderByStr = "";


		qr.put("SELECT", selectStr.trim());
		qr.put("FROM", fromStr.trim());
		qr.put("WHERE", whereStr.trim());

		if (str.contains("GROUP BY")){
			groupByStr = str.substring(groupByIndex + 8, orderByIndex);
			qr.put("GROUP BY", groupByStr.trim());
		}
		if (str.contains("ORDER BY")){
			orderByStr = str.substring(orderByIndex + 8, str.length());
			qr.put("ORDER BY", orderByStr.trim());
		}




		return qr;
	}

	public static String commaLeftSplit(String str, String sep, String blank) {
		String[] strArr = str.split(sep);
		String result = "";

		for (String temp : strArr) {
			result = result + temp.trim() + "\n" + blank + sep + " ";
		}
		result = result.substring(0, result.lastIndexOf(sep)).trim();
		return result;
	}

	public static String commaRightSplit(String str, String sep, String blank) {
		String[] strArr = str.split(sep);
		String result = "";

		for (String temp : strArr) {
			result = result + temp.trim() + sep + "\n"+ blank;
		}
		result = result.substring(0, result.trim().lastIndexOf(","));
		return result;
	}

	public static String printMap(Map<String, String> qr, boolean sortLeft, String blank) {
		String returnStr = "";
		if (sortLeft) {
			returnStr = returnStr + "SELECT   " + qr.get("SELECT") + "\n";
			returnStr = returnStr + "FROM     " + qr.get("FROM") + "\n";
			returnStr = returnStr + "WHERE    " + andSplit(qr.get("WHERE"), sortLeft, blank);
			if(qr.containsKey("GROUP BY")){
				returnStr = returnStr + "\nGROUP BY " + qr.get("GROUP BY");
			}
			if(qr.containsKey("ORDER BY")){
				returnStr = returnStr + "\nORDER BY " + qr.get("ORDER BY");
			}
		} else {
			returnStr = returnStr + "  SELECT " + qr.get("SELECT") + "\n";
			returnStr = returnStr + "    FROM " + qr.get("FROM") + "\n";
			returnStr = returnStr + "   WHERE " + andSplit(qr.get("WHERE"), sortLeft, blank);
			if(qr.containsKey("GROUP BY")){
				returnStr = returnStr + "\nGROUP BY " + qr.get("GROUP BY");
			}
			if(qr.containsKey("ORDER BY")){
				returnStr = returnStr + "\nORDER BY " + qr.get("ORDER BY");
			}
		}

		return returnStr;
	}

	public static String andSplit(String str, boolean sortLeft, String blank) {
		String[] strArr = str.split("AND");
		String result = "";

		blank = blank.substring(0, blank.lastIndexOf(" ")-3);

		for (String temp : strArr) {
			if (sortLeft) {
				result = result + temp.trim() + "\nAND "+blank;
			} else {
				result = result + temp.trim() + "\n"+ blank + "AND ";
			}
		}

		result = result.substring(0, result.lastIndexOf("AND")).trim();
		return result;
	}

}
