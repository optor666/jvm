package constant;

import constant.ConstantType;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * TODO: desc
 *
 * @author optor
 * @date 2019/9/5
 */
public class FloatConstantType implements ConstantType {

    private static final String tagStr = "Float";

    private static final byte tag = 4;

    private ConstantType[] constantPool;

    private int idx;

    private float val;

    public FloatConstantType(ConstantType[] constantPool, int idx, DataInputStream dis) {
        this.constantPool = constantPool;
        this.idx = idx;
        byte[] bytes = new byte[4];
        try {
            dis.readFully(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.val = parse(bytes);
    }

    public static void main(String[] args) {
        byte[] bytes = new byte[]{64, 96, 0, 0};
        System.out.println(parse(bytes));
    }

    private static float parse(byte[] bytes) {
        int bits = (bytes[0] << 24) + (bytes[1] << 16) + (bytes[2] << 8) + bytes[3];
        return Float.intBitsToFloat(bits);
//        if (bits == 0x7f800000) {
//            return Float.POSITIVE_INFINITY;
//        }
//        if (bits == 0xff800000) {
//            return Float.NEGATIVE_INFINITY;
//        }
//        if ((bits >= 0x7f800001 && bits <= 0x7fffffff) || (bits >= 0xff800001 && bits <= 0xffffffff)){
//            return Float.NaN;
//        }
//        int s = ((bits >> 31) == 0) ? 1 : -1;
//        int e = ((bits >> 23) & 0xff);
//        int m = (e == 0) ? (bits & 0x7fffff) << 1 : (bits & 0x7fffff) | 0x800000;
//        return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
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
        return String.format("#%d = %s\t%sf", this.idx, tagStr, this.val);
    }
}
