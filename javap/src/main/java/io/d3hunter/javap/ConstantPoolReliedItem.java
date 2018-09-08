package io.d3hunter.javap;

import io.d3hunter.javap.constant.ConstantPool;

/**
 * Created by Administrator on 2018/9/8.
 */
public abstract class ConstantPoolReliedItem implements Item {
    protected ConstantPool constantPool;

    public ConstantPoolReliedItem(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }
}
