package io.d3hunter.javap.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/29.
 */
public class MethodHandleConstant implements Constant {
    private int referenceIndex;
    private byte referenceKind;

    @Override
    public void read(DataInputStream stream) throws IOException {
        referenceKind = stream.readByte();
        referenceIndex = stream.readUnsignedShort();
    }
}
