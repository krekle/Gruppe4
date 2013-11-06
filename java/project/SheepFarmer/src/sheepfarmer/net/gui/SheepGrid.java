package sheepfarmer.net.gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sheepfarmer.net.app.Sheep;

public class SheepGrid extends HBox{
	private Label lb_name, lb_id, lb_age, lb_gen, lb_hr, lb_respiration, lb_weight, lb_temp;
	@SuppressWarnings("unused")
	private TextField txt_name, txt_id, txt_age, txt_gen, txt_hr, txt_respiration, txt_weight, txt_temp;
	
	private VBox right, left;
	
	
	public SheepGrid(Sheep s){
		setSpacing(8);
		//Keys
		left = new VBox();
		lb_name = new Label("Name");
		lb_id = new Label("Id");
		lb_age = new Label("Age");
		lb_gen = new Label("Gender");
		lb_hr = new Label("Hearth rate");
		lb_respiration = new Label("Respiration");
		lb_weight = new Label("Weight");
		lb_temp = new Label("Temp");
		//Adding keys
		left.getChildren().addAll(lb_name, lb_id, lb_age, lb_gen, lb_hr, lb_respiration, lb_weight, lb_temp);
		
		//Values
		right = new VBox();
		txt_name = new TextField(((s != null)? (s.getName()):""));
		txt_id = new TextField(((s != null)? (s.getId()):""));
		txt_age = new TextField(((s != null)? (s.getAge()):""));
		txt_gen = new TextField(((s != null)? (s.getGen().toString()):""));
		txt_hr = new TextField(((s != null)? (s.getHR()):""));
		txt_respiration = new TextField(((s != null)? (s.getRespiration()):""));
		txt_weight = new TextField(((s != null)? (s.getWeight()):""));
		txt_temp = new TextField(((s != null)? (s.getTemp()):""));
		//Adding values
		right.getChildren().addAll(txt_name, txt_id, txt_age, txt_gen, txt_hr, txt_respiration, txt_temp);
		
		getChildren().addAll(left, right);
	}
	
	public void changeContent(Sheep s){
		txt_name = new TextField(s.getName());
		txt_id = new TextField(s.getId());
		txt_age = new TextField(s.getAge());
		txt_gen = new TextField(s.getGen().toString());
		txt_hr = new TextField(s.getHR());
		txt_respiration = new TextField(s.getRespiration());
		txt_weight = new TextField(s.getWeight());
		txt_temp = new TextField(s.getTemp());
	}
	
	
}
