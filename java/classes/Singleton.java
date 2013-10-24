package pacakas;

public class Singleton {
	private String token;

	private static Singleton instance = null;

	protected Singleton() {
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