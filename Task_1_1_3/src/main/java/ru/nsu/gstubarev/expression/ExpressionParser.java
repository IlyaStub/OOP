package ru.nsu.gstubarev.expression;

/**
 * A simple parser class that only works with parentheses.
 */
public class ExpressionParser {
    private String input;
    private int position;

    /**
     * Method to parse an expression from string.
     *
     * @param input - string with expression
     * @return expression after parsing
     */
    public Expression parse(String input) {
        this.input = input;
        this.position = 0;
        return parseExp();
    }

    private Expression parseExp() {
        if (position >= input.length()) {
            throw new RuntimeException("Unexpected end of expression");
        }
        char nowChar = input.charAt(position);
        if (nowChar == '(') {
            return parseOperation();
        } else if (Character.isDigit(nowChar)) {
            return parseNumber();
        } else if (Character.isLetter(nowChar)) {
            return parseVariable();
        } else {
            throw new RuntimeException("Unexpected character: " + input.charAt(position));
        }
    }

    private Expression parseOperation() {
        position++;
        Expression left = parseExp();
        char op = input.charAt(position++);
        Expression right = parseExp();
        if (input.charAt(position) != ')'){
            throw new RuntimeException("There is no closing parenthesis");
        }
        position++;
        return switch (op) {
            case '+' -> new Add(left, right);
            case '-' -> new Sub(left, right);
            case '*' -> new Mul(left, right);
            case '/' -> new Div(left, right);
            default -> throw new RuntimeException("Unexpected operation");
        };
    }

    private Number parseNumber() {
        StringBuilder number = new StringBuilder();
        while (position < input.length() && Character.isDigit(input.charAt(position))) {
            number.append(input.charAt(position++));
        }
        return new Number(Double.parseDouble(String.valueOf(number)));
    }

    private Variable parseVariable() {
        StringBuilder variable = new StringBuilder();
        while (position < input.length() && Character.isLetter(input.charAt(position))) {
            variable.append(input.charAt(position++));
        }
        return new Variable(String.valueOf(variable));
    }
}
