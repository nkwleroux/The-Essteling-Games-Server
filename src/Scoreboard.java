import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Scoreboard implements ScoreBoardCallback {

    private final List<Player> highscores;

    public Scoreboard() {
        this.highscores = new LinkedList<>();
    }

    public void updateScoreBoard(Player player) {
        if (this.highscores.contains(player)) {
            this.highscores.remove(player);
        }
        this.highscores.add(player);
        Collections.sort(this.highscores);
        for (int i = 10; i < this.highscores.size(); i++) {
            this.highscores.remove(i);
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

    @Override
    public void onNewScore(Player player) {
        updateScoreBoard(player);
        Collections.sort(highscores);
    }
}
