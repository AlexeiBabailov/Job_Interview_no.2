import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Entities_Presentor extends Application{

	public void start(Stage stage) throws Exception{ 
		Parent root1 = (Parent) FXMLLoader.load(getClass().getResource("Entities_Presentor.fxml")); 
		Scene scene1 = new Scene(root1);
		Stage stage1 = new Stage();
		stage1.setTitle("Entities_Presentor"); 
		stage1.setScene(scene1); 
		stage1.show();
	}

	public static void main(String[] args) { 
		launch(args);
		System.out.println();
		
	} 
}
