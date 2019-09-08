package attribute;

import attribute.AttributeInfo;
import constant.ConstantType;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * TODO: desc
 *
 * @author optor
 * @date 2019/9/7
 */
public class LineNumberTableAttributeInfo extends AttributeInfo {

    public static class LineNumberTable {
        private short startPc;
        private short lineNumber;

        public LineNumberTable(DataInputStream dis) {
            try {
                this.startPc = dis.readShort();
                this.lineNumber = dis.readShort();
            } catch (IOException ignored) {
            }
        }

        @Override
        public String toString() {
            return String.format("startPc = %d, lineNumber = %d", startPc, lineNumber);
        }
    }

    private short lineNumberTableLength;

    private LineNumberTable[] lineNumberTables;

    public LineNumberTableAttributeInfo(ConstantType[] constantPool, DataInputStream dis, short attributeNameIndex, String attributeName) {
        super(constantPool, dis, attributeNameIndex, attributeName);
        try {
            this.lineNumberTableLength = dis.readShort();
            this.lineNumberTables = new LineNumberTable[this.lineNumberTableLength];
            for (int i = 0; i < this.lineNumberTables.length; i++) {
                this.lineNumberTables[i] = new LineNumberTable(dis);
            }
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
        StringBuilder sb = new StringBuilder("[\r\n");
        for (int i = 0; i < lineNumberTables.length; i++) {
            sb.append("\t").append(lineNumberTables[i].toString()).append(",\r\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
