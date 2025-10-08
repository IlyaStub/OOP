package ru.nsu.gstubarev.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a variable in mathematical expressions.
 * Stores the variable name and can be evaluated when variable values are provided.
 */
public class Variable extends Expression {
    private final String variable;
    private static String lastVarEqValue;
    private static Map<String, Double> cachedMap;

    /**
     * Constructs a variable expression with the given name.
     *
     * @param variable the name of the variable
     */
    public Variable(String variable) {
        this.variable = variable;
    }

    /**
     * Evaluates the variable by looking up its value in the provided assignments.
     *
     * @param varEqValue string in format "var1=value1; var2=value2;..."
     * @return the value assigned to this variable
     */
    @Override
    public double eval(String varEqValue) {
        if (!varEqValue.equals(lastVarEqValue)) {
            cachedMap = stringEvalToConvenient(varEqValue);
            lastVarEqValue = varEqValue;
        }
        return cachedMap.get(variable);
    }

    /**
     * Method to simplify the add.
     *
     * @return just variable
     */
    @Override
    public Expression simplify() {
        return this;
    }

    /**
     * Computes the derivative of the variable expression.
     * The derivative is 1 if differentiating with respect to this variable, 0 otherwise.
     *
     * @param variable the variable with respect to which to differentiate
     * @return Number(1) if variables match, Number(0) otherwise
     */
    @Override
    public Expression derivative(String variable) {
        if (!Objects.equals(this.variable, variable)) {
            return new Number(0);
        } else {
            return new Number(1);
        }
    }

    /**
     * Returns the name of the variable.
     *
     * @return the variable name
     */
    @Override
    public String toString() {
        return variable;
    }

    private Map<String, Double> stringEvalToConvenient(String varEqValue) {
        var map = new HashMap<String, Double>();
        varEqValue.replaceAll("\\s", "");
        String[] variables = varEqValue.split(";");
        for (String s : variables) {
            String[] nameValue = s.split("=");
            map.put(nameValue[0], Double.valueOf(nameValue[1]));
        }
        return map;
    }

    //эххх жаль плохая попытка, но почти полусилось...
    private double stringFindSubstring(String varEqValue, String str) {
        String s = varEqValue.replaceAll("\\s", "");
        int len_str = str.length();
        int d = s.indexOf(String.format("%s=", str));
        int i = d + len_str + 1;
        while (i < s.length() && (Character.isDigit(s.charAt(i)) || (s.charAt(i) == '.'))) {
            i++;
        }
        return Double.parseDouble(s.substring(d + len_str + 1, i));
    }
}
