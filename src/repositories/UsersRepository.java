package repositories;

import models.User;

import java.io.*;

public class UsersRepository extends BaseRepository<User> {
	public UsersRepository(String filePath, Class<User> userClass) {
		super(filePath, userClass);
	}

	protected void readItem (BufferedReader br, User user) {
		try {
			user.setUsername(br.readLine());
			user.setPassword(br.readLine());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	protected void writeItem(PrintWriter pw, User user) {
		pw.println(user.getId());
		pw.println(user.getUsername());
		pw.println(user.getPassword());
	}

	public Boolean checkIfUserExists(User user) {
		User verifyUser = this.getAll().stream().filter(u -> u.getUsername().equals(user.getUsername())).findFirst().orElse(null);
        if (verifyUser == null)
        {
            return false;
        }
        return true;
	}

	public User getByUsernameAndPassword(String username, String password) {
		return this.getAll().stream().filter((User) -> User.getUsername().equals(username) && User.getPassword().equals(password)).findFirst().orElse(null);
	}
}
