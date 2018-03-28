package io.d3hunter.test.asm;

/**
 * Created by Administrator on 2017/6/24.
 */
public class BoxTest {
    public int get(int a) {
        return a;
    }

    public Integer get(Integer a) {
        return a;
    }
    public Integer getI(int a) {
        return (Integer)a;
    }

    public int geti(Integer a) {
        return a;
    }

    public Object geto(int a) {
        return a;
    }

    public void call(int a) {
        int x = (Integer) geto(a);
    }

    public void callf(int a) {
        float x = (Integer) geto(a);
    }

    public void callMethod(int a) {
        get((Integer) geto(a));
    }

    public void call0(int a) {
        Integer x = (Integer) geto(a);
    }

    public Long callL(int a) {
        return (long)a;
    }
}
