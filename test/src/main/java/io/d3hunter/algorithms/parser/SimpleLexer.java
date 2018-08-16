package io.d3hunter.algorithms.parser;

class SimpleLexer {
    int currPos = 0;
    String expression;
    Object value;

    public SimpleLexer(String expression) {
        this.expression = expression;
    }

    public Token nextToken() {
        while (currPos < expression.length() && expression.charAt(currPos) == ' ') {
            currPos++;
        }
        if (currPos == expression.length()) {
            return null;
        }
        char c = expression.charAt(currPos);
        if (c == '+' || c == '-' || c == '*' || c == '/') {
            currPos += 1;
            value = c;
            return Token.getOperator(c);
        } else if (c == '(') {
            currPos += 1;
            return Token.LEFT_BRACKET;
        } else if (c == ')') {
            currPos += 1;
            return Token.RIGHT_BRACKET;
        } else if (Character.isDigit(c)) {
            int end = currPos;
            while (end < expression.length() && Character.isDigit(expression.charAt(end))) {
                end++;
            }
            value = Integer.valueOf(expression.substring(currPos, end));
            currPos = end;
            return Token.NUMBER;
        }
        throw new RuntimeException("cannot parse char " + c);
    }

    public Object getValue() {
        return value;
    }
}

