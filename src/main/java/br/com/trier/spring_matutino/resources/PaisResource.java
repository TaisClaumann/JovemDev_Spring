package br.com.trier.spring_matutino.resources;

import java.util.List;

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

import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.services.PaisService;

@RestController
@RequestMapping(value = "/pais")
public class PaisResource {
	
	@Autowired
	private PaisService service;
	
	@PostMapping
	public ResponseEntity<Pais> insert(@RequestBody Pais pais){
		return ResponseEntity.ok(service.insert(pais));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pais> update(@PathVariable Integer id, @RequestBody Pais pais){
		return ResponseEntity.ok(service.update(pais));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping()
	public ResponseEntity<List<Pais>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pais> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<Pais> findByNameEqualsIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameEqualsIgnoreCase(name));
	}
	
	@GetMapping("/like/{name}")
	public ResponseEntity<List<Pais>> findByNameContainsIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameContainsIgnoreCase(name));
	}
}
