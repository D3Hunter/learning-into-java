package io.d3hunter.test.asm.inner;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/6/27.
 */
public class InnerClassTest {
    public static void main(String[] args) throws Exception{
        // InnerClassTest.class.getResourceAsStream, 如果不是绝对路径会添加当前类所在包名到path上
        // 绝对路径会去掉前缀/
        readClass("/io/d3hunter/test/asm/inner/InnerClassTest.class");
        readClass("/io/d3hunter/test/asm/inner/InnerClassTest$AA.class");
        readClass("/io/d3hunter/test/asm/inner/InnerClassTest$BB.class");
        readClass("/io/d3hunter/test/asm/test/TestImpl$1.class");
        readClass("/io/d3hunter/test/asm/test/TestImpl$2.class");
    }

    private static void readClass(String className) throws IOException {
        InputStream resourceAsStream = InnerClassTest.class.getResourceAsStream(className);
        ClassReader cr = new ClassReader(resourceAsStream);
        ClassNode out = new ClassNode();
        cr.accept(out, 0);
        System.out.println(out.innerClasses.size());
    }

    private class AA {
        int a;
    }
    private static class BB {
        int anInt;
    }
}
