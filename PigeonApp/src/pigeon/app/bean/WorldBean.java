package pigeon.app.bean;

public class WorldBean {
	private int publishid;
	private String nickname;
	private String message;
	private String time;
	private String pic;

	public void setPublishid(int publishid) {
		this.publishid=publishid;
	}
	public int getPublishid() {
		return publishid;
	}
	
	public void setTime(String time) {
		this.time=time;
	}
	public String getTime() {
		return time;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getNickname() {
		return nickname;
	}
	
	public void setMessage(String message) {
		this.message=message;
	}
	public String getMessage() {
		return message;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getPic() {
		return pic;
	}


}
