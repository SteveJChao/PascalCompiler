package frontend;

import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalScanner;

public class FrontendFactory {

    /**
     * 前端的Parser工厂创建方法
     * @param language 表示语言种类 比如我们将要做的pascal，当然我们也可以扩展到其他具体语言
     * @param type 表示parser的类型，比如top-down(自上而下）
     * @param source 由scanner处理的source
     * @return
     */
    public static Parser createParser(String language, String type, Source source) throws Exception {
        if (language.equalsIgnoreCase("Pascal") && type.equalsIgnoreCase("top-down")) {
            Scanner scanner = new PascalScanner(source);
            return new PascalParserTD(scanner);
        } else if (!language.equalsIgnoreCase("Pascal")) {
            throw new Exception("Parser factory: Invalid language" + language);
        } else {
            throw new Exception("Parser factory: Invalid parser type '" + type + "'");
        }
    }
}
