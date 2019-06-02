package com.team4.webservice.common.syntaxEnum;

import com.sun.tools.corba.se.idl.constExpr.Equal;

public enum OperatorsSyntax {
    EQUAL("="),
    NOT("NOT"),
    LIKE("LIKE"),
    AND("AND"),
    OR("OR"),
    BETWEEN("BETWEEN"),
    IS_NULL("IS NULL"),
    NOT_BETWEEN("NOT BETWEEN"),
    NOT_IS("NOT IS"),
    IS_NOT_NULL("IS NOT NULL"),
    EXCLAMATION_POINT("!"),
    LESS_THAN("<"),
    GREATER_THAN(">"),
    LESS_THAN_EQUAL("<="),
    GREATER_THAN_EQUAL(">="),
    NOT_EQUAL_TYPE1("!="),
    NOT_EQUAL_TYPE2("^="),
    NOT_EQUAL_TYPE3("<>"),
    ;

    String character;

    OperatorsSyntax(String character) {
        this.character = character;

    }
}
