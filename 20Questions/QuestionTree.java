// Cynthia Wu
// CSE 143 AO with Batina Shikhalieva
// Homework 7
// The class QuestionTree can be used to keep track of a binary
// tree that asks or the player answers yes/no questions. The user
// will think an object and the computer will keep asking yes/no
// questions until it thinks it has enough questions to know the
// object the user is thinking of.

import java.util.*;
import java.io.*;

public class QuestionTree {
   
   private QuestionNode overallRoot;
   private Scanner console;
   
   // Post: constructs a question tree with one answer node and
   //       "computer" is the first answer.
   public QuestionTree() {
      overallRoot = new QuestionNode("computer");
      console = new Scanner(System.in);
   }
   
   // Parameters: reads another tree from the given file(Scanner input)
   // Post      : Use the tree from the file to replace the current tree
   //             with the format looking like "Q: ..." and "A: ...." in
   //             a preorder traversal
   public void read(Scanner input) {
      overallRoot = readHelper(input);
   }
   
   // Parameters: reads another tree from the given file(Scanner input)
   // Post      : Use the tree from the file to replace the current tree
   //             with the format looking like "Q: ..." and "A: ...." in
   //             a preorder traversal.
   //             Returns the question tree (QuestionNode root)
   private QuestionNode readHelper(Scanner input) {
      String type = input.nextLine();
      String text = input.nextLine();
      QuestionNode root = new QuestionNode(text);
      if (type.equals("Q:")) {
         root.left = readHelper(input);
         root.right = readHelper(input);
      }
      return root;
   }
   
   // Parameters: takes PrintStream output as parameter(PrintStream output)
   // Post      : stores the current tree to an output file and the written
   //             tree should look like "Q: ..." and "A: ..." and appear in
   //             preorder format
   public void write(PrintStream output) {
      writeHelper(overallRoot, output);
   }
   
   // Parameters: takes PrintStream output as parameter(PrintStream output)
   //             takes a QuestionNode root as parameter(QuestionNode root)
   // Post      : stores the current tree to an output file and the written
   //             tree should look like "Q: ..." and "A: ..." and appear in
   //             preorder format
   private void writeHelper(QuestionNode root, PrintStream output) {
      if (root.left == null && root.right == null) {
         output.println("A:");
         output.println(root.data);
      } else {
         output.println("Q:");
         output.println(root.data);
         writeHelper(root.left, output);
         writeHelper(root.right, output);
      }
   }
   
   // Post: uses the current tree to ask the user a series of yes/no
   //       questions until the players either guess their object correctly
   //       or fail. Modifies the current tree by including the player's object
   //       and the question to distinguish the object.
   public void askQuestions() {
      overallRoot = askQuestionsHelper(overallRoot);
   }
   
   // Parameters: takes a QuestionNode root as parameter(QuestionNode root)
   // Post: uses the current tree to ask the user a series of yes/no
   //       questions until the players either guess their object correctly
   //       or fail. Modifies the current tree by including the player's object
   //       and the question to distinguish the object.
   //       Returns the question tree (QuestionNode root)
   private QuestionNode askQuestionsHelper(QuestionNode root) {
      if (root.left == null && root.right == null) {
         if (yesTo("Would your object happen to be " + root.data +"?")) {
            System.out.println("Great, I got it right!");
         } else {
            System.out.print("What is the name of your object? ");
            QuestionNode answer = new QuestionNode(console.nextLine());
            System.out.println("Please give me a yes/no question that");
            System.out.println("distinguishes between your object");
            System.out.print("and mine--> ");
            String question = console.nextLine();
            if (yesTo("And what is the answer for your object?")) {
               root = new QuestionNode(question, answer, root);
            } else {
               root = new QuestionNode(question, root, answer);
            }
         }
      } else {
         if (yesTo(root.data)) {
            root.left = askQuestionsHelper(root.left);
         } else {
            root.right = askQuestionsHelper(root.right);
         }
      }
      return root;
   }
   
   // Parameters: takes a String prompt as parameter(String prompt)
   // post: asks the user a question, forcing an answer of "y " or "n";
   // returns true if the answer was yes, returns false otherwise
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
}