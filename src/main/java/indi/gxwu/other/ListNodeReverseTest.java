package indi.gxwu.other;

/**
 * @Author: gx.wu
 * @Date: 2020/1/3 10:40
 * @Description: 翻转一个链表
 * 输入: 1->2->3->4->null
 * 输出: 4->3->2->1->null
 */
public class ListNodeReverseTest {


    public static void main(String[] args) {
        ListNodeReverseTest t = new ListNodeReverseTest();
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        t.print(n1);
        ListNode result = t.reverse(n1);
        t.print(result);
    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }


    /**
     * @param head: n
     * @return: The new head of reversed linked list.
     */
    public ListNode reverse(ListNode head) {
        // write your code here
        if(head==null){
            return null;
        }

        ListNode preNode = head;
        ListNode node = preNode.next;
        preNode.next = null;
        while (true){
            if(node==null){
                return preNode;
            }
            ListNode nextNode = node.next;
            node.next = preNode;
            if(nextNode==null){
                return node;
            }
            preNode = node;
            node = nextNode;
        }
    }

    public void print(ListNode head){
        ListNode node = head;
        while (true){
            if(node==null){
                System.out.printf("null");
                break;
            }
            System.out.printf(node.val+"->");
            node = node.next;
        }
        System.out.println("\n");
    }
}
