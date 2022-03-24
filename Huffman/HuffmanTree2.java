// Cynthia Wu
// CSE 143 AO with Batina Shikhalieva
// Homework 8 Bonus
// The class HuffmanTree2 is used to make the Huffman program
// better by eliminating the code file, which uses the original
// HuffmanTree

import java.util.*;
import java.io.*;

public class HuffmanTree2 {
   
   private HuffmanNode parent;
   
   // Parameters: takes a given array of frequencies (int[] count)
   //             values of count are frequencies(only non zeros values included)             
   //             indices of count are character values
   // Post      : the constructor initializes the Huffman tree by passing the given
   //             array of frequencies               
   public HuffmanTree2(int[] count) {
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
  public HuffmanTree2(Scanner input) {
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
   private void writeHelper(PrintStream output, HuffmanNode node, String current) {
      if (node != null) {
         if (node.left == null && node.right == null) {
            output.println(node.ascii);
            output.println(current);
         } else {
            writeHelper(output, node.left, current + "0");
            writeHelper(output, node.right, current + "1");
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
  
  // Parameters: reading an input file containing individual bits
  //             (BitInputStream input)
  // Post      : construct the huffman tree by reading the input stream
   public HuffmanTree2(BitInputStream input) {
      parent = reCreate(input);
   }
   
  // Parameters: reading an input file containing individual bits
  //             (BitInputStream input)
  // Post      : helper method to construct the huffman tree by 
  //             reading the input stream
   private HuffmanNode reCreate(BitInputStream input){
      int bit = input.readBit();
      HuffmanNode temp = new HuffmanNode(-1, 0);
      if (bit == 0){
         temp.left = reCreate(input);
         temp.right = reCreate(input);
      } else {
         temp.ascii = read9(input);
      }
      return temp;
   }
   
   // Parameters: takes an array of string(String[] codes)
   //             the array has null values before called
   // Post      : assigns codes for each character of the tree
   //             and fills in the string for each character 
   //             in the tree indicating its code
   public void assign(String[] codes) {
		assignHelper(parent, "", codes);
   }
   
   // Parameters: takes an array of string(String[] codes)
   //             the array has null values before called
   //             takes a huffman node(HuffmanNode root)
   //             takes a string of s(String s)
   // Post      : helper method to assign codes for each character of the tree
	private void assignHelper(HuffmanNode root, String s, String[] codes) {
		if (root.left == null && root.right == null){
		   codes[root.ascii] = s;
		} else {
			assignHelper(root.left, s + "0", codes);
			assignHelper(root.right, s + "1", codes);
		}
	}
   
   // Parameters: takes an output stream to write bits(BitOutputStream output)
   // Post      : writes the tree to the output stream with standard bit
   //             representation
   public void writeHeader(BitOutputStream output) {
		headerHelper(parent, output);
   }
   
   // Parameters: takes an output stream to write bits(BitOutputStream output)
   //             takes a huffman node(HuffmanNode root)
   // Post      : helper method to write the tree to the output stream with standard bit
   //             representation
   private void headerHelper(HuffmanNode root, BitOutputStream output) {
		if (root.left == null && root.right == null){
			output.writeBit(1);
			write9(output, root.ascii);
		} else {
			output.writeBit(0);
			headerHelper(root.left, output);
			headerHelper(root.right, output);
		}
	}
   
   // pre : 0 <= n < 512
   // post: writes a 9-bit representation of n to the given output stream 
   private void write9(BitOutputStream output, int n) {
      for (int i = 0; i < 9; i++) { 
         output.writeBit(n % 2);
         n /= 2;
      }        
   }
   
   // pre : an integer n has been encoded using write9 or its equivalent 
   // post: reads 9 bits to reconstruct the original integer
   private int read9(BitInputStream input) {
      int multiplier = 1;
      int sum = 0;
      for (int i = 0; i < 9; i++) {
         sum += multiplier * input.readBit();
         multiplier *= 2; 
      }
      return sum; 
   }
}