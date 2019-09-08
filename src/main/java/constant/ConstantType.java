package constant;

/**
 * 常量池数组元素基类
 *
 * @author optor
 * @date 2019/9/5
 */
public interface ConstantType {

    String getTxt();
    /**
     * 获取人性化可读字符串
     *
     * @return 人性化可读字符串
     */
    String humanizedString();
}
