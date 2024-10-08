package user.model;

import java.util.List;
import java.util.ArrayList;

public class UserModel {
	private List<User> userList;

	public UserModel() {
		this.userList = new ArrayList<>();
	}

	public void addUser(User user) {
		userList.add(user);
	}

	public List<User> getAllUsers() {
		return new ArrayList<>(userList);
	}

	public User getUserById(int id) {
		for (User user : userList) {
			if (user.getUserId() == id) {
				return user;
			}
		}
		return null;
	}

	public void updateUser(int id, User updatedUser) {
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getUserId() == id) {
				userList.set(i, updatedUser);
				break;
			}
		}
	}

	public void deleteUser(int id) {
		userList.removeIf(user -> user.getUserId() == id);
	}
}