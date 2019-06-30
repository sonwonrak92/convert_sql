package com.team4.webservice.common.syntaxEnum;

public enum AnsiSyntax {
    INNER_JOIN("INNER JOIN"),
    OUTER_JOIN("OUTER JOIN"),
    LEFT_OUTER_JOIN("LEFT OUTER JOIN"),
    RIGHT_OUTER_JOIN("RIGHT OUTER JOIN"),
    FULL_OUTER_JOIN("FULL OUTER JOIN"),     //Validation을 위해 사용
    USING("USING");

    String str;

    AnsiSyntax(String str) {
        this.str = str;
    }

    public String getSyntex() {
        return str;
    }
}
