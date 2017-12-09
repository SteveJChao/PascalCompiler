package backend;

import backend.compiler.CodeGenerator;
import backend.interpreter.Executor;

/**
 * 用来生成compiler后端或interpreter后端的后端工厂类
 */
public class BackendFactory {

    /**
     * 能根据操作的不同生成不同的后端的工厂方法。
     * @param operation 操作种类
     * @return 对应的后端
     * @throws Exception 抛出的异常
     */
    public static Backend createBackend(String operation) throws Exception{
        if (operation.equalsIgnoreCase("compile")) {
            return new CodeGenerator();
        }
        else if (operation.equalsIgnoreCase("execute")) {
            return new Executor();
        }
        else {
            throw new Exception("Backend Factory: Invalid operation '" + operation + "'");
        }
    }
}
