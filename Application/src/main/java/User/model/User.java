package user.model;

public class User {
	private String name;
	private int userID;

	public User(String name, int userID) {
		this.name = name;
		this.userID = userID;
	}

	public int getUserId() {
		return userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}