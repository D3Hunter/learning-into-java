package io.d3hunter.test.asm.test;

import io.d3hunter.test.asm.ByteCodeLoader;
import io.d3hunter.test.asm.Tools;
import org.objectweb.asm.*;

/**
 * Created by Administrator on 2018/2/17.
 */
public class InvokeSpecial {
    public static void main(String[] args) throws Exception {
        ClassReader cr = new ClassReader(InvokeSpecial.class.getResourceAsStream("Baz.class"));
        ClassWriter cw = new ClassWriter(cr, 0);
        Tools.printClass(cr.b);
        cr.accept(new ClassVisitor(Opcodes.ASM5, cw) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[]
                    interfaces) {
                super.visit(version, access & (~Opcodes.ACC_SUPER), name, signature, superName, interfaces);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[]
                    exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                if (name.equals("call")) {
                    return new MethodVisitor(Opcodes.ASM5, mv) {
                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                            super.visitMethodInsn(Opcodes.INVOKESPECIAL, owner, name, desc, itf);
                        }
                    };
                }
                return mv;
            }
        }, 0);
        byte[] bytes = cw.toByteArray();
        Tools.printClass(bytes);
        ByteCodeLoader loader = new ByteCodeLoader();
        String binaryName = Type.getObjectType(cr.getClassName()).getClassName();
        Class clazz = loader.loadByteCode(binaryName, bytes);
        Foo baz = (Foo)clazz.newInstance();
        baz.call();
    }
}

