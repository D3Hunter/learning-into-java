package io.d3hunter.test.asm.methodcall;

import io.d3hunter.test.asm.IHelloWorld;
import io.d3hunter.test.asm.Tools;
import org.objectweb.asm.*;

/**
 * Created by Administrator on 2017/2/4.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ClassReader cr = new ClassReader(Main.class.getResourceAsStream("HelloWorld.class"));
        String binaryName = Type.getObjectType(cr.getClassName()).getClassName();
        Tools.printClass(cr.b);
        ClassWriter cw = new ClassWriter(0);
        cr.accept(new ClassVisitor(Opcodes.ASM5, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[]
                    exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                return new MethodCallAdaptor(Opcodes.ASM5, access, desc, mv);
            }
        }, ClassReader.SKIP_DEBUG | ClassReader.EXPAND_FRAMES);

        /**/
        Tools.printClass(cw.toByteArray());

        System.out.println("before instrument:");
        IHelloWorld instance = new HelloWorld();
        instance.call();
        System.out.println("------------------");
        System.out.println("after instrument:");
        instance = Tools.getiHelloWorld(binaryName, cw);
        instance.call();
    }

}
