package sheepfarmer.net.client;

import sheepfarmer.net.client.Client.Type;

public class sheepdb{
	public static SheepResponse login(String email, String password) {
		Client client;
		String result = "";
		try {
			client = new Client("login", Type.POST, "email", email,"pswd", password);
			result = client.execute();
		} catch (Exception e) {
			System.out.println("ERR-database client login");
		}
		return new SheepResponse(result, "null");	
	}

	public static SheepResponse register(String email, String name, String pswd, String phone, String address, String vara ) {
		Client client;
		String result = "";
		try {
			client = new Client("register", Type.POST, "email", email, "name", name, "pswd", pswd, "phone", phone, "address", address, "vara", vara);
			result = client.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SheepResponse(result, null);
	}
	
	
	public static SheepResponse getSheep(String token) {
		Client client;
		String result = "";
		try {
			client = new Client("sheep", Type.GET, "token", token);
			result = client.execute();
		} catch (Exception e) {
			System.out.println("ERR-database client getSheep");
		}
		return new SheepResponse(result, "sheep");
	}
	
	public static SheepResponse getMe(String token) {
		Client client;
		String result = "";
		try {
			client = new Client("user", Type.GET, "token", token);
			result = client.execute();
		} catch (Exception e) {
			System.out.println("ERR-database client getMe");
		}
		return new SheepResponse(result, "farmer");
	}
	
	public static SheepResponse getNotificationUser(String token){
		try {
			Client client = new Client("notification", Type.GET, "token", token);
			return new SheepResponse(client.execute(), "notification");
		} catch (Exception e) {
			System.out.println("ERR-getNotificationUser");
		}
		return null;
	}
	
	public static SheepResponse getNotificationSheep(String token, String sheepid){
		try {
			if(sheepid != null){
				Client client = new Client("notification", Type.GET, "token", token, "sheepid", sheepid);
				return new SheepResponse(client.execute(), "notification");
			}
			else{
				Client client = new Client("notification", Type.GET, "token", token);
				return new SheepResponse(client.execute(), "notification");
			}
		} catch (Exception e) {
			System.out.println("ERR-getNotificationSheep");
		}
		return null;
	}
	

	public static SheepResponse addSheep(String name, String age, String gender, String weight, String token) {
		Client client;
		String result = "";
		try {
			client = new Client("sheep/add", Type.GET, "name", name, "age", age, "gender", gender, "weight", weight, "token", token);
			result = client.execute();
		} catch (Exception e) {
			System.out.println("ERR-database client addSheep");
		}
		return new SheepResponse(result, null);
	}

//	public static void main(String[] args) throws Exception{
//		SheepResponse sr = login("kristian.ekle@gmail.com", "kristian");
//		System.out.println(sr.getSimpleResponse("token"));
//		SheepResponse sr = getNotificationSheep("sheep$5b143a12d244ebe20db603d85f622211ed483780", "1");
//		ArrayList<Notification> not = sr.getNotificationList();
//		for (int i = 0; i < not.size(); i++) {
//			System.out.println(not.get(i).getMsg());
			
//		}
		//Working - System.out.print(register("Christof.kekle@gmail.com", "Christobar", "chritof", "32578532", "Someplace", "kristian.ekle@gmail.com")[2]);
		//System.out.print(addSheep("Birgitte", "13", "Female", "44", "sheep$635689a8574f4c4c60d343bb378384bfdc5d27a3")[1]);
//		SheepResponse sr = getSheep("sheep$fdfd0e4159ec79b2885553e0f02262198d37ab0d");
//		ArrayList<Sheep> sheeps = sr.getSheepList();
//	}


}
