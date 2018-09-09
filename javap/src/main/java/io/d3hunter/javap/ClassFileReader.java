package io.d3hunter.javap;

import io.d3hunter.javap.constant.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/3/6.
 */
public class ClassFileReader implements Item {
    private int magic;
    private int minor;
    private int major;
    private ConstantPool constantPool;
    private int access;
    private int thisClassIndex;
    private int superClassIndex;
    private int interfacesCount;
    private int[] interfaceIndices;
    private int fieldsCount;
    private int methodsCount;
    private int attributesCount;
    private FieldInfo[] fields;
    private MethodInfo[] methods;
    private Attribute[] attributes;

    @Override
    public void read(DataInputStream stream) throws IOException {
        this.magic = stream.readInt();
        this.minor = stream.readUnsignedShort();
        this.major = stream.readUnsignedShort();
        // read constant pool
        this.constantPool = new DefaultConstantPool();
        this.constantPool.read(stream);

        // basic class info
        this.access = stream.readUnsignedShort();
        this.thisClassIndex = stream.readUnsignedShort();
        this.superClassIndex = stream.readUnsignedShort();
        this.interfacesCount = stream.readUnsignedShort();
        this.interfaceIndices = new int[interfacesCount];
        for (int i = 0; i < interfacesCount; i++) {
            interfaceIndices[i] = stream.readUnsignedShort();
        }

        // fields
        this.fieldsCount = stream.readUnsignedShort();
        this.fields = new FieldInfo[fieldsCount];
        for (int i = 0; i < fieldsCount; i++) {
            fields[i] = new FieldInfo(constantPool);
            fields[i].read(stream);
        }

        // methods
        this.methodsCount = stream.readUnsignedShort();
        this.methods = new MethodInfo[methodsCount];
        for (int i = 0; i < methodsCount; i++) {
            methods[i] = new MethodInfo(constantPool);
            methods[i].read(stream);
        }

        // attributes
        this.attributesCount = stream.readUnsignedShort();
        this.attributes = new Attribute[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            attributes[i] = Attribute.readFrom(stream, constantPool);
        }
    }

    public void print() {
        stdoutPrintln("magic 0x%X", this.magic);
        stdoutPrintln("file version: %d.%d", this.major, this.minor);
        stdoutPrintln("access flags: %s", AccessFlags.getClassAccessFlags(this.access));
        stdoutPrintln("super class: %s", constantPool.getString(this.superClassIndex));
        stdoutPrintln("number of direct interfaces: %d", interfacesCount);
        for (int interfaceIndex : interfaceIndices) {
            stdoutPrintln("    %s", constantPool.getString(interfaceIndex));
        }
        stdoutPrintln("number of fields: %s", fieldsCount);
        for (FieldInfo field : fields) {
            stdoutPrintln("    %s", field);
        }
        stdoutPrintln("number of methods: %d", methodsCount);
        for (MethodInfo method : methods) {
            stdoutPrintln("    %s", method);
        }
    }

    private static void stdoutPrintln(String format, Object... arguments) {
        System.out.print(String.format(format, arguments));
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        InputStream inputStream = ClassFileReader.class.getResourceAsStream("constant/DefaultConstantPool.class");
        DataInputStream stream = new DataInputStream(new BufferedInputStream(inputStream));
        ClassFileReader classFileReader = new ClassFileReader();
        classFileReader.read(stream);
        classFileReader.print();
    }
}
