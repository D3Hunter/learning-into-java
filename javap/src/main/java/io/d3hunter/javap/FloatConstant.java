package io.d3hunter.javap;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/29.
 */
public class FloatConstant implements Constant {
    private float value;

    @Override
    public void read(DataInputStream stream) throws IOException {
        value = java.lang.Float.intBitsToFloat(stream.readInt());
    }
}
