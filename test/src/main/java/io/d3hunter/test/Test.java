package io.d3hunter.test;

/**
 * Created by jujj on 2016/11/11.
 */
public class Test {
    boolean field = true;

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static final Test EMPTY_MAP_FIELD = new Test();

    static {
        EMPTY_MAP_FIELD.ensureMutable();
    }

    private void ensureMutable() {
        if (!field)
            throw new UnsupportedOperationException();
    }

    public String getString() {
        return "Hahahah, it works";
    }
}
