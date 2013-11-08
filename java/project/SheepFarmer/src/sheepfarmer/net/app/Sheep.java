package sheepfarmer.net.app;

import java.security.SecureRandom;

public class Sheep {

	private Mood mood;
	private Gender gen;
	private Colour col;
	private String name, temp, id, age, HR, weight, respiration, ownerid;
	double latitude, longitude;
	private boolean dead;

	public Sheep(String id, String age, String HR, String weight,
			String respiration, String ownerid, double latitude,
			double longitude, String name, String gen, String temp, boolean dead) {
		this.id = id;
		this.age = age;
		this.HR = HR;
		this.weight = weight;
		this.respiration = respiration;
		this.ownerid = ownerid;
		this.latitude = latitude;
		this.longitude = longitude;
		this.setName(name);
		this.setGen(gen);
		this.setCol(randomEnum(Colour.class));
		this.setMood(randomEnum(Mood.class));
		this.setTemp(temp);
		this.setDead(dead);

	}

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
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getHR() {
		return HR;
	}

	public void setHR(String hR) {
		HR = hR;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getRespiration() {
		return respiration;
	}

	public void setRespiration(String respiration) {
		this.respiration = respiration;
	}

	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
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
		if (gen == "Male") {
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

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getMarkerString() {
		return ("document.setNewMarkerWithParameters('" + getId() + "-"
				+ getLatitude() + "-" + getLongitude() + "-" + getName() + "')");
	}

	public String genderTostring() {
		if (this.gen == Gender.MALE) {
			return "Male";
		}
		return "female";
	}

}
