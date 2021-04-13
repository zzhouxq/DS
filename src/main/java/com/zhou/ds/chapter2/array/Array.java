package com.zhou.ds.chapter2.array;

/**
 * 用数组表示多项式
 *
 * @author zhouxq
 * @date 2021/4/13
 */
public class Array {


    public static void main(String[] args) {
        // 多项式P1(X) = 9X^12 + 15X^8 + 3X^2
        // 多项式P2(X) = 26X^19 - 4X^8 - 13X^6 + 82
        // 两个多项式相加

        // 初始化
        Item[] p1 = new Item[3];
        p1[0] = new Item(9, 12);
        p1[1] = new Item(15, 8);
        p1[2] = new Item(3, 2);

        Item[] p2 = new Item[4];
        p2[0] = new Item(26, 19);
        p2[1] = new Item(-4, 8);
        p2[2] = new Item(-13, 6);
        p2[3] = new Item(82, 0);

        Item[] addResult = add(p1, p2);
        print(p1);
        print(p2);

        print(addResult);
    }

    /**
     * 多项式相加
     */
    private static Item[] add(Item[] p1, Item[] p2) {
        int p1Index = 0;
        int p2Index = 0;
        Item[] result = new Item[p1.length + p2.length];
        int resultIndex = 0;
        while (p1Index < p1.length && p2Index < p2.length) {
            if (p1[p1Index].i < p2[p2Index].i) {
                // 将p2[p2Index]放入结果数组
                result[resultIndex] = p2[p2Index];
                p2Index++;
            } else if (p1[p1Index].i > p2[p2Index].i) {
                // 将p1[p1Index]放入结果数组
                result[resultIndex] = p1[p1Index];
                p1Index++;
            } else {
                // 系数相加，指数不变
                int totalA = p1[p1Index].a + p2[p2Index].a;
                result[resultIndex] = new Item(totalA, p1[p1Index].a);
                p1Index++;
                p2Index++;
            }
            resultIndex++;
        }

        // 判断哪个多项式先扫描完
        if (p1Index == p1.length) {
            // 将剩余p2内容拷贝到结果数组
            for (int i = p2Index; i < p2.length; i++) {
                result[resultIndex] = p2[p2Index];
                resultIndex++;
            }
        } else if (p2Index == p2.length) {
            // 将剩余p1内容拷贝到结果数组
            for (int i = p1Index; i < p1.length; i++) {
                result[resultIndex] = p1[p1Index];
                resultIndex++;
            }
        }

        // 去除数组后面为空的部分
        Item[] res = new Item[resultIndex];
        // TODO 能否省去拷贝
        System.arraycopy(result, 0, res, 0, res.length);
        return res;
    }

    /**
     * 多项式相乘
     */
    private static Item[] multiply(Item[] p1, Item[] p2) {
        return null;
    }

    static class Item {
        // 系数
        public int a;
        // 指数
        public int i;

        public Item(int a, int i) {
            this.a = a;
            this.i = i;
        }

    }

    private static void print(Item[] data) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            if (data[i].a > 0) {
                builder.append("+ ").append(data[i].a);
            } else {
                builder.append(data[i].a);
            }
            if (data[i].i > 0) {
                builder.append("X^").append(data[i].i);
            }
            builder.append(" ");
        }
        System.out.println(builder.toString().substring(2));
    }
}
