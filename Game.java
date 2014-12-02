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
    for(Bowl b : board) {
      b = new Bowl(4);
      System.out.println(b);
    }
  
    System.out.println(board.length + "    " + board[0]);
    // for(int i = 0; i < 12; i++) { // 12 bowls 
    //   board[i] = new Bowl(4);
    // }
    
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
    return board[bowl].getStones() > 0;
  }

  public boolean canCurrentPlayerMove() {
    // NB: players[0] can only take stones from bowl 1 to 6.
    //     players[1]                                7 to 12.
    
    for (int i = 0; i < 6; i++) {
      int index = i + idCurrentPlayer * 6;

      if (!isValidMove(index)) {
        return false;
      }
    }
    return true;
  }

  // pre: non empty, valid bowl index, valid id of player, valid range of bowl
  public void move(int bowl) {
    assert bowl > 0 : "Negative index should be impossible!";
    assert idCurrentPlayer == 0 || idCurrentPlayer == 1 : "Impossible id...";
    assert board[bowl].getStones() > 0 : "Illegal empty bowl selection";
    assert 0 + idCurrentPlayer * 6 <= bowl && bowl < 6 + idCurrentPlayer * 6;

    for (int i = 1; i <= board[bowl].getStones(); i ++) {
      int index = bowl + i;
      board[index].depositStone();
    }
    
    for (int i = 0; i < board.length; i++) {
      int points = board[i].updateAndGetScore(); 
      players[idCurrentPlayer].addToScore(points);
    }
    
  }

  public int getLeadingPlayer() {
    return (players[0].getScore() > players[1].getScore()) ? 0 : 1;
  }

  public boolean isOver() {
    return players[getLeadingPlayer()].getScore() >= 24;
  }

  public void display() {
    System.out.println("Player 2: " + players[1].getScore());

    String[] lines = new String[4];

    for(int i = 12; i > 6; i --) {
      lines[0]  += "  " + i + "  ";                               //  12  11...
      int amount = board[i - 1].getStones();
      lines[1]  += "( " + amount + ((amount < 10) ? " )" : ")");  // ( 4 )  ...
      
      // New line to be printed...
      
      int j = 12 - i + 1;
      amount = board[j - 1].getStones();
      lines[2] += "( " + amount + ((amount < 10) ? " )" : ")");   // ( 4 )  ...
      lines[3] += "  " + j + "  ";                                //   1  2 ...
    }

    System.out.println("Player 1: " + players[0].getScore() + "\n\n"
                        + lines[0] + "\n"
                        + lines[1] + "\n\n"
                        + lines[2] + "\n"
                        + lines[3] + "\n\n"
                        + "Player 2: " + players[1].getScore() + "\n\n");
  }
}
