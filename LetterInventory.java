// Cynthia Wu
// CSE 143 AO with Batina Shikhalieva
// Homework 1
// The class LetterInventory can be used to take a String and store
// the count of letters(ignoring the case) of the alphabet in it.

public class LetterInventory {
   private int[] elementData; // list of integers
   private int size;          // current number of elements in the list
   
   public static final int COUNT = 26; // the number of letters in the alphabet
   
   // post: constructs an inventory for alphabetic characters(ignoring the case)
   //       with the given String
   public LetterInventory(String data) {
      data = data.toLowerCase();
      elementData = new int[COUNT];
      for (int i = 0; i < data.length(); i++) {
         if (data.charAt(i) - 'a' >= 0 && data.charAt(i) - 'z' <= 0) {
            elementData[data.charAt(i) - 'a']++;
            size++;
         }
      }
   }
   
   // post: returns the sum of all the counts in the inventory
   public int size() {
      return size;
   }
   
   // post: returns true if the inventory is empty
   public boolean isEmpty() {
      return size == 0;
   }
   
   // pre:  letters must be alphabetic characters(lowercase or uppercase)
   //       (throws IllegalArgumentException if not)
   // post: returns the count(integer) of each letter in the inventory
   public int get(char letter) {
      letter = Character.toLowerCase(letter);
      if (letter < 'a' || letter > 'z') {
         throw new IllegalArgumentException();
      }
      return elementData[letter - 'a'];
   }
   
   // post: creates a bracketed version of the inventory with
   // the letters in lowercase and sorted order
   public String toString() {
      if (size == 0) {
         return "[]";
      } else {
         String result = "[";
         for (int i = 0; i < COUNT; i++) {
            for (int j = 0; j < elementData[i]; j++) {
               result += (char) ('a' + i);
            }
         }
         return result + "]";
      }
   }
   
   // pre : letters must be alphabetic characters and
   //       the given value should be non-negative
   //       (throws IllegalArgumentException if not)
   // post: replaces the count of the given letter with the given value
   public void set(char letter, int value) {
      letter = Character.toLowerCase(letter);
      if (letter < 'a' || letter > 'z' || value < 0) {
         throw new IllegalArgumentException();
      } else {
         size += (value - elementData[letter - 'a']);
         elementData[letter - 'a'] = value;
      }
   }
   
   // post: constructs and returns a new LetterInventory object that combines
   //       two different LetterInventory by adding each letter's counts
   //       together
   public LetterInventory add(LetterInventory other) {
      String total = "";
      LetterInventory addTwo = new LetterInventory(total);
      for (int i = 0; i < COUNT; i++) {
         addTwo.elementData[i] = elementData[i] + other.elementData[i];
      }
      addTwo.size = size + other.size;
      return addTwo;
   }
   
   // post: constructs and returns a new LetterInventory object that subtracts
   //       one LetterInventory from the other by calculating each letter's
   //       difference. 
   //       returns null if difference of any letter's counts is less than 0
   public LetterInventory subtract(LetterInventory other) {
      String difference = "";
      LetterInventory subtractTwo = new LetterInventory(difference);
      for (int i = 0; i < COUNT; i++) {
         subtractTwo.elementData[i] = elementData[i] - other.elementData[i];
         if (subtractTwo.elementData[i] < 0) {
            return null;
         }
      }
      subtractTwo.size = size - other.size;
      return subtractTwo;
   }
}    