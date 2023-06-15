package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.User;
import br.com.trier.spring_matutino.repositories.UserRepository;
import br.com.trier.spring_matutino.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository repo;

	@Override
	public User salvar(User user) {
		return repo.save(user);
	}

	@Override
	public List<User> listAll() {
		return repo.findAll();
	}

	@Override
	public User findById(Integer id) {
		Optional<User> obj = repo.findById(id);
		return obj.orElse(null);
	}

	@Override
	public User update(User user) {
		return repo.save(user);
	}

	@Override
	public void delete(Integer id) {
		User user = findById(id);
		if(user != null) {
			repo.delete(user);
		}
	}
}
