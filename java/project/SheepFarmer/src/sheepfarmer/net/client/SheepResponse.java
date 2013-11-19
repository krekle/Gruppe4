package sheepfarmer.net.client;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sheepfarmer.net.app.Notification;
import sheepfarmer.net.app.Sheep;
import sheepfarmer.net.app.User;


public class SheepResponse {
	private String code;
	private String msg;
	private JSONObject objectResponse;
	private JSONArray arrayResponse;
	
	public boolean isArray(){
		if(arrayResponse != null) return true;
		else return false;
	}
	
	private ArrayList<Sheep> createSheepList() throws NumberFormatException, JSONException{
		ArrayList<Sheep> sheepList = new ArrayList<Sheep>();
		for (int i = 0; i < arrayResponse.length(); i++) {
			JSONObject jo = arrayResponse.getJSONObject(i);
		
			sheepList.add(new Sheep(jo.getString("id"), jo.getString("age"), jo.getString("hr"), jo.getString("weight"), jo.getString("respiration"), jo.getString("owner"), Float.parseFloat(jo.getString("lat")), Float.parseFloat(jo.getString("long")), jo.getString("name"), jo.getString("gender"), jo.getString("temp"), ((jo.getString("dead").equals("0"))?false:true)));
		}
		return sheepList;
	}
	
	public User getUser() {
		User me = null;
		try {
			JSONObject jo = arrayResponse.getJSONObject(0);
			me = new User(jo.getString("name"), jo.getString("email"), jo.getString("address"), jo.getString("vara"), jo.getString("telephone"));
		} catch (Exception e) {
			System.out.println("ERR parsing json to me in SheepResponse");
		}
		return me;
	}
	
	private ArrayList<Notification> createNotificationList() throws JSONException {
		ArrayList<Notification> notList = new ArrayList<Notification>();
		for (int i = 0; i < arrayResponse.length(); i++) {
			JSONObject jo = arrayResponse.getJSONObject(i);
			notList.add(new Notification(jo.getString("id"), jo.getString("owner"), jo.getString("sheepid"), jo.getString("level"), jo.getString("msg"), jo.getString("lat"), jo.getString("lng"), jo.getString("datime")));
		}
		return notList;
	}
	
	public ArrayList<Notification> getNotificationList(){
		try {
			return createNotificationList();
		} catch (JSONException e) {
			System.out.println("ERR-Json parsing in getNotificationList");
		}
		return null;
	}

	public ArrayList<Sheep> getSheepList(){
		try {
			return createSheepList();
		} catch (NumberFormatException e) {
			System.out.println("ERR-NumberFormat parsing String to float");
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("ERR-json parsing sheep");
		}
		return null;
	}
	
	public ObservableList<String> getChatList() {
		ObservableList<String> items =FXCollections.observableArrayList ();
		for (int i = 0; i < arrayResponse.length(); i++) {
			JSONObject jo;
			try {
				jo = arrayResponse.getJSONObject(i);
				items.add(jo.getString("uname") + System.getProperty("line.separator") + jo.getString("msg").replace("+", " "));
			} catch (JSONException e) {
				System.out.println("ERR-parsing json Chat in SheepResponse");
			}
		}
		return items;
	}

	public SheepResponse(String resp, String var){
		try {
			JSONObject data = new JSONObject(resp);
			//Parsing out response, code and message
			code = data.getString("code");
			msg = data.getString("msg");
			try {
				JSONObject temp = data.getJSONObject("response");
				arrayResponse = temp.getJSONArray(var);
			} catch (Exception e) {
				objectResponse = data.getJSONObject("response");
			}
			} catch (JSONException e) {
				//If response = null: this is no error
//				System.out.println("ERR-Json parsing error");
		}
	}

	public String getCode(){
		return this.code;
	}
	
	public String getMsg(){
		return this.msg;
	}
	
	public String getSimpleResponse(String var){
		try {
			return objectResponse.getString(var);
		} catch (JSONException e) {
			return null;
		}
	}
}