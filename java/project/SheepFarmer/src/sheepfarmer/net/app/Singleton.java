package sheepfarmer.net.app;

/**
 * 
 * @author sondre_dyvik This creates a singleton that we use as a session token
 *         to separate users This class is handled by the database
 */
public class Singleton {
	private String token;

	private static Singleton instance = null;

	private Singleton() {
		// Exists only to defeat instantiation.
	}

	public static Singleton getInstance() {
		if (instance == null) {
			instance = new Singleton();
		}
		return instance;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}