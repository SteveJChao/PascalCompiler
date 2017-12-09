package frontend.pascal;

import frontend.EofToken;
import frontend.Scanner;
import frontend.Source;
import frontend.Token;

import static frontend.Source.EOF;

public class PascalScanner extends Scanner {

    /**
     * 构造器
     * @param source 该scanner用来构造token的source
     */
    public PascalScanner(Source source) {
        super(source);
    }

    /**
     * 为source构造token
     * @return 构造的token 会包括普通的token和遇到结束符时的构造的EofToken，由currentChar决定。
     * @throws Exception
     */
    @Override
    public Token extractToken() throws Exception {
        Token token;
        char currentChar = currentChar();
        if(currentChar != EOF) {
            token = new Token(source);
        } else {
            token = new EofToken(source);
        }
        return token;
    }
}
