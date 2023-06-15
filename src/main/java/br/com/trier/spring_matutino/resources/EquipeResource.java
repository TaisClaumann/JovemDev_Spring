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
@RequestMapping("/equipe")
public class EquipeResource {
	
	@Autowired
	private EquipeService service;
	
	@PostMapping
	public ResponseEntity<Equipe> insert(@RequestBody Equipe equipe){
		Equipe newEquipe = service.insert(equipe);
		return newEquipe != null ? ResponseEntity.ok(equipe) : ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Equipe> update(@RequestBody Equipe equipe, @PathVariable Integer id){
		equipe.setId(id);
		equipe = service.insert(equipe);
		return equipe != null ? ResponseEntity.ok(equipe) : ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Equipe> findById(@PathVariable Integer id){
		Equipe equipe = service.findById(id);
		return equipe != null ? ResponseEntity.ok(equipe) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping()
	public ResponseEntity<List<Equipe>> listAll(){
		List<Equipe> equipes = service.listAll();
		return equipes.size()>0 ? ResponseEntity.ok(equipes) : ResponseEntity.noContent().build();
	}
	

}
