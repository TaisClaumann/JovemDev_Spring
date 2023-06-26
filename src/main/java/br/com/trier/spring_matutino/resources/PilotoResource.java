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

import br.com.trier.spring_matutino.domain.Piloto;
import br.com.trier.spring_matutino.domain.dto.PilotoDTO;
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
	
	@Secured({"ROLE_ADMIN_USER"})
	@PostMapping
	public ResponseEntity<PilotoDTO> insert(@RequestBody PilotoDTO pilotoDTO){
		equipeService.findById(pilotoDTO.getEquipeId());
		paisService.findById(pilotoDTO.getPaisId());
		return ResponseEntity.ok(service.insert(new Piloto(pilotoDTO)).toDTO());
	}
	
	@Secured({"ROLE_ADMIN_USER"})
	@PutMapping("/{id}")
	public ResponseEntity<PilotoDTO> update(@RequestBody PilotoDTO pilotoDTO, @PathVariable Integer id){
		equipeService.findById(pilotoDTO.getEquipeId());
		paisService.findById(pilotoDTO.getPaisId());
		Piloto piloto = new Piloto(pilotoDTO);
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
	public ResponseEntity<List<PilotoDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream().map((piloto) -> piloto.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<PilotoDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<PilotoDTO>> findByNomeContainsIgnoreCaseOrderByNome(@PathVariable String nome){
		return ResponseEntity.ok(service.findByNomeContainsIgnoreCaseOrderByNome(nome).stream()
																					  .map((piloto) -> piloto.toDTO())
																					  .toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/pais/{idPais}")
	public ResponseEntity<List<PilotoDTO>> findByPais(@PathVariable Integer idPais){
		return ResponseEntity.ok(service.findByPais(paisService.findById(idPais)).stream()
																			 .map((piloto) -> piloto.toDTO())
																			 .toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/equipe/{idEquipe}")
	public ResponseEntity<List<PilotoDTO>> findByEquipe(@PathVariable Integer idEquipe){
		return ResponseEntity.ok(service.findByEquipe(equipeService.findById(idEquipe)).stream()
																			     .map((piloto) -> piloto.toDTO())
																			     .toList());
	}
}
