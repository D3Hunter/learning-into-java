package io.d3hunter.test.asm.test;

/**
 * Created by Administrator on 2018/2/17.
 */
public class Baz extends Bar {
    @Override
    public void call() {
        Foo foo = this;
        foo.call();
    }
}
