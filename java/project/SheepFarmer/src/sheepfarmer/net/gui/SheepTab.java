package sheepfarmer.net.gui;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;
import sheepfarmer.net.app.Notification;
import sheepfarmer.net.app.Sheep;
import sheepfarmer.net.app.Sheep.Colour;
import sheepfarmer.net.app.Singleton;
import sheepfarmer.net.client.SheepResponse;
import sheepfarmer.net.client.sheepdb;
 
/**
 * Class for managing GUI and Actions for the SheepTab
 * @author krekle
 *
 */
public class SheepTab extends Application {
 
	  private static String[] imagelist = {
	    	"/Ali_Baa-Baa_Sheep-icon.png",
	    	"/Party_Clown_Sheep-icon.png",
	    	"/Road_Trip_Sheep-icon.png",
	    	"/Secretary_Sheep-icon.png",
	    	"/Sinclair_Sheep-icon.png"};
	  
	private static ObservableList<String> notification;
	private static ObservableList<Sheep> sheepdata = getsheepDetails();  
    private static ListView<String> sheepDetails;
    private static Label lb_Grid = new Label("Sheep");
    private static TextField txt_search;
    private static Image currentImage = new Image(imagelist[1]);
    private static ImageView img = new ImageView();
    private static Sheep currentSheep;
    private static Label lb_mood;
    
    private static ObservableList<String> details = FXCollections.observableArrayList(
			"ID: Unknown ", "Age: Unknown ", "Heartrate: unknown ",
			"Weight: unknown", "Respiration: unknown", "OwnerID: unknwon ",
			"Latitude: unknwon", "Longitude: unknown", "Name: unknown",
			"Gender: unknown", "Temperature: unknown", "Lifesatus: unknown");
    private VBox master;
    
    ///////Left - Right//////////////
    private SplitPane sp1;
    private VBox left;
    private VBox right;
    
    ///////// Left: label and table
    private Label lb_table;
    private TableView<Sheep> table;
    
