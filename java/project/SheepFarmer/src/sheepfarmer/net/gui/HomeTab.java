package sheepfarmer.net.gui;

import java.util.ArrayList;

import sheepfarmer.net.app.Notification;
import sheepfarmer.net.app.Singleton;
import sheepfarmer.net.app.User;
import sheepfarmer.net.client.SheepResponse;
import sheepfarmer.net.client.sheepdb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;

/**This Class handles all the GUI and actions for the homeTab
 * 
 * @author krekle
 *
 */
public class HomeTab {

	private static ObservableList<String> notification;

	private static ObservableList<String> chat;
	private static ImageView logo;
	private User me;

	/**
	 * 
	 * @return
	 * List of notifications
	 */
	private ObservableList<String> getNotification() {
		SheepResponse sr = null;
		ObservableList<String> items = FXCollections.observableArrayList();
		try {
			sr = sheepdb.getNotificationSheep(Singleton.getInstance()
					.getToken(), null);
			ArrayList<Notification> temp = sr.getNotificationList();
			for (int i = 0; i < temp.size(); i++) {
				items.add(temp.get(i).getMsg());
			}
		} catch (Exception e) {
			System.out.println("ERR-client getNotifications in HomeTab");
		}
		return items;

	}
/**
 * 
 * @return
 * the view which is under the tab
 */
	public BorderPane createView() {
		// AnchorPane view = new AnchorPane();
		logo = new ImageView(new Image("/thislogo.png"));
		logo.setFitHeight(Screen.getPrimary().getVisualBounds().getWidth() * 0.4);
		logo.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.5);
		BorderPane pane = new BorderPane();
		pane.setCenter(logo);

		// Getting user from server
		SheepResponse sr = sheepdb.getMe(Singleton.getInstance().getToken());
		me = sr.getUser();
		sr = sheepdb.getChat();

		// Getting notifications from the server
		notification = getNotification();
		chat = sr.getChatList();

		// Creating top
		VBox top = new VBox();
		top.setSpacing(10);
		Label lb_info = new Label("Welcome to SheepFarmer 3000");
		lb_info.setFont(new Font("Arial", 20));
		lb_info.setAlignment(Pos.CENTER);
		lb_info.setLayoutX((Screen.getPrimary().getVisualBounds().getWidth() * 0.5));
		Label lb_user = new Label("You are logged in as " + me.getName());
		top.getChildren().addAll(lb_info, lb_user);

		// Creating Vbox for usercontent to left
		VBox userContent = userContent(me);
		VBox user = new VBox();
		user.setSpacing(15);
		user.getChildren().addAll(lb_user, userContent);
		userContent.setPadding(new Insets(10));
		// Creating listview for chat in the center:
		VBox chatBox = new VBox();
		Label lb_chat = new Label("SheepChat");
		lb_chat.setFont(new Font("Arial", 20));
		ListView<String> chatList = new ListView<String>();
		chatList.setItems(chat);
		chatList.scrollTo(0);
		final TextField txt_chat = new TextField();
		txt_chat.setPrefWidth(chatList.getPrefWidth());

		
		txt_chat.setOnKeyReleased(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent arg0) {
				if (arg0.getCode().equals(KeyCode.ENTER)) {
					if (txt_chat.getText().toString() != null
							&& !txt_chat.getText().toString()
									.equalsIgnoreCase("")) {
						sheepdb.addChatMessage(txt_chat.getText().toString());
						chat.add(me.getName() + System.getProperty("line.separator") + txt_chat.getText());
						txt_chat.setText("");
					}
				}
			}
		});

		chatBox.getChildren().addAll(lb_chat, chatList, txt_chat);
		chatBox.setSpacing(10);
		pane.setRight(chatBox);

		// Creating ListView and adding the notification set for bottom
		VBox bottom = new VBox();
		ListView<String> notifications = new ListView<String>();
		notifications.setPrefWidth(Screen.getPrimary().getVisualBounds()
				.getWidth());
		notifications.setPrefHeight(Screen.getPrimary().getVisualBounds()
				.getHeight() * 0.25);
		notifications.setItems(notification);
		Label lb_noti = new Label("Notifications");
		lb_noti.setFont(new Font("Arial", 20));
		bottom.getChildren().addAll(lb_noti, notifications);

		pane.setTop(lb_info);
		pane.setBottom(bottom);
		pane.setLeft(user);
		pane.setStyle("-fx-box-border: transparent;");
		return pane;
	}

	/**Creates the element containing User information
	 * 
	 * @param me
	 * @return
	 * A VBox containing User information
	 */
	private VBox userContent(User me) {
		final VBox userContent = new VBox();
		userContent.setSpacing(2.0);

		Label lb_name = new Label("Name");
		final TextField txt_name = new TextField();
		txt_name.setText(me.getName());
		txt_name.setEditable(false);
		lb_name.setLabelFor(txt_name);

		Label lb_email = new Label("Email");
		final TextField txt_email = new TextField();
		txt_email.setText(me.getEmail());
		txt_email.setEditable(false);
		lb_email.setLabelFor(txt_email);

		Label lb_phone = new Label("Phone");
		final TextField txt_phone = new TextField();
		txt_phone.setText(me.getPhone());
		txt_phone.setEditable(false);
		lb_phone.setLabelFor(txt_phone);

		Label lb_address = new Label("Address");
		final TextField txt_address = new TextField();
		txt_address.setText(me.getAddress());
		txt_address.setEditable(false);
		lb_address.setLabelFor(txt_address);

		Label lb_vara = new Label("Vara");
		final TextField txt_vara = new TextField();
		txt_vara.setEditable(false);
		txt_vara.setText(me.getVara());
		lb_vara.setLabelFor(txt_vara);
		
		final Button btn_edit = new Button("Save");
		btn_edit.setVisible(false);
		
		final Hyperlink hl_edit = new Hyperlink("Edit..");
		hl_edit.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				if (hl_edit.getText().toString().equals("Edit..")){
					txt_address.setEditable(true);
					txt_name.setEditable(true);
					txt_vara.setEditable(true);
					txt_phone.setEditable(true);
					txt_email.setEditable(true);
					btn_edit.setVisible(true);
					hl_edit.setText("Cancel edit.");
				}else {
					txt_address.setEditable(false);
					txt_name.setEditable(false);
					txt_vara.setEditable(false);
					txt_phone.setEditable(false);
					txt_email.setEditable(false);
					btn_edit.setVisible(false);
					hl_edit.setText("Edit..");
				}
				
				
			}
		});
		
		btn_edit.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				sheepdb.editUser(txt_name.getText().toString(), txt_email.getText().toString(), txt_vara.getText().toString(), txt_phone.getText().toString(), txt_address.getText().toString());
				txt_address.setEditable(false);
				txt_name.setEditable(false);
				txt_vara.setEditable(false);
				txt_phone.setEditable(false);
				txt_email.setEditable(false);
				btn_edit.setVisible(false);
				hl_edit.setText("Edit..");
			}
		});
		
		userContent.getChildren().addAll(lb_name, txt_name, lb_email,
				txt_email, lb_address, txt_address, lb_phone, txt_phone,
				lb_vara, txt_vara, hl_edit,btn_edit);
		return userContent;
	}
}
