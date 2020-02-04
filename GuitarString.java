// Cynthia Wu
// CSE 143 AO with Batina Shikhalieva
// Homework 2
// 01/22/2020
// The class GuitarString simulates a vibrating guitar string with a
// given frequency and is used to keep track of a ring buffer

import java.util.*;

public class GuitarString {
   private Queue<Double> ringBuffer;
   
   public static final double ENERGY_DECAY = 0.996; // the energy decay factor
   
   // Pre : frequency must be greater than 0 and resulting size of the
   //      ring buffer would be greater or equal to 2
   //      (throws an IllegalArgumentException if not)
   // Post: equeueing 0s to initialize the ring buffer and represent a
   //       a guitar string at rest
   public GuitarString(double frequency) {
      ringBuffer = new LinkedList<Double>();
      int capacity = (int)Math.round(StdAudio.SAMPLE_RATE / frequency);
      for (int i = capacity; i > 0; i--) {
         ringBuffer.add(0.0);
      }
      if (frequency <= 0 || ringBuffer.size() < 2) {
         throw new IllegalArgumentException();
      }
   }
   
   // Pre : the length of the array should be greater or equal to 2
   //       (throws an IllgalArgumentException if not)
   // Post: initialize the contents of ring buffer to all the values
   //       in the given buffer
   public GuitarString(double[] init) {
      ringBuffer = new LinkedList<Double>();
      if (init.length < 2) {
         throw new IllegalArgumentException();
      }
      for (double initial: init) {
         ringBuffer.add(initial);
      }
   }
   
   // Post: changes the values in the buffer to random values
   //       between -0.5 inclusive and +0.5 exclusive to fill
   //       ring buffer with white noise
   public void pluck() {
      Random r = new Random();
      for (int i = ringBuffer.size(); i > 0; i--) {
         double element = r.nextDouble() - 0.5;
         ringBuffer.remove();
         ringBuffer.add(element);
      }
   }
   
   // Post: applies the karplus-Strong alogorithm to the ring
   //       buffer
   public void tic() {
      double delete = ringBuffer.remove();
      ringBuffer.add(0.5 * (delete + ringBuffer.peek()) * ENERGY_DECAY);
   }
   
   // Post: returns the value at the front of the ring buffer
   public double sample() {
      return ringBuffer.peek();
   }
}
