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

import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.services.EquipeService;

@RestController
@RequestMapping(value = "/equipe")
public class EquipeResource {
	
	@Autowired
	private EquipeService service;
	
	@PostMapping
	public ResponseEntity<Equipe> insert(@RequestBody Equipe equipe){
		return ResponseEntity.ok(service.insert(equipe));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Equipe> update(@RequestBody Equipe equipe, @PathVariable Integer id){
		equipe.setId(id);
		return ResponseEntity.ok(service.update(equipe));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Equipe> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping()
	public ResponseEntity<List<Equipe>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<Equipe> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name));
	}
	
	public ResponseEntity<List<Equipe>> findByNameContainsIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameContainsIgnoreCase(name));
	}
}
