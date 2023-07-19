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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.services.CampeonatoService;

@RestController
@RequestMapping(value = "/campeonato")
public class CampeonatoResource {

	@Autowired
	private CampeonatoService service;
	
	//@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<Campeonato> insert(@RequestBody Campeonato campeonato){
		return ResponseEntity.ok(service.insert(campeonato));
	}
	
	//@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<Campeonato> update(@RequestBody Campeonato campeonato, @PathVariable Integer id){
		campeonato.setId(id);
		return ResponseEntity.ok(service.update(campeonato));
	}
	
	//@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<Campeonato> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<Campeonato>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/description/{description}")
	public ResponseEntity<Campeonato> findByDescriptionEqualsIgnoreCase(@PathVariable String description){
		return ResponseEntity.ok(service.findByDescriptionEqualsIgnoreCase(description));
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/ano/{ano}")
	public ResponseEntity<List<Campeonato>> findByAno(@PathVariable String ano){
		return ResponseEntity.ok(service.findByAno(ano));
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/between/{anoInicial}/{anoFinal}")
	public ResponseEntity<List<Campeonato>> findByAnoBetween(@PathVariable String anoInicial, @PathVariable String anoFinal) {
		return ResponseEntity.ok(service.findByAnoBetween(anoInicial, anoFinal));
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/like/{description}")
	public ResponseEntity<List<Campeonato>> findByDescriptionContainsIgnoreCase(@PathVariable String description){
		return ResponseEntity.ok(service.findByDescriptionContainsIgnoreCase(description));
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/description-ano/{description}/{ano}")
	public ResponseEntity<List<Campeonato>> findByDescriptionContainsIgnoreCaseAndAnoEquals(@PathVariable String description, @PathVariable String ano) {
		return ResponseEntity.ok(service.findByDescriptionContainsIgnoreCaseAndAnoEquals(description, ano));
	}
}
