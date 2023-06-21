package br.com.trier.spring_matutino.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.domain.Corrida;
import br.com.trier.spring_matutino.domain.Pista;

@Repository
public interface CorridaRepository extends JpaRepository<Corrida, Integer>{
	
	List<Corrida> findByData(ZonedDateTime data);
	List<Corrida> findByDataBetween(ZonedDateTime dataInicial, ZonedDateTime dataFinal);
	List<Corrida> findByPista(Pista pista);
	List<Corrida> findByCampeonato(Campeonato campeonato);
}
