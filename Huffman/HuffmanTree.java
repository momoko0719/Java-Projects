// Cynthia Wu
// CSE 143 AO with Batina Shikhalieva
// Homework 8
// The class HuffmanTree is used to compress the text files by using
// the coding scheme based on the frequency of characters as well as
// expand the compressed binary file to the original.

import java.util.*;
import java.io.*;

public class HuffmanTree {
   
   private HuffmanNode parent;
   
   // Parameters: takes a given array of frequencies (int[] count)
   //             values of count are frequencies(only non zeros values included)
   //             indices of count are character values
   // Post      : the constructor initializes the Huffman tree by passing the given
   //             array of frequencies
   public HuffmanTree(int[] count) {
      Queue<HuffmanNode> tree = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < count.length; i++) {
         if (count[i] > 0) {
            tree.add(new HuffmanNode(i, count[i]));
         }
      }
      tree.add(new HuffmanNode(256, 1));
      while (tree.size() > 1) {
         HuffmanNode left = tree.remove();
         HuffmanNode right = tree.remove();
         tree.add(new HuffmanNode(left.frequency + right.frequency, left, right));
      }
      parent = tree.remove();
   }
   
   // Parameters: reading an input file (Scanner input)
   //             the Scanner contains a tree in standard format
   // Post      : reconstruct the huffman tree by reading the input file
   public HuffmanTree(Scanner input) {
      parent = new HuffmanNode(-1, 0);
      HuffmanNode temp = parent;
      while (input.hasNextLine()){
         int n = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         for (int i = 0; i < code.length(); i++) {
            if (i == code.length() - 1) {
               temp = createNode(temp, i, code, n);
            } else {
               temp = createNode(temp, i, code, -1);
            }
         }
         temp = parent;
      }
   }
   
   // Parameters: takes a huffman node (HuffmanNode root)
   //             takes an integer of index (int index)
   //             takes a string of code (String code)
   //             takes an integer of character value (int n)
   // Post      : helper method to reconstruct the huffman tree
   private HuffmanNode createNode(HuffmanNode root, int index, String code, int n) {
      if (root == null) {
         root = new HuffmanNode(-1, 0);
      }
      if (code.charAt(index) == '0') {
         if (root.left == null) {
            root.left = new HuffmanNode(n, 0);
         }
         return root.left;
      } else {
         if (root.right == null) {
            root.right = new HuffmanNode(n, 0);
         }
         return root.right;
      }
   }
   
   // Parameters: takes an output stream (PrintStream output)
   // Post      : write the tree to the given output stream in
   //             standard format
   public void write(PrintStream output) {
      writeHelper(output, parent, "");
   }
   
   // Parameters: takes a huffman node (HuffmanNode root)
   //             takes a string of code (String code)
   //             takes an output stream (PrintStream output)
   // Post      : helper method to write the tree to the given
   //             output stream in a standard format
   private void writeHelper(PrintStream output, HuffmanNode root, String current) {
      if (root != null) {
         if (root.left == null && root.right == null) {
            output.println(root.ascii);
            output.println(current);
         } else {
            writeHelper(output, root.left, current + "0");
            writeHelper(output, root.right, current + "1");
         }
      }
   }
   
   // Parameters: takes an output stream (PrintStream output)
   //             takes an input stream containing individual bits
   //             (BitInputStream input)
   //             takes an integer parameter of eof (int eof)
   // Post      : read the individual bits from the given input stream
   //             and write the corresponding characters to the output stream.
   //             When encountering a character whose value equals the given
   //             eof, it should stop reading
   public void decode(BitInputStream input, PrintStream output, int eof) {
      HuffmanNode temp = parent;
      while (temp.ascii != eof) {
         if (temp.left == null && temp.right == null) {
            output.write(temp.ascii);
            temp = parent;
         }
         int bit = input.readBit();
         if (bit == 0) {
            temp = temp.left;
         } else {
            temp = temp.right;
         }
      }
   }
}