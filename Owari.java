public class Owari {

  public static void main(String[] args) {
    
    System.out.println("Welcome in Owari! :-)");

    Game game = new Game();

    do {
    
      game.display();

    if (game.canCurrentPlayerMove()) {
      
      int pickedBowl = -1;

      do {
        System.out.println("Player " + game.getCurrentPlayer() + ", your turn."
                          + "Which bowl would you like to pick stones from?");
        pickedBowl = IOUtil.readInt();
      
      } while (pickedBowl < 1 || pickedBowl > 12);
      
        pickedBowl--; // Translate choice to an array index
        game.move(pickedBowl); 

    } else {
      continue;
    }
      game.swapPlayers();
    
    } while (!game.isOver());

      game.swapPlayers();

      System.out.println("Player " + game.getCurrentPlayer() + " you won !");

  }

}
