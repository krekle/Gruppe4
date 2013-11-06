package backend;

import org.json.JSONException;
import org.json.JSONObject;

import backend.Client.Type;

public class sheepdb{
	public static String[] login(String email, String password) throws Exception{
		Client client = new Client("login", Type.POST, "email", email,"pswd", password);
		String result = client.execute();
		//[2] for returning the token, 1 for msg and 0 for code:
		return parser(result, "token");	
	}
	
	public static String[] register(String email, String name, String pswd, String phone, String address, String vara ) throws Exception{
		Client client = new Client("register", Type.POST, "email", email, "name", name, "pswd", pswd, "phone", phone, "address", address, "vara", vara);
		String result = client.execute();
		return parser(result, "token");
	}
	
	public static String[] parser(String res, String var){
		if(res != null){
			String[] response = {null, null, null};
			
			try {
                            if(res.charAt(0) == '{'){
				//Making json of full server-callback
				JSONObject data = new JSONObject(res);
				//Parsing out response, code and message
				response[0] = data.getString("code");
				response[1] = data.getString("msg");
				try {
					JSONObject resp = data.getJSONObject("response");
					response[2] = resp.getString(var);
				} catch (Exception e) {
					//nothing
				}
                            }
				
			} catch (JSONException e) {
				new JSONException("Error parsing data");
				e.printStackTrace();
			}
			return response;
		}
		return null;
		
	}
	
	public static String[] getSheep(String token) throws Exception{
		Client client = new Client("sheep", Type.GET, "token", token);
		String result = client.execute();
		return parser(result, "sheep");
	}
	
	public static String[] addSheep(String name, String age, String gender, String weight, String token) throws Exception{
		Client client = new Client("sheep/add", Type.GET, "name", name, "age", age, "gender", gender, "weight", weight, "token", token);
		String result = client.execute();
		return parser(result, "sheep");
	}
	
	//public static void main(String[] args) throws Exception{
		//System.out.print(login("kristian.ekle@gmail.com", "kristian")[2]);
		//Working - System.out.print(register("Christof.kekle@gmail.com", "Christobar", "chritof", "32578532", "Someplace", "kristian.ekle@gmail.com")[2]);
		//System.out.print(addSheep("Birgitte", "13", "Female", "44", "sheep$635689a8574f4c4c60d343bb378384bfdc5d27a3")[1]);
		//System.out.print(getSheep("sheep$635689a8574f4c4c60d343bb378384bfdc5d27a3")[2]);
	//}
	
	
}
