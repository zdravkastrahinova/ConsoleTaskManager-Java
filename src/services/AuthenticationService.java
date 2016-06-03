package services;

import models.User;
import repositories.UsersRepository;

public class AuthenticationService {
	private static User LoggedUser;

	public static User getLoggedUser() {
		return LoggedUser;
	}

	public static void setLoggedUser(User loggedUser) {
		LoggedUser = loggedUser;
	}
	
	public static void authenticate(String username, String password) {
		UsersRepository usersRepo = new UsersRepository("users.txt", User.class);
		setLoggedUser(usersRepo.getByUsernameAndPassword(username, password));
	}

	public static void logout() {
		setLoggedUser(null);
	}
}
