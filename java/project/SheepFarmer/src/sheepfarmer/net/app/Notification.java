package sheepfarmer.net.app;

public class Notification {
	private String id, owner, sheepid, level, msg, lat, lng, datime;
	public Notification(String id,String owner,String sheepid,String level,String msg,String lat,String lng,String datime){
		this.id = id;
		this.owner = owner;
		this.sheepid = sheepid;
		this.level = level;
		this.msg = msg;
		this.lat = lat;
		this.lng = lng;
		this.datime = datime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getSheepid() {
		return sheepid;
	}
	public void setSheepid(String sheepid) {
		this.sheepid = sheepid;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getDatime() {
		return datime;
	}
	public void setDatime(String datime) {
		this.datime = datime;
	}
}
