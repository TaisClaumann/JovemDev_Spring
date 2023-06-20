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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.services.CampeonatoService;

@RestController
@RequestMapping(value = "/campeonato")
public class CampeonatoResource {

	@Autowired
	private CampeonatoService service;
	
	@PostMapping
	public ResponseEntity<Campeonato> insert(@RequestBody Campeonato campeonato){
		return ResponseEntity.ok(service.insert(campeonato));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Campeonato> update(@RequestBody Campeonato campeonato, @PathVariable Integer id){
		return ResponseEntity.ok(service.update(campeonato));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Campeonato> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Campeonato>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/description/{description}")
	public ResponseEntity<Campeonato> findByDescriptionEqualsIgnoreCase(@PathVariable String description){
		return ResponseEntity.ok(service.findByDescriptionEqualsIgnoreCase(description));
	}
	
	@GetMapping("/ano/{ano}")
	public ResponseEntity<List<Campeonato>> findByAno(@PathVariable String ano){
		return ResponseEntity.ok(service.findByAno(ano));
	}
	
	@GetMapping("/between/{anoInicial}/{anoFinal}")
	public ResponseEntity<List<Campeonato>> findByAnoBetween(@PathVariable String anoInicial, @PathVariable String anoFinal) {
		return ResponseEntity.ok(service.findByAnoBetween(anoInicial, anoFinal));
	}
	
	@GetMapping("/like/{description}")
	public ResponseEntity<List<Campeonato>> findByDescriptionContainsIgnoreCase(@PathVariable String description){
		return ResponseEntity.ok(service.findByDescriptionContainsIgnoreCase(description));
	}
	
	@GetMapping("/description-ano/{description}/{ano}")
	public ResponseEntity<List<Campeonato>> findByDescriptionContainsIgnoreCaseAndAnoEquals(@PathVariable String description, @PathVariable String ano) {
		return ResponseEntity.ok(service.findByDescriptionContainsIgnoreCaseAndAnoEquals(description, ano));
	}
}
