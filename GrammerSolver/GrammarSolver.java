// Cynthia Wu
// CSE 143 AO with Batina Shikhalieva
// Homework 5
// The class GrammarSolver is used to read an input file with a grammar in BNF
// form and the player can randomly generate the elements of grammar.

import java.util.*;

public class GrammarSolver {
   
   private SortedMap<String, String[]> words;
   
   // Parameters: takes a list of strings, which is a grammar in BNF format(List<String> grammar)
   // Pre       : the grammar should not be empty and there should not be two or more entries in
   //             the grammar for the same nonterminal.
   //             (throws an IllegalArgumentException if not)
   // Post      :  stores the grammar so that it can be later generated
   public GrammarSolver(List<String> grammar) {
      if (grammar.isEmpty()) {
         throw new IllegalArgumentException();
      }
      words = new TreeMap<>();
      for (String s : grammar) {
         String[] parts = s.split("::=");
         if (grammarContains(parts[0])) {
            throw new IllegalArgumentException();
         }
         String[] rules = parts[1].split("[|]");
         words.put(parts[0], rules);
      }
   }
   
   // Parameters: takes a string of symbol to test (String symbol)
   // Post      : returns true if the given symbol is a nonterminal of the grammar;
   //             returns false otherwise.
   public boolean grammarContains(String symbol) {
      return words.containsKey(symbol);
   }
   
   // Parameters: takes a string of symbol, which uses bnf rule to generate strings(String symbol)
   //             a target times which is the number of objects in each sentence (int times)
   // Pre       : the grammar should contain the given nonterminal symbol and the number of times should
   //             equal or larger than 0
   //             (throws an IllegalArgumentException if not)
   // Post      : uses the grammar to randomly generate the given number of times of the given symbol
   //             returns the result as an array of strings (String[] result).
   
   public String[] generate(String symbol, int times) {
      if (!grammarContains(symbol) || times < 0) {
         throw new IllegalArgumentException();
      }
      String[] result = new String[times];
      for (int i = 0; i < times; i++) {
         result[i] = generateString(symbol);
      }
      return result;
      
   }
   
   // Parameters: takes a String of symbol(String symbol)
   // Post      : generates string of given symbol and returns
   //             the result(String result)
   private String generateString(String symbol) {
      if (!grammarContains(symbol)) {
         return symbol;
      } else {
         String result = "";
         String[] terminal = words.get(symbol);
         Random rand = new Random();
         int randomNum = rand.nextInt(terminal.length);
         String[] terminals = terminal[randomNum].split("[ \t]+");
         for (String s: terminals) {
            result += generateString(s) + " ";
         }
         return result.trim();
      }
   }   
   
   // Post: returns a string of the various nonterminal symbols from the grammar,
   //       as a sorted, comma-separated list enclosed in square brackets.
   public String getSymbols() {
      return words.keySet().toString();
   }
}
