public class Player {

  // Fields
  private int score;

  // Constructor
  public Player() {
    this.score = 0;
  }

  // Methods
  public void addToScore(int points) {
    score += points;
  }

  public int getScore() {
    return score;
  }

}
