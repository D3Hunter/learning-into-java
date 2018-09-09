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

    public static int ACC_SYNCHRONIZED = 0x0020;
    public static int ACC_BRIDGE = 0x0040;
    public static int ACC_VARARGS = 0x0080;
    public static int ACC_NATIVE = 0x0100;
    public static int ACC_STRICT = 0x0800;

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

    public static boolean isPrivate(int access) {
        return 0 != (access & ACC_PRIVATE);
    }

    public static boolean isProtected(int access) {
        return 0 != (access & ACC_PROTECTED);
    }

    public static boolean isStatic(int access) {
        return 0 != (access & ACC_STATIC);
    }

    public static boolean isVolatile(int access) {
        return 0 != (access & ACC_VOLATILE);
    }

    public static boolean isTransient(int access) {
        return 0 != (access & ACC_TRANSIENT);
    }

    public static boolean isSynchronized(int access) {
        return 0 != (access & ACC_SYNCHRONIZED);
    }

    public static boolean isBridge(int access) {
        return 0 != (access & ACC_BRIDGE);
    }

    public static boolean isVarargs(int access) {
        return 0 != (access & ACC_VARARGS);
    }

    public static boolean isNative(int access) {
        return 0 != (access & ACC_NATIVE);
    }

    public static boolean isStrict(int access) {
        return 0 != (access & ACC_STRICT);
    }

    public static String getClassAccessFlags(int access) {
        StringBuilder sb = new StringBuilder();
        if (isPublic(access)) {
            sb.append(" ").append("public");
        }
        if (isFinal(access)) {
            sb.append(" ").append("final");
        }
        if (isSuper(access)) {
            sb.append(" ").append("super");
        }
        if (isInterface(access)) {
            sb.append(" ").append("interface");
        }
        if (isAbstract(access)) {
            sb.append(" ").append("abstract");
        }
        if (isSynthetic(access)) {
            sb.append(" ").append("synthetic");
        }
        if (isAnnotation(access)) {
            sb.append(" ").append("annotation");
        }

        return sb.toString().trim();
    }

    public static String getFieldAccessFlags(int access) {
        StringBuilder sb = new StringBuilder();
        if (isPublic(access)) {
            sb.append(" ").append("public");
        }
        if (isPrivate(access)) {
            sb.append(" ").append("private");
        }
        if (isProtected(access)) {
            sb.append(" ").append("protected");
        }
        if (isStatic(access)) {
            sb.append(" ").append("static");
        }
        if (isFinal(access)) {
            sb.append(" ").append("final");
        }
        if (isVolatile(access)) {
            sb.append(" ").append("volatile");
        }
        if (isTransient(access)) {
            sb.append(" ").append("transient");
        }
        if (isSynthetic(access)) {
            sb.append(" ").append("synthetic");
        }
        if (isEnum(access)) {
            sb.append(" ").append("enum");
        }

        return sb.toString().trim();
    }

    public static String getMethodAccessFlags(int access) {
        StringBuilder sb = new StringBuilder();
        if (isPublic(access)) {
            sb.append(" ").append("public");
        }
        if (isPrivate(access)) {
            sb.append(" ").append("private");
        }
        if (isProtected(access)) {
            sb.append(" ").append("protected");
        }
        if (isStatic(access)) {
            sb.append(" ").append("static");
        }
        if (isFinal(access)) {
            sb.append(" ").append("final");
        }
        if (isSynchronized(access)) {
            sb.append(" ").append("synchronized");
        }
        if (isBridge(access)) {
            sb.append(" ").append("bridge");
        }
        if (isVarargs(access)) {
            sb.append(" ").append("varargs");
        }
        if (isNative(access)) {
            sb.append(" ").append("native");
        }

        return sb.toString().trim();
    }
}
