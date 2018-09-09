package io.d3hunter.javap;

import io.d3hunter.javap.constant.ConstantPool;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/31.
 */
public class FieldInfo extends ConstantPoolReliedItem {
    private int access;
    private int nameIndex;
    private int descriptorIndex;
    private int attributeCount;
    private Attribute[] attributes;

    public FieldInfo(ConstantPool constantPool) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AccessFlags.getFieldAccessFlags(access)).append(" ")
                .append(constantPool.getString(nameIndex)).append(": ")
                .append(constantPool.getString(descriptorIndex));
        return sb.toString().trim();
    }
}
