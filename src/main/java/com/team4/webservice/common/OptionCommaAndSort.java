package com.team4.webservice.common;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	/** [Option] Alias 형태 표준화
	 *
	 * String	query - 쿼리문
	 * boolean 	hasAs - true인 경우에는 AS를 SELECT 절에 모두 붙여준다.
	 *
	 * String	query - 옵션을 적용한 쿼리문
	 * */

	public static String optAddAlias(String query, boolean hasAs) {
		// validation
		if (query.length() == 0 || query == null || "".equals(query)) {
			return query;
		}

		// SELECT절 추출
		Pattern regex = Pattern.compile("(?<=SELECT).+(?=FROM)");    // 정규식 변수
		Matcher matcher = regex.matcher(query);
		String targetSql = "";
		if (matcher.find()) {
			targetSql = matcher.group(0);
		}

		targetSql = StringUtils.trimWhitespace(targetSql);	// trim

		String[] selectSqlArr = targetSql.split(",");
		String resultQuery = "SELECT ";
		for (String column :
				selectSqlArr) {
			// 옵션값이 false면 AS를 제거한다
			if (!hasAs) {
				// AS가 있는 경우 제거한다.
				if (column.indexOf(" AS ") > -1) {
					column = column.replaceFirst(" AS ", " ");
				}
			}column
			resultQuery += column;
		}

		return resultQuery;
	}
}
