package com.zerone.scms.dontMind;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator<T extends Number> {
//    private T num1;
//    private T num2;
//
//    public Calculator(T num1, T num2) {
//        this.num1 = num1;
//        this.num2 = num2;
//    }

    public T add(T num1, T num2) {
        Number result = null;
        BigDecimal bigDecimal1 = new BigDecimal(num1.toString());
        BigDecimal bigDecimal2 = new BigDecimal(num2.toString());
        result = bigDecimal1.add(bigDecimal2);
        return (T) result;
    }

    public T sub(T num1, T num2) {
        Number result = null;
        BigDecimal bigDecimal1 = new BigDecimal(num1.toString());
        BigDecimal bigDecimal2 = new BigDecimal(num2.toString());
        result = bigDecimal1.subtract(bigDecimal2);
        return (T) result;
    }

    public T mult(T num1, T num2) {
        Number result = null;
        BigDecimal bigDecimal1 = new BigDecimal(num1.toString());
        BigDecimal bigDecimal2 = new BigDecimal(num2.toString());
        result = bigDecimal1.multiply(bigDecimal2);
        return (T) result;
    }

    public T div(T num1, T num2) {
        Number result = null;
        BigDecimal bigDecimal1 = new BigDecimal(num1.toString());
        BigDecimal bigDecimal2 = new BigDecimal(num2.toString());
        result = bigDecimal1.divide(bigDecimal2, 2, RoundingMode.FLOOR);
        return (T) result;
    }
}
