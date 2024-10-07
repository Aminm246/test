package user.controller;

import user.model.UserModel;
import user.model.User;
import user.view.UserView;
import java.util.List;

public class UserController {
	private UserModel model;
	private UserView view;

	public UserController(UserModel model, UserView view) {
		this.model = model;
		this.view = view;
	}

	public void addUser(String name, int id) {
		User newUser = new User(name, id);
		model.addUser(newUser);
		view.updateView();
	}

	public void updateUser(int id, String newName) {
		User user = model.getUserById(id);
		if (user != null) {
			user.setName(newName);
			model.updateUser(id, user);
			view.updateView();
		}
	}

	public void deleteUser(int id) {
		model.deleteUser(id);
		view.updateView();
	}

	public User getUserById(int id) {
		return model.getUserById(id);
	}

	public List<User> getAllUsers() {
		return model.getAllUsers();
	}

	public void handleSaveButtonClick() {
	}

	public void handleCancelButtonClick() {
	}
}
