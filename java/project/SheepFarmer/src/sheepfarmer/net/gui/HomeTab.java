package sheepfarmer.net.gui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import sheepfarmer.net.app.Notification;
import sheepfarmer.net.app.Singleton;
import sheepfarmer.net.client.SheepResponse;
import sheepfarmer.net.client.sheepdb;


public class HomeTab {

	private static ObservableList<String> notification;
	
	
	private ObservableList<String> getNotification(){
    	SheepResponse sr = null;
    	ObservableList<String> items =FXCollections.observableArrayList ();
		try {
			sr = sheepdb.getNotificationSheep(Singleton.getInstance().getToken(), null);
			ArrayList<Notification> temp = sr.getNotificationList();
			for (int i = 0; i < temp.size(); i++) {
				items.add(temp.get(i).getMsg());
			}
		} catch (Exception e) {
			System.out.println("ERR-client getNotifications in HomeTab");
		}
    	return items;
    			
    }
	
	public AnchorPane createView() {
		AnchorPane view = new AnchorPane();
		
		notification = getNotification();
		ListView<String> notifications = new ListView<String>();
		notifications.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth());
		notifications.setItems(notification);
		
		AnchorPane.setBottomAnchor(notifications, 0.0);
		view.getChildren().add(notifications);
		return view;
	}
}
