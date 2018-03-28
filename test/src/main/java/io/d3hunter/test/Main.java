package io.d3hunter.test;

/**
 * Created by jujj on 2016/11/11.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(TestInteger.var);
        System.out.println(TestInt.var);
        System.out.println(TestString.var);
    }
}

class TestInteger {
    public static final Integer var = 100;

    static {
        System.out.println("Integer");
    }
}

class TestInt {
    public static final int var = 200;

    static {
        System.out.println("int");
    }
}

class TestString {
    public static final String var = "var";

    static {
        System.out.println("String");
    }
}
