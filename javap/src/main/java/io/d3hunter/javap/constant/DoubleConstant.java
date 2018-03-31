package io.d3hunter.javap.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/29.
 */
public class DoubleConstant implements Constant {
    private double value;

    @Override
    public void read(DataInputStream stream) throws IOException {
        value = java.lang.Double.longBitsToDouble(stream.readLong());
    }
}
