package sheepfarmer.net.gui;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
 
public class SheepTab extends Application {
 
	  private static String[] imagelist = {
	    	"/Ali_Baa-Baa_Sheep-icon.png",
	    	"/Party_Clown_Sheep-icon.png",
	    	"/Road_Trip_Sheep-icon.png",
	    	"/Secretary_Sheep-icon.png",
	    	"/Sinclair_Sheep-icon.png"};
	
    private static ObservableList<String> notification;
    private static ObservableList<Sheep> sheepdata;
    private static ListView<String> sheepDetails;
    private static Label lb_Grid = new Label("Sheep");
    private static Image currentImage = new Image(imagelist[1]);
    private static ImageView img = new ImageView();
    private static Sheep currentSheep;
    
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
    
    private ObservableList<Sheep> getsheepDetails(){
    	SheepResponse sr = null;
		try {
			sr = sheepdb.getSheep(Singleton.getInstance().getToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return FXCollections.observableArrayList(sr.getSheepList());
    			
    }
    
    private ObservableList<String> getNotification(String sheepid){
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
    
    @SuppressWarnings("unchecked")
	private void createLeft(){
    	left = new VBox();
    	//Generating content
    	sheepdata = getsheepDetails();
    	
    	lb_table = new Label("Sheeps: ");
        lb_table.setFont(new Font("Arial", 20));
        
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
					details.set(9, "Status: "+ ((item.isDead())? "Alive":"Dead"));
					details.set(10,	"Color: "+ String.valueOf(item.getCol()));
					details.set(11, "Gender: "+ item.getGen().toString());
					
					notification = getNotification(item.getId());
					lb_Grid.setText(String.valueOf(item.getName()));
					

					refresh();
				}
			}
		});        
        left.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight()-20);
        left.setMinWidth((Screen.getPrimary().getVisualBounds().getWidth()*0.34)-20);
        left.getChildren().addAll(lb_table, table);
    }

    private void createRight(){
//      Splitting:
//     	__________
//     	|   |    |
//     	|   |____|
//     	|___|____|
        
        right = new VBox();
        right.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight()-20);
        right.setMinWidth((Screen.getPrimary().getVisualBounds().getWidth()*0.66) -20);
        
        SplitPane sp2 = new SplitPane();
        sp2.setOrientation(Orientation.VERTICAL);
        
        HBox up = new HBox();
        VBox upleft = new VBox();
        VBox upright = new VBox();
        
        up.setSpacing(100);
        lb_Grid.setFont(new Font("Arial", 20));
        sheepDetails = new ListView<String>(details);
        sheepDetails.setMaxSize(Screen.getPrimary().getVisualBounds().getHeight() * 0.50, Screen.getPrimary().getVisualBounds().getWidth() * 0.34);
        
        Label lb_mood = new Label("Mood: ");
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
        Button editSheep = new Button("Edit..");
        Button deleteSheep = new Button("Delete");
        
        deleteSheep.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				Sheep item = table.getSelectionModel().getSelectedItem();
				MessageBox.show(null,
						currentSheep.getName() + " is no more",
					    "Sheep deleted",
					     MessageBox.ICON_INFORMATION | MessageBox.OK | MessageBox.CANCEL);
				
			}
		});
        
        HBox btns = new HBox();
        btns.getChildren().addAll(newSheep, editSheep, deleteSheep);
        
        down.getChildren().addAll(not, notify, btns);
        
        sp2.getItems().addAll(up, down);
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
    	
    }
}