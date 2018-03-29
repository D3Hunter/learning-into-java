package io.d3hunter.javap;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/29.
 */
public class InvokeDynamicConstant implements Constant {
    private int nameAndTypeIndex;
    private int bootstrapMethodAttrIndex;

    @Override
    public void read(DataInputStream stream) throws IOException {
        bootstrapMethodAttrIndex = stream.readUnsignedShort();
        nameAndTypeIndex = stream.readUnsignedShort();
    }
}
