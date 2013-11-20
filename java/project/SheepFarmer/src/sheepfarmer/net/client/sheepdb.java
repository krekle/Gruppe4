package sheepfarmer.net.client;

import sheepfarmer.net.app.Singleton;
import sheepfarmer.net.client.Client.Type;

/**
 * 
 * @author krekle
 *Only class is a middleman between the Client class and the rest of the program
 *it handles all exceptions the Client class might get.
 */
public class sheepdb{
	/**
	 * Method for checking username and password
	 * 
	 * @param email
	 * @param password
	 * @return
	 * A SheepResponse with response; code, msg and token
	 */
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
	/**Method for user registration
	 * 
	 * @param email
	 * @param name
	 * @param pswd
	 * @param phone
	 * @param address
	 * @param vara
	 * @return
	 * A SheepResponse with response; code, msg and token
	 */
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

	/**Method for getting all sheeps for a user with token.
	 * 
	 * @param token
	 * @return
	 * 
	 * A SheepResponse containing a list of Sheep Objects for this user
	 */
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
	/**Method for getting all the information on user with token 
	 * 
	 * @param token
	 * @return
	 * 
	 * A SheepResponse with a User Object
	 */
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
	/**Method for getting all the notifications for user with token
	 * 
	 * @param token
	 * @return
	 * 
	 * A SheepResponse with a list of Notification Objects
	 */

	public static SheepResponse getNotificationUser(String token){
		try {
			Client client = new Client("notification", Type.GET, "token", token);
			return new SheepResponse(client.execute(), "notification");
		} catch (Exception e) {
			System.out.println("ERR-getNotificationUser");
		}
		return null;
	}
	/**Method for getting all notifications for one sheep with id
	 * Users token required for authentification on server-side
	 * 
	 * @param token
	 * @param sheepid
	 * @return
	 * 
	 * A SheepResponse with a list of Notification Objects
	 */
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

	/**Method for adding a new sheep 
	 * 
	 * @param name
	 * @param age
	 * @param gender
	 * @param weight
	 * @param token
	 * @return
	 * 
	 * SheepResponse with code and msg, no response
	 * 
	 */
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

	/**Method for deleting a Sheep
	 * 
	 * @param token
	 * @param sheepid
	 * @return
	 * 
	 * SheepResponse with code and msg, no response
	 */
	public static SheepResponse deleteSheep(String token, String sheepid){
		Client client;
		String result= "";
		try {
			if(sheepid != null){
				client = new Client("delete/sheep", Type.GET, "token", Singleton.getInstance().getToken(), "sheepid", sheepid);
			} else {
				client = new Client("delete/sheep", Type.GET, "token", Singleton.getInstance().getToken());
			}
			result = client.execute();
		} catch (Exception e) {
			System.out.println("ERR-database client deleteSheep");
		}
		return new SheepResponse(result, null);
	}
	/**Method for editing a Sheep
	 * 
	 * @param sheepid
	 * @param name
	 * @param weight
	 * @param gender
	 * @param age
	 * @return
	 * 
	 * SheepResponse with code and msg, no response
	 */
	public static SheepResponse editSheep(String sheepid, String name, String weight, String gender, String age){
		Client client;
		String result = "";
		try {
			client = new Client("edit", Type.GET, "token", Singleton.getInstance().getToken(), "uid", sheepid, "name", name, "weight", weight, "gender", gender, "age", age);
			result = client.execute();
		} catch (Exception e) {
			System.out.println("Err-database client editSheep");
		}
		return new SheepResponse(result, null);

	}
	/**Method for editing a user
	 * 
	 * @param name
	 * @param mail
	 * @param vara
	 * @param phone
	 * @param address
	 * @return
	 * 
	 * SheepResponse with code and msg, no response
	 */
	public static SheepResponse editUser(String name, String mail, String vara, String phone, String address){
		Client client;
		String result = "";
		try {
			client = new Client("edit", Type.GET, "token", Singleton.getInstance().getToken(),"uid", "", "name", name, "mail", mail,  "vara", vara, "telephone", phone, "address", address);
			result = client.execute();
		} catch (Exception e) {
			System.out.println("Err-database client editUser");
		}
		return new SheepResponse(result, null);

	}
	/**Mehod for adding a message to the chat
	 * 
	 * @param msg
	 * @return
	 * 
	 * SheepResponse with code and msg, no response
	 */
	public static SheepResponse addChatMessage(String msg){
		Client client;
		String result = "";
		try {
			System.out.println("Chat added!");
			client = new Client("add/sheepchat", Type.GET, "token", Singleton.getInstance().getToken(), "msg", msg);
			result = client.execute();
		} catch (Exception e) {
			System.out.println("Err-database client addChatMessage");
			e.printStackTrace();
		}
		return new SheepResponse(result, null);
	}
	/**Method for getting the chat
	 * No token requied, everyone can see it.
	 * 
	 * @return
	 * 
	 * SheepResponse with a list of String 
	 */
	public static SheepResponse getChat(){
		Client client;
		String result = "";
		try {
			client = new Client("get/sheepchat", Type.GET, "token", Singleton.getInstance().getToken());
			result = client.execute();
		} catch (Exception e) {
			System.err.println("Err-database client getChat");
		}
		return new SheepResponse(result, "chat");
	}
}
