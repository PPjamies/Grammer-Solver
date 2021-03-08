//CS211 Chapter12_Project03
//This class randomly generates sentences given BNF grammar file
//Reference: Blake Denniston -his code gave reference to my generatePhrase method.
//I really liked the structure of his code as it was easier to follow than mine and so, I fixed it up.

package Projects;
import java.util.*;


public class GrammarSolver {
	private TreeMap<String, List<String>> grammarMap = new TreeMap<>();
	private Random rand = new Random();
	private String sent = "";
	
	//Reminder:
	//each line from text file is saved as a string ---> each string is saved to an index in grammar list
	public GrammarSolver(List<String> grammar) {
		if(grammar.isEmpty() || grammar.size() == 0) {
			throw new IllegalArgumentException("Grammar list is empty");
		}else{
			for(int i =0; i< grammar.size(); i++) {
				String s = grammar.get(i);
				//split non-terminal variable from the terminal string; save these two strings in an array
				String[] split = s.split(":");
				String nonTerm = split[0].trim();
				
				//if the non-terminal key already exists in the grammarMap then throw exception
				if(grammarMap.containsKey((nonTerm))) {
					throw new IllegalArgumentException("Multiple non-terminal entries found");
				}
				
				//split the terminal string into terminal variables
				String terminals = split[1].trim();
				String[] again = terminals.split("[|]+");
				//clean up the the individuals strings before storing them into a list
				List<String> values = new ArrayList<>();
				for(String n: again) {
					n.trim();
					values.add(n);
				}
				//mapping non-terminal variables to their respective values
				grammarMap.put(nonTerm,values);
			}
		}
	}

	//if user inputs symbol that equals a non-terminal symbol return true
	public boolean grammarContains(String symbol) {
		return grammarMap.containsKey(symbol);
	}
	
	//returns all the keys from grammarMap as a list of Strings
	//Reminder:
	//grammarMap is a TreeMap therefore values stored are in natural ordering
	public String getSymbols() {
		return grammarMap.keySet().toString();
	}
	
	//public method that references a private method: generatePhrase
	//public method that returns an array of sentences
	//number of sentences is provided by the user
	public String[] generate(String symbol, int times) {
		if(!grammarContains(symbol) || times < 0) {
			throw new IllegalArgumentException("No no");
		}else {
			String[] saveSent = new String[times];
			for(int i=0; i<times; i++) {
				saveSent[i] = generatePhrase(symbol);
				this.sent = "";
			}
			return saveSent;
		}		
	}
	
	//private method that randomly generates sentences using recursion
	private String generatePhrase(String symbol) {
		//saves rules (respective to given symbol) into a temporary list
		List<String> terminals = grammarMap.get(symbol);
		//randomly picks a rule
		String chosenRule = terminals.get(rand.nextInt(terminals.size())).trim();
		if(terminals.size()==1) {
			//no choice
			chosenRule = terminals.get(0).trim();
		}
		//split rules by spaces and stores them in an array
		//effective on rules that are actually a series of rules to be executed 
		String[] ruleArray = chosenRule.split("[ \t]+");
		

		//evaluates the array by examining each rule in the series 
		if (ruleArray.length > 1) {
			//System.out.println(ruleArray.length);
			for (int i = 0; i<ruleArray.length; i++) {
				//checks if rule is also a key
				//if rule is a key then continue the path
				if (grammarMap.containsKey(ruleArray[i])) {
					//System.out.println(ruleArray[i]);
					generatePhrase(ruleArray[i]);
				}
			}
		//if rule is a single rule and not a series of rules
		//check to see if single rule is also a key
		//if single rule is a key then continue the path
		}else if (grammarMap.containsKey(chosenRule)) {
			generatePhrase(chosenRule);
		} else {
			//rule is not a key, therefore you've reached a definite word (nouns, adjectives, pronouns etc.)
			//save the word into a field variable
			//System.out.println(chosenRule);
			this.sent+= chosenRule + " ";
		}		
		return this.sent;
	}
}
