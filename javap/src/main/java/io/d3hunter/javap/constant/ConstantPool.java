package io.d3hunter.javap.constant;

import io.d3hunter.javap.Item;

import java.io.IOException;

/**
 * Created by Administrator on 2018/3/31.
 */
public interface ConstantPool extends Item {
    String getString(int index) throws IOException;
}
