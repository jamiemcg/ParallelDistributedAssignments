import java.io.*;
import java.net.*;

/**
  * Thread for listening to multicast messages
  */
public class MulticastReceiver implements Runnable {
	private Thread t;

	public MulticastReceiver () {
		
	}

	public void run () {
		MulticastSocket socket = null;
		DatagramPacket inPacket = null;

		byte [] inBuff = new byte[256];

		try {
			socket = new MulticastSocket(8888);
			InetAddress address = InetAddress.getByName("224.2.2.3");
			socket.joinGroup(address);

			while(true) {
				inPacket = new DatagramPacket(inBuff, inBuff.length);
				socket.receive(inPacket);
				String message = new String(inBuff, 0, inPacket.getLength());
				System.out.println(message + " \t(" + inPacket.getAddress() + ")");
			}
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}

	public void start () {
		System.out.println("Starting Receiver Thread");
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}
}