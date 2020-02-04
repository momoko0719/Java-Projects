// Cynthia Wu
// CSE 143 AO with Batina Shikhalieva
// Homework 2
// The class Guitar37 can be used to keep track of a guitar instrument
// containing 37 notes on the chromatic scale from 110Hz to 880Hz

public class Guitar37 implements Guitar {
   private GuitarString[] allString; // strings of GuitarString
   private int ticTimes;
   
   public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
   // the layout of the keyboard
   
   // Post: constructs an array of GuitarStrings with frequencies
   //       ranging from 110 to 880Hz
   public Guitar37() {
      ticTimes = 0;
      allString = new GuitarString[KEYBOARD.length()];
      for (int i = 0; i < KEYBOARD.length(); i++) {
         allString[i] = new GuitarString(440.0 * Math.pow(2, (i-24.0)/12.0));
      }
   }
   
   // Post: converts the pitch to specify which note to
   //       play and ignores illegal pitches
   public void playNote(int pitch) {
      if (pitch + 24 < KEYBOARD.length() && pitch + 24 >= 0) {
         allString[pitch + 24].pluck();
      }
   }
   
   // Post: checks and returns whether the given character has a
   //       corresponding string for this guitar
   public boolean hasString(char string) {
      return (KEYBOARD.indexOf(string) != -1);
   }
   
   // Pre : the given character must be in the Sting of keyboard
   //       (throws an IllegalArgumentException if not)
   // Post: plucks the given character in the keyboard string
   public void pluck(char string) {
      if(!hasString(string)){
         throw new IllegalArgumentException();
      }
      allString[KEYBOARD.indexOf(string)].pluck();
   }
   
   // Post: adds up all the samples from the strings of the guitar
   //       and returns the value of sum
   public double sample() {
      double allSample = 0.0;
      for (GuitarString string: allString) {
         allSample += string.sample();
      }
      return allSample;
   }
   
   // Post: moves the time forward with one tic per turn
   public void tic() {
      for (GuitarString string: allString) {
         string.tic();
      }
      ticTimes++;
   }
   
   // Post: returns the number of times that tic has been called
   public int time() {
      return ticTimes;
   }
}
