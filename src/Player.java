import java.util.HashMap;

public class Player implements Comparable<Player>{

    private int id;
    private String name;
    private HashMap<String, Integer> scores;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.scores = new HashMap<>();
    }

    public String getUsername() {
        return name + " "+ id;
    }

    public int getTotalScore() {
        int sum = 0;
        for (String key : this.scores.keySet()) {
            sum += this.scores.get(key);
        }
        return sum;
    }

    public void updateScore(String assignment, int score) {
        if (this.scores.containsKey(assignment)) {
            if (score > this.scores.get(assignment)) {
                this.scores.put(assignment, score);
            }
        } else {
            this.scores.put(assignment, score);
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + getUsername() + '\'' +
                ", score=" + getTotalScore() +
                '}';
    }

    @Override
    public int compareTo(Player o) {
        return this.getTotalScore() - o.getTotalScore();
    }
}
