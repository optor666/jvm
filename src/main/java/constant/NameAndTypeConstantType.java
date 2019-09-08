package constant;

import constant.ConstantType;

/**
 * TODO: desc
 *
 * @author optor
 * @date 2019/9/5
 */
public class NameAndTypeConstantType implements ConstantType {

    private static final String tagStr = "NameAndType";

    private static final byte tag = 12;

    private ConstantType[] constantPool;

    private int idx;

    private short nameIndex;

    private short descriptorIndex;

    public NameAndTypeConstantType(ConstantType[] constantPool, int idx, short nameIndex, short descriptorIndex) {
        this.constantPool = constantPool;
        this.idx = idx;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    public String getTxt() {
        return ((Utf8ConstantType) this.constantPool[this.nameIndex]).getTxt() + ":" +
                ((Utf8ConstantType) this.constantPool[this.descriptorIndex]).getTxt();
    }

    /**
     * 获取人性化可读字符串
     *
     * @return 人性化可读字符串
     */
    @Override
    public String humanizedString() {
        return String.format("#%d = %s\t#%d:#%d\t//%s", this.idx, tagStr, this.nameIndex, this.descriptorIndex, this.getTxt());
    }
}
