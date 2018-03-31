package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {
		// COMPLETE THIS METHOD
		
		CardNode ptr;
		CardNode curr;
		CardNode prev;
		
		curr = deckRear.next;
		ptr = curr.next;
		prev = deckRear;
		
		//System.out.println("START JOKER A");
		//printList(deckRear);
		
		do{	//does it stop after we find 27 ?	Should I use a do-while loop? 
			if(curr.cardValue == 27){
				
				curr.next = ptr.next;
				prev.next = ptr;
				ptr.next = curr;
				
				if(curr == deckRear){
					deckRear = ptr;
				}
				break;
			}
			prev = curr;
			curr = ptr;
			ptr = ptr.next;
		}while(curr != deckRear.next );
		
		//System.out.println("END JOKER A");
		//printList(deckRear);


	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {
	    // COMPLETE THIS METHOD
		
		CardNode ptr;
		CardNode curr;
		CardNode prev;
		
		curr = deckRear.next;
		prev = deckRear;
		ptr = curr.next.next;
		
		//System.out.println("START JOKER B");
		//printList(deckRear);


		do{
			if(curr.cardValue == 28){
				prev.next = curr.next;
				curr.next = ptr.next;
				ptr.next = curr;
				
				if(curr == deckRear){
					deckRear = curr.next;
				}
				break;
			}
			prev = curr;
			curr = curr.next;
			ptr = ptr.next;
			
		}while(curr != deckRear.next);
		
		//System.out.println("END JOKER B");
		//printList(deckRear);		
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		// COMPLETE THIS METHOD
		
		CardNode prev;
		CardNode curr;
		CardNode ptr;
		
		prev = deckRear;
		curr = deckRear.next;
		ptr = curr.next;
		
		
		//System.out.println("START TRIPLE CUT");
		//printList(deckRear);	
		
		while(curr.cardValue != 27 && curr.cardValue != 28 ){
			
			prev = curr;
			curr = ptr;
			ptr = ptr.next;
				
		}
					
				while(ptr.cardValue != 28 && ptr.cardValue != 27 ){  //Not Sure what I should use here. || or && ???
					ptr = ptr.next;
				}
				
				if(curr == deckRear.next){	// If there are no cards before the first joker, second joker becomes the last one
					
					deckRear = ptr;
					//System.out.println("END TRIPLE CUT");
					//printList(deckRear);
					
					return;//break;
				}
				if(ptr == deckRear){				//What if the jokers are next to each other???		
					deckRear = prev;
					
					//System.out.println("END TRIPLE CUT");
					//printList(deckRear);
					
					return;
				}
				
				prev.next = ptr.next;

				ptr.next = deckRear.next;
				deckRear.next = curr;
				deckRear = prev;
				
				//System.out.println("END TRIPLE CUT");
				//printList(deckRear);
				
				return;
			}
			
			
		
	
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {		
		// COMPLETE THIS METHOD
	
		//System.out.println("START COUNT CUT");
		//printList(deckRear);
			
		int l = deckRear.cardValue;
		
		CardNode curr = deckRear.next;
		CardNode ptr = deckRear.next;
		
		while(ptr.next != deckRear){
			ptr = ptr.next;
		}
		
		if(l == 28 || l == 27){ 	
			l = 27;
		}
		
			//I don't know about this 
			
		for(int count = 1; count < l; count++){	//should be < l DOUBLE CHECK
			curr = curr.next;
		}
		ptr.next = deckRear.next;
		deckRear.next = curr.next;
		curr.next = deckRear;
		
		
		//System.out.println("END COUNT CUT");
		//printList(deckRear);
	
	}
	
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value 
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE

		CardNode curr = deckRear.next;
		
		int frontValue = deckRear.next.cardValue;
		int key = -1;
		
		if(frontValue == 28){
			frontValue = 27;
		}
		
		for (int count = 1; count < frontValue; count++){
			curr = curr.next;
		}
		while (curr.next.cardValue == 27 || curr.next.cardValue == 28){
			
			jokerA();
			jokerB();
			tripleCut();
			countCut();
			
			if(frontValue == 28){
				frontValue = 27;
			}
			
			frontValue = deckRear.next.cardValue;
			for (int count = 1; count < frontValue; count++){
				curr = curr.next;
			}
			
		}
		
		 key = curr.next.cardValue;
		
		//System.out.println("The key is: "+key);
		return key;
		
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {	
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		
		String output = "";
		

		String msg = message.replaceAll("[^a-zA-Z]","");
		
	
		
		for(int i = 0;i <msg.length();i++)
		{
			char letter = Character.toUpperCase(msg.charAt(i));
			//System.out.println(letter);
			int letterInt = letter-'A'+1;
			
			jokerA();
			jokerB();
			tripleCut();
			countCut();

			int key = getKey();
			int sum = letterInt + key;
			
			//System.out.println("Letter int is: "+letterInt);
			//System.out.println("The key is: "+key);
			//System.out.println("The sum is: "+sum);

			if(sum > 26)
			{
				sum = sum - 26;
			}
			
			letter = (char)(sum-1+'A');
			//System.out.println(letter);
			output = output + letter;
		}
		
	    return output;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		
		String output = "";
		
		String msg = message.replaceAll("[^a-zA-Z]","");

		for(int i = 0;i < msg.length();i++)
		{
			char letter = Character.toUpperCase(msg.charAt(i));
			int letterInt = letter-'A'+1;
			
			jokerA();
			jokerB();
			tripleCut();
			countCut();

			int key = getKey();
			int sum = letterInt - key;

			if(sum <= 0)
			{
				sum = sum + 26;
			}
			
			letter = (char)(sum-1+'A');
			output = output + letter;
		}
	    return output;
		
	}
}
