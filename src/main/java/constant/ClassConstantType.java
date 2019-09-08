package constant;

/**
 * TODO: desc
 *
 * @author optor
 * @date 2019/9/5
 */
public class ClassConstantType implements ConstantType {

    private static final String tagStr = "Class";

    private static final byte tag = 7;

    private ConstantType[] constantPool;

    private int idx;

    private short nameIndex;

    public ClassConstantType(ConstantType[] constantPool, int idx, short nameIndex) {
        this.constantPool = constantPool;
        this.idx = idx;
        this.nameIndex = nameIndex;
    }

    public String getTxt() {
        return ((Utf8ConstantType) this.constantPool[this.nameIndex]).getTxt();
    }

    /**
     * 获取人性化可读字符串
     *
     * @return 人性化可读字符串
     */
    @Override
    public String humanizedString() {
        return String.format("#%d = %s\t#%d\t// %s", this.idx, tagStr, this.nameIndex, this.getTxt());
    }
}
