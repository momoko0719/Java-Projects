// Cynthia Wu
// CSE 143 AO with Batina Shikhalieva
// Homework 7
// The class QuestionNode is used to store a binary tree
// of yes/no questions

public class QuestionNode {
   
   public String data;
   public QuestionNode left;
   public QuestionNode right;
   
   // Parameters: takes a string of data(String data)
   // post      : constructs a leaf node with given data
   public QuestionNode(String data) {
      this(data, null, null);
   }
   
   // Parameters: takes a string of data(String data)
   //             takes a QuestionNode left as parameter(QuestionNode left)
   //             takes a QuestionNode right as parameter(QuestionNode right)
   // post      : constructs a branch node with given data, left subtree,
   //             right subtree
   public QuestionNode(String data, QuestionNode left, QuestionNode right) {
      this.data = data;
      this.left = left;
      this.right = right;
   }
}