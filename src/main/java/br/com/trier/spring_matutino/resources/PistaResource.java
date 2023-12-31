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

import br.com.trier.spring_matutino.domain.Pista;
import br.com.trier.spring_matutino.services.PaisService;
import br.com.trier.spring_matutino.services.PistaService;

@RestController
@RequestMapping(value = "/pista")
public class PistaResource {
	
	@Autowired
	private PistaService service;
	
	@Autowired
	private PaisService paisService;
	
	//@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<Pista> insert(@RequestBody Pista pista){
		paisService.findById(pista.getPais().getId());
		return ResponseEntity.ok(service.insert(pista));
	}
	
	//@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<Pista> update(@RequestBody Pista pista, @PathVariable Integer id){
		paisService.findById(pista.getPais().getId());
		pista.setId(id);
		return ResponseEntity.ok(service.update(pista));
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<Pista>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}

	//@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<Pista> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/between/{tamInicial}/{tamFinal}")
	public ResponseEntity<List<Pista>> findByTamanhoBetween(@PathVariable Integer tamInicial, @PathVariable Integer tamFinal){
		return ResponseEntity.ok(service.findByTamanhoBetween(tamInicial, tamFinal));
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/pais/{idPais}")
	public ResponseEntity<List<Pista>> findByPaisOrderByTamanhoDesc(@PathVariable Integer idPais){
		return ResponseEntity.ok(service.findByPaisOrderByTamanhoDesc(paisService.findById(idPais)));
	}
	
}
