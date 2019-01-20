//TOWSON UNIVERSITY
//MATH 314
//MICHAEL SANTORO, 11-30-2014
import java.util.*;
public class MATH_314_Project {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    int[] cipherText = new int[100000];     //used for the user enter sequence of numbers for ciphertext
    int[] plainTextNum = new int[100000];   //used to store the sequence of numbers after decryption 
    String plainText = "";                  //used for the plaintext in english character format
    int[] factors = new int[10];            //small array to store the 2 prime factors of n 
    int n;
    int e;
    int p;
    int q;
    
    
    //Input
    System.out.print("Enter your public key (n, e): ");
    n = getInt(input);
    e = getInt(input);
    System.out.println("Enter the cipher text as a sequence of numbers, (type \"break\" when finished entering ciphertext");
    int i = 0;
    String next;
    int temp;
    while(input.hasNext()) {                        //this will proccess each set of numbers and stop at each space...taking in as a string 
      next = input.next();                          //then turning into an integer that is pushed into an array
      if (next.equals("break"))
        break;                                   //i will stop when the user enters the word break...
      else {
        temp = Integer.parseInt(next); 
        cipherText[i] = temp;
        i++;
      }
    }      
    
    //Factoring n to get p and q
    factors = factor(n);            //will return an array and p and q will be the 2 elements in the array
    p = factors[0];
    q = factors[1];
    
    System.out.println("Your public key is: (n, e): " + "(" + n + ", " + e + ") ");
    System.out.println("N = " + p + " * " + q);
    
    
    //Extended Euclidian Algorithm to find the multiplicative inverse of e (mod (p-1)(q-1)) to get the exponent D     
    int[] extended = multInverse(e, (p-1) * (q-1));  
    int d = extended[1];                                                            // D will be the 2nd value in this array
    System.out.println("After using the Extended Euclidian Algorithm, d = " + d);
    System.out.println("The private key is: (" + d + ", " + p + ", " + q + ")");
    
    
    //Modular Exponentiation Method to decrypt the ciphertext....
    System.out.println("Using repeated squaring to solve for m = c^d (mod n) for each sequence of numbers entered");
    for (int k = 0; k < i; k++) {                             //set k = to i because i is a count for how many sequences of text were entered
      plainTextNum[k] = modularExpo(cipherText[k], d, n);
    } 
    System.out.println("\nThe plaintext in numerical form is: ");
    for (int k = 0; k < i; k++) { 
    System.out.print(plainTextNum[k] + " ");
    if ((k % 10 == 0) && (k != 0))                //used to format the text in a more legible manner 
      System.out.println();
    }
    System.out.println();
    
    
    //Using numToText method to turn the numerical plaintext into the corresponding 3 letter sequences 
    for (int k = 0; k < i; k++) {
      plainText = (plainText + numToText(plainTextNum[k]));
      if ((k % 20 == 0) && (k != 0))                          //used to format the text in a more legible manner
        plainText = plainText + "\n";
    }
    
    //Final printing of the plaintext in characters 
    System.out.println("\nThe plaintext is : ");
    System.out.println(plainText);   
    
    
    
  } //Ends main
  
  //******************************************************************************
  
  //input validation 
  public static int getInt(Scanner input) {
    int num;
    while(!input.hasNextInt()) {
      input.next();
      System.out.print("Invalid!, enter an integer");
    }
    num = input.nextInt();
    return num;    
  }
  
  //factoring
  public static int[] factor(int num) {
    int[] factors = new int[10];
    int j = 0;
    for (int i = 2; i < num; i++) {   //i is used to test all values starting at the smallest prime to the given number to factor
      if (num % i == 0) {           //if i is a factor we store it in the array
        factors[j] = i;            //j moves only when a factor is found, it keeps the index of the array
        j++;
      }
    }
    return factors;
  }
  
  //Euclidian Algorithm 
  public static int[] multInverse(int num, int mod) { 
      int[] inverse = new int[3];
      int division;
      
      if (mod == 0)  {     //base case...
        inverse[0] = num;
        inverse[1] = 1;
        inverse[2] = 0;
      }
      else {
        division = num / mod;
        inverse = multInverse(mod, num % mod);    //we call the method again passing the new numbers 
        
        int store = inverse[1] - inverse[2] * division;           //this is the extended part where we go up back through the equations 
        
        inverse[1] = inverse[2];   //the 2nd value in the array will be the value for d          
        inverse[2] = store;
      }
      
      return inverse;
  }
  
  
  //Modular Exponentiation...repeated squaring method
  public static int modularExpo(int cipherText, int d, int modulus) {
    int recursive;
    int ans;
    
    //cipherText ^ D (mod modulus)
    if (d == 0)              //if the exponent is 0, no matter what the answer will be 1...this is our base case
      return 1;
    
    recursive  = modularExpo(cipherText, d / 2, modulus);  //will continue to to call the method but changing the exponent by dividing it by 2
    ans = (recursive * recursive) % modulus;
    
    if (d % 2 == 1)                       //if the exponent is prime
      ans = (ans * cipherText) % modulus;         //simply multiply by the derived number and take the modulus
    
    
    return ans;
  }
  
  //Numbers to text 
  public static String numToText(int plainText) {
    int x;
    int y;
    int z;
    int remainder;
    String plain = "";
      
    x = (plainText / (26 *26));
    remainder = (plainText % (26 * 26));
    y = (remainder / 26);
    remainder = remainder % 26;
    z = remainder; 
    
    //plainText = (x * (26 * 26)) + (y * 26) + z;
    //knowing this i wll divide by 26^2...keep the remainder and divide by 26, then keep the remainder...
    //these 3 numbers will be the 3 corresponding letters
    
    plain = plain + numText(x) + numText(y) + numText(z);  //calling numText to get the corresponding letter and build the string
    return plain;
    
  }
  
  //helper method to turn number to letters once the numToText method has properly evaluated the numbers
  public static String numText(int num) {        
    if (num == 0)
      return "A";
    else if (num == 1)
      return "B";
    else if (num == 2)
      return "C";
    else if (num == 3)
      return "D";
    else if (num == 4)
      return "E";
    else if (num == 5)
      return "F";
    else if (num == 6)
      return "G";
    else if (num == 7)
      return "H";
    else if (num == 8)
      return "I";
    else if (num == 9)
      return "J";
    else if (num == 10)
      return "K";
    else if (num == 11)
      return "L";
    else if (num == 12)
      return "M";
    else if (num == 13)
      return "N";
    else if (num == 14)
      return "O";
    else if (num == 15)
      return "P";
    else if (num == 16)
      return "Q";
    else if (num == 17)
      return "R";
    else if (num == 18)
      return "S";
    else if (num == 19)
      return "T";
    else if (num == 20)
      return "U";
    else if (num == 21)
      return "V";
    else if (num == 22)
      return "W";
    else if (num == 23)
      return "X";
    else if (num == 24)
      return "Y";
    else
      return "Z";
  }
  //*******************************************************************************
  
} // Ends Class


