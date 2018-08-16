package io.d3hunter.algorithms.parser;

import java.util.Stack;

/**
 * implementation of shunting-yard algorithm
 * https://en.wikipedia.org/wiki/Shunting-yard_algorithm
 */
public class ShuntingYard {
    public void parse(String expression) {
        SimpleLexer lexer = new SimpleLexer(expression);
        Stack<Token> stack = new Stack<Token>();
        Token currToken;
        while ((currToken = lexer.nextToken()) != null) {
            if (currToken == Token.NUMBER) {
                print(lexer.getValue());
            } else if (currToken == Token.LEFT_BRACKET) {
                stack.push(currToken);
            } else if (currToken == Token.RIGHT_BRACKET) {
                Token top;
                while ((top = stack.pop()) != Token.LEFT_BRACKET) {
                    print(top.getSymbol());
                }
            } else {// operator
                while (!stack.isEmpty()) {
                    Token peek = stack.peek();
                    if (peek == Token.LEFT_BRACKET || currToken.getPrecedence() > peek.getPrecedence()) {
                        break;
                    }
                    print(peek.getSymbol());
                    stack.pop();
                }
                stack.push(currToken);
            }
        }
        while (!stack.isEmpty()) {
            Token top = stack.pop();
            print(top.getSymbol());
        }
    }

    private void print(Object item) {
        System.out.print(item + " ");
    }

    public static void main(String[] args) {
        new ShuntingYard().parse("12 + 3 * (2 * 9 + (1 - 3) * 4) * (2 - 4) / 3");
    }
}

