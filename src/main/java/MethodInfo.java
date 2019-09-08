import attribute.AttributeInfo;
import attribute.AttributeInfoParser;
import constant.ConstantType;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: desc
 *
 * @author optor
 * @date 2019/9/6
 */
public class MethodInfo {

    private final ConstantType[] constantPool;

    private short accessFlags;
    private short nameIndex;
    private short descriptorIndex;
    private short attributesCount;
    private AttributeInfo[] attributeInfos;

    public MethodInfo(ConstantType[] constantPool, DataInputStream dis) {
        this.constantPool = constantPool;
        try {
            this.accessFlags = dis.readShort();
            this.nameIndex = dis.readShort();
            this.descriptorIndex = dis.readShort();
            this.attributesCount = dis.readShort();
            this.attributeInfos = new AttributeInfo[this.attributesCount];
            for (int i = 0; i < this.attributeInfos.length; i++) {
                this.attributeInfos[i] = AttributeInfoParser.parse(constantPool, dis);
            }
        } catch (IOException ignored) {
        }
    }

    public short getAccessFlags() {
        return accessFlags;
    }

    public short getNameIndex() {
        return nameIndex;
    }

    public short getDescriptorIndex() {
        return descriptorIndex;
    }

    private List<String> accessFlagStrings;
    private String name;
    private String descriptor;

    public List<String> getAccessFlagStrings() {
        if (this.accessFlagStrings == null) {
            this.accessFlagStrings = new ArrayList<>();
            if ((accessFlags & 0x0001) == 0x0001) {
                this.accessFlagStrings.add("ACC_PUBLIC");
            }
            if ((accessFlags & 0x0002) == 0x0002) {
                this.accessFlagStrings.add("ACC_PRIVATE");
            }
            if ((accessFlags & 0x0004) == 0x0004) {
                this.accessFlagStrings.add("ACC_PROTECTED");
            }
            if ((accessFlags & 0x0008) == 0x0008) {
                this.accessFlagStrings.add("ACC_STATIC");
            }
            if ((accessFlags & 0x0010) == 0x0010) {
                this.accessFlagStrings.add("ACC_FINAL");
            }
            if ((accessFlags & 0x0020) == 0x0020) {
                this.accessFlagStrings.add("ACC_SYNCHRONIZED");
            }
            if ((accessFlags & 0x0040) == 0x0040) {
                this.accessFlagStrings.add("ACC_BRIDGE");
            }
            if ((accessFlags & 0x0080) == 0x0080) {
                this.accessFlagStrings.add("ACC_VARARGS");
            }
            if ((accessFlags & 0x0100) == 0x0100) {
                this.accessFlagStrings.add("ACC_NATIVE");
            }
            if ((accessFlags & 0x0400) == 0x0400) {
                this.accessFlagStrings.add("ACC_ABSTRACT");
            }
            if ((accessFlags & 0x0800) == 0x0800) {
                this.accessFlagStrings.add("ACC_STRICT");
            }
            if ((accessFlags & 0x1000) == 0x1000) {
                this.accessFlagStrings.add("ACC_SYNTHETIC");
            }
        }
        return this.accessFlagStrings;
    }

    public String getName() {
        if (name == null) {
            name = constantPool[nameIndex].getTxt();
        }
        return name;
    }

    public String getDescriptor() {
        if (descriptor == null) {
            descriptor = constantPool[descriptorIndex].getTxt();
        }
        return descriptor;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{").append("\r\n");
        sb.append("\t").append("accessFlagStrings = ").append(getAccessFlagStrings()).append(",").append("\r\n");
        sb.append("\t").append("name = ").append(getName()).append(",").append("\r\n");
        sb.append("\t").append("descriptor = ").append(getDescriptor()).append(",").append("\r\n");
        sb.append("\t").append("attributeInfos = ").append("[").append("\r\n");
        for (int i = 0; i < attributeInfos.length; i++) {
            sb.append("\t\t").append(attributeInfos[i].humanizedString()).append(",").append("\r\n");
        }
        sb.append("\t").append("]").append("\r\n");
        sb.append("}");
        return sb.toString();
    }
}
