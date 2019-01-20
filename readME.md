# MATH 314 Project #1

## Description:

	For this project I decided to write a Java program to decrypt a ciphertext encrypted with a toy version of RSA. I began by first mapping out the methods needed for each step of the decryption, and testing them one at a time. I started by writing the method to factor n into p and q. I tested this method with multiple values of n, to make sure it was factoring correctly. My factoring method was essentially based on the fact that, a number modulus its prime factors should equal zero. So I used brute force to test each prime number up to the number to be factored, and stored the 2 prime factors into an array. I passed this array back to my main method and then split each of the 2 elements, storing them into integer variables p and q. 


	Now that I had p and q using factoring, I wrote the method for the Euclidean Algorithm to find the last piece of the private key, d. Because e · d = 1( mod (p − 1)(q − 1))...this means that d is the multiplicative inverse of e, so this is why we use the extended Euclidean Algorithm. My method passes the user entered value for e, and (p – 1)(q – 1) as parameters, and uses recursion. It passes the new parameters moving down the divisions and remainders until we get to zero. It returns an array of which the 2nd value is the value for d. At the end of the recursion the method will move back up updating the array and further evaluating the equations that were set up. 
	Once we have our full private key (d, p, q), we can proceed to process each sequence of numbers from the ciphertext, and decrypt them using m = c^d (mod n). We use the repeated squaring method to solve the modular exponentiation for our plaintext m. I again use a recursive method with a starting condition that if the exponent passed is 0, the answer will be 1 no matter what else is passed. Then we make recursive calls passing the exponent divided by 2, integer division. This value is evaluated on the way back up the recursion each time multiplying and reducing by our modulus. If our exponent is prime we multiply by the passed in ciphertext. This value is returned and stored in an array.  This array is filled with all the values for the plaintext in number form. 

	
	Now that we have an array with all the decrypted numerical values for the plaintext, the final step is returning these numerical values into characters in the same way as show in the example. To do this I used two methods. The first one, will take a plantext integer, and divide it by 26^2. Integer division will take over, and this quotient will be stored for the value of x. The remainder will be divided by 26, and stored as y, and the following remainder will be stored as z. These 3 number x,y,and z are the numbers that correspond to the letters of the alphabet A-Z, 0-25. So I used another method passing an integer and returning a string of the corresponding letter. I call this 3 times in my numToText method, then return the string of 3 letters. This is done for all entered integers, and this gives the desired plaintext in English. 

## Methods
public static int getInt(Scanner input)
public static int[] factor(int num)
public static int[] multInverse(int num, int mod)
public static int modularExpo(int cipherText, int d, int modulus)
public static String numToText(int plainText)
