package indi.gxwu.structure;

import java.util.Arrays;

/**
 * @author: gx.wu
 * @date: 2022/10/14
 * @description:
 **/
public class MinHeap3 {

    public static void main(String[] args) {
//        int[] a = new int[]{49, 38, 65, 97, 76, 13, 27, 49, 55, 4};

        int a[] = new int[]{9, 12, 17, 30, 50, 20, 60, 65, 4, 49};

        // 构建堆测试
        MakeMinHeap(a, a.length);
        System.out.println(Arrays.toString(a));

        // 测试add
        a = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = 32;
        fixAfterAdd(a);
        System.out.println(Arrays.toString(a));

        // 测试删除，堆的删除始终删除堆顶元素，为了方便重构，将最后一个元素放置堆顶进行一次下沉（这是最快的）
        int tmp = a[a.length - 1];
        a = Arrays.copyOf(a, a.length - 1);
        a[0] = tmp;
        fixAfterDel(a);
        System.out.println(Arrays.toString(a));

        // 测试堆排序
        sort(a);
        System.out.println(Arrays.toString(a));

    }

    // 添加元素后重新调整堆
    static void fixAfterAdd(int[] a) {
        int k = a.length - 1;
        while (k > 1) {
            int j = (k - 1) >> 1; // 找到当前节点的父节点坐标
            if (a[j] < a[k])
                break;
            int tmp = a[j];
            a[j] = a[k];
            a[k] = tmp;
            k = j;
        }
    }

    // 删除元素后重新调整堆
    static void fixAfterDel(int[] a) {
        MinHeapFixdown(a, 0, a.length);
    }


    // 建立最小堆
    static void MakeMinHeap(int a[], int n) {
        for (int i = n / 2 - 1; i >= 0; i--)
            MinHeapFixdown(a, i, n);
    }

    /**
     * 以向下的方式（如果发生了交换，则确保交换后的节点也满足堆结构）调整某个节点的堆结构
     *
     * @param i 节点下标
     * @param n n-1表示向下调整到指定坐标
     */
    static void MinHeapFixdown(int a[], int i, int n) {
        int j, temp;

        temp = a[i];
        j = 2 * i + 1;
        while (j < n) {
            if (j + 1 < n && a[j + 1] < a[j]) //在左右孩子中找最小的
                j++;

            if (a[j] >= temp)
                break;

            a[i] = a[j];     //把较小的子结点往上移动,替换它的父结点
            i = j;
            j = 2 * i + 1;
        }
        a[i] = temp;
    }


    // 堆排序
    static void sort(int[] a) {
        for (int length = a.length; length > 0; length--) {
            int temp = a[0];
            a[0] = a[length - 1];
            a[length - 1] = temp;
            MinHeapFixdown(a, 0, length - 1);
        }
    }

}
