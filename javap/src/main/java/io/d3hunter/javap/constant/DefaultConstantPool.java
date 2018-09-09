package io.d3hunter.javap.constant;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/8.
 */
public class DefaultConstantPool implements ConstantPool {

    private static final Map<Integer, Class<? extends Constant>> CONSTANT_TYPES = new HashMap<>();

    private int constantPoolCount;
    private Constant[] constants;

    static {
        CONSTANT_TYPES.put(Constant.Class, ClassConstant.class);
        CONSTANT_TYPES.put(Constant.Fieldref, FieldMethodRefConstant.class);
        CONSTANT_TYPES.put(Constant.Methodref, FieldMethodRefConstant.class);
        CONSTANT_TYPES.put(Constant.InterfaceMethodref, FieldMethodRefConstant.class);
        CONSTANT_TYPES.put(Constant.String, StringConstant.class);
        CONSTANT_TYPES.put(Constant.Integer, IntegerConstant.class);
        CONSTANT_TYPES.put(Constant.Float, FloatConstant.class);
        CONSTANT_TYPES.put(Constant.Long, LongConstant.class);
        CONSTANT_TYPES.put(Constant.Double, DoubleConstant.class);
        CONSTANT_TYPES.put(Constant.NameAndType, NameAndTypeConstant.class);
        CONSTANT_TYPES.put(Constant.Utf8, Utf8Constant.class);
        CONSTANT_TYPES.put(Constant.MethodHandle, MethodHandleConstant.class);
        CONSTANT_TYPES.put(Constant.MethodType, MethodTypeConstant.class);
        CONSTANT_TYPES.put(Constant.InvokeDynamic, InvokeDynamicConstant.class);
    }


    @Override
    public String getString(int index) {
        if (index <= 0 || index >= constantPoolCount) {
            throw new IndexOutOfBoundsException("invalid constant pool index");
        }
        return constants[index].getContent();
    }

    @Override
    public void read(DataInputStream stream) throws IOException {
        constantPoolCount = stream.readUnsignedShort();
        this.constants = new Constant[constantPoolCount];
        readConstantPool(stream);
    }

    private void readConstantPool(DataInputStream stream) throws IOException {
        for (int i = 1; i < constantPoolCount; i++) {
            int tag = stream.readByte();

            constants[i] = createConstant(tag);
            constants[i].read(stream);
        }
    }

    private Constant createConstant(int tag) throws IOException {
        Class<? extends Constant> klass = CONSTANT_TYPES.get(tag);
        if (klass == null) {
            throw new IOException("unrecongenized constant tag " + tag);
        }

        try {
            Constructor<? extends Constant> constructor = klass.getConstructor(ConstantPool.class, int.class);
            return constructor.newInstance(this, tag);
        } catch (Exception e) {
            throw new IOException("unrecongenized constant tag " + tag, e);
        }
    }

    private static abstract class AbstractConstant implements Constant {
        protected ConstantPool constantPool;
        protected int tag;

        public AbstractConstant(ConstantPool constantPool, int tag) {
            this.constantPool = constantPool;
            this.tag = tag;
        }

        @Override
        public String toString() {
            return getContent();
        }
    }

    private static class ClassConstant extends AbstractConstant {
        private int nameIndex;

        public ClassConstant(ConstantPool constantPool, int tag) {
            super(constantPool, tag);
        }

        @Override
        public void read(DataInputStream stream) throws IOException {
            nameIndex = stream.readUnsignedShort();
        }

        @Override
        public String getContent() {
            return constantPool.getString(nameIndex);
        }
    }

    private static class DoubleConstant extends AbstractConstant {
        private double value;

        public DoubleConstant(ConstantPool constantPool, int tag) {
            super(constantPool, tag);
        }

        @Override
        public void read(DataInputStream stream) throws IOException {
            value = java.lang.Double.longBitsToDouble(stream.readLong());
        }

        @Override
        public String getContent() {
            return java.lang.Double.toString(value);
        }
    }

    private static class FieldMethodRefConstant extends AbstractConstant {
        private int classIndex;
        private int nameAndTypeIndex;

        public FieldMethodRefConstant(ConstantPool constantPool, int tag) {
            super(constantPool, tag);
        }

