package participants;

import java.util.Enumeration;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import jakarta.jms.QueueBrowser;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageQueueBrowser {

    // URL of the JMS server
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    // default broker URL is : tcp://localhost:61616"

    // Name of the queue we will receive messages from
    private static String subject = "JOGG_QUEUE";

    public void browseMessages() {
        try {

            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false /*Transacter*/, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(subject);

            QueueBrowser browser = session.createBrowser((Queue) destination);
            Enumeration messageEnumeration = browser.getEnumeration();
            if (messageEnumeration != null) {
                if (!messageEnumeration.hasMoreElements()) {
                    System.out.println("There are no messages " + "in the queue.");
                } else {
                    System.out.println("The following messages are in the queue:");
                    while (messageEnumeration.hasMoreElements()) {
                        TextMessage textMessage = (TextMessage) messageEnumeration.nextElement();
                        System.out.println(textMessage.getText());
                    }
                }
            }
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MessageQueueBrowser().browseMessages();
    }
}
