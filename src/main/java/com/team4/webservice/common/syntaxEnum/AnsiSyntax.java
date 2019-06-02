package com.team4.webservice.common.syntaxEnum;

public enum AnsiSyntax {
    INNER_JOIN("INNER JOIN"),
    OUTER_JOIN("OUTER JOIN"),
    LEFT_OUTER_JOIN("LEFT INNER JOIN"),
    RIGHT_OUTER_JOIN("RIGHT INNER JOIN"),
    PULL_OUTER_JOIN("PULL INNER JOIN"),
    USING("USING");

    String character;

    AnsiSyntax(String character) {
        this.character = character;
    }
}
