import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Scoreboard {

    private final List<Player> highscores;

    public Scoreboard() {
        this.highscores = new LinkedList<>();
        initScoreBoard();
    }

    public void updateScoreBoard(Player player) {
        for (Player k : highscores) {
            if (player.getTotalScore() > k.getTotalScore()) {
                int position = highscores.indexOf(k);
                highscores.add(position, player);
                highscores.remove(10);
                return;
            }
        }
    }

    public void initScoreBoard() {
        for (int i = 10; i > 0; i--) {
            addHighscore(new Player(i, "player"));
        }
    }

    public void addHighscore(Player player) {
        this.highscores.add(player);
    }

    public List<Player> getHighscores() {
        return highscores;
    }

    public static void main(String[] args) {
        Scoreboard s = new Scoreboard();
        Random random = new Random();


        System.out.println(s.getHighscores().toString());
    }
}
