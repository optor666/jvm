package attribute;

import constant.ConstantType;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * TODO: desc
 *
 * @author optor
 * @date 2019/9/6
 */
public abstract class AttributeInfo {

    protected final ConstantType[] constantPool;

    private final short attributeNameIndex;

    private int attributeLength;

    public AttributeInfo(ConstantType[] constantPool, DataInputStream dis, short attributeNameIndex, String attributeName) {
        this.constantPool = constantPool;
        this.attributeNameIndex = attributeNameIndex;
        this.attributeName = attributeName;
        try {
            this.attributeLength = dis.readInt();
        } catch (IOException ignored) {
        }
    }

    /**
     * 获取人性化可读字符串
     *
     * @return 人性化可读字符串
     */
    public abstract String humanizedString();

    public short getAttributeNameIndex() {
        return attributeNameIndex;
    }

    public int getAttributeLength() {
        return attributeLength;
    }

    private final String attributeName;

    public String getAttributeName() {
        return this.attributeName;
    }
}
