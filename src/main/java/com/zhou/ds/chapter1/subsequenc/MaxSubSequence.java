package com.zhou.ds.chapter1.subsequenc;

/**
 * 最大子列和问题
 *
 * @author zhouxq
 * @date 2021/4/5
 */
public class MaxSubSequence {

    private static final int ARRAY_LENGTH = 100000000;

    private static final int[] DATA = {1 ,2 ,3 ,-4, -2, 10, 3, -6, 2, 1};

    private static final int[] LONG_DATA = new int[ARRAY_LENGTH];

    static {
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            LONG_DATA[i] = 1;
        }
    }

    public static void main(String[] args) {
        /*int brute = brute(DATA);
        System.out.println(brute);

        int reuse = reuse(DATA);
        System.out.println(reuse);

        int divide = divide(DATA);
        System.out.println(divide);

        int online = online(DATA);
        System.out.println(online);*/

        // 对比不同算法的性能
        long start = System.currentTimeMillis();
        divide(LONG_DATA);
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println(time / 1000.0);
    }

    /**
     * 穷举
     * 时间复杂度 T(N) = O(N^3)
     */
    private static int brute(int[] datas) {
        int maxSum = 0;
        int thisSum;
        for (int i = 0; i < datas.length; i++) {
            for (int j = i; j < datas.length; j++) {
                // 归零
                thisSum = 0;
                // 从累加到j
                for (int k = i; k <= j; k++) {
                    thisSum += datas[k];
                }
                // 取两者里面较大的
                maxSum = thisSum > maxSum ? thisSum : maxSum;
            }
        }
        return maxSum;
    }

    /**
     * 复用
     * j每次加1后，不用重头计算k的累计，只需要在之前j-1的基础上加上datas[j]即可
     * 时间复杂度 T(N) = O(N^2)
     */
    private static int reuse(int[] datas) {
        int maxSum = 0;
        int thisSum;
        for (int i = 0; i < datas.length; i++) {
            // 归零
            thisSum = 0;
            for (int j = i; j < datas.length; j++) {
                thisSum += datas[j];
                // 取两者里面较大的
                maxSum = thisSum > maxSum ? thisSum : maxSum;
            }
        }
        return maxSum;
    }

    /**
     * 分治
     * 时间复杂度 T(N) = O(N logN)
     */
    private static int divide(int[] datas) {
        return divideSum(datas, 0, datas.length - 1);
    }


    /**
     * 在线处理
     * 时间复杂度 T(N) = O(N)
     */
    private static int online(int[] datas) {
        int maxSum = 0;
        int thisSum = 0;
        for (int i = 0; i < datas.length; i++) {
            thisSum += datas[i];
            if (thisSum >= 0) {
                maxSum = thisSum > maxSum ? thisSum : maxSum;
            } else {
              // 如果当前子列和小于0，那么加上它，不会使后面的子列和变大，所以舍弃
              thisSum = 0;
            }
        }
        return maxSum;
    }

    /**
     * 求子列的最大的子列和
     * @param datas 数据
     * @param left 左边界
     * @param right 又边界
     * @return
     */
    private static int divideSum(int[] datas, int left, int right) {
        int maxLeftSum, maxRightSum;

        // 递归终止条件（子列只有一个数字）
        if (left == right) {
            return datas[left] > 0 ? datas[left] : 0;
        }

        // "分"
        int center = (left + right) / 2;

        // 递归计算左边子列的最大子列和
        maxLeftSum = divideSum(datas, left, center);
        // 递归计算右边子列的最大子列和
        maxRightSum = divideSum(datas, center + 1, right);

        // 跨分界线的最大子列和（左）
        int maxLeftBorderSum = 0;
        int leftBorderSum = 0;
        // 从中间开始向左扫描
        for (int i = center; i >= left; i--) {
            leftBorderSum += datas[i];
            maxLeftBorderSum = leftBorderSum > maxLeftBorderSum ? leftBorderSum : maxLeftBorderSum;
        }

        // 跨分界线的最大子列和（右）
        int maxRightBorderSum = 0;
        int rightBorderSum = 0;
        // 从中间开始向右扫描
        for (int i = center + 1; i <= right; i++) {
            rightBorderSum += datas[i];
            maxRightBorderSum = rightBorderSum > maxRightBorderSum ? rightBorderSum : maxRightBorderSum;
        }

        // "治"
        return maxNum(maxLeftSum, maxRightSum, maxLeftBorderSum + maxRightBorderSum);
    }

    /**
     * 返回三个数里面的最大值
     */
    private static int maxNum(int a, int b, int c) {
        return a > b ? (a > c ? a : c) : (b > c ? b : c);

    }
}
