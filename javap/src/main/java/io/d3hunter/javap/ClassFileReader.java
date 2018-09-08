package io.d3hunter.javap;

import io.d3hunter.javap.constant.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/6.
 */
public class ClassFileReader {
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

    public void read() throws Exception {
        DataInputStream stream = new DataInputStream(new BufferedInputStream(ClassFileReader.class.getResourceAsStream("ClassFileReader$A.class")));

        this.magic = stream.readInt();
        this.minor = stream.readUnsignedShort();
        this.major = stream.readUnsignedShort();
        // read constant pool
        this.constantPool = new DefaultConstantPool();
        this.constantPool.read(stream);

        this.access = stream.readUnsignedShort();
        this.thisClassIndex = stream.readUnsignedShort();
        this.superClassIndex = stream.readUnsignedShort();
        this.interfacesCount = stream.readUnsignedShort();
        this.interfaceIndices = new int[interfacesCount];
        for (int i = 0; i < interfacesCount; i++) {
            interfaceIndices[i] = stream.readUnsignedShort();
        }
        this.fieldsCount = stream.readUnsignedShort();
        // fields
        this.methodsCount = stream.readUnsignedShort();
        // methods
        this.attributesCount = stream.readUnsignedShort();
        // attributes
    }

    protected static class A {

    }
}
