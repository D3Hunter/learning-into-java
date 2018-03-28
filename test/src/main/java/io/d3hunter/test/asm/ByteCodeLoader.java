package io.d3hunter.test.asm;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Administrator on 2017/2/5.
 */
public class ByteCodeLoader extends URLClassLoader {
    public ByteCodeLoader() {
        super(new URL[0]);
    }

    public Class loadByteCode(String name, byte[] code) {
        Class result = super.defineClass(name, code, 0, code.length);
        return result;
    }
}
