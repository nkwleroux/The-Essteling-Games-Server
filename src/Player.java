import java.util.HashMap;

public class Player implements Comparable<Player>{

    private int id;
    private String name;
    private int score;

    public Player(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public String getUsername(){
        return this.name + ' ' + id;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Player o) {
        return this.score - o.score;
    }
}
