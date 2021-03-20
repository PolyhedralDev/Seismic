package com.dfsek.seismic.function.exotic;

import com.dfsek.paralithic.Expression;
import com.dfsek.paralithic.eval.parser.Parser;
import com.dfsek.paralithic.eval.parser.Scope;
import com.dfsek.paralithic.eval.tokenizer.ParseException;
import com.dfsek.seismic.NoiseSampler;

/**
 * NoiseSampler implementation using Paralithic expression
 */
public class ExpressionSampler implements NoiseSampler {
    private final Expression expression;

    public ExpressionSampler(String equation, Parser parser, Scope parent) throws ParseException {
        Scope scope = new Scope().withParent(parent);

        scope.addInvocationVariable("x");
        scope.addInvocationVariable("y");
        scope.addInvocationVariable("z");

        this.expression = parser.parse(equation, scope);
    }

    @Override
    public double getNoise(double x, double y) {
        return getNoise(x, 0, y);
    }

    @Override
    public double getNoise(double x, double y, double z) {
        return expression.evaluate(x, y, z);
    }

    @Override
    public double getNoiseSeeded(int seed, double x, double y) {
        return getNoise(x, y);
    }

    @Override
    public double getNoiseSeeded(int seed, double x, double y, double z) {
        return getNoise(x, y, z);
    }
}
