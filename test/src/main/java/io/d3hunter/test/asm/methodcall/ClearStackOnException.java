package io.d3hunter.test.asm.methodcall;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by Administrator on 2017/2/6.
 */
public class ClearStackOnException extends MethodVisitor implements Opcodes {
    public ClearStackOnException(int api, int access, String desc, MethodVisitor mv) {
        super(api, mv);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (name.equals("callPrivate")) {
            // 栈上多余一个this
            //visitInsn(Opcodes.POP);
            super.visitMethodInsn(INVOKESTATIC, owner, "callStatic", desc, false);
            return;
        }
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack + 1, maxLocals);
    }
}

