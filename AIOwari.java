public class AIOwari {

  public static void main(String[] args) {

    System.out.println("\n\n =================================");
    System.out.println(    " ===== Welcome in Owari! :-) =====");
    System.out.println(    " =================================\n\n");

    Game game = new Game();
    AIPlayer aiPlayer = new AIPlayer(1);

    do {
     
      game.display();

      if (game.canCurrentPlayerMove()) {

        int pickedBowl = -1;
        
        // AI plays
        if (game.getCurrentPlayer() == 1) {
          pickedBowl = aiPlayer.continueGame(new Game(game));          
         
          assert (game.isValidMove(pickedBowl)) : "AI chosed impossible move!";
         
          System.out.println(" --> AI chosed bowl " + (pickedBowl + 1) + "\n\n");

        // Humans plays
        } else { 
        
          do {
            System.out.print("  Player " + (game.getCurrentPlayer() + 1)
                      + ", your turn."
                      + "\n  Which bowl would you like to pick stones from?\t");
            pickedBowl = IOUtil.readInt();
            pickedBowl--; // Translate choice to an array index

            //
            System.out.println(
                  ((!game.isValidMove(pickedBowl) ? " -> NOT VALID!\n" : "")));
            //
          } while (!game.isValidMove(pickedBowl));
        }
          game.move(pickedBowl);

      } else {
        System.out.println(
           " --> OOOUPS! Sorry but you can't make any move... next turn!\n\n");
      }
        game.swapPlayers();
        clearConsole(game);        

    } while (!game.isOver());

      game.display();

      System.out.println("\n  >> Player " + (game.getLeadingPlayer() + 1)
                    + " you won with a score of "
                    + game.getLeadingScore() + "!\n\n\n"
                    + "  Cordialment, barrez-vous! \n\n\n");
  }

  // Thank you internet !
  public final static void clearConsole(Game game) {
    if (game.getCurrentPlayer() == 1) {
      System.out.print("\033[H\033[2J");
      System.out.flush();
    }
    /*
    try
    {
      final String os = System.getProperty("os.name");

      if (os.contains("Windows")) {
        Runtime.getRuntime().exec("cls");
      
      } else {
        Runtime.getRuntime().exec("clear");
      }
    
    } catch (final Exception e) {
      //  Handle any exceptions.
    }
    */
  }
}
