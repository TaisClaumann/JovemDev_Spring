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
		Campeonato newCampeonato = service.insert(campeonato);
		return newCampeonato != null ? ResponseEntity.ok(newCampeonato) : ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Campeonato> update(@RequestParam Campeonato campeonato, @PathVariable Integer id){
		campeonato.setId(id);
		campeonato = service.update(campeonato);
		return campeonato != null ? ResponseEntity.ok(campeonato) : ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Campeonato> findById(@PathVariable Integer id){
		Campeonato campeonato = service.findById(id);
		return campeonato != null ? ResponseEntity.ok(campeonato) : ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Campeonato>> listAll(){
		List<Campeonato> campeonatos = service.listAll();
		return campeonatos.size()>0 ? ResponseEntity.ok(campeonatos) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<Campeonato> findByDescricaoEqualsIgnoreCase(@PathVariable String descricao){
		Campeonato campeonato = service.findByDescriptionEqualsIgnoreCase(descricao);
		return campeonato != null ? ResponseEntity.ok(campeonato) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/ano/{ano}")
	public ResponseEntity<List<Campeonato>> findByAno(@PathVariable String ano){
		List<Campeonato> campeonatos = service.findByYear(ano);
		return campeonatos.size()>0 ? ResponseEntity.ok(campeonatos) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/ano-entre/{anoInicial}/{anoFianl}")
	public ResponseEntity<List<Campeonato>> findByAnoBetween(@PathVariable String anoInicial, @PathVariable String anoFinal) {
		List<Campeonato> campeonatos = service.findByYearBetween(anoInicial, anoFinal);
		return campeonatos.size()>0 ? ResponseEntity.ok(campeonatos) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/like/{descricao}")
	public ResponseEntity<List<Campeonato>> findByDescricaoContainsIgnoreCase(@PathVariable String descricao){
		List<Campeonato> campeonatos = service.findByDescriptionContainsIgnoreCase(descricao);
		return campeonatos.size()>0 ? ResponseEntity.ok(campeonatos) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/descricao-ano/{descricao}/{ano}")
	public ResponseEntity<List<Campeonato>> findByDescricaoContainsIgnoreCaseAndAnoEquals(String descricao, String ano) {
		List<Campeonato> campeonatos = service.findByDescriptionContainsIgnoreCaseAndYearEquals(descricao, ano);
		return campeonatos.size()>0 ? ResponseEntity.ok(campeonatos) : ResponseEntity.noContent().build();
	}

}
