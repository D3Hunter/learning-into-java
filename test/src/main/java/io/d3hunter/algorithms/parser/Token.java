package io.d3hunter.algorithms.parser;

enum Token {
    NUMBER(-1, ""), ADD(5, "+"), MIN(5, "-"), MUL(6, "*"), DIV(6, "/"), LEFT_BRACKET(-1, ")"), RIGHT_BRACKET(-1, ")");
    private int precedence;
    private String symbol;

    Token(int precedence, String symbol) {
        this.precedence = precedence;
        this.symbol = symbol;
    }

    public int getPrecedence() {
        return precedence;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Token getOperator(char c) {
        if (c == '+') {
            return ADD;
        } else if (c == '-') {
            return MIN;
        } else if (c == '*') {
            return MUL;
        } else {
            return DIV;
        }
    }
}

