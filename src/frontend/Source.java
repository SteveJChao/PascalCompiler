package frontend;

import java.io.BufferedReader;
import java.io.IOException;

import message.Message;
import message.MessageHandler;
import message.MessageListener;
import message.MessageProducer;

import static message.MessageType.SOURCE_LINE;

/**
 * 源程序类的框架实现
 */
public class Source implements MessageProducer {

    public static final char EOL = '\n';     //end of line
    public static final char EOF = (char) 0;  //end of file

    protected static MessageHandler messageHandler;

    private BufferedReader reader;
    private String line;//当前行的内容
    private int lineNum;//第几行
    private int currentPos;

    static {
        messageHandler = new MessageHandler();
    }

    /**
     * 构造器
     * @param reader 源程序的reader
     */
    public Source(BufferedReader reader) throws IOException {
        this.lineNum = 0;
        this.currentPos = -2; //设置为-2以便后文判断。 -2表示一行都没有读，将作为开始读的标志
        this.reader = reader;
    }

    /**
     * 在该方法中，前面四个判断排除掉可能会出现的不同情况，这些情况也是要单独处理的。我们必须佩服设计者的思路的清晰但又不失严谨和巧妙。
     * @return
     * @throws Exception
     */
    public char currentChar() throws Exception {
        //第一次读？
        if (currentPos == -2) {
            readLine();//在readLine方法中，读取一行之后会置currentPos为-1
            return nextChar();//正常情况下currentPos会在nextChar中被置为0,然后就可以正常读取了
        }

        //end of file?
        else if (line == null) {
            return EOF;
        }

        //end of line?
        else if ((currentPos == -1) || (currentPos == line.length())){
            return EOL;
        }

        //需要读取下一行？
        else if (currentPos > line.length()) {
            readLine();//currentPos被置为-1,返回换行标志，后面换行
            return nextChar();
        }

        //正常返回当前字符
        else {
            return line.charAt(currentPos);
        }
    }

    /**
     * 位置游标前进一步并返回对应的位置的字符
     * 另外，currentChat和nextChar之间存在的递归将会使读取持续
     * @return
     */
    public char nextChar() throws Exception {
        ++currentPos;//在-1的基础上加为0
        return currentChar();
    }

    /**
     * 探测下一个字符，但位置游标不增加
     * @return
     */
    public char peekChar() throws Exception {
        currentChar();
        if (line == null) {
            return EOF;
        }

        int nextPos = currentPos + 1;
        return nextPos < line.length() ? line.charAt(nextPos) : EOL;
    }

    /**
     * 读取下一行
     */
    public void readLine() throws IOException {
        line = reader.readLine(); //当到达文件末尾时 读取到null
        currentPos = -1;//为换行提供服务

        if (line != null) {
            ++lineNum;//只要成功读到一行就让行数加一
        }

        if (line != null) {
            sendMessage(new Message(SOURCE_LINE,
                    new Object[] {lineNum,line}));
        }
    }

    public void close() throws Exception{
        if (reader != null) {
            try{
                reader.close();
            }catch (IOException ioe) {
                ioe.printStackTrace();
                throw ioe;
            }
        }
    }

    public int getLineNum() {
        return lineNum;
    }

    public int getPosition() {
        return currentPos;
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
}
