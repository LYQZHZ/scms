package com.zerone.scms.daoyuexingchen;


public class TwoTest {
    public static void main(String[] args) {
        int[][] arr = {{1, 3, 7}, {6, 8, 10}, {15, 18, 30}};
        ForTwoTest forTwoTest = new ForTwoTest();

        System.out.println(forTwoTest.findTarget(8, arr));
        System.out.println(forTwoTest.findTarget(199, arr));
    }
}

class ForTwoTest {
    /**
     * @Description:由于数组已经排好序，所以可以通过和每一行或者每一列的最大值比较，此处按照行排除, 如果小于等于该值则可以锁定在这一行中，否则则比较下一行的最大值，直到第三行，如果都不在，则返回[],
     * 如果在某一行则返回相应的位置，如果锁定在某一行的范围中但在这行中也没有对应的值则也返回[]
     * 测试代码包含了题目所给的两个值的测试，均符合预期结果
     * <p>
     * 时间复杂度：O(n)
     * @return:字符串
     * @Author: 李岩青
     * @Date: int target, int[][] arr
     */
    String findTarget(int target, int[][] arr) {
        if (target <= arr[0][2]) {
            for (int i = 0; i < 3; i++) {
                if (target == arr[0][i]) {
                    return "[0," + i + "]";
                }
            }
        }
        if (target >= arr[1][0] && target <= arr[1][2]) {
            for (int i = 0; i < 3; i++) {
                if (target == arr[1][i]) {
                    return "[1," + i + "]";
                }
            }
        }
        if (target >= arr[2][0] && target <= arr[2][2]) {
            for (int i = 0; i < 3; i++) {
                if (target == arr[2][i]) {
                    return "[2," + i + "]";
                }
            }
        }
        return "[]";

    }
}


