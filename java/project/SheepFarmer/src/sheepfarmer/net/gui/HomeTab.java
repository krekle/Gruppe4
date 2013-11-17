package sheepfarmer.net.gui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import sheepfarmer.net.app.Notification;
import sheepfarmer.net.app.Singleton;
import sheepfarmer.net.app.User;
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
		
		//Getting notifications from the server
		notification = getNotification();

		//Creating ListView and adding the notification set
		VBox bottom = new VBox();
		ListView<String> notifications = new ListView<String>();
		notifications.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth());
		notifications.setItems(notification);
		Label lb_noti = new Label("Notifications");
		lb_noti.setFont(new Font("Arial", 20));
		bottom.getChildren().addAll(lb_noti, notifications);
		AnchorPane.setBottomAnchor(bottom, 10.0);
		
		//Creating Profile Info
		SheepResponse sr = sheepdb.getMe(Singleton.getInstance().getToken());
		User me = sr.getUser();
		VBox userContent = new VBox();
		userContent.setSpacing(10.0);
		
		HBox name = new HBox();
		Label lb_name = new Label("Name     ");
		TextField txt_name = new TextField();
		txt_name.setText(me.getName());
		txt_name.setEditable(false);
		name.getChildren().addAll(lb_name, txt_name);
		userContent.getChildren().add(name);
		
		HBox email = new HBox();
		Label lb_email = new Label("Email      ");
		TextField txt_email = new TextField();
		txt_email.setText(me.getEmail());
		txt_email.setEditable(false);
		email.getChildren().addAll(lb_email, txt_email);
		userContent.getChildren().add(email);
		
		HBox phone = new HBox();
		Label lb_phone = new Label("Phone    ");
		TextField txt_phone = new TextField();
		txt_phone.setText(me.getPhone());
		txt_phone.setEditable(false);
		phone.getChildren().addAll(lb_phone, txt_phone);
		userContent.getChildren().add(phone);
		
		HBox address = new HBox();
		Label lb_address = new Label("Address ");
		TextField txt_address = new TextField();
		txt_address.setText(me.getAddress());
		txt_address.setEditable(false);
		address.getChildren().addAll(lb_address, txt_address);
		userContent.getChildren().add(address);
		
		HBox vara = new HBox();
		Label lb_vara = new Label("Vara       ");
		TextField txt_vara = new TextField();
		txt_vara.setEditable(false);
		txt_vara.setText(me.getVara());
		vara.getChildren().addAll(lb_vara, txt_vara);
		userContent.getChildren().add(vara);
		
		AnchorPane.setTopAnchor(userContent, 10.0);
		AnchorPane.setLeftAnchor(userContent, 10.0);
		
		view.getChildren().addAll(bottom, userContent);
		return view;
	}
}
