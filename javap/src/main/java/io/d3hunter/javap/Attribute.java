package io.d3hunter.javap;

import io.d3hunter.javap.constant.ConstantPool;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/31.
 */
public abstract class Attribute implements Item {
    private int length;
    private int nameIndex;
    private ConstantPool constantPool;

    public Attribute(int nameIndex, ConstantPool constantPool) {
        this.nameIndex = nameIndex;
        this.constantPool = constantPool;
    }

    @Override
    public void read(DataInputStream stream) throws IOException {
        nameIndex = stream.readUnsignedShort();
        length = stream.readInt();
        read0(stream);
    }

    public static Attribute readFrom(DataInputStream stream, ConstantPool constantPool) throws IOException {
        int nameIndex = stream.readUnsignedShort();
        String attributeName = constantPool.getString(nameIndex);

        Attribute attribute;
        switch (attributeName) {
            case "ConstantValue":
                attribute = new ConstantValue(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "Code":
                attribute = new Code(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "StackMapTable":
                attribute = new StackMapTable(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "Exceptions":
                attribute = new Exceptions(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "InnerClasses":
                attribute = new InnerClasses(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "EnclosingMethod":
                attribute = new EnclosingMethod(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "Synthetic":
                attribute = new Synthetic(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "Signature":
                attribute = new Signature(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "SourceFile":
                attribute = new SourceFile(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "SourceDebugExtension":
                attribute = new SourceDebugExtension(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "LineNumberTable":
                attribute = new LineNumberTable(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "LocalVariableTable":
                attribute = new LocalVariableTable(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "LocalVariableTypeTable":
                attribute = new LocalVariableTypeTable(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "Deprecated":
                attribute = new Deprecated(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "RuntimeVisibleAnnotations":
                attribute = new RuntimeVisibleAnnotations(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "RuntimeInvisibleAnnotations":
                attribute = new RuntimeInvisibleAnnotations(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "RuntimeVisibleParameterAnnotations":
                attribute = new RuntimeVisibleParameterAnnotations(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "RuntimeInvisibleParameterAnnotations":
                attribute = new RuntimeInvisibleParameterAnnotations(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "AnnotationDefault":
                attribute = new AnnotationDefault(nameIndex, constantPool);
                attribute.read(stream);
                break;
            case "BootstrapMethods":
                attribute = new BootstrapMethods(nameIndex, constantPool);
                attribute.read(stream);
                break;
            default:
                System.err.println("unknown attribute " + attributeName);
                attribute = new Unknown(nameIndex, constantPool);
                attribute.read(stream);
                break;
        }

        return attribute;
    }

    protected abstract void read0(DataInputStream stream) throws IOException;

    public int getLength() {
        return length;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public static class ConstantValue extends Attribute {

        private int valueIndex;

        public ConstantValue(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {
            valueIndex = stream.readUnsignedShort();
        }
    }

    public static class Code extends Attribute {
        private int maxStack;
        private int maxLocals;
        private int codeLength;
        private byte[] code;
        private int exceptionTableLength;
        private ExceptionTable[] exceptionTables;
        private int attributeCount;
        private Attribute[] attributes;

        public Code(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {
            maxStack = stream.readUnsignedShort();
            maxLocals = stream.readUnsignedShort();
            codeLength = stream.readInt();
            code = new byte[codeLength];
            int var = stream.read(code);
            assert var == codeLength;

            exceptionTableLength = stream.readUnsignedShort();
            exceptionTables = new ExceptionTable[exceptionTableLength];
            for (int i = 0; i < exceptionTableLength; i++) {
                exceptionTables[i] = new ExceptionTable();
                exceptionTables[i].read(stream);
            }

            attributeCount = stream.readUnsignedShort();
            attributes = new Attribute[attributeCount];
            for (int i = 0; i < attributeCount; i++) {
                attributes[i] = Attribute.readFrom(stream, getConstantPool());
            }
        }

        private static class ExceptionTable implements Item {
            private int startPc;
            private int endPc;
            private int handlerPc;
            private int catchType;

            @Override
            public void read(DataInputStream stream) throws IOException {
                startPc = stream.readUnsignedShort();
                endPc = stream.readUnsignedShort();
                handlerPc = stream.readUnsignedShort();
                catchType = stream.readUnsignedShort();
            }
        }
    }

    private static class StackMapTable extends Attribute {
        private int numberOfEntries;
        private StackMapFrame[] frames;

        public StackMapTable(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {
            numberOfEntries = stream.readUnsignedShort();
            frames = new StackMapFrame[numberOfEntries];
            for (int i = 0; i < numberOfEntries; i++) {
                frames[i] = new StackMapFrame();
                frames[i].read(stream);
            }
        }
    }

    private static class Exceptions extends Attribute {
        public Exceptions(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    private static class InnerClasses extends Attribute {
        public InnerClasses(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    private static class EnclosingMethod extends Attribute {
        public EnclosingMethod(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    private static class Synthetic extends Attribute {
        public Synthetic(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    private static class Signature extends Attribute {
        public Signature(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    private static class SourceFile extends Attribute {
        public SourceFile(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    private static class SourceDebugExtension extends Attribute {
        public SourceDebugExtension(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    private static class LineNumberTable extends Attribute {
        public LineNumberTable(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    private static class LocalVariableTable extends Attribute {
        public LocalVariableTable(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    private static class LocalVariableTypeTable extends Attribute {
        public LocalVariableTypeTable(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    public static class Deprecated extends Attribute {

        public Deprecated(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {

        }
    }

    private static class RuntimeVisibleAnnotations extends Attribute {
        public RuntimeVisibleAnnotations(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    private static class RuntimeInvisibleAnnotations extends Attribute {
        public RuntimeInvisibleAnnotations(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    private static class RuntimeVisibleParameterAnnotations extends Attribute {
        public RuntimeVisibleParameterAnnotations(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    private static class RuntimeInvisibleParameterAnnotations extends Attribute {
        public RuntimeInvisibleParameterAnnotations(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    private static class AnnotationDefault extends Attribute {
        public AnnotationDefault(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    private static class BootstrapMethods extends Attribute {
        public BootstrapMethods(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }
    }

    private static class Unknown extends Attribute {
        private byte[] content;

        public Unknown(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {
            content = new byte[getLength()];
            int len = stream.read(content);
            assert len == getLength();
        }
    }
}
