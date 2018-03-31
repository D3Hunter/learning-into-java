package io.d3hunter.javap;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/31.
 */
public interface Item {
    void read(DataInputStream stream) throws IOException;
}
