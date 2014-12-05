import java.util.Arrays;

public class AIPlayer {

  // Fields
  private Bowl[] aiBoard;
  private int idAI;
  private int depth;

  // Cons
  public AIPlayer(int idAI) {
    
    this.aiBoard = new Bowl[12];
    for(int i = 0; i < 12; i++) { // 12 bowls
      this.aiBoard[i] = new Bowl(4);
    }

    this.idAI = idAI;
    this.depth = 3;
  }

  // Public methods...
  
  // post: return the index [6] of the chosen bowl
  public int continueGame(Game distantGame) {
    
    int[] anticipatedScores = new int[6];
    Arrays.fill(anticipatedScores, -1);

    updateBoard(distantGame.currentState());
    int index;

    for(int i = 0; i < 6; i++) {
      
      index = i + 6 * idAI;

      if (distantGame.isValidMove(index)) {
        Game gameCopy = new Game(distantGame);
        anticipatedScores[i] = simulateMoveAndGetGain(gameCopy, index);
        //System.out.println("For bowl " + (index + 1) 
        //                 + ", AI gets " + anticipatedScores[i] + "points");
      }
    
    }

    return indexOfBiggest(anticipatedScores);
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
