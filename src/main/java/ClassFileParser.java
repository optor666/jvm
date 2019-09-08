import attribute.AttributeInfo;
import attribute.AttributeInfoParser;
import constant.*;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Java 字节码文件解析器
 *
 * @author optor
 * @date 2019/9/5
 */
public class ClassFileParser {

    private static final char[] hexChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
            'd', 'e', 'f'};

    public static void main(String[] args) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream("/tmp/Hello.class"));

        // 魔数
        byte[] magic = new byte[4];
        dis.readFully(magic);
        System.out.println(String.format("magic: %s", bytesToHexString(magic)));

        // 主副版本号
        short minorVersion = dis.readShort();
        short majorVersion = dis.readShort();
        System.out.println(String.format("minor version: %d", minorVersion));
        System.out.println(String.format("major version: %d", majorVersion));

        // 常量池计数器
        short constantPoolCount = dis.readShort();
        System.out.println(String.format("constant_pool_count: %d", constantPoolCount));

        // 常量池
        ConstantType[] constantPool = new ConstantType[constantPoolCount];
        System.out.println("constant pool: ");
        for (int i = 1; i < constantPoolCount; i++) {
            byte constantTypeIdx = dis.readByte();
            switch (constantTypeIdx) {
                case 1:
                    constantPool[i] = new Utf8ConstantType(constantPool, i, dis);
                    break;
                case 3:
                    constantPool[i] = new IntegerConstantType(constantPool, i, dis);
                    break;
                case 4:
                    constantPool[i] = new FloatConstantType(constantPool, i, dis);
                    break;
                case 5:
                    constantPool[i] = new LongConstantType(constantPool, i, dis);
                    i++;
                    break;
                case 6:
                    constantPool[i] = new DoubleConstantType(constantPool, i, dis);
                    i++;
                    break;
                case 7:
                    constantPool[i] = new ClassConstantType(constantPool, i, dis.readShort());
                    break;
                case 8:
                    constantPool[i] = new StringConstantType(constantPool, i, dis.readShort());
                    break;
                case 9:
                    constantPool[i] = new FieldrefConstantType(constantPool, i, dis.readShort(), dis.readShort());
                    break;
                case 10:
                    constantPool[i] = new MethodrefConstantType(constantPool, i, dis.readShort(), dis.readShort());
                    break;
                case 11:
                    constantPool[i] = new InterfaceMethodrefConstantType(constantPool, i, dis.readShort(), dis.readShort());
                    break;
                case 12:
                    constantPool[i] = new NameAndTypeConstantType(constantPool, i, dis.readShort(), dis.readShort());
                    break;
                case 13:
                    break;
                case 14:
                    break;
                case 15:
                    constantPool[i] = new MethodHandleConstantType(constantPool, i, dis);
                    break;
                case 16:
                    constantPool[i] = new MethodTypeConstantType(constantPool, i, dis);
                    break;
                case 17:
                    break;
                case 18:
                    constantPool[i] = new InvokeDynamicTypeConstantType(constantPool, i, dis);
                    break;
            }
        }
        for (int i = 1; i < constantPoolCount; i++) {
            if (constantPool[i] == null) {
                continue;
            }
            System.out.println("\t" + constantPool[i].humanizedString());
        }

        // AccessFlags, 访问标志
        byte[] bytes = new byte[2];
        dis.readFully(bytes);
        List<String> accessFlags = new ArrayList<>();
        if ((bytes[1] & 0x01) == 1) {
            accessFlags.add("ACC_PUBLIC");
        }
        if ((bytes[1] & 0x10) == 16) {
            accessFlags.add("ACC_FINAL");
        }
        if ((bytes[1] & 0x20) == 32) {
            accessFlags.add("ACC_SUPER");
        }
        if ((bytes[0] & 0x02) == 2) {
            accessFlags.add("ACC_INTERFACE");
        }
        if ((bytes[0] & 0x04) == 4) {
            accessFlags.add("ACC_ABSTRACT");
        }
        if ((bytes[0] & 0x10) == 16) {
            accessFlags.add("ACC_SYNTHETIC");
        }
        if ((bytes[0] & 0x20) == 32) {
            accessFlags.add("ACC_ANNOTATION");
        }
        if ((bytes[0] & 0x40) == 64) {
            accessFlags.add("ACC_ENUM");
        }
        System.out.println("access_flags: " + String.join(", ", accessFlags));

        // this_class
        System.out.println("this_class: " + constantPool[dis.readShort()].getTxt());
        // super_class
        System.out.println("super_class: " + constantPool[dis.readShort()].getTxt());

        // interfaces_count
        short interfacesCount = dis.readShort();
        System.out.println(String.format("interfaces_count: %d", interfacesCount));

        // interfaces[interfaces_count]
        short[] interfaces = new short[interfacesCount];
        String[] interfaceNames = new String[interfacesCount];
        for (int i = 0; i < interfacesCount; i++) {
            interfaces[i] = dis.readShort();
            interfaceNames[i] = (constantPool[interfaces[i]]).getTxt();
        }
        System.out.println(String.format("interfaces: %s", Arrays.toString(interfaces)));
        System.out.println(String.format("interfaceNames: %s", Arrays.toString(interfaceNames)));

        // fields_count
        short fieldsCount = dis.readShort();
        System.out.println(String.format("fields_count: %d", fieldsCount));
        FieldInfo[] fieldInfos = new FieldInfo[fieldsCount];
        for (int i = 0; i < fieldInfos.length; i++) {
            fieldInfos[i] = new FieldInfo(constantPool, dis);
        }
        for (int i = 0; i < fieldInfos.length; i++) {
            System.out.println("\t" + fieldInfos[i].getAccessFlagStrings() + " " + fieldInfos[i].getDescriptor() + " " + fieldInfos[i].getName());
        }

        // methods_count
        short methodsCount = dis.readShort();
        System.out.println(String.format("methods_count: %d", methodsCount));
        MethodInfo[] methodInfos = new MethodInfo[methodsCount];
        for (int i = 0; i < methodInfos.length; i++) {
            methodInfos[i]= new MethodInfo(constantPool, dis);
        }
        for (int i = 0; i < methodInfos.length; i++) {
            System.out.println(methodInfos[i]);
        }

        // attributes_count
        short attributesCount = dis.readShort();
        AttributeInfo[] attributeInfos = new AttributeInfo[attributesCount];
        for (int i = 0; i < attributeInfos.length; i++) {
            attributeInfos[i] = AttributeInfoParser.parse(constantPool, dis);
        }
        for (int i = 0; i < attributeInfos.length; i++) {
            System.out.println(attributeInfos[i].humanizedString());
        }

        dis.close();
    }

    /**
     * 转换字节数组为十六进制字符串
     *
     * @param bytes 字节数组
     * @return 相应的十六进制字符串
     */
    private static String bytesToHexString(byte[] bytes) {
        char[] chars = new char[bytes.length * 2];
        int i = 0;
        for (byte b : bytes) {
            char[] tmpChars = byteToHexChars(b);
            chars[i++] = tmpChars[0];
            chars[i++] = tmpChars[1];
        }
        return new String(chars);
    }

    /**
     * 使用两个十六进制字符表示一个字节
     *
     * @param b 一个字节
     * @return 相应的十六进制字符数组
     */
    private static char[] byteToHexChars(byte b) {
        char[] chars = new char[2];
        chars[1] = hexChars[(b & 0xf)];
        chars[0] = hexChars[(b >> 4 & 0xf)];
        return chars;
    }
}
