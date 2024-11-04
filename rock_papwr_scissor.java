import java.util.Random;
import java.util.Scanner;

public class RPSGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        
        String[] moves = {"Rock", "Paper", "Scissors"};
        
        System.out.print("Choose (Rock, Paper, Scissors): ");
        String userMove = sc.nextLine().trim();
        
        if (!userMove.equalsIgnoreCase("Rock") && 
            !userMove.equalsIgnoreCase("Paper") && 
            !userMove.equalsIgnoreCase("Scissors")) {
            System.out.println("Invalid choice.");
        } else {
            String compMove = moves[rand.nextInt(3)];
            System.out.println("Computer chose: " + compMove);
            
            if (userMove.equalsIgnoreCase(compMove)) {
                System.out.println("Tie!");
            } else if (
                (userMove.equalsIgnoreCase("Rock") && compMove.equals("Scissors")) ||
                (userMove.equalsIgnoreCase("Paper") && compMove.equals("Rock")) ||
                (userMove.equalsIgnoreCase("Scissors") && compMove.equals("Paper"))) {
                System.out.println("You win!");
            } else {
                System.out.println("Computer wins!");
            }
        }
        
        sc.close();
    }
}
