package com.zerone.scms.dontMind;

public class testForDontMind {
    public static void main(String[] args) {
        System.out.println("----------Integer----------");
        Calculator<Integer> calculator = new Calculator<>();

        System.out.println(calculator.add(2, 3));
        System.out.println(calculator.sub(2, 3));
        System.out.println(calculator.mult(2, 3));
        System.out.println(calculator.div(2, 3));
        System.out.println("----------Double--------");
        Calculator<Double> calculator2 = new Calculator<>();

        System.out.println(calculator2.add(4.0, 2.0));
        System.out.println(calculator2.sub(4.0, 2.0));
        System.out.println(calculator2.mult(4.0, 2.0));
        System.out.println(calculator2.div(4.0, 2.0));

        System.out.println("----------Integer2----------");
        CalculatorToObject calculatorToObject = new CalculatorToObject(2, 3);

        System.out.println(calculatorToObject.add());
        System.out.println(calculatorToObject.sub());
        System.out.println(calculatorToObject.mult());
        System.out.println(calculatorToObject.div());
        System.out.println("----------Double2--------");
        CalculatorToObject calculatorToObject2 = new CalculatorToObject(4.0, 2.0);

        System.out.println(calculatorToObject2.add());
        System.out.println(calculatorToObject2.sub());
        System.out.println(calculatorToObject2.mult());
        System.out.println(calculatorToObject2.div());


        Double[] a1 = {1.0, 2.0, 3.0};
        Integer[] a2 = {2, 11, 3};
        MyArray<Double> myArray1 = new MyArray<>(a1);
        MyArray2<Integer> myArray2 = new MyArray2<>(a2);
        System.out.println("myArray1-avg:" + myArray1.average());
        System.out.println("myArray2-avg:" + myArray2.average());
        System.out.println("平均值：" + (myArray1.sameAvg(myArray2) ? "相等" : "不相等"));

    }
}
