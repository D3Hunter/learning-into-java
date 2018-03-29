package io.d3hunter.javap;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/3/6.
 */
public class ClassFileReader {
    public static void main(String[] args) throws Exception {
        DataInputStream stream = new DataInputStream(new BufferedInputStream(ClassFileReader.class.getResourceAsStream("ClassFileReader.class")));

        int magic = stream.readInt();
        int minor = stream.readUnsignedShort();
        int major = stream.readUnsignedShort();
        int constantPoolCount = stream.readUnsignedShort();

        // read constant pool
        int access = stream.readUnsignedShort();
        int thisClassIndex= stream.readUnsignedShort();
        int superClassIndex = stream.readUnsignedShort();
        int interfacesCount = stream.readUnsignedShort();
        // interfaces
        int fieldsCount = stream.readUnsignedShort();
        // fields
        int methodsCount = stream.readUnsignedShort();
        // methods
        int attributesCount = stream.readUnsignedShort();
        // attributes
    }
}
