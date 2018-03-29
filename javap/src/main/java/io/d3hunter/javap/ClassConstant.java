package io.d3hunter.javap;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/29.
 */
public class ClassConstant implements Constant {
    private int nameIndex;

    @Override
    public void read(DataInputStream stream) throws IOException {
        nameIndex = stream.readUnsignedShort();
    }
}
