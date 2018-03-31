package io.d3hunter.javap.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/29.
 */
public class Utf8Constant implements Constant {
    private String value;

    @Override
    public void read(DataInputStream stream) throws IOException {
        value = stream.readUTF();
    }
}