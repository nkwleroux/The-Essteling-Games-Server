import java.util.HashMap;
import java.util.Objects;

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
        return this.name + id;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Player o) {
        return this.score - o.score;
    }

    @Override
    public String toString() {
        return "Username: " + getUsername() + ", score: " + score;
    }

    public String toStringSimplified(){
        return getUsername() + "," + score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id &&
                name.equals(player.name);
    }

}
