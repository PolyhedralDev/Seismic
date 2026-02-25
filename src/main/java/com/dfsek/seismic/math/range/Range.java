package com.dfsek.seismic.math.range;

public record Range(double min, double max) {
    public Range {
        if(min > max) {
            throw new IllegalArgumentException("Min cannot be greater than max");
        }
    }
    public Range add(double i) {
        return new Range(min + i, max + i);
    }
    public Range add(Range that) {
        return new Range(this.min + that.min, this.max + that.max);
    }
    public Range sub(double i) {
        return new Range(min - i, max - i);
    }
    public Range sub(Range that) {
        return new Range(this.min - that.min, this.max - that.max);
    }
    public Range mul(double i) {
        return new Range(min * i, max * i);
    }
    public Range mul(Range that) {
        double minmin = this.min * that.min;
        double minmax = this.min * that.max;
        double maxmin = this.max * that.min;
        double maxmax = this.max * that.max;
        return new Range(Math.min(Math.min(minmin, minmax), Math.min(maxmin, maxmax)), Math.max(Math.max(minmin, minmax), Math.max(maxmin, maxmax)));
    }
    public Range frac(double i) {
        //TODO: zero cases :)
        return new Range(i / min, i / max);
    }
    public Range div(double i) {
        return new Range(min / i, max / i);
    }
    public Range div(Range that) {
        return mul(that.frac(1));
    }
    public Range and(Range that) {
        return new Range(Math.max(this.min, that.min), Math.min(this.max, that.max));
    }
    public Range or(Range that) {
        return new Range(Math.min(this.min, that.min), Math.max(this.max, that.max));
    }
    public Range min(Range that) {
        return new Range(Math.min(this.min, that.min), Math.min(this.max, that.max));
    }
    public Range max(Range that) {
        return new Range(Math.max(this.min, that.min), Math.max(this.max, that.max));
    }

    public static Range one() {
        return new Range(-1, 1);
    }
    public static Range infinity() {
        return new Range(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
    public static Range positive() {
        return new Range(0, Double.POSITIVE_INFINITY);
    }
    public static Range negative() {
        return new Range(Double.NEGATIVE_INFINITY, 0);
    }
    public static Range value(double value) {
        return new Range(value, value);
    }
}
