//import /../Bowl.class
//import /../Game.class

public class ParallelOwariExec {

  // Fields
  private Bowl[] parBoard;
  private int idAI;

  // Cons
  public ParallelOwariExec(int idAI) {
    
    this.parBoard = new Bowl[12];
    for(int i = 0; i < 12; i++) { // 12 bowls
      this.parBoard[i] = new Bowl(4);
    }

    this.idAI = idAI;
  }

  private void updateBoard(int[] data) {
    for(int i = 0; i < 12; i++) {
      parBoard[i].setStones(data[i]);
    }
  }

  public int beginGame() {

    return -1;
  }

  public int continueGame(Game distantGame) {
    
    int[] cases = new int[6];
    
    updateBoard(distantGame.currentState());
    int index = -1;

    for(int i = 0; i < 6; i++) {
      
      index = i + 6 * idAI;

      if (distantGame.isValidMove(index)) {
        cases[i] = simulateBowlAndGetGain();
      }
    
    }

    return indexOfBiggest(cases);
  }

  private int simulateBowlAndGetGain() {
    return -1;
  }

  private int indexOfBiggest(int[] data) {
    int res = -1;
    int currentMaxVal = -1;

    for (int i = 0; i < data.length; i++) {
      
      if (currentMaxVal < data[i]) {
        res = i;
      }
    }
    return res;
  }
}
