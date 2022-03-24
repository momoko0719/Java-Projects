// Cynthia Wu
// CSE 143 AO with Batina Shikhalieva
// Homework 8
// The class HuffmanNode is used to store a single node in the
// huffman tree

public class HuffmanNode implements Comparable<HuffmanNode> {
   public int ascii;
   public int frequency;
   public HuffmanNode left;
   public HuffmanNode right;
   
   // Parameters: takes an integer of frequency(int frequency)
   //             takes a HuffmanNode left as parameter(HuffmanNode left)
   //             takes a HuffmanNode right as parameter(HuffmanNode right)
   // post      : constructs a branch node with given frequency, left subtree,
   //             right subtree
   public HuffmanNode (int frequency, HuffmanNode left, HuffmanNode right) {
      this.frequency = frequency;
      this.left = left;
      this.right = right;
   }
   
   // Parameters: takes an integer of frequency(int frequency)
   //             takes an integer of ascii value(int ascii)
   // post      : constructs a leaf node with given frequency and ascii value
   public HuffmanNode (int ascii, int frequency) {
      this.frequency = frequency;
      this.ascii = ascii;
   }
   
   // Parameters: takes another HuffmanNode as parameter(HuffmanNode other)
   // post      : compares the nodes by frequency
   public int compareTo(HuffmanNode other) {
      return frequency - other.frequency;
   }
}