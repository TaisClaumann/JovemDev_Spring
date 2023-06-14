package br.com.trier.spring_matutino.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring_matutino.domain.User;

@RestController
@RequestMapping(value = "/user")
public class UserResource {
	
	List<User> lista = new ArrayList<User>();
	
	public UserResource() {
		lista.add(new User(1, "Usuário 1", "1@", "123"));
		lista.add(new User(2, "Usuário 2", "2@", "123"));
		lista.add(new User(3, "Usuário 3", "3@", "123"));
	}
	
	@GetMapping()
	public List<User> findAll() {
		return lista;
	}
	
	@GetMapping("/{cod}")
	public ResponseEntity<User> findById(@PathVariable (name = "cod") Integer cod) {
		User u = lista.stream().filter(id -> id.getId().equals(cod)).findAny().orElse(null);
		return u != null ? ResponseEntity.ok(u) : ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public User insert(@RequestBody User user) {
		user.setId(lista.size()+1);
		lista.add(user);
		return user;
	}
}

