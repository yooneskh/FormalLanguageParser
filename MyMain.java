import java.util.Scanner;

/**
 * Created by Yoones on 10/31/2015.
 */
public class MyMain {

    public static void main(String[] args) {

        Scanner inp = new Scanner(System.in);

        System.out.print("Please enter initial Variable: ");

        ESParser parser = new LandaFree(inp.nextLine().trim());

        String another = "yes";

        while (another.equals("yes") || another.equals("y")) {

            System.out.println("Please enter a rule in this form:  X --> Y");
            String[] parts = inp.nextLine().trim().split("\\s*\\-\\->\\s*");

            parser.addRule(parts[0], parts[1]);

            System.out.print("Do you want to add another rule? (yes/no) ");
            another = inp.nextLine().trim();

        }

        another = "yes";

        while (another.equals("yes") || another.equals("y")) {

            System.out.print("Enter your sentence to test for derivability: ");
            parser.canDerive(inp.nextLine().trim());

            if (!parser.getCouldDerive()) {
                System.out.println("Could not derive ...");
            }
            else {

                System.out.println("\nThis sentence is derivable as follows: \n");
                System.out.println(parser.getLastDerivationStringForm());

            }

            System.out.print("Do you want to test another sentence? (yes/no)");
            another = inp.nextLine().trim();

        }

    }
}
