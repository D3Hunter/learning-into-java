package io.d3hunter.javap.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/29.
 */
public class StringConstant implements Constant {
    private int stringIndex;

    @Override
    public void read(DataInputStream stream) throws IOException {
        stringIndex = stream.readUnsignedShort();
    }
}
