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
public class Utf8ConstantType implements ConstantType {

    private static final String tagStr = "Utf8";

    private static final byte tag = 1;

    private ConstantType[] constantPool;

    private int idx;

    private short length;

    private byte[] bytes;

    private String txt;

    public Utf8ConstantType(ConstantType[] constantPool, int idx, DataInputStream dis) {
        this.constantPool = constantPool;
        this.idx = idx;

        short length = 0;
        try {
            length = dis.readShort();
        } catch (IOException ignored) {
        }
        byte[] bytes = new byte[length];
        try {
            dis.readFully(bytes);
        } catch (IOException ignored) {
        }

        this.length = length;
        this.bytes = bytes;
    }

    public String getTxt() {
        if (this.txt == null) {
            // 将字节数组转换为字符串
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < bytes.length; j++) {
                if (bytes[j] > 0) {
                    // 单字节 ASCII 字符
                    sb.append((char) bytes[j]);
                } else{
                    byte k = (byte) (-128 - bytes[j]);
                    if (k <= -64 && k > -96) {
                        // 双字节
                        byte x = bytes[j];
                        byte y = bytes[++j];
                        sb.append((char) (((x & 0x1f) << 6) + (y & 0x3f)));
                    } else if (k <= -96 && k > -109) {
                        // 三字节
                        byte x = bytes[j];
                        byte y = bytes[++j];
                        byte z = bytes[++j];
                        sb.append((char) (((x & 0xf) << 12) + ((y & 0x3f ) << 6) + (z & 0x3f)));
                    } else if (k == -109) {
                        // 六字节
                        byte u = bytes[j];
                        byte v = bytes[++j];
                        byte w = bytes[++j];
                        byte x = bytes[++j];
                        byte y = bytes[++j];
                        byte z = bytes[++j];
                        sb.append((char) (0x10000 + ((v & 0x0f) << 16) + ((w & 0x3f) << 10) + ((y & 0x0f) << 6) + (z & 0x3f)));
                    }
                }
            }
            this.txt = sb.toString();
        }
        return this.txt;
    }

    /**
     * 获取人性化可读字符串
     *
     * @return 人性化可读字符串
     */
    @Override
    public String humanizedString() {
        return String.format("#%d = %s\t%s", this.idx, tagStr, this.getTxt());
    }
}
