package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.trier.spring_matutino.domain.User;
import br.com.trier.spring_matutino.repositories.UserRepository;
import br.com.trier.spring_matutino.services.UserService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	
	@Configuration
	public class AppConfig {
		@Bean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}
	}

	@Override
	public User insert(User user) {
		if(validaUser(user)) {
			Optional<User> usuarioOptional = repository.findByEmail(user.getEmail());
			if (usuarioOptional.isPresent()) {
				User usuario = usuarioOptional.get();
				if (user.getId() != usuario.getId()) {
					throw new ViolacaoDeIntegridade("Esse email já existe");
				}
			}
		}
		return repository.save(user);
	}
	
	private boolean validaUser(User user) {
		if(user == null) {
			throw new ViolacaoDeIntegridade("O usuário está nulo");
		} else if(user.getName() == null || user.getName().isBlank()) {
			throw new ViolacaoDeIntegridade("O nome está vazio");
		} else if(user.getEmail() == null || user.getEmail().isBlank()) {
			throw new ViolacaoDeIntegridade("O email está vazio");
		}
		return true;
	}

	@Override
	public List<User> listAll() {
		List<User> usuarios = repository.findAll();
		if (usuarios.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há usuários cadastrados");
		}
		return repository.findAll();
	}

	@Override
	public User findById(Integer id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Usuário %s não encontrado".formatted(id)));
	}

	@Override
	public User update(User user) {
		if(!listAll().contains(user)) {
			throw new ObjetoNaoEncontrado("Esse usuário não existe");
		}
		return insert(user);
	}

	@Override
	public void delete(Integer id) {
		User user = findById(id);
		repository.delete(user);
	}

	@Override
	public User findByName(String name) {
		Optional<User> user = repository.findByName(name);
		return user.orElseThrow(() -> new ObjetoNaoEncontrado("Usuário %s não encontrado".formatted(name)));
	}

	@Override
	public User findByEmail(String email) {
		Optional<User> user = repository.findByEmail(email);
		return user.orElseThrow(() -> new ObjetoNaoEncontrado("Usuário %s não encontrado".formatted(email)));
	}

	@Override
	public List<User> findByNameContainsIgnoreCase(String name) {
		List<User> usuarios = repository.findByNameContainsIgnoreCase(name);
		if (usuarios.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há nenhum usuário com o nome " + name);
		}
		return usuarios;
	}
}
