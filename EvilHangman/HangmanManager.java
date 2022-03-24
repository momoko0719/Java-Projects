// Cynthia Wu
// CSE 143 AO with Batina Shikhalieva
// Homework 4
// The class HangmanManager is used to administer a game of hangman
// in which to keep track of the state of the game including the current words
// in the dictionary, the number of guesses left, the characters that already
// guessed and how the current pattern is.

import java.util.*;

public class HangmanManager {
   
   private Set<String> currentWord;
   private Set<Character> currentChar;
   private int guessNum;
   private String currentPattern;
   
   // Parameters: takes a dictionary of words(Collection<String> dictionary),
   //             a target word length (int length),
   //             the maximum number of wrong guesses that allowed(int max)
   // Pre       : the target word length should be at least 1 and the
   //             maximum number of wrong guesses should be at least 0
   //             (throws an IllegalArgumentException if not)
   // Post      : forms a set of words from the given dictionary, without duplicates
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if (length < 1 || max < 0) {
         throw new IllegalArgumentException();
      }
      currentWord = new TreeSet<>();
      currentChar = new TreeSet<>();
      guessNum = max;
      currentPattern = "-";
      for (int i = 1; i < length; i++) {
         currentPattern += " -";
      }
      Iterator itr = dictionary.iterator();
      while (itr.hasNext()){
         String word = (String)itr.next();
         if (word.length() == length){
            currentWord.add(word);
         }
      }
   }
   
   // Post: returns what words are currently possible
   //       (Set<String> currentWord)
   public Set<String> words() {
      return currentWord;
   }
   
   // Post: returns how many guesses left that the player
   //       is allowed to make (int guessNum)
   public int guessesLeft() {
      return guessNum;
   }
   
   // Post: returns the set of characters which has been guessed
   //       (Set<Character> currentChar)
   public Set<Character> guesses() {
      return currentChar;
   }
   
   // Pre:  the set of words should not be empty
   //       (throws an IllegalStateException if not)
   // Post: returns the current pattern, considering guesses that
   //       have been made (String currentPattern) and the letters
   //       that have not yet been guessed will be displayed as a dash
   public String pattern() {
      if (currentWord.isEmpty()) {
         throw new IllegalStateException();
      }
      return currentPattern.trim();
   }
   
   // Parameters: takes a of guess of letter(char guess)
   // Pre:        the number of guesses left should be at least 1 and
   //             set of words should not be empty
   //             (throws an IllegalStateException if not)
   //             On the condition that the previous exception was not thrown,
   //             the character being guessed should not been guessed before
   //             (throws an ILLegalArgumentException if not)
   // Post:       record the user's guess and update the dictionary of words to
   //             go forward. Update the pattern, according to the new pattern,
   //             calculate the occurrences of the guessed letter.
   //             returns the occurrences of the guessed letter(int occurrence)
   //             It will also update the number of guesses left (if gets a wrong
   //             guess, the user will have one less guess)
   public int record(char guess) {
      if (guessNum < 1 || currentWord.isEmpty()) {
         throw new IllegalStateException();
      } else if (currentChar.contains(guess)){
         throw new IllegalArgumentException();
      }
      int occurrence = 0;
      Map<String, Set<String>> patterns = new TreeMap<>();
      currentChar.add(guess);
      if (guessNum == 1) {   
         Iterator itr = currentWord.iterator();
         String word = (String)itr.next();
         while (itr.hasNext() && word.indexOf(guess) != -1){
            word = (String)itr.next();
         }
         getPattern(word, guess);
         currentWord.clear();
         currentWord.add(word);
      } else {
         for (String word: currentWord) {
            getPattern(word, guess);
            // Store the patterns
            if (!patterns.containsKey(currentPattern)) {
               patterns.put(currentPattern, new TreeSet<>());
            }
            patterns.get(currentPattern).add(word);
         }
         decidePattern(patterns);
      }
         // Calculate the occurrences of the guess in the new pattern
      for (int i = 0; i < currentPattern.length(); i++) {
         if (currentPattern.charAt(i) == guess) {
            occurrence++;
         }
      }
      if (occurrence == 0) {
         guessNum--;
      }
      return occurrence;
   }
   
   // Parameters: takes a string of word (String word)
   //             and a guess of letter (char guess)
   // Post:       form the current pattern according to
   //             the position of the guessed letter in
   //             the word
   private void getPattern(String word, char guess) {
      currentPattern = "";
      for (int i = 0; i < word.length(); i++) {
         char letter = word.charAt(i);
         if (letter == guess) {
            currentPattern += guess + " ";
         } else if(currentChar.contains(letter)){
            currentPattern += letter + " ";
         } else {
            currentPattern += "- ";
         }
      }
   }
   
   // Parameters: takes a map of patterns and the set of word
   //             (Map<String, Set<String>> patterns)
   // Post:       according to each pattern's set of word, decide
   //             which pattern to use (the pattern has the most
   //             words)
   private void decidePattern(Map<String, Set<String>> patterns) {
      int largest = 0;
      String largestPattern = "";
      for (String pattern: patterns.keySet()) {
         int size = patterns.get(pattern).size();
         if (size > largest) {
            largest = size;
            largestPattern = pattern;
         }
      }
      currentPattern = largestPattern;
      currentWord = patterns.get(currentPattern);
   }
}