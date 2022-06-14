import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server  extends Thread{

	public static ArrayList<ClientSocket> clients;	//list of all Map Presenters clients, uses for broadcasting POI
	private Entities_CreatorController creator;
	ObjectOutputStream outStream;
	ClientSocket clientSocket;
	private static ServerSocket server;


	public Server(Entities_CreatorController creator) {
		this.creator = creator;
	}

	public void run() {
		ServerSocket server = null;
		try {
			server = new ServerSocket(7777);
		} catch (IOException e) {
			System.out.println("Server Socket didn't open");
		}
		clients = new ArrayList<>();
		Socket s = null;
		while(!server.isClosed()) {
			try {
				s = server.accept();
				new ClientSocket(this,s).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//add new clients to client list - use us to broadcast to all clients every new POI
	public static void addClient(ClientSocket client) {
		clients.add(client);
	}

	public static void removeClient(ClientSocket client) {
		clients.remove(client);
	}

	//Broadcast it to all current connected clients
	public static void broadcastPointToConnectedSockets(PointOfInterest p)throws IOException{
		for(ClientSocket client : clients) {
			client.getObjectOutputStream().writeObject(p);
			client.getObjectOutputStream().flush();
		}
	}

	public static void closeServer() {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
