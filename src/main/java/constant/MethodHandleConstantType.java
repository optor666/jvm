package constant;

import constant.ConstantType;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * TODO: desc
 *
 * @author optor
 * @date 2019/9/5
 */
public class MethodHandleConstantType implements ConstantType {

    private static final String tagStr = "MethodType";

    private static final byte tag = 15;

    private ConstantType[] constantPool;

    private int idx;

    /**
     * referenceKind 值必须在范围 1-9 （包括 1 和 9）之内，它表示方法句柄的类型（kind）。方法句柄类型决定句柄的字节码行为（bytecode
     *  behavior）
     */
    private byte referenceKind; // or 8(REF_newInvokeSpecial)

    /**
     * referenceIndex 值必须是对常量池表的有效索引。该位置的常量池表项必须符合下列规则：
     *  - if referenceKind is 1(REF_getField) or 2(REF_getStatic) or 3(REF_putField) or 4(REF_putStatic), then
     *      constantPool[referenceIndex] is CONSTANT_Fieldref_info.
     *  - if referenceKind is 5(REF_invokeVirtual), then constantPool[referenceIndex] is
     *      CONSTANT_Methodref_info, and constantPool[referenceIndex] isn't <init> or <clinit>.
     *  - if referenceKind is 6(REF_invokeStatic) or 7(REF_invokeSpecial), then constantPool[referenceIndex] is
     *      CONSTANT_Methodref_info or CONSTANT_InterfaceMethodref_info, and constantPool[referenceIndex]
     *       isn't <init> or <clinit>.
     *  - if referenceKind is 5(REF_invokeVirtual), then constantPool[referenceIndex] is
     *      CONSTANT_Methodref_info, and constantPool[referenceIndex] is <init> or <clinit>.
     *  - if referenceKind is 9(REF_invokeInterface), then constantPool[referenceIndex] is
     *      CONSTANT_InterfaceMethodref_info, and constantPool[referenceIndex] isn't <init>.
     *
     */
    private short referenceIndex;


    public MethodHandleConstantType(ConstantType[] constantPool, int idx, DataInputStream dis) {
        this.constantPool = constantPool;
        this.idx = idx;
        try {
            this.referenceKind = dis.readByte();
            this.referenceIndex = dis.readShort();
        } catch (IOException ignored) {
        }
    }

    public String getTxt() {
        return null;
    }

    /**
     * 获取人性化可读字符串
     *
     * @return 人性化可读字符串
     */
    @Override
    public String humanizedString() {
        return null;
    }
}
