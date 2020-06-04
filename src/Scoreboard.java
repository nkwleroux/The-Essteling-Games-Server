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
//        if (highscores.size() < 10) {
            if (highscores.size() == 0) {
                addHighscore(player);
//            }else {
//                for (Player k : highscores) {
//                    if (!player.getUsername().equals(player.getUsername()))
//                        addHighscore(player);
//                }
//            }
        } else {
            for (Player k : highscores) {
                if (!player.getUsername().equals(k.getUsername())) {
                    int position = highscores.indexOf(k);
                    highscores.add(position, player);
                    highscores.remove(10);
                    return;
                } else if (player.getUsername().equals(k.getUsername()) && player.getScore() >= k.getScore()) {
                    int position = highscores.indexOf(k);
                    highscores.remove(position);
                    highscores.add(position, player);
                    return;
                }
            }
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
