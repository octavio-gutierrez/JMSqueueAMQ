package mx.itam.packages.jmsqueueamq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageReceiver {

    // URL of the JMS server
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    // default broker URL is : tcp://localhost:61616"

    // Name of the queue we will receive messages from
    private static String subject = "JOGG_QUEUE";

    public void getMessages() {

        boolean goodByeReceived = false;

        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false /*Transacter*/, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(subject);

            MessageConsumer messageConsumer = session.createConsumer(destination);

            while (!goodByeReceived) {
                System.out.println("Waiting for messages...");
                TextMessage textMessage = (TextMessage) messageConsumer.receive();
                if (textMessage != null) {
                    System.out.print("Received the following message: ");
                    System.out.println(textMessage.getText());
                    System.out.println();
                }
                if (textMessage.getText() != null && textMessage.getText().equals("Good bye!")) {
                    goodByeReceived = true;
                }
            }

            messageConsumer.close();
            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MessageReceiver().getMessages();
    }

}
