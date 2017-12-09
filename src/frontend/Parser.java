package frontend;

import intermediate.ICode;
import intermediate.SymTab;
import message.Message;
import message.MessageHandler;
import message.MessageListener;
import message.MessageProducer;

/**
 * 语言无关的抽象类，子类中实现具体的语言分析
 */
public abstract class Parser implements MessageProducer {

    protected static SymTab symTab;//用于生成字符表
    protected static MessageHandler messageHandler;

    static {
        symTab = null;
        messageHandler = new MessageHandler();
    }

    protected Scanner scanner;//parser将会向它要token
    protected ICode iCode;//由该parser生成的中间码

    /**
     * 构造器
     * @param scanner
     */
    public Parser(Scanner scanner) {
        this.scanner = scanner;
        this.iCode = null;
    }

    /**
     * 语言无关的分析方法，用于将源程序处理成中间码，具体的实现由子类中的方法实现
     * @throws Exception 当有Exception出现时抛出
     */
    public abstract void parse() throws Exception;

    /**
     * 返回由parser发现的语法错误的数量，在具体的语言实现子类中去实现细节
     * @return 错误数量
     */
    public abstract int getErrorCount();

    /**
     * 代理模式 调用Scanner的方法
     * @return
     */
    public Token currentToken() {
        return scanner.currentToken();
    }

    public Token nextToken() throws Exception {
        return scanner.nextToken();
    }


    @Override
    public void addMessageListener(MessageListener listener) {
        messageHandler.addListener(listener);
    }

    @Override
    public void removeMessageListener(MessageListener listener) {
        messageHandler.removeListener(listener);
    }

    @Override
    public void sendMessage(Message message) {
        messageHandler.sendMessage(message);
    }

    /**
     * getter
     * @return
     */
    public ICode getiCode() {
        return iCode;
    }

    /**
     * getter
     * @return
     */
    public SymTab getSymTab() {
        return symTab;
    }
}
