package sheepfarmer.net.gui;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
 /**Class for managing the "main" screen with tabs
  * 
  * @author krekle
  *
  */
public class TabsControl extends Application {
 
  public Scene createView() {
      Group root = new Group();
      Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
      
      TabPane tabPane = new TabPane();
      BorderPane mainPane = new BorderPane();
      
      //Create Tabs
      Tab tabA = new Tab();
      tabA.setClosable(false);
      tabA.setText("Home");
      HomeTab home = new HomeTab();
      tabA.setContent(home.createView());
      tabPane.getTabs().add(tabA);
      
      Tab tabB = new Tab();
      tabB.setClosable(false);
      tabB.setText("Sheep");
      SheepTab ma = new SheepTab();
      VBox ro = ma.createView();
      tabB.setContent(ro);
      tabPane.getTabs().add(tabB);
      
      final Tab tabC = new Tab();
      tabC.setClosable(false);
      tabC.setText("Map");
      MapView map = new MapView();
      tabC.setOnSelectionChanged(new EventHandler<Event>() {
		public void handle(Event arg0) {
			MapView map = new MapView();
			tabC.setContent(map.createMap());
		}
	});
      
      tabC.setContent(map.createMap());
      tabC.setDisable(false);
      tabPane.getTabs().add(tabC);
      
      mainPane.setCenter(tabPane);
      mainPane.prefHeightProperty().bind(scene.heightProperty());
      mainPane.prefWidthProperty().bind(scene.widthProperty());
      scene.getStylesheets().add("myStyle.css");
      
      root.getChildren().add(mainPane);
      return scene;
  }
  @Override
  public void start(Stage primaryStage) {
	  //Nothing.
  }
}