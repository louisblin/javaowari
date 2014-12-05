public class Player {

  // Fields
  private int score;

  // Constructor
  public Player() {
    this.score = 0;
  }

  public Player(Player another) {
    this.score = another.score;
  }

  // Methods
  public void addToScore(int points) {
    score += points;
  }

  public int getScore() {
    return score;
  }

}
