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
public class FieldInfo {

    private final ConstantType[] constantPool;

    private short accessFlags;
    private short nameIndex;
    private short descriptorIndex;
    private short attributesCount;
    private AttributeInfo[] attributeInfos;

    public FieldInfo(ConstantType[] constantPool, DataInputStream dis) {
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
            if ((accessFlags & 0x0040) == 0x0040) {
                this.accessFlagStrings.add("ACC_VOLATILE");
            }
            if ((accessFlags & 0x0080) == 0x0080) {
                this.accessFlagStrings.add("ACC_TRANSIENT");
            }
            if ((accessFlags & 0x1000) == 0x1000) {
                this.accessFlagStrings.add("ACC_SYNTHETIC");
            }
            if ((accessFlags & 0x4000) == 0x4000) {
                this.accessFlagStrings.add("ACC_ENUM");
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
}