        @Override
        public void read(DataInputStream stream) throws IOException {
            classIndex = stream.readUnsignedShort();
            nameAndTypeIndex = stream.readUnsignedShort();
        }

        @Override
        public String getContent() {
            return constantPool.getString(classIndex) + " " + constantPool.getString(nameAndTypeIndex);
        }
    }

    private static class FloatConstant extends AbstractConstant {
        private float value;

        public FloatConstant(ConstantPool constantPool, int tag) {
            super(constantPool, tag);
        }

        @Override
        public void read(DataInputStream stream) throws IOException {
            value = java.lang.Float.intBitsToFloat(stream.readInt());
        }

        @Override
        public String getContent() {
            return java.lang.Float.toString(value);
        }
    }

    private static class IntegerConstant extends AbstractConstant {
        private int value;

        public IntegerConstant(ConstantPool constantPool, int tag) {
            super(constantPool, tag);
        }

        @Override
        public void read(DataInputStream stream) throws IOException {
            value = stream.readInt();
        }

        @Override
        public String getContent() {
            return java.lang.Integer.toString(value);
        }
    }

    private static class InvokeDynamicConstant extends AbstractConstant {
        private int nameAndTypeIndex;
        private int bootstrapMethodAttrIndex;

        public InvokeDynamicConstant(ConstantPool constantPool, int tag) {
            super(constantPool, tag);
        }

        @Override
        public void read(DataInputStream stream) throws IOException {
            bootstrapMethodAttrIndex = stream.readUnsignedShort();
            nameAndTypeIndex = stream.readUnsignedShort();
        }

        @Override
        public String getContent() {
            return constantPool.getString(nameAndTypeIndex);
        }
    }

    private static class LongConstant extends AbstractConstant {
        private long value;

        public LongConstant(ConstantPool constantPool, int tag) {
            super(constantPool, tag);
        }

        @Override
        public void read(DataInputStream stream) throws IOException {
            value = stream.readLong();
        }

        @Override
        public String getContent() {
            return java.lang.Long.toString(value);
        }
    }

    private static class MethodHandleConstant extends AbstractConstant {
        private int referenceIndex;
        private byte referenceKind;

        public MethodHandleConstant(ConstantPool constantPool, int tag) {
            super(constantPool, tag);
        }

        @Override
        public void read(DataInputStream stream) throws IOException {
            referenceKind = stream.readByte();
            referenceIndex = stream.readUnsignedShort();
        }

        @Override
        public String getContent() {
            return constantPool.getString(referenceIndex);
        }
    }

    private static class MethodTypeConstant extends AbstractConstant {
        private int descriptorIndex;

        public MethodTypeConstant(ConstantPool constantPool, int tag) {
            super(constantPool, tag);
        }

        @Override
        public void read(DataInputStream stream) throws IOException {
            descriptorIndex = stream.readUnsignedShort();
        }

        @Override
        public String getContent() {
            return constantPool.getString(descriptorIndex);
        }
    }

    private static class StringConstant extends AbstractConstant {
        private int stringIndex;

        public StringConstant(ConstantPool constantPool, int tag) {
            super(constantPool, tag);
        }

        @Override
        public void read(DataInputStream stream) throws IOException {
            stringIndex = stream.readUnsignedShort();
        }

        @Override
        public String getContent() {
            return constantPool.getString(stringIndex);
        }
    }

    private static class Utf8Constant extends AbstractConstant {
        private String value;

        public Utf8Constant(ConstantPool constantPool, int tag) {
            super(constantPool, tag);
        }

        @Override
        public void read(DataInputStream stream) throws IOException {
            value = stream.readUTF();
        }

        @Override
        public String getContent() {
            return value;
        }
    }

    private static class NameAndTypeConstant extends AbstractConstant {
        private int nameIndex;
        private int descriptorIndex;

        public NameAndTypeConstant(ConstantPool constantPool, int tag) {
            super(constantPool, tag);
        }

        @Override
        public void read(DataInputStream stream) throws IOException {
            nameIndex = stream.readUnsignedShort();
            descriptorIndex = stream.readUnsignedShort();
        }

        @Override
        public String getContent() {
            return constantPool.getString(nameIndex) + " " + constantPool.getString(descriptorIndex);
        }
    }

}
