package frontend;

/**
 * Scanner扫描返回的Token单元
 */
public class Token {

    protected TokenType type;
    protected String text;//token文本
    protected Object value;//token值，有些可能直接算出结果
    protected Source source;
    protected int lineNum;//由于在source中，lineNub也是随着读取的进行而增加，所以这里得到的既是当前的行的位置为第几行也是总共目前有多少行
    protected int position;

    /**
     * 构造器
     * @param source 用于构造token串的Source
     */
    public Token(Source source) throws Exception {
        this.source = source;
        this.lineNum = source.getLineNum();
        this.position = source.getPosition();

        extract();
    }

    /**
     * 虽然在这个框架方法中每次为一个char来构造token是不现实的，但是却很好的展示了每次构造一个token之后position会后移的点，具体的实现将交由子类来实现
     * @throws Exception
     */
    public void extract() throws Exception {
        text = Character.toString(currentChar());
        value = null;

        nextChar();//通过nextChar()的吞噬来实现position向后递增移东
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public char currentChar() throws Exception {
        return source.currentChar();
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public char nextChar() throws Exception {
        return source.nextChar();
    }

    public char peekChar() throws Exception {
        return source.peekChar();
    }

    /**
     * 返回source当前处理的行数
     * @return
     */
    public int getLineNum() {
        return lineNum;
    }

    /**
     * 返回source的currentPos
     * @return
     */
    public int getPosition() {
        return position;
    }

}
