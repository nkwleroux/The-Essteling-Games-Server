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
            if (player.getScore() > k.getScore()) {
                int position = highscores.indexOf(k);
                highscores.add(position, player);
                highscores.remove(10);
                return;
            }
        }
    }

    public void initScoreBoard() {
        for (int i = 10; i > 0; i--) {
            addHighscore(new Player("player " + i, 0));
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

        for (int i = 5; i > 0; i--) {
            s.updateScoreBoard(new Player("test " + i, Math.abs(random.nextInt(500)) * 10));
        }
        System.out.println(s.getHighscores().toString());
    }
}
