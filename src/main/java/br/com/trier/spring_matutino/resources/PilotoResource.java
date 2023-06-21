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

import br.com.trier.spring_matutino.domain.Piloto;
import br.com.trier.spring_matutino.services.EquipeService;
import br.com.trier.spring_matutino.services.PaisService;
import br.com.trier.spring_matutino.services.PilotoService;

@RestController
@RequestMapping(value = "/piloto")
public class PilotoResource {
	
	@Autowired
	private PilotoService service;
	
	@Autowired
	private EquipeService equipeService;
	
	@Autowired
	private PaisService paisService;
	
	@PostMapping
	public ResponseEntity<Piloto> insert(@RequestBody Piloto piloto){
		equipeService.findById(piloto.getEquipe().getId());
		paisService.findById(piloto.getPais().getId());
		return ResponseEntity.ok(service.insert(piloto));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Piloto> update(@RequestBody Piloto piloto, @PathVariable Integer id){
		equipeService.findById(piloto.getEquipe().getId());
		paisService.findById(piloto.getPais().getId());
		piloto.setId(id);
		return ResponseEntity.ok(service.update(piloto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Piloto>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Piloto> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Piloto>> findByNomeContainsIgnoreCaseOrderByNome(@PathVariable String nome){
		return ResponseEntity.ok(service.findByNomeContainsIgnoreCaseOrderByNome(nome));
	}
	
	@GetMapping("/pais/{idPais}")
	public ResponseEntity<List<Piloto>> findByPais(@PathVariable Integer id){
		return ResponseEntity.ok(service.findByPais(paisService.findById(id)));
	}
	
	@GetMapping("/equipe/{idEquipe}")
	public ResponseEntity<List<Piloto>> findByEquipe(@PathVariable Integer id){
		return ResponseEntity.ok(service.findByEquipe(equipeService.findById(id)));
	}
}
