package constant;

import constant.ConstantType;

/**
 * TODO: desc
 *
 * @author optor
 * @date 2019/9/5
 */
public class StringConstantType implements ConstantType {

    private static final String tagStr = "String";

    private static final byte tag = 8;

    private ConstantType[] constantPool;

    private int idx;

    private short stringIndex;

    public StringConstantType(ConstantType[] constantPool, int idx, short stringIndex) {
        this.constantPool = constantPool;
        this.idx = idx;
        this.stringIndex = stringIndex;
    }

    public String getTxt() {
        return ((Utf8ConstantType) this.constantPool[this.stringIndex]).getTxt();
    }

    /**
     * 获取人性化可读字符串
     *
     * @return 人性化可读字符串
     */
    @Override
    public String humanizedString() {
        return String.format("#%d = %s\t#%d\t// %s", this.idx, tagStr, this.stringIndex, this.getTxt());
    }
}
