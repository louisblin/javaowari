import java.util.Arrays;

public class AIPlayer {

  // Fields
  private Bowl[] aiBoard;
  private int idAI;

  // Cons
  public AIPlayer(int idAI) {

    this.aiBoard = new Bowl[12];
    for(int i = 0; i < 12; i++) { // 12 bowls
      this.aiBoard[i] = new Bowl(4);
    }

    this.idAI = idAI;
  }

  // Public methods...

  // post: return the index of the chosen bowl
  public int continueGame(Game distantGame) {
    // TODO: run 'depth' turns...
    //int[] anticipScores = new int[6];
    //Arrays.fill(anticipScores, -100);

    //for (int i = 0; i < 6; i ++) {
    //  if (distantGame.isValidMove(i)) {
    //    Game test = new Game(distantGame);
    //    anticipScores[i] = simulateMoveAndGetGain(test, i + 6); 
    //    anticipScores[i] -= indexOfBiggest(runTurn(test, 1));
    //  }
    //}
      
    //return indexOfBiggest(anticipScores);
    return indexOfBiggest(runTurn(distantGame));
  }

  private int[] runTurn (Game distantGame) {

    int[] anticipatedScores = new int[6];
    Arrays.fill(anticipatedScores, -100);

    updateBoard(distantGame.currentState());
    int index;

    for(int i = 0; i < 6; i++) {

      index = i + 6 * idAI;

      if (distantGame.isValidMove(index)) {
        Game gameCopy = new Game(distantGame);
        anticipatedScores[i] = simulateMoveAndGetGain(gameCopy, index);
        //System.out.println("For bowl " + (index + 1)
        //                 + ", AI " + idAI + " gets " + anticipatedScores[i] + "points");
        
      }
    }
    return anticipatedScores;
  }

  // Private helper methods

  private void updateBoard(int[] data) {
    for(int i = 0; i < 12; i++) {
      aiBoard[i].setStones(data[i]);
    }
  }

  private int simulateMoveAndGetGain(Game game, int index) {
    int previousScore = game.currentState()[13];

    game.move(index);

    return game.currentState()[13] - previousScore;
  }

  private int indexOfBiggest(int[] data) {
    assert data.length > 0 : "No empty data accepted";

    int res = 0;
    int currentMaxVal = data[0];

    for (int i = 1; i < data.length; i++) {

      if (currentMaxVal <= data[i]) {
        //System.out.println("CurMaxVal = " + currentMaxVal + " | data = " + data[i] + " => res = " + res + " | i = " + i);
        currentMaxVal = data[i];
        res = i;
      }
    }
    //System.out.println("Biggest index is " + (res + 6) + " from array " + Arrays.toString(data));
    return res + 6;
  }
}
