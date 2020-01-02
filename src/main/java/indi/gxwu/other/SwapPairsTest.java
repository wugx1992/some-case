package indi.gxwu.other;

/**
 * @Author: gx.wu
 * @Date: 2020/1/2 10:20
 * @Description: 链表两两交换
 */
public class SwapPairsTest {


    public static void main(String[] args) {
        SwapPairsTest t = new SwapPairsTest();

        ListNode v1 = new ListNode(1);
        ListNode v2 = new ListNode(2);
        ListNode v3 = new ListNode(3);
        ListNode v4 = new ListNode(4);
        v1.next = v2;
        v2.next = v3;
        v3.next = v4;
        ListNode show = v1;
        while (true){
            if(show==null){
                break;
            }
            System.out.printf(show.val+"->");
            show = show.next;
        }
        System.out.println("null");

        ListNode swapVal = t.swapPairs(v1);
        show = swapVal;
        while (true){
            if(show==null){
                break;
            }
            System.out.printf(show.val+"->");
            show = show.next;
        }
        System.out.println("null");


    }

    public static class ListNode{
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }


    /**
     * @param head: a ListNode
     * @return: a ListNode
     */
    public ListNode swapPairs(ListNode head) {
        if(head==null){
            return null;
        }
        if(head.next==null){
            return head;
        }
        ListNode newHead = head.next;
        swapNode(null, head);

        return newHead;
    }

    public void swapNode(ListNode parentNode, ListNode node){
        if(node==null || node.next==null){
            return;
        }
        ListNode v2 = node.next;
        ListNode v3 = v2.next;
        node.next = v3;
        v2.next = node;
        if(parentNode!=null) {
            parentNode.next = v2;
        }
        swapNode(node, v3);
    }




}
