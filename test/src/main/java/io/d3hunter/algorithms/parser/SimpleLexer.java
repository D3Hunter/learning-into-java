package io.d3hunter.algorithms.parser;

class SimpleLexer {
    int currPos = 0;
    String expression;
    Object value;

    public SimpleLexer(String expression) {
        this.expression = expression;
    }

    public Token peekToken() {
        return getToken(true);
    }

    public Token nextToken() {
        return getToken(false);
    }

    private Token getToken(boolean peek) {
        while (currPos < expression.length() && expression.charAt(currPos) == ' ') {
            currPos++;
        }
        if (currPos == expression.length()) {
            return null;
        }
        char c = expression.charAt(currPos);
        int delta = 1;
        try {
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                value = c;
                return Token.getOperator(c);
            } else if (c == '(') {
                return Token.LEFT_BRACKET;
            } else if (c == ')') {
                return Token.RIGHT_BRACKET;
            } else if (Character.isDigit(c)) {
                int end = currPos;
                while (end < expression.length() && Character.isDigit(expression.charAt(end))) {
                    end++;
                }
                value = Integer.valueOf(expression.substring(currPos, end));
                delta = end - currPos;
                return Token.NUMBER;
            }
        } finally {
            if (!peek) {
                currPos += delta;
            }
        }
        throw new RuntimeException("cannot parse char " + c);
    }

    public Object getValue() {
        return value;
    }
}

