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
     * @throws RuntimeException if the variable is not found in the assignments
     */
    @Override
    public double eval(String varEqValue) {
        Map<String, Double> map = stringEvalToConvenient(varEqValue);
        return map.get(variable);
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

    //надо переделать
    private Map<String, Double> stringEvalToConvenient(String varEqValue) {
        var map = new HashMap<String, Double>();
        StringBuilder variableName;
        StringBuilder variableValue;
        int i = 0;
        while (i < varEqValue.length()) {
            variableName = new StringBuilder();
            variableValue = new StringBuilder();
            while (varEqValue.charAt(i) != '=') {
                variableName.append(varEqValue.charAt(i));
                i++;
            }
            i++;
            while (varEqValue.charAt(i) != ';') {
                variableValue.append(varEqValue.charAt(i));
                if (i == varEqValue.length() - 1) {
                    break;
                }
                i++;
            }
            map.put(String.valueOf(variableName), Double.valueOf(String.valueOf(variableValue)));
            i += 2;
        }
        return map;
    }
}
