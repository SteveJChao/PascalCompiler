package test;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class Test {

    public static void main(String[] args) throws InterruptedException {
//        String[] statement = {"java","-classpath","classes","Pascal", "compile", "hello.pas"};
//        String operation = statement[0];
//
//        int i = 0;
//        String flags = "";
//
//        while ((++i < statement.length) && (statement[i].charAt(0) == '-')) {
//            flags += statement[i].substring(1);
//        }
//        System.out.println("i "+ i);
//
//        if(i < statement.length) {
//            String path = statement[i];
//            System.out.println("path " + path);
//        }
//
//        System.out.println("flags " + flags);

        for(String s : args) {
            System.out.println(s);
        }

    }

}
