public class Player implements Comparable<Player> {

    private final int id;
    private final String name;
    private final int score;

    public Player(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    /**
     * Getter for the username which includes the name and id.
     *
     * @return The name + id.
     */
    public String getUsername() {
        return this.name + id;
    }

    /**
     * Getter for the player ID.
     *
     * @return The variable id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter for the player score.
     *
     * @return The variable score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Comparator to check if the score is bigger, smaller or equal to the player score.
     *
     * @param o Player object which will be used to compare the scores with.
     * @return An int between -1 and 1.
     */
    @Override
    public int compareTo(Player o) {
        return this.score - o.score;
    }

    /**
     * ToString method which is overwritten with the username and score.
     *
     * @return A string with the username and score.
     */
    @Override
    public String toString() {
        return "Username: " + getUsername() + ", score: " + score;
    }

    /**
     * ToString method used to send messages to the server.
     *
     * @return A string with the username and score.
     */
    public String toStringSimplified() {
        return getUsername() + "," + score;
    }

    /**
     * Equals method to check if the player id equals the object player id.
     *
     * @param o Object which will be used to compare to the player id.
     * @return True if player id = object id.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id;
    }

}
