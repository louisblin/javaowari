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
    return indexOfBiggest(runTurn(distantGame, 1));
  }

  private int[] runTurn (Game distantGame, int depth) {

    int[] anticipatedScores = new int[6];
    //int[] step2anticipScores = new int[6];
    Arrays.fill(anticipatedScores, -100);
    //Arrays.fill(step2anticipScores, 0);

    updateBoard(distantGame.currentState());
    int index;

    for(int i = 0; i < 6; i++) {

      index = i + 6 * idAI;

      if (distantGame.isValidMove(index)) {
        Game gameCopy = new Game(distantGame);
        anticipatedScores[i] = simulateMoveAndGetGain(gameCopy, index);
        //System.out.println("For bowl " + (index + 1)
        //                 + ", AI gets " + anticipatedScores[i] + "points");
        
        // Run other player's turn
        //if (depth > 0) {
        //  idAI = 0;
        //  step2anticipScores[i] = indexOfBiggest(runTurn(new Game(gameCopy), 
        //                                                 depth - 1));
        //  idAI = 1;
        //}
      }

    }
    //for (int i = 0; i < anticipatedScores.length; i++) {
    //  anticipatedScores[i] -= step2anticipScores[i];
    //}

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
        res = i;
      }
    }
    //System.out.println("Biggest index is " + (res + 6) + " form array " + Arrays.toString(data));
    return res + 6;
  }
}
