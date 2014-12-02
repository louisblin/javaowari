public class Bowl {

  // Field
  private int numberOfStones;
  private int pendingDeposit;

  // Cons
  public Bowl(int stones) {
    this.numberOfStones = stones;
  }

  // Methods
  public int getStones() {
    return numberOfStones;
  }

  public int takeAllStones() {
    int currentAmount = numberOfStones;

    numberOfStones = 0;
    pendingDeposit = 0;

    return currentAmount;
  }

  public void depositStone() {
    pendingDeposit++;
  }

  public int updateAndGetScore() {
    int resultScore = 0;

    if (numberOfStones == 1 && pendingDeposit > 0) {
      resultScore = pendingDeposit + numberOfStones;
      //System.out.println("1 stone found : adding score " + resultScore);
      numberOfStones = 0;
      pendingDeposit = 0;

    } else {
      numberOfStones += pendingDeposit;
      pendingDeposit = 0;
    }

    return resultScore;
  }

}
