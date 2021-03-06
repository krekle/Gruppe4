package sheepfarmer.net.app;

import java.security.SecureRandom;

import javafx.beans.property.SimpleStringProperty;

/**
 * This controls the fields for each separate sheep
 * 
 */

public class Sheep {

	private Mood mood;
	private Gender gen;
	private Colour col;
	private SimpleStringProperty name, temp, id, age, hr, weight, respiration,
			ownerid;
	double latitude, longitude;
	private boolean dead;

	/**
	 * This is the constructor for the class
	 * 
	 * @param id
	 * @param age
	 * @param hr
	 * @param weight
	 * @param respiration
	 * @param ownerid
	 * @param latitude
	 * @param longitude
	 * @param name
	 * @param gen
	 * @param temp
	 * @param dead
	 */

	public Sheep(String id, String age, String hr, String weight,
			String respiration, String ownerid, double latitude,
			double longitude, String name, String gen, String temp, boolean dead) {
		this.id = new SimpleStringProperty(id);
		this.age = new SimpleStringProperty(age);
		this.hr = new SimpleStringProperty(hr);
		this.weight = new SimpleStringProperty(weight);
		this.respiration = new SimpleStringProperty(respiration);
		this.ownerid = new SimpleStringProperty(ownerid);
		this.name = new SimpleStringProperty(name);
		this.temp = new SimpleStringProperty(temp);
		this.dead = dead;
		
		this.latitude = latitude;
		this.longitude = longitude;
		this.setGen(gen);
		this.setCol(randomEnum(Colour.class));
		this.setMood(randomEnum(Mood.class));

		

	}

	/**
	 * This is a random generator to randomize the enums mood, color and gender.
	 */

	private static final SecureRandom random = new SecureRandom();

	public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
		int x = random.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}

	public enum Mood {
		HAPPY, SAD, ANGRY, SCARED, CONTENT;

	}

	public enum Colour {
		BLACK, WHITE, BROWN, YELLOW, GRAY
	}

	public enum Gender {
		MALE, FEMALE
	}

	public String getName() {
		return this.name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getId() {
		return this.id.get();
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public String getAge() {
		return this.age.get();
	}

	public void setAge(String age) {
		this.age.set(age);
	}

	public String getHr() {
		return this.hr.get();
	}

	public void setHr(String hr) {
		this.hr.set(hr);
	}

	public String getWeight() {
		return this.weight.get();
	}

	public void setWeight(String weight) {
		this.weight.set(weight);
	}

	public String getRespiration() {
		return this.respiration.get();
	}

	public void setRespiration(String respiration) {
		this.respiration.set(respiration);
	}

	public String getOwnerid() {
		return this.ownerid.get();
	}

	public void setOwnerid(String ownerid) {
		this.ownerid.set(ownerid);
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Gender getGen() {
		return gen;
	}

	public void setGen(String gen) {
		if (gen.equalsIgnoreCase("Male")) {
			this.gen = Gender.MALE;
		} else {
			this.gen = Gender.FEMALE;
		}
	}

	public Colour getCol() {
		return col;
	}

	public void setCol(Colour col) {
		this.col = col;
	}

	public Mood getMood() {
		return mood;
	}

	public void setMood(Mood mood) {
		this.mood = mood;
	}

	public boolean isDead() {
		return dead;
	}
	
	public String isDeadString(){
		if(this.dead) return "Dead";
		else return "Alive";
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public String getTemp() {
		return this.temp.get();
	}

	public void setTemp(String temp) {
		this.temp.set(temp);
	}

	/**
	 * This creates a string on a form that can be interpreted as a command by
	 * the javascript in the html document.
	 * 
	 * @return
	 */

	public String getMarkerString() {
		return ("document.setNewMarkerWithParameters('" + getId() + "-"
				+ getLatitude() + "-" + getLongitude() + "-" + getName() + "')");
	}

	public String genderTostring() {
		if (this.gen == Gender.MALE) {
			return "Male";
		}
		return "Female";
	}

}
