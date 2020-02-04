// Cynthia Wu
// CSE 143 AO with Batina Shikhalieva
// Homework 3
// 01/30/2020
// The class AssassinManager is used to administer a game of assassin
// in which to keep track of who is stalking whom and the history of
// who killed whom

import java.util.*;

public class AssassinManager {
   
   private AssassinNode killRingFront;
   private AssassinNode graveyardFront;
   
   // Parameters: takes a list of names to form the kill ring
   // Pre       : the list of names should have at least one name
   //             (throws an IllegalArgumentException if not)
   // Post      : forms a kill ring with names in the same order in the given list
   public AssassinManager(List<String> names) {
      if (names.size() == 0) {
         throw new IllegalArgumentException();
      }
      for (int i = names.size() - 1; i >= 0; i--) {
         killRingFront = new AssassinNode(names.get(i), killRingFront);
      }
   }
   
   // Post: prints the names in the kill ring and who they are stalking.
   //       If there's only one left, prints stalking him/herself
   public void printKillRing() {
      AssassinNode current = killRingFront;
      while (current.next != null) {
         System.out.println("    " + current.name + " is stalking " + current.next.name);
         current = current.next;
      }
      System.out.println("    " + current.name + " is stalking " + killRingFront.name);
   }
   
   // Post: prints the names in the graveyard and who they are killed by.
   public void printGraveyard() {
      if (graveyardFront != null) {
         AssassinNode current = graveyardFront;
         while (current != null) {
            System.out.println("    " + current.name + " was killed by " + current.killer);
            current = current.next;
         }
      }
   }
   
   // Post: returns true if the kill ring or graveyard contains the given name
   //       (ignoring the case of names)
   //       returns false otherwise
   private boolean contain(AssassinNode current, String name) {
      while(current != null){
         if(current.name.equalsIgnoreCase(name)){
            return true;
         }
         current = current.next;
      }
      return false;
   }
   
   // Parameters: takes a string of name to check if the kill ring has
   // Post      : returns true if the kill ring contains the given name
   //             (ignoring the case of names)
   //             returns false otherwise
   public boolean killRingContains(String name) {
      AssassinNode current = killRingFront;
      return contain(current, name);
   }
   
   // Parameters: takes a string of name to check if the graveyard has
   // Post      : returns true if the graveyard contains the given name
   //             (ignoring the case of names)
   //             returns false otherwise
   public boolean graveyardContains(String name) {
      AssassinNode current = graveyardFront;
      return contain(current, name);
   }
   
   // Post: returns true if there's no person left in the kill ring
   //       returns false otherwise
   public boolean gameOver() {
      return (killRingFront.next == null);
   }
   
   // Post: returns the last person(winner)'s name in the kill ring
   //       returns null if the game is not over (more than one person
   //       in the kill ring)
   public String winner() {
      if (!gameOver()) {
         return null;
      }
      return killRingFront.name;
   }
   
   // Parameters: takes a string of name to decide which one in the kill ring
   //             will be killed
   // Pre:        the game should not be over yet
   //             (throws an IllgalStateException if not)
   //             the kill ring should contain the given name
   //             (throws an IllgalArgumentException if not)
   // Post:       removes the killed person from the kill ring to the graveyard
   public void kill(String name) {
      if (gameOver()) {
         throw new IllegalStateException();
      }
      if (!killRingContains(name)) {
         throw new IllegalArgumentException();
      }
      AssassinNode current = killRingFront;
      AssassinNode pre = graveyardFront;
      if (!killRingFront.name.equalsIgnoreCase(name)) {
         // when the targeted victim is not at the front of the kill ring
         while (current != null && current.next != null &&
         !current.next.name.equalsIgnoreCase(name)) {
            current = current.next;
         }
         pre = current.next;
         pre.killer = current.name;
         // checks if there's person after the killed person
         if(current.next.next != null){
            current.next = current.next.next;
         } else {
            current.next = null;
         }
      } else {
         // when the targeted victim is at the front of the kill ring
         pre = killRingFront;
         while (current != null && current.next != null){
            current = current.next;
         }
         pre.killer = current.name;
         current = current.next;
         killRingFront = killRingFront.next;
      }
      // puts the killed person at the front of the graveyard
      pre.next = graveyardFront;
      graveyardFront = pre;
   }
}
