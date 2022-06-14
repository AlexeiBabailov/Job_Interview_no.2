import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Entities_CreatorController extends Thread {

	private static final int nameMaxLength = 30;	//default max name length, can easily be changed
	private static final int mapWidth = 1272;	//default world map width, can easily be changed accordingly to the image itself
	private static final int mapHeight = 666;	//default world map Height, can easily be changed accordingly to the image itself
	private ArrayList<PointOfInterest> points = new ArrayList<PointOfInterest>(0);	//memory of all points, for further use
	private final static String ip = "127.0.0.1";	//default IP, can be easily changed to be an as an user input
	private final static Integer port = 7777;	//default port, can be easily changed to be an as an user input
	private boolean serverConnected = false;
	Server myServer;


	@FXML
	public void initialize() { 
		points = new ArrayList<>();
		serverConnected = false;
	}

	@FXML
	private TextField textName;

	@FXML
	private TextField textX;

	@FXML
	private TextField textY;

	@FXML
	private Button Create_Server;

	@FXML
	private Button sendBtn;

	@FXML
	private Button CloseBtn;

	//creates server socket which will use us to connect to our clients and send them our created POI
	@FXML
	void createServerBtn_pressed(ActionEvent event) {
		if (serverConnected == false) {
			new Server(this).start();
			serverConnected = true;
		}
	}


	@FXML
	void btnPressed(ActionEvent event) {
		try {	//try-catch in case non Double values entered to coordinate text box
			Double cor_X = Double.valueOf(textX.getText());
			Double cor_Y = Double.valueOf(textY.getText()); 
			PointOfInterest p = new PointOfInterest(textName.getText(),cor_X,cor_Y);
			if (cor_X < 0 || cor_X > mapWidth) {	//X coordinate bounds
				JOptionPane.showMessageDialog(null, "X coordinate out of bounds, please try again");
				textX.clear();
				return;
			}
			if (cor_Y < 0 || cor_Y > mapHeight) {	//Y coordinate bounds
				JOptionPane.showMessageDialog(null, "Y coordinate out of bounds, please try again");
				textY.clear();
				return;
			}
			if (textName.getText().length() > nameMaxLength || textName.getText().length() == 0) {	//name length limits
				JOptionPane.showMessageDialog(null, "Name error, you didnt enter a name or try name shorter than " + nameMaxLength + " letters");
				textName.clear();
				return;
			}
			addNewPoint(p);
		} catch(Exception e){
			JOptionPane.showMessageDialog(null, "you didnt enter proper Values, please try again");
			return;
		}	
	}

	//adding new POI to the memory ArrayList of points and broadcast it to all current connected clients
	public void addNewPoint(PointOfInterest p) {
		points.add(p);
		try {
			Server.broadcastPointToConnectedSockets(p);
		} catch (IOException e) { 
			System.out.println("didnt succeed to broadcast this point to some/all map clients");
		}
	}

	//terminates the creator application and server as well, map presenters will keep presenting their POI
	@FXML
	void CloseBtn_pressed(ActionEvent event) {
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close(); 
		System.exit(0);	
	}
}





