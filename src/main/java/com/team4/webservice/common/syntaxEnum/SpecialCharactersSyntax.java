package com.team4.webservice.common.syntaxEnum;

public enum SpecialCharactersSyntax {
    PERCENT_SIGN("%"),
    UNDER_BAR("_");

    String character;

    SpecialCharactersSyntax(String character) {
        this.character = character;
    }
}
