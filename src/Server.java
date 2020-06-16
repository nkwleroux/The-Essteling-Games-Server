import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.StringReader;
import java.util.HashMap;

public class Server {

    private final String url = "tcp://maxwell.bps-software.nl:1883";
    private final String userName = "androidTI";
    private final char[] password = "&FN+g$$Qhm7j".toCharArray();

    private final String topic = "A1/TheEsstelingGames/AndroidData";
    private final String publishTopic = "A1/TheEsstelingGames/Scoreboard";
    private final String clientId = "Server";
    private final String will = clientId + " has disconnected";
    private final int qos = 2;
    private final MemoryPersistence memoryPersistence = new MemoryPersistence();

    private final HashMap<Integer, Player> playerHashMap;

    private final Scoreboard scoreboard;

    public static void main(String[] args) {
        Scoreboard scoreboard = new Scoreboard();

        new Server(scoreboard).start();
    }

    public Server(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        playerHashMap = new HashMap<>();
    }

    /**
     * Method which is used to start the server. Listens and sends messages to and from the MQTT server topics.
     */
    public void start() {

        //sends message to server and sets
        try {
            MqttClient client = new MqttClient(url, clientId, memoryPersistence);

            if (!client.isConnected()) {
                if (connectWithOptions(client)) {
                    //Used to subscribe to the MQTT server.
                    //Method message arrived is the message that arrives at the server.
                    client.setCallback(new MqttCallback() {
                        @Override
                        public void connectionLost(Throwable throwable) {
                            //Prints message in console if connection is lost.
                            System.out.println("Server connection lost");
                        }

                        @Override
                        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                            //Reads messages from mqtt server subscribed topic.
                            System.out.println("Message from server: " + mqttMessage);
                            String message = mqttMessage.toString();
                            if (message.equals("close server")) {
                                System.out.println("Received message to close server");
                                client.disconnect();
                                client.close();
                                System.exit(0);
                            } else if (message.equals("get Scoreboard")) {
                                System.out.println("Received message to retrieve Scoreboard");
                                client.publish(publishTopic, messageToServer("Clear scoreboard"));
                                for (int i = 0; i < scoreboard.getHighScores().size(); i++) {
                                    client.publish(publishTopic, messageToServer(scoreboard.getHighScore(i).toStringSimplified()));
                                }
                                client.publish(publishTopic, messageToServer("Close listener"));
                            } else {
                                //Gets player from app.
                                try {
                                    JSONParser jsonParser = new JSONParser();
                                    JSONObject jsonObject = (JSONObject) jsonParser.parse(new StringReader(mqttMessage.toString()));
                                    int id = (int) (long) jsonObject.get("id");
                                    String character = (String) jsonObject.get("character");
                                    int score = (int) (long) jsonObject.get("score");

                                    System.out.println("received new player id:" + id + " name:" + character + " score:" + score);

                                    Player newPlayer = new Player(id, character, score);
                                    if (!playerHashMap.containsKey(id) || playerHashMap.get(id).getScore() <= newPlayer.getScore()) {
                                        scoreboard.onNewScore(newPlayer);
                                        playerHashMap.put(newPlayer.getId(), newPlayer);
                                    } else {
                                        for (Player player : scoreboard.getHighScores()) {
                                            if (player.equals(playerHashMap.get(id))) {
                                                scoreboard.updateScoreBoard(newPlayer);
                                            }
                                        }
                                    }
                                    System.out.println(scoreboard.getHighScores());
                                    System.out.println(playerHashMap);

                                } catch (ClassCastException e) {
                                    System.out.println("received improper json\n" + mqttMessage.toString());
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                        }
                    });
                    client.subscribe(topic);
                    client.publish(topic, messageToServer("Server connected"));
                }
            }
        } catch (
                MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to check if the user can and is connected to the MQTT server. Sets the options of the connection.
     * @param client The client which the options will be assigned to and used to connect to the MQTT server.
     * @return True if the client is connected with the server.
     */
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

    /**
     * Method which is used to send messages to the servers.
     * @param content The message which is to be sent to the MQTT server.
     * @return A MQTT message object with the content.
     */
    public MqttMessage messageToServer(String content) {
        MqttMessage message = new MqttMessage();
        message.setPayload(content.getBytes());
        message.setQos(qos);
        return message;
    }

}
