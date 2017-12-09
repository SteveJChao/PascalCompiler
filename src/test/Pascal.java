package test;

import backend.Backend;
import backend.BackendFactory;
import frontend.FrontendFactory;
import frontend.Parser;
import frontend.Source;
import intermediate.ICode;
import intermediate.SymTab;
import message.Message;
import message.MessageListener;
import message.MessageType;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * 通过这个类我们可以验证目前的框架代码是否可以正确编译或执行pascal程序
 */
public class Pascal {

    private Source source;//语言无关的scanner
    private Parser parser;//语言无关的parser
    private Backend backend;//后端

    private ICode icode;
    private SymTab symTab;
    /**
     * 构造器 在其中编译或解释源程序
     * @param operation 知名操作类型 compile或execute
     * @param filePath pascal原文件的路径
     * @param flags 命令行参数标记
     */
    public Pascal(String operation, String filePath, String flags) {

        try {
            //显示中间码结构
            boolean intermediate = flags.indexOf('i') > -1;
            //线是否好引用
            boolean xref = flags.indexOf('x') > -1;

            source = new Source(new BufferedReader(new FileReader(filePath)));
            source.addMessageListener(new SourceMessageListener());

            parser = FrontendFactory.createParser("Pascal", "top-down", source);
            parser.addMessageListener(new ParserMessageListener());

            backend = BackendFactory.createBackend(operation);
            backend.addMessageListener(new BackendMessageListener());

            parser.parse();
            source.close();

            icode = parser.getiCode();
            symTab = parser.getSymTab();

            backend.progress(symTab, icode);

        }
        catch (Exception e) {
            System.out.println("----------编译错误----------");
            e.printStackTrace();
        }
    }

    private static final String FLAGS = "[-ix]";
    private static final String USAGE =
            "Usage: Pascal execute|compile " + FLAGS + " <source file path>";
    public static void main(String args[])
    {
        try {
            String operation = args[0];

            // Operation.
            if (!(   operation.equalsIgnoreCase("compile")
                  || operation.equalsIgnoreCase("execute"))) {
                throw new Exception();
            }

            int i = 0;
            String flags = "";

            // Flags.
            while ((++i < args.length) && (args[i].charAt(0) == '-')) {
                flags += args[i].substring(1);
            }

            // Source path.
            if (i < args.length) {
                String path = args[i];
                new Pascal(operation, path, flags);
            }
            else {
                throw new Exception();
            }
        }
        catch (Exception ex) {
            System.out.println(USAGE);
        }
    }

    private static final String SOURCE_LINE_FORMAT = "%03d %s";//打印格式 如001行 ...
    /**
     * Source发送的message的订阅者
     */
    private class SourceMessageListener implements MessageListener{

        @Override
        public void messageReceived(Message message) {
            MessageType type = message.getType();
            Object body[] = (Object[]) message.getBody();

            switch (type) {
                case SOURCE_LINE:
                    int lineNum = (Integer) body[0];
                    String lineText = (String) body[1];
                    System.out.println(String.format(SOURCE_LINE_FORMAT, lineNum, lineText));
                    break;
            }
        }
    }

    //parser发送的message的打印格式
    private static final String PARSER_SUMMARY_FORMAT =
            "\n%,20d source lines."
            +"\n%,20d syntax errors."
            +"\n%,20.2f seconds total parsing time\n";

    /**
     * Parser发送的message的订阅者类
     */
    private class ParserMessageListener implements MessageListener {

        /**
         * 消息订阅者对消息的处理，每当Parser发送一条message都会被调用
         * @param message
         */
        @Override
        public void messageReceived(Message message) {
            MessageType type = message.getType();

            switch (type) {
                case PARSER_SUMMARY:
                    Object body[] = (Object[]) message.getBody();
                    int lineNum = (Integer) body[0];
                    int errorCount = (Integer) body[1];
                    float parsingTime = (Float) body[2];

                    System.out.println("\n--------------------SOURCE CODE PARSING INFO--------------------\n");
                    System.out.println(String.format(PARSER_SUMMARY_FORMAT, lineNum, errorCount, parsingTime));
                    break;
            }

        }
    }

    //打印格式
    private static final String COMPILER_SUMMARY_FORMAT =
            "\n%,20d instruction generated."
            +"\n%,20.2f seconds total code generation time.\n";

    private static final String INTERPRETER_SUMMARY_FORMAT =
            "\n%,20d statements executed."
            +"\n%,20d runtimeErrors."
            +"\n%,20.2f seconds total code execution time.\n";

    /**
     * 后端的订阅者类
     */
    private class BackendMessageListener implements MessageListener {

        /**
         * 在后端发送消息时被调用
         * @param message
         */
        @Override
        public void messageReceived(Message message) {
            MessageType type = message.getType();

            switch (type) {
                case COMPILER_SUMMARY:{
                    Object body[] = (Object[]) message.getBody();
                    int instructionCount = (Integer) body[0];
                    float elapsedTime = (Float)body[1];

                    System.out.println("\n----------------------GENERATOR INFO----------------------");
                    System.out.println(String.format(COMPILER_SUMMARY_FORMAT,instructionCount, elapsedTime));
                    break;
                }
                case INTERPRETER_SUMMARY:{
                    Object body[] = (Object[]) message.getBody();
                    int executionCount = (Integer) body[0];
                    int runtimeErrors = (Integer) body[1];
                    float elapsedTime = (Float) body[2];

                    System.out.println("\n----------------------EXECUTOR INFO----------------------");
                    System.out.println(String.format(INTERPRETER_SUMMARY_FORMAT, executionCount, runtimeErrors, elapsedTime));
                    break;
                }
            }
        }
    }
}
