import java.util.ArrayList;

public class User {

    private final String userName = "androidTI";
    private final char[] password = "&FN+g$$Qhm7j".toCharArray();
    private final String url = "tcp://maxwell.bps-software.nl:1883";

    private String will;
    private String clientId;
    private String content;
    private ArrayList<String> contents;

    public User(String clientId) {
        this.clientId = clientId;
        will = getClientId() + " has left";
    }

    public ArrayList<String> getContents() {
        return contents;
    }

    public void setContents(ArrayList<String> contents) {
        this.contents = contents;
    }

    public String getWill() {
        return will;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public char[] getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }
}
