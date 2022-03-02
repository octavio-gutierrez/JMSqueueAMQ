package mx.itam.packages.jmsqueueamq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageSender {

    // A static manner to link jms resources with java objects
    //@Resource(mappedName = "jms/TestConnectionFactory")  
    //private static ConnectionFactory connectionFactory;  
    //@Resource(mappedName = "jms/TestQueue")  
    //private static Queue queue;  
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
    private static String subject = "JOGG_QUEUE"; // Queue Name. You can create any/many queue names as per your requirement. 

    public void produceMessages() {
        MessageProducer messageProducer;
        TextMessage textMessage;
        try {

            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false /*Transacter*/, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subject);

            messageProducer = session.createProducer(destination);
            textMessage = session.createTextMessage();

            textMessage.setText("Testing, 1, 2, 3. Can you hear me?");
            System.out.println("Sending the following message: " + textMessage.getText());
            messageProducer.send(textMessage);

            textMessage.setText("Do you copy?");
            System.out.println("Sending the following message: " + textMessage.getText());
            messageProducer.send(textMessage);

            textMessage.setText("Good bye!");
            System.out.println("Sending the following message: " + textMessage.getText());
            messageProducer.send(textMessage);

            messageProducer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MessageSender().produceMessages();
    }
}
