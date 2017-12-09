package backend.interpreter;

import backend.Backend;
import intermediate.ICode;
import intermediate.SymTab;
import message.Message;
import message.MessageType;

public class Executor extends Backend{

    /**
     * 展示解释器执行通过处理由parser生成的中间码和字母表执行源程序的进程的方法。
     * 他会发送一条INTERPRETER_SUMMARY消息，包含执行指令的条数，执行时间以及错误数
     * @param symtab parser生成的字母表
     * @param icode parser生成的中间码
     * @throws Exception
     */
    @Override
    public void progress(SymTab symtab, ICode icode) throws Exception {
        long startTime = System.currentTimeMillis();
        float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
        int executionCount = 0;
        int runtimeErrors = 0;
        sendMessage(new Message(MessageType.INTERPRETER_SUMMARY,
                new Number[] {executionCount, runtimeErrors, elapsedTime}));
    }
}
