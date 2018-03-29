package io.d3hunter.javap;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/29.
 */
public interface Constant {
    int Class = 7;
    int Fieldref = 9;
    int Methodref = 10;
    int InterfaceMethodref = 11;
    int String = 8;
    int Integer = 3;
    int Float = 4;
    int Long = 5;
    int Double = 6;
    int NameAndType = 12;
    int Utf8 = 1;
    int MethodHandle = 15;
    int MethodType = 16;
    int InvokeDynamic = 18;

    void read(DataInputStream stream) throws IOException;
}
