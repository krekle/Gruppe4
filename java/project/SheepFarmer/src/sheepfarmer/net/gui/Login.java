package sheepfarmer.net.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sheepfarmer.net.app.CallListener;
import sheepfarmer.net.app.Singleton;
import sheepfarmer.net.client.SheepResponse;
import sheepfarmer.net.client.sheepdb;


public class Login extends Application {
	VBox content;
	CallListener controller;
	
	public Login(CallListener cont){
		 controller = cont;
	}
	
	sheepdb db = new sheepdb();
	
	protected DropShadow drop(){

		DropShadow ds = new DropShadow();
		ds.setOffsetX(5.0);
		ds.setOffsetY(5.0);
		ds.setColor(Color.GRAY);
		return ds;
		
	}
	
	protected Reflection reflect(){
		Reflection reflect = new Reflection();
		reflect.setFraction(0.8);
		reflect.setTopOffset(-20);
		return reflect;
	}
	private VBox makeLogin(){
		VBox login = new VBox();
		login.setSpacing(12);
		
		final PasswordField pswd_fld = new PasswordField();
		pswd_fld.setText("password");
		
		final TextField eml_fld = new TextField();
		eml_fld.setText("username");
		
		Button btn_Login = new Button("Login");
		btn_Login.setEffect(drop());
		
		final Label lb_msg = new Label("");
		login.getChildren().addAll(eml_fld, pswd_fld, lb_msg, btn_Login);
		
		btn_Login.setOnAction(new EventHandler<ActionEvent>() {
			@SuppressWarnings("static-access")
			public void handle(ActionEvent arg0) {
				SheepResponse sr;
				try {
					sr = db.login(eml_fld.getText(), pswd_fld.getText());
					if (sr.getCode().equals(new String("200"))){
						String token = sr.getSimpleResponse("token");
						if(token != null){
							Singleton me = Singleton.getInstance();
							me.getInstance().setToken(token);
							controller.loginCallBack(true);
							}
					}
					else{
						lb_msg.setText(sr.getMsg());
						}	
				}catch (Exception e) {
					lb_msg.setText("No Connection to Server");
					System.out.println("Database ERR");
				}
			}
		});
		return login;
	}
	
	@SuppressWarnings("unused")
	private VBox makeRegister(){
		VBox register = new VBox();
		register.setSpacing(8);
		TextField email = new TextField("Email");
		TextField name = new TextField("Name");
		TextField password = new TextField("Password");
		TextField retype = new TextField("Retype Password");
		TextField phone = new TextField("Phone");
		TextField address = new TextField("Address");
		TextField vara = new TextField("Vara");
		
		Label msg = new Label("");
		
		Button regist = new Button("Register");
		regist.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				
				
			}
		});
		register.getChildren().addAll(email, name, password, retype, phone, address, vara, msg, regist);
		return register;
		
	}
	
	public VBox createView(){
		Label lb_Title = new Label("SheepFarmer");
		lb_Title.setEffect(reflect());
		lb_Title.getStyleClass().add("my_customLabel");
		
		content = makeLogin();
		
		Hyperlink reg_lin = new Hyperlink();
		reg_lin.setText("Register");
		
		
		
		reg_lin.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				getHostServices().showDocument("http://dev.krekle.net:8081/register");
			}
		});
		
		
		VBox root = new VBox();
		root.autosize();
		root.getChildren().addAll(lb_Title, content, reg_lin);
		return root;
	}

	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}

}
