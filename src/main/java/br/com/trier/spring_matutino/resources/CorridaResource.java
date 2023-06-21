package br.com.trier.spring_matutino.resources;

import java.time.ZonedDateTime;
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

import br.com.trier.spring_matutino.domain.Corrida;
import br.com.trier.spring_matutino.services.CampeonatoService;
import br.com.trier.spring_matutino.services.CorridaService;
import br.com.trier.spring_matutino.services.PistaService;

@RestController
@RequestMapping(value = "/corrida")
public class CorridaResource {
	
	@Autowired
	private CorridaService service;
	@Autowired
	private PistaService pistaService;
	@Autowired
	private CampeonatoService campeonatoService;
	
	@PostMapping
	public ResponseEntity<Corrida> insert(@RequestBody Corrida corrida){
		pistaService.findById(corrida.getPista().getId());
		campeonatoService.findById(corrida.getCampeonato().getId());
		return ResponseEntity.ok(service.insert(corrida));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Corrida> update(@RequestBody Corrida corrida, @PathVariable Integer id){
		pistaService.findById(corrida.getPista().getId());
		campeonatoService.findById(corrida.getCampeonato().getId());
		corrida.setId(id);
		return ResponseEntity.ok(service.update(corrida));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Corrida>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Corrida> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping("/data/{data}")
	public ResponseEntity<List<Corrida>> findByData(@PathVariable ZonedDateTime data){
		return ResponseEntity.ok(service.findByData(data));
	}
	
	@GetMapping("/between/{dataInicial}/{dataFinal}")
	public ResponseEntity<List<Corrida>> findByDataBetwee(@PathVariable ZonedDateTime dataInicial, @PathVariable ZonedDateTime dataFinal){
		return ResponseEntity.ok(service.findByDataBetween(dataInicial, dataFinal));
	}
	
	@GetMapping("/pista/{idPista}")
	public ResponseEntity<List<Corrida>> findByPista(@PathVariable Integer idPista){
		return ResponseEntity.ok(service.findByPista(pistaService.findById(idPista)));
	}
	
	@GetMapping("/campeonato/{idCampeonato}")
	public ResponseEntity<List<Corrida>> findByCampeonato(@PathVariable Integer idCampeonato){
		return ResponseEntity.ok(service.findByCampeonato(campeonatoService.findById(idCampeonato)));
	}
}
