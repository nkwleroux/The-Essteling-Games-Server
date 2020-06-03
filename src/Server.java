import jdk.nashorn.internal.parser.JSONParser;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.ArrayList;

public class Server implements MqttCallback{

    private final String url = "tcp://maxwell.bps-software.nl:1883";
    private final String userName = "androidTI";
    private final char[] password = "&FN+g$$Qhm7j".toCharArray();

    private final String topic = "A1/TheEsstelingGames/Scoreboard";
    private final String clientId = "Server";
    private final String will = clientId + " has disconnected";
    private final int qos = 2;
    MemoryPersistence memoryPersistence = new MemoryPersistence();

    ScoreBoardCallback scoreBoardCallback;

    public static void main(String[] args) {
        Scoreboard scoreboard = new Scoreboard();
        new Server(scoreboard).start();
    }

    public Server(ScoreBoardCallback scoreBoardCallback) {

        this.scoreBoardCallback = scoreBoardCallback;
    }

    public void start() {

        //sends message to server and sets
        try {
            MqttClient client = new MqttClient(url, clientId, memoryPersistence);

            if (!client.isConnected()) {
                if (connectWithOptions(client)) {
                    //used to subscribe to the MQTT server.
                    //Method message arrived is the message that arrives at the server.
                    client.setCallback(new MqttCallback() {
                        @Override
                        public void connectionLost(Throwable throwable) {
                            System.out.println("Server connection lost");
                        }

                        @Override
                        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                            System.out.println("Message from server: " + mqttMessage);
                            if (mqttMessage.toString().equals("close server")) {
                                client.disconnect();
                                client.close();
                                System.exit(0);
                            } else {
                                try (JsonReader jsonReader = Json.createReader(new StringReader(new String(mqttMessage.getPayload())));) {

                                    JsonObject jsonObject = jsonReader.readObject();

                                    int id = jsonObject.getInt("id");
                                    String character = jsonObject.getString("character");
                                    int score = jsonObject.getInt("score");

                                    scoreBoardCallback.onNewScore(new Player(id, character, score));

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }


                        @Override
                        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                        }
                    });
                    client.subscribe(topic);

                    //sends a single string to the server.
//                    MqttMessage message = messageToServer(content);
                    //Sends an array of strings to the MQTT server
//                    MqttMessage message = arrayToServer(testList);
                    client.publish(topic, messageToServer("Server connected"));
                }
            }
        } catch (
                MqttException e) {
            e.printStackTrace();
        }
    }

    public boolean connectWithOptions(MqttClient client) {
        try {
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(userName);
            connOpts.setPassword(password);
            connOpts.setWill(topic, will.getBytes(), qos, false);
            client.connect(connOpts);
            System.out.println("Server connected");
            return true;
        } catch (MqttException e) {
            System.out.println("Server connection failed");
            e.printStackTrace();
            return false;
        }
    }

    public MqttMessage messageToServer(String content) {
        MqttMessage message = new MqttMessage();
        message.setPayload(content.getBytes());
        message.setQos(qos);
        return message;
    }

    //    private String content;
    //    private ArrayList<String> testList;

//    public MqttMessage arrayToServer(ArrayList<String> contentArray) {
//        MqttMessage message = new MqttMessage();
//        StringBuilder arrayMessage = new StringBuilder("{messages: ");
//        for (String s : contentArray) {
//            if (contentArray.indexOf(s) == 0) {
//                arrayMessage.append("{ ").append(s).append(", ");
//            } else if (contentArray.indexOf(s) == contentArray.size() - 1) {
//                arrayMessage.append(s).append(" } }");
//            } else arrayMessage.append(s).append(", ");
//        }
//        message.setPayload(arrayMessage.toString().getBytes());
//        message.setQos(qos);
//        return message;
//    }

    //    public ArrayList<String> testData(){
//        ArrayList<String> testList = new ArrayList<>();
//
//        testList.add("Nic");
//        testList.add("Hello world");
//        testList.add("mike");
//        testList.add("Quit");
//        return testList;
//    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("connection lost");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("Message from server: " + mqttMessage);
        try (JsonReader jsonReader = Json.createReader(new StringReader(new String(mqttMessage.getPayload())))) {
            JsonObject jsonObject = jsonReader.readObject();

            int id = jsonObject.getInt("id");
            String character = jsonObject.getString("character");
            int score = jsonObject.getInt("score");

            System.out.println("received player id:" + id + " name :" + character + " score:" + score);

            this.scoreBoardCallback.onNewScore(new Player(id, character, score));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("Sent from server to MQTT: ");
    }
}
