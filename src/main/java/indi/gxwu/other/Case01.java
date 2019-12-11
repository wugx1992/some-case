package indi.gxwu.other;

/**
 * @Author: gx.wu
 * @Date: 2019/6/4 11:37
 */
public class Case01 {

    public static void main(String[] args) throws Exception {
        try {
            try {
                throw new Sneeze();
            } catch (Annoyance a) {
                System.out.println("Caught Annoyance");
                throw a;
            }
        } catch (Sneeze s) {
            System.out.println("Caught Sneeze");
            return;
        } finally {
            System.out.println("Hello World!");
        }
    }
}


class Annoyance extends Exception {
}

class Sneeze extends Annoyance {
}
