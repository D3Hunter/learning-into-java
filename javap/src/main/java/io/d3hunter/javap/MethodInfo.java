package io.d3hunter.javap;

import io.d3hunter.javap.constant.ConstantPool;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/9/8.
 */
public class MethodInfo extends ConstantPoolReliedItem {
    private int access;
    private int nameIndex;
    private int descriptorIndex;
    private int attributeCount;
    private Attribute[] attributes;

    public MethodInfo(ConstantPool constantPool) {
        super(constantPool);
    }

    @Override
    public void read(DataInputStream stream) throws IOException {
        access = stream.readUnsignedShort();
        nameIndex = stream.readUnsignedShort();
        descriptorIndex = stream.readUnsignedShort();
        attributeCount = stream.readUnsignedShort();
        attributes = new Attribute[attributeCount];
        for (int i = 0; i < attributeCount; i++) {
            attributes[i] = Attribute.readFrom(stream, constantPool);
        }
    }
}
