import java.util.Random;
import java.util.Scanner;

public class SnakeAndLadder {
    private static final int WINNING_POSITION = 100;
    private static final int[] snakes = {17, 54, 62, 88, 95, 99};
    private static final int[] snakeEnds = {7, 34, 19, 24, 75, 78};
    private static final int[] ladders = {3, 6, 20, 36, 63, 68};
    private static final int[] ladderEnds = {22, 55, 38, 44, 81, 91};
    
    private int player1Position = 0;
    private int player2Position = 0;

    public static void main(String[] args) {
        SnakeAndLadder game = new SnakeAndLadder();
        game.startGame();
    }

    private void startGame() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean isPlayer1Turn = true;

        System.out.println("Welcome to Snake and Ladder!");
        System.out.println("First to reach position 100 wins!");

        while (player1Position < WINNING_POSITION && player2Position < WINNING_POSITION) {
            if (isPlayer1Turn) {
                System.out.println("\n=== Player 1's Turn ===");
                System.out.println("Press Enter to roll the dice...");
                scanner.nextLine();
                int roll = random.nextInt(6) + 1;
                System.out.println("🎲 Player 1 rolled a " + roll + "!");
                movePlayer(1, roll);
            } else {
                System.out.println("\n=== Player 2's Turn ===");
                System.out.println("Press Enter to roll the dice...");
                scanner.nextLine();
                int roll = random.nextInt(6) + 1;
                System.out.println("🎲 Player 2 rolled a " + roll + "!");
                movePlayer(2, roll);
            }
            isPlayer1Turn = !isPlayer1Turn;
        }

        if (player1Position >= WINNING_POSITION) {
            System.out.println("\n🎉 Congratulations! Player 1 wins!");
        } else {
            System.out.println("\n🎉 Congratulations! Player 2 wins!");
        }

        System.out.println("Thank you for playing!");
        scanner.close();
    }

    private void movePlayer(int player, int roll) {
        if (player == 1) {
            player1Position += roll;
            if (player1Position > WINNING_POSITION) {
                player1Position = WINNING_POSITION;
            }
            player1Position = checkSnakesAndLadders(player1Position);
            System.out.println("🚶‍♂️ Player 1 is now on position " + player1Position);
        } else {
            player2Position += roll;
            if (player2Position > WINNING_POSITION) {
                player2Position = WINNING_POSITION;
            }
            player2Position = checkSnakesAndLadders(player2Position);
            System.out.println("🚶‍♂️ Player 2 is now on position " + player2Position);
        }
    }

    private int checkSnakesAndLadders(int position) {
        for (int i = 0; i < snakes.length; i++) {
            if (position == snakes[i]) {
                System.out.println("😱 Oh no! Player encountered a snake! Moving down from " + position + " to " + snakeEnds[i]);
                return snakeEnds[i];
            }
        }
        for (int i = 0; i < ladders.length; i++) {
            if (position == ladders[i]) {
                System.out.println("🎉 Great! Player found a ladder! Moving up from " + position + " to " + ladderEnds[i]);
                return ladderEnds[i];
            }
        }
        return position;
    }
}
