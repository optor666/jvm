package constant;

import constant.ClassConstantType;
import constant.ConstantType;

/**
 * TODO: desc
 *
 * @author optor
 * @date 2019/9/5
 */
public class MethodrefConstantType implements ConstantType {

    private static final String tagStr = "Methodref";

    private static final byte tag = 10;

    private ConstantType[] constantPool;

    private int idx;

    private short classIndex;

    private short nameAndTypeIndex;

    public MethodrefConstantType(ConstantType[] constantPool, int idx, short classIndex, short nameAndTypeIndex) {
        this.constantPool = constantPool;
        this.idx = idx;
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public String getTxt() {
        return ((ClassConstantType) this.constantPool[this.classIndex]).getTxt() + "."  +
                ((NameAndTypeConstantType) this.constantPool[this.nameAndTypeIndex]).getTxt();
    }

    /**
     * 获取人性化可读字符串
     *
     * @return 人性化可读字符串
     */
    @Override
    public String humanizedString() {
        return String.format("#%d = %s\t#%d.#%d\t//%s", this.idx, tagStr, this.classIndex, this.nameAndTypeIndex, this.getTxt());
    }
}
