package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.User;
import br.com.trier.spring_matutino.repositories.UserRepository;
import br.com.trier.spring_matutino.services.UserService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository repo;

	@Override
	public User insert(User user) {
		Optional<User> usuarioOptional = repo.findByEmail(user.getEmail());
		if(usuarioOptional.isPresent()) {
			User usuario = usuarioOptional.get();
			if(user.getId() != usuario.getId()) {
				throw new ViolacaoDeIntegridade("Esse email já existe");
			}
		}
		return repo.save(user);
	}

	@Override
	public List<User> listAll() {
		return repo.findAll();
	}

	@Override
	public User findById(Integer id) {
		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Usuário %s não encontrado".formatted(id)));
	}

	@Override
	public User update(User user) {
		return insert(user);
	}

	@Override
	public void delete(Integer id) {
		User user = findById(id);
		repo.delete(user);
	}

	@Override
	public List<User> findByName(String name) {
		List<User> usuarios = repo.findByName(name);
		if(usuarios.size()==0) {
			throw new ObjetoNaoEncontrado("Não há nenhum usuário com o nome " + name);
		}
		return usuarios;
	}

	@Override
	public User findByEmail(String email) {
		Optional<User> user = repo.findByEmail(email);
		return user.orElse(null);
	}

	@Override
	public List<User> findByNameContainsIgnoreCase(String name) {
		List<User> usuarios = repo.findByNameContainsIgnoreCase(name);
		if(usuarios.size()==0) {
			throw new ObjetoNaoEncontrado("Não há nenhum usuário com o nome " + name);
		}
		return usuarios;
	}
}
