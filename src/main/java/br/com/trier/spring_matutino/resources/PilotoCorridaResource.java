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

import br.com.trier.spring_matutino.domain.PilotoCorrida;
import br.com.trier.spring_matutino.domain.dto.PilotoCorridaDTO;
import br.com.trier.spring_matutino.services.CorridaService;
import br.com.trier.spring_matutino.services.PilotoCorridaService;
import br.com.trier.spring_matutino.services.PilotoService;

@RestController
@RequestMapping(value = "/piloto_corrida")
public class PilotoCorridaResource {
	
	@Autowired
	private PilotoCorridaService service;
	@Autowired
	private CorridaService corridaService;
	@Autowired
	private PilotoService pilotoService;
	
	@Secured({"ROLE_ADMIN_USER"})
	@PostMapping
	public ResponseEntity<PilotoCorridaDTO> insert(@RequestBody PilotoCorridaDTO pilotoCorridaDTO){
		return ResponseEntity.ok(service.insert(new PilotoCorrida(
													pilotoCorridaDTO, 
													pilotoService.findById(pilotoCorridaDTO.getPilotoId()), 
													corridaService.findById(pilotoCorridaDTO.getCorridaId()))).toDTO());
	}
	
	@Secured({"ROLE_ADMIN_USER"})
	@PutMapping("/{id}")
	public ResponseEntity<PilotoCorridaDTO> update(@RequestBody PilotoCorridaDTO pilotoCorridaDTO, @PathVariable Integer id){
		PilotoCorrida piloto = new PilotoCorrida(
										pilotoCorridaDTO, 
										pilotoService.findById(pilotoCorridaDTO.getPilotoId()), 
										corridaService.findById(pilotoCorridaDTO.getCorridaId()));
		piloto.setId(id);
		return ResponseEntity.ok(service.update(piloto).toDTO());
	}
	
	@Secured({"ROLE_ADMIN_USER"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<PilotoCorridaDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream()
											      .map((pilotoCorrida) -> pilotoCorrida.toDTO())
											      .toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<PilotoCorridaDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/piloto/{pilotoId}")
	public ResponseEntity<List<PilotoCorridaDTO>> findByPiloto(@PathVariable Integer pilotoId){
		return ResponseEntity.ok(service.findByPiloto(
									pilotoService.findById(pilotoId)).stream()
																	 .map((pilotoCorrida) -> pilotoCorrida.toDTO())
																	 .toList());
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/corrida/{corridaId}")
	public ResponseEntity<List<PilotoCorridaDTO>> findByCorrida(@PathVariable Integer corridaId){
		return ResponseEntity.ok(service.findByCorrida(
									corridaService.findById(corridaId)).stream()
																	   .map((pilotoCorrida) -> pilotoCorrida.toDTO())
																	   .toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/corrida_colocacao/{corridaId}")
	public ResponseEntity<List<PilotoCorridaDTO>> findByCorridaOrderByColocacaoAsc(@PathVariable Integer corridaId){
		return ResponseEntity.ok(service.findByCorridaOrderByColocacaoAsc(
									corridaService.findById(corridaId)).stream()
												   					   .map(pilotoCorrida -> pilotoCorrida.toDTO())
												                       .toList());
	}
}
