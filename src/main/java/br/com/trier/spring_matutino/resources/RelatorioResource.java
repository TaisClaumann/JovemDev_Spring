package br.com.trier.spring_matutino.resources;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring_matutino.domain.Corrida;
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.dto.CorridaDTO;
import br.com.trier.spring_matutino.domain.dto.CorridaPaisAnoDTO;
import br.com.trier.spring_matutino.services.CorridaService;
import br.com.trier.spring_matutino.services.PaisService;
import br.com.trier.spring_matutino.services.PistaService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;

@RestController
@RequestMapping(value = "/relatorios")
public class RelatorioResource {
	
	@Autowired
	private PaisService paisService;
	@Autowired
	private PistaService pistaService;
	@Autowired
	private CorridaService corridaService;

	@GetMapping("/corridas-by-ano-pais/{anoCorrida}/{idPais}")
	public ResponseEntity<CorridaPaisAnoDTO> findCorridaByPaisAndAno(@PathVariable Integer anoCorrida, @PathVariable Integer paisId){
		Pais pais = paisService.findById(paisId);
		List<CorridaDTO> corridasDTO = pistaService.findByPaisOrderByTamanhoDesc(pais).stream()
		        .flatMap(pista -> {
		            try {
		                return corridaService.findByPista(pista).stream();
		            } catch (ObjetoNaoEncontrado e) {
		                return Stream.empty();
		            }
		        })
		        .filter(corrida -> corrida.getData().getYear() == anoCorrida)
		        .map(Corrida::toDTO)
		        .collect(Collectors.toList());
		
		return ResponseEntity.ok(new CorridaPaisAnoDTO(anoCorrida, pais.getName(), corridasDTO));
	}
}
