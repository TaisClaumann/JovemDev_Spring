package br.com.trier.spring_matutino.services;

import java.time.ZonedDateTime;
import java.util.List;

import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.domain.Corrida;
import br.com.trier.spring_matutino.domain.Pista;

public interface CorridaService {

	Corrida insert(Corrida corrida);
	Corrida update(Corrida corrida);
	void delete(Integer id);
	List<Corrida> listAll();
	Corrida findById(Integer id);
	List<Corrida> findByData(ZonedDateTime data);
	List<Corrida> findByDataBetween(ZonedDateTime dataInicial, ZonedDateTime dataFinal);
	List<Corrida> findByPista(Pista pista);
	List<Corrida> findByCampeonato(Campeonato campeonato);
}
