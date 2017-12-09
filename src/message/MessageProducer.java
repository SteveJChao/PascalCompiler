package message;

public interface MessageProducer {
    /**
     * 向listener list中添加一个listener
     * @param listener
     */
    public void addMessageListener(MessageListener listener);

    /**
     * 从listener list中移除一个listener
     * @param listener
     */
    public void removeMessageListener(MessageListener listener);

    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(Message message);
}
