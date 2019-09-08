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
public class InvokeDynamicTypeConstantType implements ConstantType {

    private static final String tagStr = "InvokeDynamic";

    private static final byte tag = 18;

    private ConstantType[] constantPool;

    private int idx;

    private short bootstrapMethodAttrIndex;

    private short nameAndTypeIndex;

    public InvokeDynamicTypeConstantType(ConstantType[] constantPool, int idx, DataInputStream dis) {
        this.constantPool = constantPool;
        this.idx = idx;
        try {
            this.bootstrapMethodAttrIndex = dis.readShort();
            this.nameAndTypeIndex = dis.readShort();
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