    /**
     * Method for getting a list of Sheep from sheepdb
     * @return
     * A list of Sheep
     */
    private static ObservableList<Sheep> getsheepDetails(){
    	SheepResponse sr = null;
		try {
			sr = sheepdb.getSheep(Singleton.getInstance().getToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return FXCollections.observableArrayList(sr.getSheepList());
    			
    }
    /**
     * Method for getting a list of Sheep from sheepdb
     * @return
     * A list of String
     */
    private static ObservableList<String> getNotification(String sheepid){
    	SheepResponse sr = null;
    	ObservableList<String> items =FXCollections.observableArrayList ();
		try {
			sr = sheepdb.getNotificationSheep(Singleton.getInstance().getToken(), sheepid);
			ArrayList<Notification> temp = sr.getNotificationList();
			for (int i = 0; i < temp.size(); i++) {
				items.add(temp.get(i).getMsg());
			}
		} catch (Exception e) {
			System.out.println("ERR-client getNotifications in SheepTab");
		}
    	return items;
    			
    }
    
    /**
     * Creates the Left side of the screen
     */
    @SuppressWarnings("unchecked")
	private void createLeft(){
    	left = new VBox();
    	left.setPadding(new Insets(10));
    	//Generating content
    	lb_table = new Label("Sheeps: ");
        lb_table.setFont(new Font("Arial", 20));
        
        txt_search = new TextField();
        txt_search.setText("Search id/name");
        txt_search.setOnKeyReleased(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent arg0) {
				if(arg0.getCode().equals(KeyCode.ENTER)){
					ObservableList<Sheep> items =FXCollections.observableArrayList ();
					
					if(txt_search.getText().toString() != null && !txt_search.getText().toString().equalsIgnoreCase(new String("Search id/name"))){
						for (int i = 0; i < sheepdata.size(); i++) {
							String name = (String)sheepdata.get(i).getName().toString();
							String text = (String)txt_search.getText().toString();
							
							if(name.equalsIgnoreCase(text)){
								items.add(sheepdata.get(i));
							}
						}
						sheepdata = items;
						refresh();
					} else {
						sheepdata = getsheepDetails();
						refresh();
					}
					
				}
			}
        });
        
        HBox toleft = new HBox();
        toleft.setSpacing(((Screen.getPrimary().getVisualBounds().getWidth()*0.30)-lb_table.getWidth()-txt_search.getWidth()-5));
        toleft.getChildren().addAll(lb_table, txt_search);
        
        table = new TableView<Sheep>();

        TableColumn<Sheep, String> idCol = new TableColumn<Sheep, String>("Id");
        idCol.setCellValueFactory(
        	    new PropertyValueFactory<Sheep,String>("id")
        	);
        TableColumn<Sheep, String> nameCol = new TableColumn<Sheep, String>("Name");
        nameCol.setCellValueFactory(
        	    new PropertyValueFactory<Sheep,String>("name")
        	);
        TableColumn<Sheep, String> ageCol = new TableColumn<Sheep, String>("Age");
        ageCol.setCellValueFactory(
        	    new PropertyValueFactory<Sheep,String>("age")
        	);
        TableColumn<Sheep, String> hrCol = new TableColumn<Sheep, String>("Hearth Rate");
        hrCol.setCellValueFactory(
        	    new PropertyValueFactory<Sheep,String>("hr")
        	);
        TableColumn<Sheep, String> respCol = new TableColumn<Sheep, String>("Respiration");
        respCol.setCellValueFactory(
        	    new PropertyValueFactory<Sheep,String>("respiration")
        	);
        TableColumn<Sheep, String> tempCol = new TableColumn<Sheep, String>("Temperature");
        tempCol.setCellValueFactory(
        	    new PropertyValueFactory<Sheep,String>("temp")
        	);
        
        table.getColumns().clear();
        table.getColumns().addAll(idCol, nameCol, ageCol, hrCol, respCol, tempCol);
        table.setItems(sheepdata);
        table.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight()*0.80);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setOnMouseClicked(new EventHandler<Event>() {

			public void handle(Event arg0) {
				Sheep item = table.getSelectionModel().getSelectedItem();
				if(item != null){
					currentSheep = item;
					try{
						if(item.getCol() == Colour.BLACK) currentImage = new Image(imagelist[0]); 
						else if(item.getCol() == Colour.WHITE) currentImage = new Image(imagelist[1]);
						else if(item.getCol() == Colour.YELLOW) currentImage = new Image(imagelist[2]);
						else if(item.getCol() == Colour.GRAY) currentImage = new Image(imagelist[3]);
						else  currentImage = new Image(imagelist[4]);			
						img.setImage(currentImage);
					}catch(Exception e){
						System.out.println("Nullpointer from tableClick event image");
					}
				
					details.set(0, "Id: "+ item.getId());
					details.set(1, "Age: "+ item.getAge());
					details.set(2, "Heart-Rate: "+ item.getHr());
					details.set(3, "Weight: "+ item.getWeight());
					details.set(4, "Respiration: "+ item.getRespiration());
					details.set(5, "OwnerId: "+ item.getOwnerid());
					details.set(6, "Latitude: "+ String.valueOf(item.getLatitude()));
					details.set(7, "Longitude: "+ String.valueOf(item.getLongitude()));
					details.set(8, "Temperature "+ item.getTemp());
					details.set(9, "Status: "+ ((item.isDeadString())));
					details.set(10,	"Color: "+ String.valueOf(item.getCol()));
					details.set(11, "Gender: "+ item.getGen().toString());
					
					notification = getNotification(item.getId());
					lb_Grid.setText(String.valueOf(item.getName()));
					

					refresh();
				}
			}
		});        
        left.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight()-20);
        left.setMinWidth((Screen.getPrimary().getVisualBounds().getWidth()*0.50)-20);
        left.getChildren().addAll(toleft, table);
        left.setStyle("-fx-box-border: transparent;");
    }
    /**
     * Creates the right side of the screen
     */
    private void createRight(){
//      Splitting:
//     	__________
//     	|   |    |
//     	|   |____|
//     	|___|____|
        
        right = new VBox();
        right.setPadding(new Insets(10));
        right.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight()-20);
        right.setMinWidth((Screen.getPrimary().getVisualBounds().getWidth()*0.50) -20);
        
        SplitPane sp2 = new SplitPane();
        sp2.setOrientation(Orientation.VERTICAL);
        
        HBox up = new HBox();
        VBox upleft = new VBox();
        VBox upright = new VBox();
        
        up.setSpacing(50);
        lb_Grid.setFont(new Font("Arial", 20));
        sheepDetails = new ListView<String>(details);
        sheepDetails.setMaxSize(Screen.getPrimary().getVisualBounds().getHeight() * 0.50, Screen.getPrimary().getVisualBounds().getWidth() * 0.34);
        
        lb_mood = new Label("Mood: " + (currentSheep != null ? currentSheep.getMood().toString() : "unknown"));
        img.setImage(currentImage);
        
        upleft.getChildren().addAll(lb_Grid, sheepDetails);
        upright.getChildren().addAll(img, lb_mood);
        upright.setSpacing(20);
        up.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight()*0.50);
        up.getChildren().addAll(upleft, upright);
        
        VBox down = new VBox();
        down.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight()*0.50);
        
        Label not = new Label("Notifications");
        not.setFont(new Font("Arial", 20));
        
        ListView<String> notify = new ListView<String>();
        notify.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight()*0.25);
        notify.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth()*0.40);
        notify.setItems(notification);
        
        Button newSheep = new Button("New..");
        newSheep.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				sheepControl(null);
				
			}
		});
        Button editSheep = new Button("Edit..");
        editSheep.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				if(currentSheep != null) sheepControl(currentSheep);
			}
		});
        
        Button deleteSheep = new Button("Delete");
        deleteSheep.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				if(currentSheep != null){
					sheepdb.deleteSheep(Singleton.getInstance().getToken(), currentSheep.getId().toString());
					MessageBox.show(null,
							currentSheep.getName() + " is no more",
						    "Sheep deleted",
						    MessageBox.ICON_INFORMATION | MessageBox.OK | MessageBox.CANCEL);
							refresh();
				}	
				currentSheep = null;
			}});
        
        HBox btns = new HBox();
        btns.getChildren().addAll(newSheep, editSheep, deleteSheep);
        
        down.getChildren().addAll(not, notify, btns);
        
        sp2.getItems().addAll(up, down);
        sp2.setStyle("-fx-box-border: transparent;");
        right.getChildren().add(sp2);
    }
    
	public VBox createView() {
		
//         Splitting:
//        	__________
//        	|   |    |
//        	|   |    |
//        	|___|____|
        SplitPane sp1 = new SplitPane();
        createLeft();
        createRight();
        sp1.getItems().addAll(left, right);
        sp1.setStyle("-fx-box-border: transparent;");
        
        master = new VBox();
        master.setSpacing(5);
        master.getChildren().add(sp1);
 
        return master;
    }
	
	public void refresh(){
		sp1 = new SplitPane();
		createRight();
		createLeft();
		
		sp1.getItems().addAll(left, right);
		master.getChildren().clear();
		master.getChildren().add(sp1);
	}
    
	@Override
    public void start(Stage stage) {
		//Nothing
    }
	/**
	 * Creates the pop-up window for editing or adding a sheep
	 * @param s
	 * If a sheep s is sent the edit method is called from sheepdb then to the server
	 * If sheep s = null a the new sheep method is called from sheepdb then to the server
	 */
	private void sheepControl(final Sheep s){
		Stage stage = new Stage();
		VBox addOrEdit = new VBox();
		
		Label lb_addOrEdit = new Label((s == null) ? "Add Sheep":"Edit Sheep");
		lb_addOrEdit.setFont(new Font("Arial", 20));
		final TextField txt_name = new TextField((s == null)? "Name": s.getName());
		final TextField txt_age = new TextField((s == null)? "Age": s.getAge());
		final TextField txt_gender = new TextField((s == null)? "Gender": s.getGen().toString());
		final TextField txt_weight = new TextField((s == null)? "Weight": s.getWeight());
		
		
		HBox btns = new HBox();
		Button btn_done = new Button((s == null)?"Add Sheep":"Save");
		btn_done.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if(s==null){
					sheepdb.addSheep(txt_name.getText(), txt_age.getText(), txt_gender.getText(), txt_weight.getText(), Singleton.getInstance().getToken());
				}else {
					sheepdb.editSheep(s.getId().toString(), txt_name.getText().toString(), txt_weight.getText().toString(), txt_gender.getText().toLowerCase().toString(), txt_age.getText().toString());
				}
				
				Button  btn = (Button)  arg0.getSource();
				Node source = (Node) btn.getParent();
			    Stage stage  = (Stage) source.getScene().getWindow();
			    sheepdata = getsheepDetails();
			    refresh();
			    stage.close();
			}});
		Button btn_cancel = new Button("Cancel");
		btn_cancel.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				Button  btn = (Button)  arg0.getSource();
				Node source = (Node) btn.getParent();
			    Stage stage  = (Stage) source.getScene().getWindow();
			    stage.close();
			}});
		
		btns.getChildren().addAll(btn_done, btn_cancel);
		
		addOrEdit.setSpacing(10.0);
		addOrEdit.getChildren().addAll(lb_addOrEdit, new Label("Name"), txt_name, new Label("Age"), txt_age, new Label("Gender"), txt_gender, new Label("Weight"), txt_weight, btns);
		
		Scene scene = new Scene(addOrEdit, 400, 300, Color.rgb(50, 50, 50));
		scene.getStylesheets().add("myStyle.css");
		stage.setScene(scene);
		stage.show();
	}
		
		
}