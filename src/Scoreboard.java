import java.util.LinkedList;
import java.util.List;

public class Scoreboard {

    private List<Karakter> highscores;

    public Scoreboard() {
        this.highscores = new LinkedList<>();
    }

    public void addScore(Karakter karakter){
        for (Karakter k : highscores){
            if(karakter.getScore() > k.getScore()){
                int position = highscores.indexOf(k);
                highscores.add(position, karakter);
                highscores.remove(10);
                return;
            }
        }
    }
}
