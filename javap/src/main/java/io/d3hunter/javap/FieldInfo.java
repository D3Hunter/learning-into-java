package io.d3hunter.javap;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/31.
 */
public class FieldInfo implements Item {
    private int access;
    private int nameIndex;
    private int descriptorIndex;
    private int attributeCount;
    private Attribute[] attributes;

    @Override
    public void read(DataInputStream stream) throws IOException {
        access = stream.readUnsignedShort();
        nameIndex = stream.readUnsignedShort();
        descriptorIndex = stream.readUnsignedShort();
        attributeCount = stream.readUnsignedShort();
        attributes = new Attribute[attributeCount];
        for (int i = 0; i < attributeCount; i++) {
//            attributes[i] = new Attribute();
//            attributes[i].read(stream);
        }
    }
}
