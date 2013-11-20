package sheepfarmer.net.app;
/**
 * 
 * @author krekle
 *	Class for storing and representing the farmer.
 */
public class User {
	private String name, email, address, vara, phone;
/**
 * 
 * @param name
 * @param email
 * @param address
 * @param vara
 * @param phone
 */
	public User(String name, String email, String address, String vara, String phone){
		this.name = name;
		this.email = email;
		this.address = address;
		this.vara = vara;
		this.phone = phone;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVara() {
		return vara;
	}

	public void setVara(String vara) {
		this.vara = vara;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
