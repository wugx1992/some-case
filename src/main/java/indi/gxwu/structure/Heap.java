package indi.gxwu.structure;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author: gx.wu
 * @date: 2022/10/14
 * @description:
 **/
public class Heap {
    // 定义数组和数组的长度
    public int[] elem;
    public int usedSize;

    public Heap(){
        this.elem = new int[10];
    }

    @Test
    public void test(){
        int array[] = new int[]{9, 12, 17, 30, 50, 20, 60, 65, 4, 49};
        printArray(array);
        Heap heap = new Heap();
        createHeap(array);
    }

    public void createHeap(int[] array){
        for (int i = 0; i < array.length; i++) {
            // 把数组的值拷贝给 elem
            this.elem[i] = array[i];
            this.usedSize++;
        }

        // parent 就代表每棵子树的根节点
        // 如何拿到最后 最后一个节点的下标: array.length-1
        // 最后一个节点的父节点: (array.length-1-1)/2
        for (int parent = (array.length-1-1)/2; parent >= 0 ; parent--) {
            // 注意：第2个参数 每次调整的结束位置应该是：不超过数组的最大值，下标不取等于就行
            // this.usedSize
            adjustDown(parent,this.usedSize);
        }
        printArray(this.elem);
    }
    // root:每棵树的根值 len:数组长度
    private void adjustDown(int root, int len) {
        int parent = root;
        int child = 2*parent+1;

        // 在调整的过程中 child要小于len 防止数组越界
        // 只要能进循环 就代表至少有左孩子
        while (child < len){
            // 找到左右孩子的最大值

            // 1.前提是你得有右孩子
            // 先判断是否有有孩子，并且判断左右孩子哪个大，并保证child是两个孩子中值最大的那一个
            if(child+1 < len && this.elem[child] < this.elem[child+1]){
                // 保证child是两个孩子中值最大的那一个
                child++;
            }
            // 保证，child下标的数据 一定是左右孩子的最大值的下标
            if(this.elem[child] > this.elem[parent]){
                int tmp = this.elem[child];
                this.elem[child] = this.elem[parent];
                this.elem[parent] = tmp;
                // 先移动parent
                parent = child;
                // 再移动child
                child = 2*parent+1;
            }else {
                // 当调整的过程中，孩子节点没有父亲节点的值大，那么此时说明这棵树已经是大根堆了
                break;
            }
        }
    }

    // child是新插入数据的下标
    public void adjustUp(int child){
        int parent = (child-1)/2;
        while (child > 0){
            if(this.elem[child]>this.elem[parent]){
                int tmp = this.elem[parent];
                this.elem[parent] = this.elem[child];
                this.elem[child] = tmp;
                child = parent;
                parent = (child-1)/2;
            }else {
                break;
            }
        }
    }
    public void push(int val){
        if(ifFull()){
            this.elem = Arrays.copyOf(this.elem,2*this.elem.length);
        }
        this.elem[this.usedSize] = val;
        this.usedSize++;
        adjustUp(this.usedSize-1);
    }

    private boolean ifFull() {
        if(this.elem.length == this.usedSize){
            return true;
        }else {
            return false;
        }
    }

    public void pop(){
        if(isEmpty()){
            return;
        }
        int tmp = this.elem[0];
        this.elem[0] = this.elem[this.usedSize-1];
        this.elem[this.usedSize-1] = tmp;
        this.usedSize--;
        adjustDown(0,this.usedSize);

    }

    public int peek(){
        if(isEmpty()){
            // return -1;
            throw new RuntimeException();
        }
        return this.elem[0];
    }


    public boolean isEmpty(){
        return this.usedSize==0;
    }



    void printArray(int[] array){
        for(int v : array) {
            System.out.print(v+", ");
        }
        System.out.println();
    }
}
