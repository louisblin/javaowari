public class Game {

  /* CORRECTION
   *  - NO MAGIC NUMBERS: use constant declarations at the top of the file.
   *    NB: these constant don't live in the heap / stack !
   *
   *  - override toString in Bowl class to be able to print the number of stones
   *     by simply calling bowls[i];
   */

  // Fields
  public static final int BOWLS_PER_SIDE = 6;
  public static final int INITIAL_STONES_PER_BOWL = 4;
  public static final int NUMBER_OF_PLAYERS = 2;

  public static final int NUMBER_OF_BOWLS
    = BOWLS_PER_SIDE * NUMBER_OF_PLAYERS;

  public static final int STONES_TO_WIN
    = BOWLS_PER_SIDE * INITIAL_STONES_PER_BOWL;

  private Player[] players;
  private Bowl[] board;
  private int idCurrentPlayer;

  // Cons
  public Game() {
    this.players    = new Player[NUMBER_OF_PLAYERS];
    this.players[0] = new Player();
    this.players[1] = new Player();

    this.board = new Bowl[NUMBER_OF_BOWLS];
    for(int i = 0; i < NUMBER_OF_BOWLS; i++) { // 12 bowls
      this.board[i] = new Bowl(INITIAL_STONES_PER_BOWL);
    }

    //for(Bowl b : board) {
    //  b = new Bowl(INITIAL_STONES_PER_BOWL);
    //  System.out.println(b);
    //}

    this.idCurrentPlayer = 0;
  }

  public Game(Game another) {
    this.players    = new Player[NUMBER_OF_PLAYERS];
    this.players[0] = new Player(another.players[0]);
    this.players[1] = new Player(another.players[1]);

    this.board = new Bowl[NUMBER_OF_BOWLS];
    for(int i = 0; i < NUMBER_OF_BOWLS; i++) { // 12 bowls
      this.board[i] = new Bowl(another.board[i]);
    }

    this.idCurrentPlayer = another.idCurrentPlayer;
  }

  // Methods
  public int getCurrentPlayer() {
    return idCurrentPlayer;
  }

  public void swapPlayers() {
    assert idCurrentPlayer == 0 || idCurrentPlayer == 1 : "Impossible id...";

    // idCurrentPlayer = (idCurrentPlayer == 0) ? 1 : 0;
    idCurrentPlayer = (idCurrentPlayer + 1) % NUMBER_OF_PLAYERS;
  }

  public boolean isValidMove(int bowl) {
    // int low = 0 + idCurrentPlayer * BOWLS_PER_SIDE;
    //int up  = 5 + idCurrentPlayer * BOWLS_PER_SIDE;

    //return (low <= bowl && bowl <= up) && board[bowl].getStones() > 0;

    // CORRECTION
    //
    // NB: RHS executed only if LHS of '&&' is true;
    return isMoveOnCurrentSide(bowl) &&
            board[bowl].getStones() > 0;
  }

  private boolean isMoveOnCurrentSide(int bowlIndex) {
    int playerStartIndex = idCurrentPlayer * BOWLS_PER_SIDE;
    int playerEndIndex = 5 + idCurrentPlayer * BOWLS_PER_SIDE;

    return (playerStartIndex <= bowlIndex && bowlIndex <= playerEndIndex);
  }

  public boolean canCurrentPlayerMove() {
    // NB: players[0] can only take stones from bowl 1 to 6.
    //     players[1]                                7 to 12.

    for (int i = 0; i < BOWLS_PER_SIDE; i++) {

      int index = i + idCurrentPlayer * BOWLS_PER_SIDE;

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
    assert 0 + idCurrentPlayer * BOWLS_PER_SIDE <= bowl 
           && bowl < BOWLS_PER_SIDE + idCurrentPlayer * BOWLS_PER_SIDE;

    // Resetting chosen bowl
    int stonesCount = board[bowl].getStones();
    board[bowl].takeAllStones();

    // Distribute the stones
    for (int i = 1; i <= stonesCount; i ++) {
      int index = (bowl + i) % NUMBER_OF_BOWLS;
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


    for(int i = NUMBER_OF_BOWLS; i > BOWLS_PER_SIDE; i --) {
      lines[0]  += "  " + i + ((i < 10) ? "  " : " ");            //  12  11...
      int amount = board[i - 1].getStones();
      lines[1]  += "( " + amount + ((amount < 10) ? " )" : ")");  // ( 4 )  ...

      // New line to be printed...

      int j = NUMBER_OF_BOWLS - i + 1;
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
  
    int[] result = new int[NUMBER_OF_BOWLS + 2];
    
    for(int i = 0; i < NUMBER_OF_BOWLS; i++) {
      result[i] = board[i].getStones();
    }
    result[NUMBER_OF_BOWLS] = players[0].getScore();
    result[NUMBER_OF_BOWLS + 1] = players[1].getScore();

    return result;
  }
}
