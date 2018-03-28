package io.d3hunter.test.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/2/6.
 */
public class Tools {
    public static void printClass(byte[] code) {
        ClassReader cr;
        cr = new ClassReader(code);
        TraceClassVisitor visitor = new TraceClassVisitor(new PrintWriter(System.out));
        cr.accept(visitor, ClassReader.EXPAND_FRAMES);
    }

    public static IHelloWorld getiHelloWorld(String binaryName, ClassWriter cw) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        ByteCodeLoader loader = new ByteCodeLoader();
        Class helloWorld = loader.loadByteCode(binaryName, cw.toByteArray());
//        loader.loadClass(binaryName);
        return (IHelloWorld) helloWorld.newInstance();
    }
}
