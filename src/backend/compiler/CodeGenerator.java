package backend.compiler;

import backend.Backend;
import intermediate.ICode;
import intermediate.SymTab;
import message.Message;
import message.MessageType;

/**
 * 编译器的后端生成obj code
 */
public class CodeGenerator extends Backend {

    /**
     *
     * 后端编译器 compiler的进程方法，表示由前端生成的中间码和字母表生成机器指令的进程
     * @param symtab 前端生成的字母表
     * @param icode 前端生成的中间码
     * @throws Exception
     */
    @Override
    public void progress(SymTab symtab, ICode icode) throws Exception {
        long startTime = System.currentTimeMillis();
        float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
        int instructionCount = 0;

        //发送一条COMPILER_SUMMARY
        sendMessage(new Message(MessageType.COMPILER_SUMMARY,
                new Number[] {instructionCount, elapsedTime
        }));
    }
}
