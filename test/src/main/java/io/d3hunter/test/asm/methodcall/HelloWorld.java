package io.d3hunter.test.asm.methodcall;

import io.d3hunter.test.asm.IHelloWorld;

/**
 * Created by Administrator on 2017/2/4.
 */
public class HelloWorld implements IHelloWorld {
    private String getStr() {
        return null;
    }

    public static void callStatic() {
        System.out.println("callStatic");
    }

    @Override
    public void empty() {
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0)
                i++;
        }
    }

    private void callPrivate() {
        System.out.println("callPrivate");
    }

    @Override
    public void call() throws Exception {
        try {
            System.out.println("call");
            callPrivate();
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
