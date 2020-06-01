public class Scoreboard {

    private String karakter;
    private int score;

    public Scoreboard(String karakter, int score) {
        this.karakter = karakter;
        this.score = score;
    }

    public String getKarakter() {
        return this.karakter;
    }

    public void setKarakter(String karakter) {
        this.karakter = karakter;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
