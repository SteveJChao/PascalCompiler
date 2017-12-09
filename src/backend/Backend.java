package backend;

import intermediate.ICode;
import intermediate.SymTab;
import message.Message;
import message.MessageHandler;
import message.MessageListener;
import message.MessageProducer;

public abstract class Backend implements MessageProducer {

    protected static MessageHandler messageHandler;

    static {
        messageHandler = new MessageHandler();
    }

    protected SymTab symtab;
    protected ICode icode;//中间码

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

    public abstract void progress(SymTab symtab, ICode icode) throws Exception;
}
