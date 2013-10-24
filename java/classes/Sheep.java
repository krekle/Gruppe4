package pacakas;

import java.security.SecureRandom;

public class Sheep {

	private Mood mood;
	private Gender gen;
	private Colour col;
	private String name;
	private int id, age, HR, weight, respiration, ownerid;
	private float latitude, longitude;

	public Sheep(int id, int age, int HR, int weight, int respiration,
			int ownerid, float latitude, float longitude, String name,
			String gen) {
		this.id = id;
		this.age = age;
		this.HR = HR;
		this.weight = weight;
		this.respiration = respiration;
		this.ownerid = ownerid;
		this.latitude = latitude;
		this.longitude = longitude;
		this.setGen(gen);
		this.setCol(randomEnum(Colour.class));
		this.setMood(randomEnum(Mood.class));

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
		BLACK, WHITE, BROWN
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getHR() {
		return HR;
	}

	public void setHR(int hR) {
		HR = hR;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getRespiration() {
		return respiration;
	}

	public void setRespiration(int respiration) {
		this.respiration = respiration;
	}

	public int getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(int ownerid) {
		this.ownerid = ownerid;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
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

}
