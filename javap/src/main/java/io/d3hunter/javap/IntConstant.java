package io.d3hunter.javap;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/29.
 */
public class IntConstant implements Constant {
    private int value;

    @Override
    public void read(DataInputStream stream) throws IOException {
        value = stream.readInt();
    }
}
