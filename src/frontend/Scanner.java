package frontend;

/**
 * 语言无关的抽象类Scanner，在子类中实现具体语言的Scanner逻辑
 */
public abstract class Scanner {

    protected Source source;
    private Token currentToken;

    /**
     * 构造器
     * @param source
     */
    public Scanner(Source source) {
        this.source = source;
    }

    /**
     * 返回currentToken
     * @return
     */
    public Token currentToken() {
        return currentToken;
    }

    /**
     * 从原程序返回next Token
     * @return
     * @throws Exception
     */
    public Token nextToken() throws Exception {
        currentToken = extractToken();
        return currentToken;
    }

    /**
     *
     * 由于各语言的Token构造方式不同，所以实际的工作由子类去实现
     * @return
     * @throws Exception
     */
    public abstract Token extractToken() throws Exception;

    /**
     * source的一个快捷方法，可让子类不依赖于source
     * @return
     * @throws Exception
     */
    public char currentChar() throws Exception {
        return source.currentChar();
    }

    /**
     * source的快捷方法
     * @return
     * @throws Exception
     */
    public char nextChar() throws Exception {
        return source.nextChar();
    }
}
