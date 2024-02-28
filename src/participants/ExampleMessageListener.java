package participants;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

public class ExampleMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.print("Received the following message: ");
            System.out.println(textMessage.getText());
            System.out.println();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
