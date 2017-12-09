package frontend;

public class EofToken extends Token {

    /**
     * 构造器
     * @param source 处理的source文件
     * @throws Exception
     */
    public EofToken(Source source) throws Exception {
        super(source);
    }

    /**
     * 由于它不涉及对字符的处理。所以他什么也不用做
     * @throws Exception
     */
    public void extract() throws Exception {
    }
}
