public class Test {

  public static void main(String[] args) {

    Game game = new Game();

    System.out.println("Choose test : "
                        + " 1 - isValidMove" );

    int choice = IOUtil.readInt();

    switch (choice) {
      case 1:

        do {

          System.out.print("input ? -->  ");
          int x = IOUtil.readInt();

          if (x == 99) {
            game.swapPlayers();
          }

          System.out.print("Player : " + game.getCurrentPlayer());
          if (game.isValidMove(x)) {
            System.out.println(" -> VALID with value");
          } else {
            System.out.println(" -> NOT VALID with value");
          }

        } while (true);
    }

  }

}
