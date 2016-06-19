import java.util.*;

/**
  * Solution to CS402 Assignment 2. A multicast chat server. 
  * @author Jamie McGowan
  */

public class ChatServer {
	public static void main(String [] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a username: ");
		String name = scan.nextLine();
		System.out.println();

		// Create and start the treads for receiving and sending multicast messages
		MulticastReceiver receiver = new MulticastReceiver();
		receiver.start();

		MulticastSender sender = new MulticastSender(name);
		sender.start();		
	}
}