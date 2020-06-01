import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class main {

    public static void main(String[] args) {

        User user = new User("Nicholas");
        user.setContent("Message from test");
        final int qos = 2;
        final String topic = "TheEsstelingGames/JavaServerTest";

        String clientId = user.getClientId();
        String content = user.getContent();
        String will = user.getWill();

        String userName = user.getUserName();
        char[] password = user.getPassword();
        String url = user.getUrl();
        //Might not be needed everywhere. Only at the start of the connection.
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(url, clientId, persistence);

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(userName);
            connOpts.setPassword(password);
            connOpts.setWill(topic,will.getBytes(),2,false);

            System.out.println("Connecting to broker: " + url);
            sampleClient.connect(connOpts);

            System.out.println("Connected");
            System.out.println("Publishing message: " + content);
            MqttMessage messageMQTT = new MqttMessage(content.getBytes());
            messageMQTT.setQos(qos);
            sampleClient.publish(topic, messageMQTT);
            System.out.println("Message published");

//            sampleClient.disconnect();
//            System.out.println("Disconnected");
//            System.exit(0);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
}
