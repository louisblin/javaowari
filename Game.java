public class Game {

  // Fields
  private Player[] players;
  private Bowl[] board;
  private int idCurrentPlayer;

  // Cons
  public Game() {
    this.players    = new Player[2];
    this.players[0] = new Player();
    this.players[1] = new Player();

    this.board = new Bowl[12];
    for(int i = 0; i < 12; i++) { // 12 bowls
      this.board[i] = new Bowl(4);
    }

    //for(Bowl b : board) {
    //  b = new Bowl(4);
    //  System.out.println(b);
    //}

    this.idCurrentPlayer = 0;
  }

  // Methods
  public int getCurrentPlayer() {
    return idCurrentPlayer;
  }

  public void swapPlayers() {
    assert idCurrentPlayer == 0 || idCurrentPlayer == 1 : "Impossible id...";

    idCurrentPlayer = (idCurrentPlayer == 0) ? 1 : 0;
  }

  public boolean isValidMove(int bowl) {
    int low = 0 + idCurrentPlayer * 6;
    int up  = 5 + idCurrentPlayer * 6;

    return (low <= bowl && bowl <= up) && board[bowl].getStones() > 0;
  }

  public boolean canCurrentPlayerMove() {
    // NB: players[0] can only take stones from bowl 1 to 6.
    //     players[1]                                7 to 12.

    for (int i = 0; i < 6; i++) {

      int index = i + idCurrentPlayer * 6;

      //System.out.println("i = " + i
      //                 + " - index = " + index + (isValidMove(index)));
      //int bullshit = IOUtil.readInt();

      if (isValidMove(index)) {
        return true;
      }
    }
    return false;
  }

  // pre: non empty, valid bowl index, valid id of player, valid range of bowl
  public void move(int bowl) {
    assert bowl >= 0 : "Negative index should be impossible!";
    assert idCurrentPlayer == 0 || idCurrentPlayer == 1 : "Impossible id...";
    assert board[bowl].getStones() > 0 : "Illegal empty bowl selection";
    assert 0 + idCurrentPlayer * 6 <= bowl && bowl < 6 + idCurrentPlayer * 6;

    // Resetting chosen bowl
    int stonesCount = board[bowl].getStones();
    board[bowl].takeAllStones();

    // Distribute the stones
    for (int i = 1; i <= stonesCount; i ++) {
      int index = (bowl + i) % 12;
      board[index].depositStone();
    }

    // Uptading, getting scores
    for (int i = 0; i < board.length; i++) {
      int points = board[i].updateAndGetScore();
      players[idCurrentPlayer].addToScore(points);
    }
  }

  public int getLeadingPlayer() {
    return (players[0].getScore() > players[1].getScore()) ? 0 : 1;
  }

  public int getLeadingScore() {
    return players[getLeadingPlayer()].getScore();
  }
  
  
  public boolean isOver() {
    return players[getLeadingPlayer()].getScore() >= 24;
  }

  public void display() {

    String[] lines = new String[] {"  ", "  ", "  ", "  "};


    for(int i = 12; i > 6; i --) {
      lines[0]  += "  " + i + ((i < 10) ? "  " : " ");            //  12  11...
      int amount = board[i - 1].getStones();
      lines[1]  += "( " + amount + ((amount < 10) ? " )" : ")");  // ( 4 )  ...

      // New line to be printed...

      int j = 12 - i + 1;
      amount = board[j - 1].getStones();
      lines[2] += "( " + amount + ((amount < 10) ? " )" : ")");   // ( 4 )  ...
      lines[3] += "  " + j + "  ";                                //   1  2 ...
    }

    System.out.println("  Player 2: " + players[1].getScore() + "\n\n"
                        + lines[0] + "\n"
                        + lines[1] + "\n\n"
                        + lines[2] + "\n"
                        + lines[3] + "\n\n"
                        + "  Player 1: " + players[0].getScore() + "\n\n");
  }

  // AI Extension
  
  /* post: 12 first values for the stones in eahc bowl.
   *        2 last values for ther score of the 2 players.
   */
  public int[] currentState() {
  
    int[] result = new int[14];
    
    for(int i = 0; i < 12; i++) {
      result[i] = board[i].getStones();
    }
    result[12] = players[0].getScore();
    result[13] = players[1].getScore();

    return result;
  }
}
