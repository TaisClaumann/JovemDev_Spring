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
import br.com.trier.spring_matutino.domain.Piloto;
import br.com.trier.spring_matutino.domain.PilotoCorrida;
import br.com.trier.spring_matutino.domain.dto.CorridaDTO;
import br.com.trier.spring_matutino.domain.dto.CorridaPaisAnoDTO;
import br.com.trier.spring_matutino.domain.dto.PilotoCorridaPaisDTO;
import br.com.trier.spring_matutino.domain.dto.PilotoPaisDTO;
import br.com.trier.spring_matutino.domain.dto.TotalPilotoPaisCorridaDTO;
import br.com.trier.spring_matutino.services.CorridaService;
import br.com.trier.spring_matutino.services.PaisService;
import br.com.trier.spring_matutino.services.PilotoCorridaService;
import br.com.trier.spring_matutino.services.PilotoService;
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
	@Autowired
	private PilotoCorridaService pilotoCorridaService;
	@Autowired
	private PilotoService pilotoService;

	@GetMapping("/corridas_by_ano_pais/{anoCorrida}/{paisId}")
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
	
	@GetMapping("/pilotos_by_pais_by_corrida/{corridaId}/{paisId}")
	public ResponseEntity<PilotoCorridaPaisDTO> findPilotoByPaisAndCorrida(@PathVariable Integer corridaId, @PathVariable Integer paisId){
		Corrida corrida = corridaService.findById(corridaId);
		Pais pais = paisService.findById(paisId);
		List<PilotoCorrida> pilotosCorrida = pilotoCorridaService.findByCorridaOrderByColocacaoAsc(corrida);
		
		List<PilotoPaisDTO> pilotosPais = pilotosCorrida.stream()
			        .filter(pilotoCorrida -> pilotoService.findByPais(pais).stream()
			            .anyMatch(piloto -> pilotoCorrida.getPiloto().getId().equals(piloto.getId())))
			        .map(pilotoCorrida -> {
			            Piloto piloto = pilotoCorrida.getPiloto();

			            return new PilotoPaisDTO(piloto.getId(), piloto.getNome(), piloto.getEquipe().getId(),
			                piloto.getEquipe().getName(), pilotoCorrida.getColocacao());
			        })
			        .collect(Collectors.toList());
		
		return ResponseEntity.ok(new PilotoCorridaPaisDTO(paisId, pais.getName(), corridaId, pilotosPais));
	}
	
	@GetMapping("/total_pilotos_pais_by_corrida/{corridaId}/{paisId}")
	public ResponseEntity<TotalPilotoPaisCorridaDTO> findTotalPilotosByPaisAndCorrida(@PathVariable Integer corridaId, @PathVariable Integer paisId){
		Corrida corrida = corridaService.findById(corridaId);
		Pais pais = paisService.findById(paisId);
		List<PilotoCorrida> pilotosCorrida = pilotoCorridaService.findByCorrida(corrida);
		
		Long total = pilotosCorrida.stream()
	            				   .map(PilotoCorrida::getPiloto)
	                               .filter(pilotoService.findByPais(pais)::contains)
	                               .count();
		
		return ResponseEntity.ok(new TotalPilotoPaisCorridaDTO(pais.getName(), corrida.getId(), total.intValue()));
	}
}
