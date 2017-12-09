package message;

import java.util.ArrayList;

public class MessageHandler {
    private Message message;
    private ArrayList<MessageListener> listeners;

    public MessageHandler() {
        this.listeners = new ArrayList<MessageListener>();
    }

    /**
     * parser和source的代理方法
     * @param listener
     */
    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }

    /**
     * 代理方法
     * @param listener
     */
    public void removeListener(MessageListener listener) {
        listeners.remove(listener);
    }

    /**
     * 代理方法
     * @param message
     */
    public void sendMessage(Message message) {
        this.message = message;
        notifyListeners();
    }

    /**
     * 私有方法，用来通知所有订阅者
     */
    private void notifyListeners() {
        for (MessageListener listener : listeners) {
            listener.messageReceived(message);
        }
    }
}
