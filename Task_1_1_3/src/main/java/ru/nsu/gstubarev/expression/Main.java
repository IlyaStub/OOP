package ru.nsu.gstubarev.expression;

/**
 * Main class demonstrating the expression system functionality.
 */
public class Main {
    /**
     * Main method.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x")));
        e.print();
        Expression de = e.derivative("x");
        de.print();
        double result = e.eval("x=10; y=13");
        System.out.println(result);
        ExpressionParser parser = new ExpressionParser();
        Expression as = parser.parse("(4*((2+1)*3))");
        as.print();
    }
}
