import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocket extends Thread {

	private Server server;
	private Socket socket;
	ObjectOutputStream objectOutputStream;
	ObjectInputStream objectIntputStream; 
	PointOfInterest point;

	//creates thread to handle this client communication
	public ClientSocket(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectIntputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("has an exception on establishing link");
		}
	}

	public void run() {
		server.addClient(this); //update Server's client list
		try {
			while (socket.isConnected()) { //this socket will run until the client map presenter socket will close or the creator server shut down 
				point = (PointOfInterest)objectIntputStream.readObject();
			}
		}catch (IOException | ClassNotFoundException e) {
			server.removeClient(this); //this client has been disconnected or socket closed
			CloseSequence();	
		} 
	}

	//uses to give the server access to broadcast POI
	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public void CloseSequence() {
		try {
			objectIntputStream.close();
			objectOutputStream.close();
			socket.close();
		}catch (IOException e) {
			System.out.println("client thread closed");
		} 
	}


}
