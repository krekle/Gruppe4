package sheepfarmer.net.client;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sheepfarmer.net.app.Notification;
import sheepfarmer.net.app.Sheep;


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
			sheepList.add(new Sheep(jo.getString("id"), jo.getString("age"), jo.getString("hr"), jo.getString("weight"), jo.getString("respiration"), jo.getString("owner"), Float.parseFloat(jo.getString("lat")), Float.parseFloat(jo.getString("long")), jo.getString("name"), jo.getString("gender"), jo.getString("temp"), ((jo.getString("dead")=="0")?false:true)));
		}
		return sheepList;
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
				System.out.println("ERR-Json parsing error");
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