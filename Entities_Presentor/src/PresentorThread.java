import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PresentorThread extends Thread {

	private	 Socket socket = null;
	private Entities_PresentorController presentor;
	ObjectInputStream inStream;
	ObjectOutputStream outStream;
	PointOfInterest point;

	//constructor of ServerThread, establishing the connection via serverSocket
	public PresentorThread(Entities_PresentorController presentor, Socket socket) {
		this.presentor = presentor;
		this.socket = socket;
		try {
			inStream = new ObjectInputStream(socket.getInputStream());
			outStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("has an exception on establishing link, check socket status or presentor server");
		}
	}


	public void run() {
		boolean userConnected = true; 
		try {
			while(userConnected){ //will change only if application will be closed, and system then will be shut down
				point = (PointOfInterest)inStream.readObject();
				presentor.setPointOfInterest(point);	//sets new POI
			}
		} catch (Exception e) {
			System.out.println("server or presentor socket shuted down - system is disconnected, please restart");
		}
		//while loop ended - streams and socket closing sequence 
		try {
			outStream.close();
			inStream.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("application connection closed");
		}
	}
}


