package io.d3hunter.algorithms.parser;

import static io.d3hunter.algorithms.parser.Token.*;

/**
 * Operator-precedence parser using Precedence climbing method
 * https://en.wikipedia.org/wiki/Operator-precedence_parser
 */
public class PrecedenceClimbing {
    public String parse(String expression) {
        SimpleLexer lexer = new SimpleLexer(expression);
        return parse(parsePrimary(lexer), 0, lexer);
    }

    private String parse(String lhs, int minPrecedence, SimpleLexer lexer) {
        Token lookahead = lexer.peekToken();
        while (isBinaryOperator(lookahead) && lookahead.getPrecedence() > minPrecedence) {
            Token op = lexer.nextToken();
            String rhs = parsePrimary(lexer);
            lookahead = lexer.peekToken();

            while (isBinaryOperator(lookahead) && lookahead.getPrecedence() > op.getPrecedence()) {
                rhs = parse(rhs, op.getPrecedence(), lexer);
                lookahead = lexer.peekToken();
            }
            lhs += " " + rhs + " " + op.getSymbol();
        }
        return lhs;
    }

    private boolean isBinaryOperator(Token token) {
        return token != null && (token == ADD || token == MIN || token == MUL || token == DIV);
    }

    private String parsePrimary(SimpleLexer lexer) {
        final Token token = lexer.nextToken();
        if (token == NUMBER) {
            return lexer.getValue().toString();
        } else {
            // token == LEFT_BRACKET
            String primary = parse(parsePrimary(lexer), 0, lexer);
            // consume right bracket
            lexer.nextToken();
            return primary;
        }
    }

    public static void main(String[] args) {
        System.out.println(new PrecedenceClimbing().parse("12 - (1 + 2 * (3 - 4)) / 9 * 3"));
    }
}

