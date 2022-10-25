package com.zerone.scms.dontMind;

public class MyArray<T extends Number> {
    private T[] array;

    public MyArray(T[] array) {
        this.array = array;
    }

    public double average() {
        double sum = 0.0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i].doubleValue();

        }
        return sum / array.length;
    }

    public boolean sameAvg(MyArray2<?> other) {
        return average() == other.average();
    }
}
