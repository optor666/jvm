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
public class CodeAttributeInfo extends AttributeInfo {

    public static class ExceptionTable {
        private short startPc;

        private short endPc;

        private short handlerPc;

        private short catchType;

        public ExceptionTable(DataInputStream dis) {
            try {
                this.startPc = dis.readShort();
                this.endPc = dis.readShort();
                this.handlerPc = dis.readShort();
                this.catchType = dis.readShort();
            } catch (IOException ignored) {
            }
        }

        @Override
        public String toString() {
            return String.format("startPc = %d, endPc = %d, handlerPc = %d, catchType = %d", startPc, endPc, handlerPc, catchType);
        }
    }

    // 当前方法操作数栈的最大深度
    private short maxStack;

    // 当前方法局部变量的个数（包括形参）
    private short maxLocals;

    private int codeLength;

    private byte[] codes;
    private String[] codeSymbols;

    private short exceptionTableLength;

    private ExceptionTable[] exceptionTables;

    private short attributesCount;

    private AttributeInfo[] attributeInfos;

    public CodeAttributeInfo(ConstantType[] constantPool, DataInputStream dis, short attributeNameIndex, String attributeName) {
        super(constantPool, dis, attributeNameIndex, attributeName);
        try {
            this.maxStack = dis.readShort();
            this.maxLocals = dis.readShort();
            this.codeLength = dis.readInt();
            this.codes = new byte[this.codeLength];
            dis.readFully(this.codes);

            this.codeSymbols = new String[this.codes.length];
            for (int i = 0; i < this.codeSymbols.length; i++) {
                int code = Byte.toUnsignedInt(this.codes[i]);
                switch (code) {
                    case 0x00:
                        // 什么都不做
                        this.codeSymbols[i] = "nop";
                        break;
                    case 0x01:
                        // 将 null 推送至栈顶
                        this.codeSymbols[i] = "aconst_null";
                        break;
                    case 0x02:
                        // 将 int 类型 -1 推送至栈顶
                        this.codeSymbols[i] = "iconst_ml";
                        break;
                    case 0x03:
                        // 将 int 类型 0 推送至栈顶
                        this.codeSymbols[i] = "iconst_0";
                        break;
                    case 0x04:
                        // 将 int 类型 1 推送至栈顶
                        this.codeSymbols[i] = "iconst_1";
                        break;
                    case 0x05:
                        // 将 int 类型 2 推送至栈顶
                        this.codeSymbols[i] = "iconst_2";
                        break;
                    case 0x06:
                        // 将 int 类型 3 推送至栈顶
                        this.codeSymbols[i] = "iconst_3";
                        break;
                    case 0x07:
                        // 将 int 类型 4 推送至栈顶
                        this.codeSymbols[i] = "iconst_4";
                        break;
                    case 0x08:
                        // 将 int 类型 5 推送至栈顶
                        this.codeSymbols[i] = "iconst_5";
                        break;
                    case 0x09:
                        // 将 long 类型 0 推送至栈顶
                        this.codeSymbols[i] = "lconst_0";
                        break;
                    case 0x0a:
                        // 将 long 类型 1 推送至栈顶
                        this.codeSymbols[i] = "lconst_1";
                        break;
                    case 0x0b:
                        // 将 float 类型 0 推送至栈顶
                        this.codeSymbols[i] = "fconst_0";
                        break;
                    case 0x0c:
                        // 将 float 类型 1 推送至栈顶
                        this.codeSymbols[i] = "fconst_1";
                        break;
                    case 0x0d:
                        // 将 float 类型 2 推送至栈顶
                        this.codeSymbols[i] = "fconst_2";
                        break;
                    case 0x0e:
                        // 将 double 类型 0 推送至栈顶
                        this.codeSymbols[i] = "dconst_0";
                        break;
                    case 0x0f:
                        // 将 double 类型 1 推送至栈顶
                        this.codeSymbols[i] = "dconst_1";
                        break;
                    case 0x10:
                        // 将单字节的常量值（-128 ~ 127）推送至栈顶
                        this.codeSymbols[i] = "bipush";
                        break;
                    case 0x11:
                        // 将一个短整类型常量值（-3276 ~ 32767）推送至栈顶
                        this.codeSymbols[i] = "sipush";
                        break;
                    case 0x12:
                        // 将 int, float, string 类型常量常量值从常量池中推送至栈顶
                        this.codeSymbols[i] = "ldc";
                        i++;
                        break;
                    case 0x13:
                        // 将 int, float, string 类型常量常量值从常量池中推送至栈顶（宽索引）
                        this.codeSymbols[i] = "ldc_w";
                        break;
                    case 0x14:
                        // 将 long, double 类型常量常量值从常量池中推送至栈顶（宽索引）
                        this.codeSymbols[i] = "ldc2_w";
                        break;
                    case 0x15:
                        // 将指定的 int 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "iload";
                        break;
                    case 0x16:
                        // 将指定的 long 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "lload";
                        break;
                    case 0x17:
                        // 将指定的 float 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "fload";
                        break;
                    case 0x18:
                        // 将指定的 double 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "dload";
                        break;
                    case 0x19:
                        // 将指定的引用类型本地变量推送至栈顶
                        this.codeSymbols[i] = "aload";
                        break;
                    case 0x1a:
                        // 将第 1 个 int 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "iload_0";
                        break;
                    case 0x1b:
                        // 将第 2 个 int 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "iload_1";
                        break;
                    case 0x1c:
                        // 将第 3 个 int 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "iload_2";
                        break;
                    case 0x1d:
                        // 将第 4 个 int 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "iload_3";
                        break;
                    case 0x1e:
                        // 将第 1 个 long 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "lload_0";
                        break;
                    case 0x1f:
                        // 将第 2 个 long 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "lload_1";
                        break;
                    case 0x20:
                        // 将第 3 个 long 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "lload_2";
                        break;
                    case 0x21:
                        // 将第 4 个 long 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "lload_3";
                        break;
                    case 0x22:
                        // 将第 1 个 float 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "fload_0";
                        break;
                    case 0x23:
                        // 将第 2 个 float 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "fload_1";
                        break;
                    case 0x24:
                        // 将第 3 个 float 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "fload_2";
                        break;
                    case 0x25:
                        // 将第 4 个 float 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "fload_3";
                        break;
                    case 0x26:
                        // 将第 1 个 double 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "dload_0";
                        break;
                    case 0x27:
                        // 将第 2 个 double 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "dload_1";
                        break;
                    case 0x28:
                        // 将第 3 个 double 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "dload_2";
                        break;
                    case 0x29:
                        // 将第 4 个 double 类型本地变量推送至栈顶
                        this.codeSymbols[i] = "dload_3";
                        break;
                    case 0x2a:
                        // 将第 1 个引用类型本地变量推送至栈顶
                        this.codeSymbols[i] = "aload_0";
                        break;
                    case 0x2b:
                        // 将第 2 个引用类型本地变量推送至栈顶
                        this.codeSymbols[i] = "aload_1";
                        break;
                    case 0x2c:
                        // 将第 3 个引用类型本地变量推送至栈顶
                        this.codeSymbols[i] = "aload_2";
                        break;
                    case 0x2d:
                        // 将第 4 个引用类型本地变量推送至栈顶
                        this.codeSymbols[i] = "aload_3";
                        break;
                    case 0x2e:
                        // 将 int 类型数组的指定元素推送至栈顶
                        this.codeSymbols[i] = "iaload";
                        break;
                    case 0x2f:
                        // 将 long 类型数组的指定元素推送至栈顶
                        this.codeSymbols[i] = "laload";
                        break;
                    case 0x30:
                        // 将 float 类型数组的指定元素推送至栈顶
                        this.codeSymbols[i] = "faload";
                        break;
                    case 0x31:
                        // 将 double 类型数组的指定元素推送至栈顶
                        this.codeSymbols[i] = "daload";
                        break;
                    case 0x32:
                        // 将引用类型数组的指定元素推送至栈顶
                        this.codeSymbols[i] = "aaload";
                        break;
                    case 0x33:
                        // 将 boolean, byte 类型数组的指定元素推送至栈顶
                        this.codeSymbols[i] = "baload";
                        break;
                    case 0x34:
                        // 将 char 类型数组的指定元素推送至栈顶
                        this.codeSymbols[i] = "caload";
                        break;
                    case 0x35:
                        // 将 short 类型数组的指定元素推送至栈顶
                        this.codeSymbols[i] = "saload";
                        break;
                    case 0x36:
                        // 将栈顶 int 类型数值存入指定本地变量
                        this.codeSymbols[i] = "istore";
                        break;
                    case 0x37:
                        // 将栈顶 long 类型数值存入指定本地变量
                        this.codeSymbols[i] = "lstore";
                        break;
                    case 0x38:
                        // 将栈顶 float 类型数值存入指定本地变量
                        this.codeSymbols[i] = "fstore";
                        break;
                    case 0x39:
                        // 将栈顶 double 类型数值存入指定本地变量
                        this.codeSymbols[i] = "dstore";
                        break;
                    case 0x3a:
                        // 将栈顶引用类型数值存入指定本地变量
                        this.codeSymbols[i] = "astore";
                        break;
                    case 0x3b:
                        // 将栈顶 int 类型数值存入第 1 个本地变量
                        this.codeSymbols[i] = "istore_0";
                        break;
                    case 0x3c:
                        // 将栈顶 int 类型数值存入第 2 个本地变量
                        this.codeSymbols[i] = "istore_1";
                        break;
                    case 0x3d:
                        // 将栈顶 int 类型数值存入第 3 个本地变量
                        this.codeSymbols[i] = "istore_2";
                        break;
                    case 0x3e:
                        // 将栈顶 int 类型数值存入第 4 个本地变量
                        this.codeSymbols[i] = "istore_3";
                        break;
                    case 0xb0:
                        // 从当前方法返回对象引用
                        this.codeSymbols[i] = "areturn";
                        break;
                    case 0xb1:
                        // 从当前方法返回 void
                        this.codeSymbols[i] = "return";
                        break;
                    case 0xb2:
                        // 获取指定类的静态字段，并将其值压入栈顶
                        this.codeSymbols[i] = "getstatic";
                        break;
                    case 0xb3:
                        // 为指定类的静态字段赋值
                        this.codeSymbols[i] = "putstatic";
                        break;
                    case 0xb4:
                        // 获取指定类的实例字段，并将其值压入栈顶
                        this.codeSymbols[i] = "getfield";
                        break;
                    case 0xb5:
                        // 为指定类的实例字段赋值
                        this.codeSymbols[i] = "putfield";
                        i++;
                        i++;
                        break;
                    case 0xb6:
                        // 调用实例方法
                        this.codeSymbols[i] = "invokevirtual";
                        break;
                    case 0xb7:
                        // 调用父类方法、实例初始化方法、私有方法
                        this.codeSymbols[i] = "invokespecial";
                        i++;
                        i++;
                        break;
                    case 0xb8:
                        // 调用静态方法
                        this.codeSymbols[i] = "invokestatic";
                        break;
                    case 0xb9:
                        // 调用接口方法
                        this.codeSymbols[i] = "invokeinterface";
                        break;
                    case 0xba:
                        // 调用动态链接方法
                        this.codeSymbols[i] = "invokedynamic";
                        break;
                    case 0xbb:
                        // 创建一个对象，并将其引用值压入栈顶
                        this.codeSymbols[i] = "new";
                        break;
                    case 0xbc:
                        // 创建一个指定原始类型（如 int、float、char 等）的数组，并将其引用值压入栈顶
                        this.codeSymbols[i] = "newarray";
                        break;
                    case 0xbd:
                        // 创建一个引用型（如类、接口、数组）的数组，并将其引用值压入栈顶
                        this.codeSymbols[i] = "anewarray";
                        break;
                    case 0xbe:
                        // 获得数组的长度并压入栈顶
                        this.codeSymbols[i] = "arraylength";
                        break;
                    case 0xbf:
                        // 将栈顶的异常抛出
                        this.codeSymbols[i] = "athrow";
                        break;
                }
            }

            this.exceptionTableLength = dis.readShort();
            this.exceptionTables = new ExceptionTable[this.exceptionTableLength];
            for (int i = 0; i < this.exceptionTables.length; i++) {
                this.exceptionTables[i] = new ExceptionTable(dis);
            }
            this.attributesCount = dis.readShort();
            this.attributeInfos = new AttributeInfo[this.attributesCount];
            for (int i = 0; i < this.attributeInfos.length; i++) {
                this.attributeInfos[i] = AttributeInfoParser.parse(constantPool, dis);
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
        StringBuilder sb = new StringBuilder("{");
        sb.append("\t").append("maxStack = ").append(maxStack).append(",\r\n");
        sb.append("\t").append("maxLocals = ").append(maxLocals).append(",\r\n");
        sb.append("\t").append("codes = [").append("\r\n");
        for (int i = 0; i < codes.length; i++) {
            sb.append("\t\t").append(codes[i]).append(",\r\n");
        }
        sb.append("\t").append("]").append("\r\n");
        sb.append("\t").append("exceptionTables = [").append("\r\n");
        for (int i = 0; i < exceptionTables.length; i++) {
            sb.append("\t\t").append(exceptionTables[i].toString()).append(",\r\n");
        }
        sb.append("\t").append("]").append("\r\n");
        sb.append("\t").append("attributeInfos = [").append("\r\n");
        for (int i = 0; i < attributeInfos.length; i++) {
            sb.append("\t\t").append(attributeInfos[i].humanizedString()).append(",\r\n");
        }
        sb.append("\t").append("]").append("\r\n");
        return sb.toString();
    }
}
