package io.d3hunter.javap;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/29.
 */
public class FieldMethodRefConstant implements Constant {
    private int type;
    private int classIndex;
    private int nameAndTypeIndex;

    public FieldMethodRefConstant(int type) {
        this.type = type;
    }

    @Override
    public void read(DataInputStream stream) throws IOException {
        classIndex = stream.readUnsignedShort();
        nameAndTypeIndex = stream.readUnsignedShort();
    }
}
