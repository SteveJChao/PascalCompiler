package message;

public interface MessageListener {

    /**
     * 当MessageProducer 生产一条message时会被调用以接受message
     * @param message
     */
    public void messageReceived(Message message);
}
