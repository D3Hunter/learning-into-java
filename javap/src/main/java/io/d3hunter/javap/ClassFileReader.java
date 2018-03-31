package io.d3hunter.javap;

import io.d3hunter.javap.constant.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;

/**
 * Created by Administrator on 2018/3/6.
 */
public class ClassFileReader {
    public static void main(String[] args) throws Exception {
        DataInputStream stream = new DataInputStream(new BufferedInputStream(ClassFileReader.class.getResourceAsStream("ClassFileReader$A.class")));

        int magic = stream.readInt();
        int minor = stream.readUnsignedShort();
        int major = stream.readUnsignedShort();
        int constantPoolCount = stream.readUnsignedShort();
        Constant[] constants = new Constant[constantPoolCount];
        for (int i = 1; i < constantPoolCount; i++) {
            int tag = stream.readByte();
            switch (tag) {
                case Constant.Class:
                    constants[i] = new ClassConstant();
                    constants[i].read(stream);
                    break;
                case Constant.Fieldref:
                case Constant.Methodref:
                case Constant.InterfaceMethodref:
                    constants[i] = new FieldMethodRefConstant(tag);
                    constants[i].read(stream);
                    break;
                case Constant.String:
                    constants[i] = new StringConstant();
                    constants[i].read(stream);
                    break;
                case Constant.Integer:
                    constants[i] = new IntegerConstant();
                    constants[i].read(stream);
                    break;
                case Constant.Float:
                    constants[i] = new FloatConstant();
                    constants[i].read(stream);
                    break;
                case Constant.Long:
                    constants[i] = new LongConstant();
                    constants[i].read(stream);
                    break;
                case Constant.Double:
                    constants[i] = new DoubleConstant();
                    constants[i].read(stream);
                    break;
                case Constant.NameAndType:
                    constants[i] = new NameAndTypeConstant();
                    constants[i].read(stream);
                    break;
                case Constant.Utf8:
                    constants[i] = new Utf8Constant();
                    constants[i].read(stream);
                    break;
                case Constant.MethodHandle:
                    constants[i] = new MethodHandleConstant();
                    constants[i].read(stream);
                    break;
                case Constant.MethodType:
                    constants[i] = new MethodTypeConstant();
                    constants[i].read(stream);
                    break;
                case Constant.InvokeDynamic:
                    constants[i] = new InvokeDynamicConstant();
                    constants[i].read(stream);
                    break;
                default:
                    System.err.println("invalid constant pool tag");
                    break;
            }
        }
        // read constant pool
        int access = stream.readUnsignedShort();
        int thisClassIndex= stream.readUnsignedShort();
        int superClassIndex = stream.readUnsignedShort();
        int interfacesCount = stream.readUnsignedShort();
        int[] interfaceIndices = new int[interfacesCount];
        for (int i = 0; i < interfacesCount; i++) {
            interfaceIndices[i] = stream.readUnsignedShort();
        }
        int fieldsCount = stream.readUnsignedShort();
        // fields
        int methodsCount = stream.readUnsignedShort();
        // methods
        int attributesCount = stream.readUnsignedShort();
        // attributes
    }

    protected static class A {

    }
}
