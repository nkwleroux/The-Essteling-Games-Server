import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class testMessageToServer {

    public static void main(String[] args) {

        final int qos = 2;
//        final String topic = "A1/TheEsstelingGames/Scoreboard";
        final String topic = "A1/TheEsstelingGames/AndroidData";

        final String userName = "androidTI";
        final char[] password = "&FN+g$$Qhm7j".toCharArray();
        final String url = "tcp://maxwell.bps-software.nl:1883";

        String clientId = "Nicholas";
        String content = "test message";
        String will = clientId + " has left";

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

            sampleClient.disconnect();
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
