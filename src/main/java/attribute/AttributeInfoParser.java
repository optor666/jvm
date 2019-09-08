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
public class AttributeInfoParser {

    public static AttributeInfo parse(ConstantType[] constantPool, DataInputStream dis) {
        try {
            short attributeNameIndex = dis.readShort();
            String attributeName = constantPool[attributeNameIndex].getTxt();
            switch (attributeName) {
                case "Code":
                    return new CodeAttributeInfo(constantPool, dis, attributeNameIndex, attributeName);
                case "LineNumberTable":
                    return new LineNumberTableAttributeInfo(constantPool, dis, attributeNameIndex, attributeName);
                case "SourceFile":
                    return new SourceFileAttributeInfo(constantPool, dis, attributeNameIndex, attributeName);
                default:
                    throw new RuntimeException(String.format("Unknown attributeName: %s", attributeName));
            }
        } catch (IOException ignored) {
            return null;
        }
    }
}
