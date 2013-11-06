package sheepfarmer.net.gui;

import sheepfarmer.net.app.CallListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainViewController extends Application implements CallListener{

	private Stage currentStage;
	private Scene currentScene;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("SheepFarmer");
		Login login = new Login(this);
		VBox root = login.createView();
		setScene(root, 0, 0);
		stage.setScene(currentScene);
		setStage(stage);
	}
	
	public void setScene(VBox vb, int x, int y){
		currentScene = new Scene(vb,((x != 0) ? x:400),((y != 0) ? y:400));
		currentScene.getStylesheets().add("myStyle.css");
	}
	
	public void setStage(Stage stage){
		if(currentStage != null){
			currentStage.close();
		}
		currentStage = stage;
		currentStage.show();
	}
	
	public void loginCallBack(boolean log) {
		System.out.println("At the callback");
		//Login success, Open the main application.
		TabsControl main = new TabsControl();
		System.out.println("THis is okay!");
		Scene scene = main.createView();
		System.out.println("Got scene!");
		Stage stage = new Stage();
		System.out.println("Got stage!");
		stage.setScene(scene);
		setStage(stage);
	}

	public void sheepTabCallBack(boolean refresh) {
		// TODO Auto-generated method stub
		
	}
}
