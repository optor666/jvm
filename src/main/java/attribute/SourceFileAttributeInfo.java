package attribute;

import constant.ConstantType;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * TODO: desc
 *
 * @author optor
 * @date 2019/9/7
 */
public class SourceFileAttributeInfo extends AttributeInfo {

    private short sourceFileIndex;

    public SourceFileAttributeInfo(ConstantType[] constantPool, DataInputStream dis, short attributeNameIndex, String attributeName) {
        super(constantPool, dis, attributeNameIndex, attributeName);
        try {
            this.sourceFileIndex = dis.readShort();
        } catch (IOException ignored) {
        }
    }

    /**
     * 获取人性化可读字符串
     *
     * @return 人性化可读字符串
     */
    @Override
    public String humanizedString() {
        return this.getSourceFileName();
    }

    private String sourceFileName;

    public String getSourceFileName() {
        if (this.sourceFileName == null) {
            this.sourceFileName = this.constantPool[this.sourceFileIndex].getTxt();
        }
        return sourceFileName;
    }
}
