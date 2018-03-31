package io.d3hunter.javap;

/**
 * Created by Administrator on 2018/3/31.
 */
public class AccessFlags {
    public static int ACC_PUBLIC = 0x0001;
    public static int ACC_FINAL = 0x0010;
    public static int ACC_SUPER = 0x0020;
    public static int ACC_INTERFACE = 0x0200;
    public static int ACC_ABSTRACT = 0x0400;
    public static int ACC_SYNTHETIC = 0x1000;
    public static int ACC_ANNOTATION = 0x2000;
    public static int ACC_ENUM = 0x4000;

    public static int ACC_PRIVATE = 0x0002;
    public static int ACC_PROTECTED = 0x0004;
    public static int ACC_STATIC = 0x0008;
    public static int ACC_VOLATILE = 0x0040;
    public static int ACC_TRANSIENT = 0x0080;

    public static boolean isPublic(int access) {
        return 0 != (access & ACC_PUBLIC);
    }

    public static boolean isFinal(int access) {
        return 0 != (access & ACC_FINAL);
    }

    public static boolean isSuper(int access) {
        return 0 != (access & ACC_SUPER);
    }

    public static boolean isInterface(int access) {
        return 0 != (access & ACC_INTERFACE);
    }

    public static boolean isAbstract(int access) {
        return 0 != (access & ACC_ABSTRACT);
    }

    public static boolean isSynthetic(int access) {
        return 0 != (access & ACC_SYNTHETIC);
    }

    public static boolean isAnnotation(int access) {
        return 0 != (access & ACC_ANNOTATION);
    }

    public static boolean isEnum(int access) {
        return 0 != (access & ACC_ENUM);
    }

}
