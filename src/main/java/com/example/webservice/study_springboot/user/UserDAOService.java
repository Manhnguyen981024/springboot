package com.example.webservice.study_springboot.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDAOService {
	private static List<User> users = new ArrayList<>();
	private static int userCount = 0;
	static {
		users.add(new User(++userCount, "Manh pro", LocalDate.now().minusYears(26)));
		users.add(new User(++userCount, "Manh pro", LocalDate.now().minusYears(25)));
		users.add(new User(++userCount, "Manh pro", LocalDate.now().minusYears(24)));
	}
	
	public List<User> findAll() {
		return users;
	}
	
	public User findOne(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		return users.stream().filter(predicate).findFirst().orElse(null);
	}
	
	public User save(User user) {
		user.setId(++userCount);
		users.add(user);
		return user;
	}
	
	public void delete(User user) {
		users.remove(user);
	}
	
}
