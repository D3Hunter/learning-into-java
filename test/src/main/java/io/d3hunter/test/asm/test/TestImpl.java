package io.d3hunter.test.asm.test;

/**
 * Created by Administrator on 2017/6/25.
 */
public class TestImpl implements ITest {
    @Override
    public void call() {
        ITest t = new ITest() {
            @Override
            public void call() {

            }
        };
        t.call();
    }

    public static void main(String[] args) {
        ITest t = new ITest() {
            @Override
            public void call() {
                ITest t = new ITest() {
                    @Override
                    public void call() {

                    }
                };
                t.call();
            }
        };
        t.call();
    }
}
