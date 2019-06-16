package com.team4.webservice.common.syntaxEnum;

public enum SpecialCharactersSyntax {
    PERCENT_SIGN("%"),
    UNDERBAR("_"),
    COMMA(",");

    private String character;

    SpecialCharactersSyntax(String character) {
        this.character = character;
    }

    public String getSyntex() {
        return character;
    }
}
