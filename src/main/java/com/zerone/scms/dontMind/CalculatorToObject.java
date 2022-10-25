package com.zerone.scms.dontMind;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorToObject {
    private Object num1;
    private Object num2;

    public CalculatorToObject(Object num1, Object num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    public Object add() {
        Number result = null;
        BigDecimal bigDecimal1 = new BigDecimal(num1.toString());
        BigDecimal bigDecimal2 = new BigDecimal(num2.toString());
        result = bigDecimal1.add(bigDecimal2);
        return result;
    }

    public Object sub() {
        Number result = null;
        BigDecimal bigDecimal1 = new BigDecimal(num1.toString());
        BigDecimal bigDecimal2 = new BigDecimal(num2.toString());
        result = bigDecimal1.subtract(bigDecimal2);
        return result;
    }

    public Object mult() {
        Number result = null;
        BigDecimal bigDecimal1 = new BigDecimal(num1.toString());
        BigDecimal bigDecimal2 = new BigDecimal(num2.toString());
        result = bigDecimal1.multiply(bigDecimal2);
        return result;
    }

    public Object div() {
        Number result = null;
        BigDecimal bigDecimal1 = new BigDecimal(num1.toString());
        BigDecimal bigDecimal2 = new BigDecimal(num2.toString());
        result = bigDecimal1.divide(bigDecimal2, 2, RoundingMode.FLOOR);
        return result;
    }
}
