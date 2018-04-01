package io.d3hunter.javap;

import io.d3hunter.javap.constant.ConstantPool;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/31.
 */
public abstract class Attribute implements Item {
    private int length;
    private int nameIndex;
    private ConstantPool constantPool;
    private static Map<String, Constructor<?>> constructorMap = new HashMap<>();

    static {
        Class<?>[] classes = {
                ConstantValue.class,
                Code.class,
                StackMapTable.class,
                Exceptions.class,
                InnerClasses.class,
                EnclosingMethod.class,
                Synthetic.class,
                Signature.class,
                SourceFile.class,
                SourceDebugExtension.class,
                LineNumberTable.class,
                LocalVariableTable.class,
                LocalVariableTypeTable.class,
                Deprecated.class,
                RuntimeVisibleAnnotations.class,
                RuntimeInvisibleAnnotations.class,
                RuntimeVisibleParameterAnnotations.class,
                RuntimeInvisibleParameterAnnotations.class,
                AnnotationDefault.class,
                BootstrapMethods.class,
                Unknown.class,
        };
        try {
            for (int i = 0; i < classes.length; i++) {
                Constructor<?> constructor = classes[i].getConstructor(int.class, ConstantPool.class);
                constructorMap.put(classes[i].getSimpleName(), constructor);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

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

        try {
            if (!constructorMap.containsKey(attributeName)) {
                System.err.println("unknown attribute " + attributeName);
                attributeName = Unknown.class.getSimpleName();
            }

            Attribute attribute = (Attribute) constructorMap.get(attributeName).newInstance(nameIndex, constantPool);
            attribute.read(stream);

            return attribute;
        } catch (Exception e) {
            throw new IOException(e);
        }
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
        private int numberOfExceptions;
        private int[] exceptionIndexTable;

        public Exceptions(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {
            numberOfExceptions = stream.readUnsignedShort();
            exceptionIndexTable = new int[numberOfExceptions];
            for (int i = 0; i < numberOfExceptions; i++) {
                exceptionIndexTable[i] = stream.readUnsignedShort();
            }
        }
    }

    private static class InnerClasses extends Attribute {
        private int numberOfClasses;
        private InnerClassInfo[] classes;

        public InnerClasses(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {
            numberOfClasses = stream.readUnsignedShort();
            classes = new InnerClassInfo[numberOfClasses];
            for (int i = 0; i < numberOfClasses; i++) {
                classes[i] = new InnerClassInfo();
                classes[i].read(stream);
            }
        }

        private class InnerClassInfo implements Item {
            private int classInfoIndex;
            private int outerClassInfoIndex;
            private int nameIndex;
            private int access;

            @Override
            public void read(DataInputStream stream) throws IOException {
                classInfoIndex = stream.readUnsignedShort();
                outerClassInfoIndex = stream.readUnsignedShort();
                nameIndex = stream.readUnsignedShort();
                access = stream.readUnsignedShort();
            }
        }
    }

    private static class EnclosingMethod extends Attribute {
        private int classIndex;
        private int methodIndex;

        public EnclosingMethod(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {
            classIndex = stream.readUnsignedShort();
            methodIndex = stream.readUnsignedShort();
        }
    }

    private static class Synthetic extends Attribute {
        public Synthetic(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {
            // no item
        }
    }

    private static class Signature extends Attribute {
        private int signatureIndex;

        public Signature(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {
            signatureIndex = stream.readUnsignedShort();
        }
    }

    private static class SourceFile extends Attribute {
        private int sourceFileIndex;

        public SourceFile(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {
            sourceFileIndex = stream.readUnsignedShort();
        }
    }

    private static class SourceDebugExtension extends Attribute {
        private byte[] debugExtension;

        public SourceDebugExtension(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {
            debugExtension = new byte[getLength()];
            int len = stream.read(debugExtension);
            assert len == getLength();
        }
    }

    private static class LineNumberTable extends Attribute {
        private int tableLength;
        private Table[] table;

        public LineNumberTable(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {
            tableLength = stream.readUnsignedShort();
            table = new Table[tableLength];
            for (int i = 0; i < tableLength; i++) {
                table[i] = new Table();
                table[i].read(stream);
            }
        }

        private class Table implements Item {
            private int startPc;
            private int lineNum;

            @Override
            public void read(DataInputStream stream) throws IOException {
                startPc = stream.readUnsignedShort();
                lineNum = stream.readUnsignedShort();
            }
        }
    }

    private static class LocalVariableTable extends Attribute {
        private int tableLength;
        private Table[] table;

        public LocalVariableTable(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {
            tableLength = stream.readUnsignedShort();
            table = new Table[tableLength];
            for (int i = 0; i < tableLength; i++) {
                table[i] = new Table();
                table[i].read(stream);
            }
        }

        private class Table implements Item {
            private int startPc;
            private int length;
            private int nameIndex;
            private int descriptorIndex;
            private int index;

            @Override
            public void read(DataInputStream stream) throws IOException {
                startPc = stream.readUnsignedShort();
                length = stream.readUnsignedShort();
                nameIndex = stream.readUnsignedShort();
                descriptorIndex = stream.readUnsignedShort();
                index = stream.readUnsignedShort();
            }
        }
    }

    private static class LocalVariableTypeTable extends Attribute {
        private int tableLength;
        private Table[] table;

        public LocalVariableTypeTable(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {
            tableLength = stream.readUnsignedShort();
            table = new Table[tableLength];
            for (int i = 0; i < tableLength; i++) {
                table[i] = new Table();
                table[i].read(stream);
            }
        }

        private class Table implements Item {
            private int startPc;
            private int length;
            private int nameIndex;
            private int signatureIndex;
            private int index;

            @Override
            public void read(DataInputStream stream) throws IOException {
                startPc = stream.readUnsignedShort();
                length = stream.readUnsignedShort();
                nameIndex = stream.readUnsignedShort();
                signatureIndex = stream.readUnsignedShort();
                index = stream.readUnsignedShort();
            }
        }
    }

    public static class Deprecated extends Attribute {

        public Deprecated(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {
            // empty
        }
    }

    private static class RuntimeVisibleAnnotations extends Attribute {
        public RuntimeVisibleAnnotations(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {

        }
    }

    private static class RuntimeInvisibleAnnotations extends Attribute {
        public RuntimeInvisibleAnnotations(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {

        }
    }

    private static class RuntimeVisibleParameterAnnotations extends Attribute {
        public RuntimeVisibleParameterAnnotations(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {

        }
    }

    private static class RuntimeInvisibleParameterAnnotations extends Attribute {
        public RuntimeInvisibleParameterAnnotations(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {

        }
    }

    private static class AnnotationDefault extends Attribute {
        public AnnotationDefault(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {

        }
    }

    private static class BootstrapMethods extends Attribute {
        public BootstrapMethods(int nameIndex, ConstantPool constantPool) {
            super(nameIndex, constantPool);
        }

        @Override
        protected void read0(DataInputStream stream) throws IOException {

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
