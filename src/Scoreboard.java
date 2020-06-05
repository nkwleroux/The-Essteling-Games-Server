import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Scoreboard implements ScoreBoardCallback{

    private final List<Player> highscores;

    public Scoreboard() {
        this.highscores = new LinkedList<>();
        initScoreBoard();
    }

    public void updateScoreBoard(Player player) {
        this.highscores.add(player);
        Collections.sort(this.highscores);
        for (int i = 10; i < this.highscores.size(); i++) {
            this.highscores.remove(i);
        }
    }

    public void initScoreBoard() {
        for (int i = 10; i > 0; i--) {
            addHighscore(new Player(i, "player", 0));
        }
    }

    public void addHighscore(Player player) {
        this.highscores.add(player);
    }

    public List<Player> getHighscores() {
        return highscores;
    }

    public Player getHighscore(int id) {
        return highscores.get(id);
    }

    public static void main(String[] args) {
        Scoreboard s = new Scoreboard();
        Random random = new Random();


        System.out.println(s.getHighscores().toString());
    }

    @Override
    public void onNewScore(Player player) {
        updateScoreBoard(player);
    }
}
