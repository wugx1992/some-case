package indi.gxwu.gc;

import lombok.Data;

import java.lang.ref.WeakReference;

/**
 * @Author: gx.wu
 * @Date: 2020/3/31 17:07
 * @Description: code something to describe this module what it is
 */
public class WeakReferenceTest {

    @Data
    public class Card {
        private String name;
        private String cardNo;
    }

    /**
     * 对象会被回收
     */
    public static void testWeakReference(){
        WeakReferenceTest t = new WeakReferenceTest();
        Card card = t.new Card();
        card.setName("abc");
        card.setCardNo("111");

        WeakReference<Card> weakReference = new WeakReference<Card>(card);
        int i=0;
        while (true){
            if(weakReference.get()!=null){
                i++;
                System.out.println("object is alive, i: "+i+", "+weakReference);
            }else{
                System.out.println("object had been collected ");
                break;
            }
        }
    }

    /**
     * 对象不会被回收，因为对象还被引用这
     */
    public static void testWeakReference2(){
        WeakReferenceTest t = new WeakReferenceTest();
        Card card = t.new Card();
        card.setName("abc");
        card.setCardNo("111");

        WeakReference<Card> weakReference = new WeakReference<Card>(card);
        int i=0;
        while (true){
            System.out.println("here is a strong reference card: "+ card);
            if(weakReference.get()!=null){
                i++;
                System.out.println("object is alive, i: "+i+", "+weakReference);
            }else{
                System.out.println("object had been collected ");
                break;
            }
        }
    }

    public static void main(String[] args) {
        testWeakReference();
//        testWeakReference2();
    }


}
