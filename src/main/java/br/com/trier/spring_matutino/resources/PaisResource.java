package br.com.trier.spring_matutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.services.PaisService;

@RestController
@RequestMapping(value = "/pais")
public class PaisResource {
	
	@Autowired
	private PaisService service;
	
	@Secured({"ROLE_ADMIN_USER"})
	@PostMapping
	public ResponseEntity<Pais> insert(@RequestBody Pais pais){
		return ResponseEntity.ok(service.insert(pais));
	}
	
	@Secured({"ROLE_ADMIN_USER"})
	@PutMapping("/{id}")
	public ResponseEntity<Pais> update(@PathVariable Integer id, @RequestBody Pais pais){
		pais.setId(id);
		return ResponseEntity.ok(service.update(pais));
	}
	
	@Secured({"ROLE_ADMIN_USER"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping()
	public ResponseEntity<List<Pais>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<Pais> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/name/{name}")
	public ResponseEntity<Pais> findByNameEqualsIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameEqualsIgnoreCase(name));
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/like/{name}")
	public ResponseEntity<List<Pais>> findByNameContainsIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameContainsIgnoreCase(name));
	}
}
