public class Owari {

  public static void main(String[] args) {

    System.out.println("\n\n =================================");
    System.out.println(    " ===== Welcome to Owari! :-) =====");
    System.out.println(    " =================================\n\n");

    Game game = new Game();

    do {
     
      game.display();

      if (game.canCurrentPlayerMove()) {

        int pickedBowl = -1;

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
          
          game.move(pickedBowl);

      } else {
        System.out.println(
           " --> OOOUPS! Sorry but you can't make any move... next turn!\n\n");
      }
        game.swapPlayers();

    } while (!game.isOver());

      game.display();

      System.out.println("\n  >> Player " + game.getLeadingPlayer()
                    + " you won with a score of "
                    + game.getLeadingScore() + "!\n\n\n"
                    + "Cordialment, barrez-vous! \n\n\n");
  }

}
