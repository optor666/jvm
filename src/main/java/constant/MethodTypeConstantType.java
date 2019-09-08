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
public class MethodTypeConstantType implements ConstantType {

    private static final String tagStr = "MethodType";

    private static final byte tag = 16;

    private ConstantType[] constantPool;

    private int idx;

    private short descriptorIndex;

    public MethodTypeConstantType(ConstantType[] constantPool, int idx, DataInputStream dis) {
        this.constantPool = constantPool;
        this.idx = idx;
        try {
            this.descriptorIndex = dis.readShort();
        } catch (IOException ignored) {
        }
    }

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
        return null;
    }
}
