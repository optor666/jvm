package constant;

import constant.ConstantType;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * TODO: desc
 *
 * @author optor
 * @date 2019/9/5
 */
public class DoubleConstantType implements ConstantType {

    private static final String tagStr = "Double";

    private static final byte tag = 6;

    private ConstantType[] constantPool;

    private int idx;

    private double val;

    public DoubleConstantType(ConstantType[] constantPool, int idx, DataInputStream dis) {
        this.constantPool = constantPool;
        this.idx = idx;

        byte[] bytes = new byte[8];
        try {
            dis.readFully(bytes);
        } catch (IOException ignored) {
        }
        this.val = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getDouble();

//        int highBytes = 0;
//        try {
//            highBytes = dis.readInt();
//        } catch (IOException ignored) {
//        }
//        int lowBytes = 0;
//        try {
//            lowBytes = dis.readInt();
//        } catch (IOException ignored) {
//        }
//        long bits = ((long) highBytes << 32) + lowBytes;
//        this.val = Double.longBitsToDouble(bits);
//        if (bits == 0x7ff0000000000000L) {
//            this.val = Double.POSITIVE_INFINITY;
//        } else if (bits == 0xfff0000000000000L) {
//            this.val = Double.NEGATIVE_INFINITY;
//        } else if ((bits > 0x7ff0000000000000L && bits < 0x7fffffffffffffffL) || (bits > 0xfff0000000000000L && bits < 0xffffffffffffffffL)) {
//            this.val = Double.NaN;
//        } else {
//            int s = ((bits >> 63) == 0) ? 1 : -1;
//            int e = (int) ((bits >> 52) & 0x7ffL);
//            long m = (e == 0) ?
//                    (bits & 0xfffffffffffffL) << 1 :
//                    (bits & 0xfffffffffffffL) | 0x1000000000000L;
//            this.val = s * m * 2 ^ (e - 1075);
//        }
    }

    @Override
    public String getTxt() {
        return null;
    }

    /**
     * 获取人性化可读字符串
     *
     * @return 人性化可读字符串
     */
    @Override
    public String humanizedString() {
        return String.format("#%d = %s\t%sd", this.idx, tagStr, this.val);
    }
}
