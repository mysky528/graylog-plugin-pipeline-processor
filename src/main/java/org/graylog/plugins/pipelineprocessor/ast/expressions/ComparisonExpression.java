/**
 * This file is part of Graylog Pipeline Processor.
 *
 * Graylog Pipeline Processor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog Pipeline Processor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog Pipeline Processor.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.graylog.plugins.pipelineprocessor.ast.expressions;

import org.graylog.plugins.pipelineprocessor.EvaluationContext;

public class ComparisonExpression extends BinaryExpression implements LogicalExpression {
    private final String operator;

    public ComparisonExpression(Expression left, Expression right, String operator) {
        super(left, right);
        this.operator = operator;
    }

    @Override
    public Object evaluate(EvaluationContext context) {
        return evaluateBool(context);
    }

    @Override
    public Class getType() {
        return Boolean.class;
    }

    @Override
    public boolean evaluateBool(EvaluationContext context) {

        final Object leftValue = this.left.evaluate(context);
        final Object rightValue = this.right.evaluate(context);
        if (leftValue instanceof Double || rightValue instanceof Double) {
            return compareDouble(operator, (double) leftValue, (double) rightValue);
        } else {
            return compareLong(operator, (long) leftValue, (long) rightValue);
        }
    }

    @SuppressWarnings("Duplicates")
    private boolean compareLong(String operator, long left, long right) {
        switch (operator) {
            case ">":
                return left > right;
            case ">=":
                return left >= right;
            case "<":
                return left < right;
            case "<=":
                return left <= right;
            default:
                return false;
        }
    }

    @SuppressWarnings("Duplicates")
    private boolean compareDouble(String operator, double left, double right) {
        switch (operator) {
            case ">":
                return left > right;
            case ">=":
                return left >= right;
            case "<":
                return left < right;
            case "<=":
                return left <= right;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }
}