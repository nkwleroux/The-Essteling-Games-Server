import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Scoreboard  {

    private final List<Player> highscores;

    public Scoreboard() {
        this.highscores = new LinkedList<>();
    }

    public void updateScoreBoard(Player player) {
        if (this.highscores.contains(player)) {
            Player oldPlayer = this.highscores.get(this.highscores.indexOf(player));
            if (player.getScore() > oldPlayer.getScore()) {
                this.highscores.remove(player);
            }else {
                return;
            }
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

    public void onNewScore(Player player) {
        updateScoreBoard(player);
        Collections.sort(highscores,Collections.reverseOrder());
    }
}
