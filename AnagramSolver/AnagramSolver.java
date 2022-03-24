// Cynthia Wu
// CSE 143 AO with Batina Shikhalieva
// Homework 6
// The class AnagramSolver can be used to take a dictionary
// and find all the combinations of words with the same letters
// as the given phrase.

import java.util.*;

public class AnagramSolver {
   private Map<String, LetterInventory> dictionary;
   private List<String> list;
   
   // Parameters: takes a dictionary of words(List<String> list)
   // Post      : constructs an anagram solver that uses the given list
   //             as its dictionary and does not change the list, without 
   //             duplicates  
   public AnagramSolver(List<String> list) {
      dictionary = new HashMap<>();
      this.list = list;
      for (String word: list) {
         LetterInventory data = new LetterInventory(word);
         dictionary.put(word, data);
      }
   }
   
   // Parameters: takes a string of given phrase (String s),
   //             the maximum number of words that allowed (int max)
   //             (if max equals 0, means unlimited number of words;
   //              if max is no 0, means the printed words should include
   //              at most max words)
   // Pre       : the maximum number of allowed words should be at least 0
   //             (throws an IllegalArgumentException if not)
   // Post      : print out the combinations of words from the dictionary that
   //             have the same letters as the given phrase.
   public void print(String s, int max) {
      if (max < 0) {
         throw new IllegalArgumentException();
      }
      LetterInventory input = new LetterInventory(s);
      List<String> sortedList = new ArrayList<String>();
      // reduce the dictionary to smaller relevant words
      for (String word : list) {
         if (input.subtract(dictionary.get(word)) != null) {
            sortedList.add(word);
         }
      }
      List<String> soFar = new ArrayList<String>();
      print(sortedList, max, input, soFar);
   }
   
   // Parameters: takes a dictionary of sorted words(List<String> options),
   //             the maximum number of words that allowed(int max),
   //             a letterInventory object that has the given string
   //             (LetterInventory input),
   //             the solution of the anagram(List<String> soFar)
   // Post      : print out the combinations of words from the sorted dictionary 
   //             that have the same letters as the given letterInventory.
   private void print(List<String> options, int max,
                      LetterInventory input, List<String> soFar) {
      if (input.isEmpty()) {
         System.out.println(soFar.toString());
      } else if (soFar.size() < max || max == 0) {
         for (String word: options) {
            LetterInventory newInventory = input.subtract(dictionary.get(word));
            if (newInventory != null) {
               soFar.add(word);
               print(options, max, newInventory, soFar);
               soFar.remove(soFar.size()-1);
            }
         }
      }
   }
} 