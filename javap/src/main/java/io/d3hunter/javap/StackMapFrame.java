package io.d3hunter.javap;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/4/1.
 */
public class StackMapFrame implements Item {
    private int type;
    private FrameType typeEnum;
    private int offsetDelta;
    private int numberOfLocals;
    private VerificationTypeInfo[] locals;
    private int numberOfStackItems;
    private VerificationTypeInfo[] stack;

    @Override
    public void read(DataInputStream stream) throws IOException {
        type = stream.readUnsignedByte();
        StackMapFrame frame;
        if (0 <= type && type <= 63) {
            typeEnum = FrameType.SAME_FRAME;
        } else if (64 <= type && type <= 127) {
            typeEnum = FrameType.SAME_LOCALS_1_STACK_ITEM_FRAME;
            numberOfStackItems = 1;
            stack = readVerificationTypeInfos(numberOfStackItems, stream);
        } else if (type == 247) {
            typeEnum = FrameType.SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED;
            offsetDelta = stream.readUnsignedShort();
            numberOfStackItems = 1;
            stack = readVerificationTypeInfos(numberOfStackItems, stream);
        } else if (248 <= type && type <= 250) {
            typeEnum = FrameType.CHOP_FRAME;
            offsetDelta = stream.readUnsignedShort();
        } else if (type == 251) {
            typeEnum = FrameType.SAME_FRAME_EXTENDED;
            offsetDelta = stream.readUnsignedShort();
        } else if (252 <= type && type <= 254) {
            typeEnum = FrameType.APPEND_FRAME;
            offsetDelta = stream.readUnsignedShort();
            numberOfLocals = type - 251;
            locals = readVerificationTypeInfos(numberOfLocals, stream);
        } else if (type == 255) {
            typeEnum = FrameType.FULL_FRAME;
            offsetDelta = stream.readUnsignedShort();
            numberOfLocals = stream.readUnsignedShort();
            locals = readVerificationTypeInfos(numberOfLocals, stream);
            numberOfStackItems = stream.readUnsignedShort();
            stack = readVerificationTypeInfos(numberOfStackItems, stream);
        } else {
            throw new IOException("invalid frame type");
        }
    }

    private VerificationTypeInfo[] readVerificationTypeInfos(int count, DataInputStream stream) throws IOException {
        VerificationTypeInfo[] stack = new VerificationTypeInfo[count];
        for (int i = 0; i < count; i++) {
            stack[i] = new VerificationTypeInfo();
            stack[i].read(stream);
        }
        return stack;
    }

    public enum FrameType {
        SAME_FRAME,
        SAME_LOCALS_1_STACK_ITEM_FRAME,
        SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED,
        CHOP_FRAME,
        SAME_FRAME_EXTENDED,
        APPEND_FRAME,
        FULL_FRAME,
    }

    public enum VerificationType {
        TOP_VARIABLE,
        INTEGER_VARIABLE,
        FLOAT_VARIABLE,
        LONG_VARIABLE,
        DOUBLE_VARIABLE,
        NULL_VARIABLE,
        UNINITIALIZEDTHIS_VARIABLE,
        OBJECT_VARIABLE,
        UNINITIALIZED_VARIABLE;

        private static VerificationType[] types = {
                VerificationType.TOP_VARIABLE,
                VerificationType.INTEGER_VARIABLE,
                VerificationType.FLOAT_VARIABLE,
                VerificationType.DOUBLE_VARIABLE,
                VerificationType.LONG_VARIABLE,
                VerificationType.NULL_VARIABLE,
                VerificationType.UNINITIALIZEDTHIS_VARIABLE,
                VerificationType.OBJECT_VARIABLE,
                VerificationType.UNINITIALIZED_VARIABLE};

        public static VerificationType get(int num) {
            return types[num];
        }
    }

    public static class VerificationTypeInfo implements Item {
        private VerificationType type;
        private int cpoolIndex;
        private int offset;

        @Override
        public void read(DataInputStream stream) throws IOException {
            int num = stream.readUnsignedByte();
            if (num < 0 && num > 8) {
                throw new IOException("invalid verification type");
            }

            type = VerificationType.get(num);

            if (type == VerificationType.OBJECT_VARIABLE) {
                cpoolIndex = stream.readUnsignedShort();
            } else if (type == VerificationType.UNINITIALIZED_VARIABLE) {
                offset = stream.readUnsignedShort();
            }
        }
    }
}
