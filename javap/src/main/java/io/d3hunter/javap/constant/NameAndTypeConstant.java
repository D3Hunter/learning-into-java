package io.d3hunter.javap.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/29.
 */
public class NameAndTypeConstant implements Constant {
    private int nameIndex;
    private int descriptorIndex;

    @Override
    public void read(DataInputStream stream) throws IOException {
        nameIndex = stream.readUnsignedShort();
        descriptorIndex = stream.readUnsignedShort();
    }
}
