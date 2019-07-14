package com.team4.webservice.common;

import java.util.HashMap;
import java.util.Map;

public class OptionCommaAndSort {

	public static String exec(String query, boolean sort, boolean comma) {
		// TODO Auto-generated method stub
		Map<String, String> qr = new HashMap<>();

		boolean sortLeft = sort;
		boolean commaLeft = comma;
		qr = querySplit(query);

		if (commaLeft) {
			qr.put("SELECT", commaLeftSplit(qr.get("SELECT"), ","));
			qr.put("FROM", commaLeftSplit(qr.get("FROM"), ","));
			qr.put("WHERE", qr.get("WHERE"));
		} else {
			qr.put("SELECT", commaRightSplit(qr.get("SELECT"), ","));
			qr.put("FROM", commaRightSplit(qr.get("FROM"), ","));
			qr.put("WHERE", qr.get("WHERE"));
		}
		return printMap(qr, sortLeft);
	}

	public static Map<String, String> querySplit(String str) {
		Map<String, String> qr = new HashMap<>();

		int fromIndex = str.indexOf("FROM");
		int whereIndex = str.indexOf("WHERE");

		String selectStr = str.substring(6, fromIndex);
		String fromStr = str.substring(fromIndex + 4, whereIndex);
		String whereStr = str.substring(whereIndex + 5, str.length());

		qr.put("SELECT", selectStr.trim());
		qr.put("FROM", fromStr.trim());
		qr.put("WHERE", whereStr.trim());

		return qr;
	}

	public static String commaLeftSplit(String str, String sep) {
		String[] strArr = str.split(sep);
		String result = "";

		for (String temp : strArr) {
			result = result + temp.trim() + "\n       " + sep + " ";
		}
		// String.join("\n", strArr);
		result = result.substring(0, result.lastIndexOf(sep)).trim();
		return result;

		/*
		 * String[] names = new String[] {"홍길동", "임꺽정", "슈퍼맨", "배트맨", "아이언맨" };
		 * List<String> nameList = Arrays.asList(names); String sql1 = nameList.stream()
		 * .map(name -> "'" + name + "'" ) .collect(Collectors.joining(","));
		 */
	}

	public static String commaRightSplit(String str, String sep) {
		String[] strArr = str.split(sep);
		String result = "";

		for (String temp : strArr) {
			result = result + temp.trim() + sep + "\n       ";
		}
		result = result.substring(0, result.trim().lastIndexOf(","));
		return result;
	}

	public static String printMap(Map<String, String> qr, boolean sortLeft) {
		String returnStr = "";
		if (sortLeft) {
			returnStr = returnStr + "SELECT " + qr.get("SELECT") + "\n";
			returnStr = returnStr + "FROM   " + qr.get("FROM") + "\n";
			returnStr = returnStr + "WHERE  " + andSplit(qr.get("WHERE"), sortLeft);
		} else {
			returnStr = returnStr + "SELECT " + qr.get("SELECT") + "\n";
			returnStr = returnStr + "  FROM " + qr.get("FROM") + "\n";
			returnStr = returnStr + " WHERE " + andSplit(qr.get("WHERE"), sortLeft);
		}
		return returnStr;
	}

	public static String andSplit(String str, boolean sortLeft) {
		String[] strArr = str.split("AND");
		String result = "";

		for (String temp : strArr) {
			if (sortLeft) {
				result = result + temp.trim() + "\nAND    ";
			} else {
				result = result + temp.trim() + "\n   AND ";
			}
		}

		result = result.substring(0, result.lastIndexOf("AND")).trim();
		return result;
	}

}
