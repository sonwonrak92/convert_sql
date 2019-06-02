package com.team4.webservice.common.syntaxEnum;

public enum CommonSyntax {

	   SELECT("SELECT"),
	   FROM("FROM") ,
	   WHERE("WHERE"),
	   IS_NOT_NULL("IS NOT NULL"),
	   IS_NULL("IS NULL"),
	   DISTINCT("DISTINCT"),
	   WITH("WITH"),
	   UNION_ALL("UNION ALL"),
	   UNION("UNION"),
	   LIKE("LIKE"),
	   HAVING("HAVING"),
	   GROUP_BY("GROUP BY"),
	   ORDER_BY("ORDER BY"),
	   DESC("DESC"),
	   ASC("ASC"),
	   BETWEEN("BETWEEN"),
	   AND("AND"),
	   OR("OR"),
	   NOT("NOT");
	

	private String str;
	
	private CommonSyntax(String str) {
		this.str = str;
	}
	
	
	
	   
}
