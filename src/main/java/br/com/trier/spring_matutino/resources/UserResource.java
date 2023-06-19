package br.com.trier.spring_matutino.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring_matutino.domain.User;
import br.com.trier.spring_matutino.services.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User user){
		User newUser = service.insert(user);
		return newUser != null ? ResponseEntity.ok(newUser) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping
	public ResponseEntity<List<User>> listAll() {
		List<User> usuarios = service.listAll();
		return usuarios.size()>0 ? ResponseEntity.ok(usuarios) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable Integer id) {
		User user = service.findById(id);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody User user){
		user.setId(id);
		user = service.update(user);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.badRequest().build(); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<User>> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name));
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<User> findByEmail(@PathVariable String email){
		User user = service.findByEmail(email);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.badRequest().build(); 
	}
	
	@GetMapping("/like/{name}")
	public ResponseEntity<List<User>>findByNameContainsIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameContainsIgnoreCase(name));
	}
}

