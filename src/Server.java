import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;
import java.util.Arrays;

public class Server implements MqttCallback {

    final int qos = 2;
    final String topic = "TheEsstelingGames/JavaServerTest";

    User user;

    String clientId;
    String content;
    ArrayList<String> testList;
    String will;

    String userName;
    char[] password;
    String url;

    MemoryPersistence persistence = new MemoryPersistence();

    public static void main(String[] args) {
        new Server().demo();
    }

    public Server() {
       testList = testData();

        user = new User("NewN");
        user.setContents(testList);
        clientId = user.getClientId();
        content = user.getContent();
        will = user.getWill();

        userName = user.getUserName();
        password = user.getPassword();
        url = user.getUrl();
    }

    public ArrayList<String> testData(){
        ArrayList<String> testList = new ArrayList<>();

        testList.add("Nic");
        testList.add("Hello world");
        testList.add("mike");
        testList.add("Quit");
        return testList;
    }

    //sends message to server and sets
    public void demo() {
        try {
            MqttClient client = new MqttClient(url, clientId, persistence);

            if (!client.isConnected()) {
                if (connect(client)) {
                    //used to subscribe to the MQTT server.
                    //Method message arrived is the message that arrives at the server.
                    client.setCallback(this);
                    client.subscribe(topic);

//                    MqttMessage message = messageToServer(content);
                    //Sends an array of strings to the MQTT server
                    MqttMessage message = arrayToServer(testList);
                    client.publish(topic, message);
                }
            }
        } catch (
                MqttException e) {
            e.printStackTrace();
        }

    }

    public boolean connect(MqttClient client) {
        try {
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(userName);
            connOpts.setPassword(password);
            connOpts.setWill(topic, will.getBytes(), qos, false);
            client.connect(connOpts);
            System.out.println("Client connected");
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            System.out.println("Client connection failed");
            return false;
        }
    }

    public MqttMessage messageToServer(String content) {
        MqttMessage message = new MqttMessage();
        message.setPayload(content.getBytes());
        message.setQos(qos);
        return message;
    }

    public MqttMessage arrayToServer(ArrayList<String> contentArray) {
        MqttMessage message = new MqttMessage();
        StringBuilder arrayMessage = new StringBuilder("{messages: ");
        for (String s : contentArray) {
            if (contentArray.indexOf(s) == 0) {
                arrayMessage.append("{ ").append(s).append(", ");
            } else if (contentArray.indexOf(s) == contentArray.size() - 1) {
                arrayMessage.append(s).append(" } }");
            } else arrayMessage.append(s).append(", ");
        }
        message.setPayload(arrayMessage.toString().getBytes());
        message.setQos(qos);
        return message;
    }


    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection lost");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("Message from server: " + mqttMessage);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("Sent from server to MQTT: ");
    }
}
