package io.d3hunter.test.asm.methodcall;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by Administrator on 2017/2/4.
 */
public class MethodCallAdaptor extends MethodVisitor implements Opcodes {
    public MethodCallAdaptor(int api, int access, String desc, MethodVisitor mv) {
        super(api, mv);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (name.equals("callPrivate")) {
            visitInsn(Opcodes.POP);
            super.visitMethodInsn(INVOKESTATIC, owner, "callStatic", desc, false);
            return;
        }
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }
}
