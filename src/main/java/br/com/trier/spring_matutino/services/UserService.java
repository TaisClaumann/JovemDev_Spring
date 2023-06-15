package br.com.trier.spring_matutino.services;

import java.util.List;

import br.com.trier.spring_matutino.domain.User;

public interface UserService {

	User salvar(User user);
	List<User> listAll();
	User findById(Integer id);
	User update(User user);
	void delete(Integer id);
}
