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
import br.com.trier.spring_matutino.domain.Corrida;
import br.com.trier.spring_matutino.domain.Pista;
import br.com.trier.spring_matutino.domain.dto.CorridaDTO;
import br.com.trier.spring_matutino.services.CampeonatoService;
import br.com.trier.spring_matutino.services.CorridaService;
import br.com.trier.spring_matutino.services.PistaService;
import br.com.trier.spring_matutino.utils.DateUtils;

@RestController
@RequestMapping(value = "/corrida")
public class CorridaResource {
	
	@Autowired
	private CorridaService service;
	@Autowired
	private PistaService pistaService;
	@Autowired
	private CampeonatoService campeonatoService;
	
	//@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<CorridaDTO> insert(@RequestBody CorridaDTO corridaDTO){
		return ResponseEntity.ok(service.insert(new Corrida(corridaDTO, 
				campeonatoService.findById(corridaDTO.getCampeonatoId()), 
				pistaService.findById(corridaDTO.getPistaId()))).toDTO());
	}
	
	//@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<CorridaDTO> update(@RequestBody CorridaDTO corridaDTO, @PathVariable Integer id){
		Corrida corrida = new Corrida(corridaDTO, 
									  campeonatoService.findById(corridaDTO.getCampeonatoId()), 
									  pistaService.findById(corridaDTO.getPistaId()));
		corrida.setId(id);
		return ResponseEntity.ok(service.update(corrida).toDTO());
	}
	
	//@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<CorridaDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream().map((corrida) -> corrida.toDTO()).toList());
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<CorridaDTO> findById(@PathVariable Integer id){
		Corrida corrida = service.findById(id);
		return ResponseEntity.ok(corrida.toDTO());
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/data")
	public ResponseEntity<List<CorridaDTO>> findByData(@RequestParam String data){
		return ResponseEntity.ok(service.findByData(DateUtils.strToZonedDateTime(data)).stream()
																			   .map((corrida) -> corrida.toDTO())
																			   .toList());
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/between")
	public ResponseEntity<List<CorridaDTO>> findByDataBetwee(@RequestParam String dataInicial, @RequestParam String dataFinal){
		return ResponseEntity.ok(service.findByDataBetween(DateUtils.strToZonedDateTime(dataInicial), 
													       DateUtils.strToZonedDateTime(dataFinal)).stream()
																								   .map((corrida) -> corrida.toDTO())
																								   .toList());
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/pista/{idPista}")
	public ResponseEntity<List<CorridaDTO>> findByPista(@PathVariable Integer idPista){
		return ResponseEntity.ok(service.findByPista(pistaService.findById(idPista)).stream()
																					.map((corrida) -> corrida.toDTO())
																					.toList());
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/campeonato/{idCampeonato}")
	public ResponseEntity<List<CorridaDTO>> findByCampeonato(@PathVariable Integer idCampeonato){
		return ResponseEntity.ok(service.findByCampeonato(campeonatoService.findById(idCampeonato)).stream()
																								   .map((corrida) -> corrida.toDTO())
																								   .toList());
	}
}
