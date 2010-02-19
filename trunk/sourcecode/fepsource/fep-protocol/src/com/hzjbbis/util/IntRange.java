package com.hzjbbis.util;

public class IntRange {
    private int min;
    private int max;
    private int hashCode = 0;

    public IntRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("max must equal or greater than min");
        }

        this.min = min;
        this.max = max;
        this.hashCode = (37 * (629 + min) + max);
    }

    public boolean contains(int val) {
        return ((val >= this.min) && (val <= this.max));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof IntRange)) {
            return false;
        }

        IntRange range = (IntRange) obj;
        return ((this.min == range.min) && (this.max == range.max));
    }

    public int hashCode() {
        return this.hashCode;
    }
}