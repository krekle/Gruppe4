package sheepfarmer.net.gui;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//MiddleClass for connecting Login and MainTabs

public class SheepList extends Application{

	private TableView<String> list;
	private Label lb_sheep;
	public void start(Stage stage) throws Exception {
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TableView getList(){
		list = new TableView<String>();
		TableColumn<String, String> id = new TableColumn<String, String>("id");
		TableColumn<String, String> name = new TableColumn<String, String>("name");
		TableColumn<String, String> lat = new TableColumn<String, String>("lat");
		TableColumn<String, String> lng = new TableColumn<String, String>("lng");
		list.setMaxWidth(300);
		list.getColumns().addAll(id, name, lat, lng);
		return list;
		
	}
	
	@SuppressWarnings("unchecked")
	public VBox createView() {
		VBox root = new VBox();
		list = getList();
		lb_sheep = new Label("Sheeps");
		root.autosize();
		root.getChildren().addAll(lb_sheep, list);
		return root;
	}
}
