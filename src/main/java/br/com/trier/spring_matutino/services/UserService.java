package br.com.trier.spring_matutino.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.spring_matutino.domain.User;

public interface UserService {

	User insert(User user);
	List<User> listAll();
	User findById(Integer id);
	User update(User user);
	void delete(Integer id);
	User findByName(String name);
	User findByEmail(String email);
	List<User> findByNameContainsIgnoreCase(String name);
}
