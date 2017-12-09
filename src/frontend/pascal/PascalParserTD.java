package frontend.pascal;

import frontend.EofToken;
import frontend.Parser;
import frontend.Scanner;
import frontend.Token;
import message.Message;

import static message.MessageType.PARSER_SUMMARY;

public class PascalParserTD extends Parser {

    /**
     * 构造器
     * @param scanner
     */
    public PascalParserTD (Scanner scanner) {
        super(scanner);
    }

    /**
     * parser方法，在请求到EofToken为止重复调用父类中Token的代理方法 nextToken(),
     *  同时会发送一条PARSER_SUMMARY信息给所有订阅者，parse的持续时间时间、错误数、处理行数，
     * @throws Exception
     */
    @Override
    public void parse() throws Exception {
        Token token;
        long startTime = System.currentTimeMillis();
        while (!((token = nextToken()) instanceof EofToken)) {

        }
        long endTime = System.currentTimeMillis();
        float elapsedTime = (endTime - startTime) / 1000f;
        sendMessage(new Message(PARSER_SUMMARY
                ,new Number[] {token.getLineNum(), getErrorCount(), elapsedTime}));
    }

    @Override
    public int getErrorCount() {
        return 0;
    }
}
