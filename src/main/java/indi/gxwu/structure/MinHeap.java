package indi.gxwu.structure;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author: gx.wu
 * @date: 2022/10/12
 * @description:
 **/
public class MinHeap {

    @Test
    public void test() {
        int array[] = new int[]{9, 12, 17, 30, 50, 20, 60, 65, 4, 49};
        printArray(array);
        makeMinHeap(array, array.length);
        printArray(array);
        //4, 9, 17, 12, 49, 20, 60, 65, 30, 50,

    }

    /**
     * 建立最小堆
     * @param a
     * @param len
     */
    void makeMinHeap(int a[], int len) {
        for (int root = len / 2 - 1; root >= 0; root--) {
            minHeapFixDown(a, len, root);
        }
    }

    /**
     * 小顶堆结点下沉操作
     * @param a
     * @param len
     * @param root
     */
    void minHeapFixDown(int a[], int len, int root) {
        //i 的左节点：i*2+1，有节点：i*2+2，父节点：（i-1）/2
        int parent = root;
        int child = parent * 2 + 1;
        // 当下沉到叶子节点时，就不用调整了
        while (child < len) {
            if ((child + 1) < len && a[child] > a[child + 1]) {
                child++;
            }
            if (a[child] < a[parent]) {
                int temp = a[child];
                a[child] = a[parent];
                a[parent] = temp;
                parent = child;
                child = parent * 2 + 1;
            } else {
                break;
            }
        }
    }

    void printArray(int[] array) {
        for (int v : array) {
            System.out.print(v + ", ");
        }
        System.out.println();
    }

}
