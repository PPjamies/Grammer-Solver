package Projects;
import java.util.*;
import java.io.*;

	// GrammarMain contains a main program that prompts a user for the name of a
	// grammar file and then gives the user the opportunity to generate random
	// versions of various elements of the grammar.

public class GrammarMain {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(new File("C:\\Users\\Tiffani\\Documents\\HTML\\BNF_Grammar.txt"));
		Scanner console = new Scanner(System.in);

		//introduction
		System.out.println("Welcome to the cse143 random sentence generator.");
		System.out.println();

		
		// read the grammar file and construct the grammar solver
		List<String> grammar = new ArrayList<String>();
		while (input.hasNextLine())
			grammar.add(input.nextLine());
		GrammarSolver solver = new GrammarSolver(Collections.unmodifiableList(grammar));

		//program 
		showResults(console, solver);
	}

	// pre : console open for console reading, solver initialized
	// post: allows the user to repeatedly pick a grammar element to generate
	public static void showResults(Scanner console, GrammarSolver solver) {
		for (;;) {
			System.out.println();
			System.out.println("Available symbols to generate are:");
			System.out.println(solver.getSymbols());
			System.out.print("What do you want generated? ");
			String target = console.nextLine();
			if (target.length() == 0)
				break;
			if (!solver.grammarContains(target))
				System.out.println("Illegal symbol");
			else {
				System.out.print("How many do you want me to generate? ");
				if (!console.hasNextInt())
					System.out.println("that's not an integer");
				else {
					int number = console.nextInt();
					if (number < 0)
						System.out.println("no negatives allowed");
					else {
						String[] answers = solver.generate(target, number);
						for (int i = 0; i < number; i++)
							System.out.println(answers[i]);
					}
				}
				console.nextLine(); // to position to next line
			}
		}
	}
}
