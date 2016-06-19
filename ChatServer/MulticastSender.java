import java.io.*;
import java.net.*;
import java.util.*;

/**
  * Thread for sending multicast messages
  */
public class MulticastSender implements Runnable {
	private Thread t;
	private String name;
	private final int PORT = 8888;

	public MulticastSender(String name) {
		// The username of the user
		this.name = name;
	}

	public void run() {
		DatagramSocket socket = null;
		DatagramPacket outPacket = null;
		byte [] outBuff;

		Scanner scan = new Scanner(System.in);

		try {
			socket = new DatagramSocket();
			String message;

			while (true) {
				// Accept messages from the user
				message = scan.nextLine();
				if(message.length() > 0) {
					message = name + ": " + message;
					outBuff = message.getBytes();
	
					InetAddress address = InetAddress.getByName("224.2.2.3");
					outPacket = new DatagramPacket(outBuff, outBuff.length, address, PORT);
					socket.send(outPacket);
				}
			}
		} catch(IOException e) {
			System.out.println(e);
		}
	}

	public void start () {
		System.out.println("Starting Sender Thread");
		if (t == null) {
			t = new Thread(this);
			t.start();
		}

		String message = "'" + name + "' has entered the chat server";
		byte [] outBuff = message.getBytes();
	
		try {
			InetAddress address = InetAddress.getByName("224.2.2.3");
			DatagramPacket outPacket = new DatagramPacket(outBuff, outBuff.length, address, PORT);
			DatagramSocket socket = new DatagramSocket();
			socket.send(outPacket);
		} catch(IOException e) {
			System.out.println(e);
		}
	}
}