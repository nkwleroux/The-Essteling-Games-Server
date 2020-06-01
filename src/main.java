import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class main {

    public static void main(String[] args) {

        String topic = "TheEsstelingGames/JavaServerTest";
        String content = "Message from Nicholas";
        int qos = 2;
        String broker = "tcp://maxwell.bps-software.nl:1883";
        String clientId = "The Essteling games Nicholas";
        String will = "Nicholas has left";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("androidTI");
            connOpts.setPassword("&FN+g$$Qhm7j".toCharArray());
            connOpts.setWill(topic,will.getBytes(),2,false);

            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);

            System.out.println("Connected");
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");

            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
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
