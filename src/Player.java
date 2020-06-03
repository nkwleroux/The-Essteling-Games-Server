import java.util.HashMap;

public class Player {

    private String name;
    private HashMap<String , Integer> scores;

    public Player(String name, int score) {
        this.name = name;
        this.scores = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    private int getTotalScore(){
        int sum = 0;
        for (String key : this.scores.keySet()){
            sum += this.scores.get(key);
        }
        return sum;
    }


    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + getTotalScore() +
                '}';
    }
}
