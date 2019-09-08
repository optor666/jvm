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
public class LongConstantType implements ConstantType {

    private static final String tagStr = "Long";

    private static final byte tag = 5;

    private ConstantType[] constantPool;

    private int idx;

    private long val;

    public LongConstantType(ConstantType[] constantPool, int idx, DataInputStream dis) {
        this.constantPool = constantPool;
        this.idx = idx;

        int highBytes = 0;
        try {
            highBytes = dis.readInt();
        } catch (IOException ignored) {
        }
        int lowBytes = 0;
        try {
            lowBytes = dis.readInt();
        } catch (IOException ignored) {
        }

        this.val = (highBytes << 32) + lowBytes;
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
        return String.format("#%d = %s\t%sl", this.idx, tagStr, this.val);
    }
}
