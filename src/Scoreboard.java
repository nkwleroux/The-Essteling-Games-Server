import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Scoreboard {

    private final List<Player> highScores;

    public Scoreboard() {
        this.highScores = new LinkedList<>();
    }

    /**
     * Method to update the scoreboard. Adds players to highscores list if the player score is larger than a player
     * on the list.
     *
     * @param player The player object that will be added to the list.
     */
    public void updateScoreBoard(Player player) {
        if (this.highScores.contains(player)) {
            Player oldPlayer = this.highScores.get(this.highScores.indexOf(player));
            if (player.getScore() > oldPlayer.getScore()) {
                this.highScores.remove(player);
            } else {
                return;
            }
        }
        this.highScores.add(player);
        Collections.sort(this.highScores);
        for (int i = 10; i < this.highScores.size(); i++) {
            this.highScores.remove(i);
        }
    }

    /**
     * Getter for the HighScore list.
     *
     * @return A list with a size of 10 players.
     */
    public List<Player> getHighScores() {
        return highScores;
    }

    /**
     * Getter for an individual player from the HighScore list.
     *
     * @param position The position of the player in the list.
     * @return The player which is in the position which was searched for.
     */
    public Player getHighScore(int position) {
        return highScores.get(position);
    }

    /**
     * Method that adds new player by calling updateScoreBoard. Then sorts it on basis from high to low score.
     *
     * @param player The player object to be added to the HighScore list.
     */
    public void onNewScore(Player player) {
        updateScoreBoard(player);
        Collections.sort(highScores, Collections.reverseOrder());
    }
}
