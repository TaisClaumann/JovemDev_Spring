package br.com.trier.spring_matutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.Corrida;
import br.com.trier.spring_matutino.domain.Piloto;
import br.com.trier.spring_matutino.domain.PilotoCorrida;

@Repository
public interface PilotoCorridaRepository extends JpaRepository<PilotoCorrida, Integer> {
	
	List<PilotoCorrida> findByPiloto(Piloto piloto); 
	List<PilotoCorrida> findByCorrida(Corrida corrida);
	List<PilotoCorrida> findByCorridaOrderByColocacaoAsc(Corrida corrida);
}
