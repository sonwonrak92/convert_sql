package com.team4.webservice.common.syntaxEnum;

public enum CommonSyntax {

	   SELECT,
	   FROM ,
	   WHERE,
	   IS_NOT_NULL("IS NOT NULL"),
	   IS_NULL,
	   DISTICT,
	   WITH,
	   UNION_ALL,
	   UNION,
	   LIKE,
	   HAVING,
	   GROUP_BY,
	   ORDER_BY,
	   DESC,
	   ASC,
	   BETWEEN,
	   AND,
	   OR,
	   NOT;
	

	private String str;

	private CommonSyntax(String str) {
		this.str = str;
	}
	
	
	
	   
}
