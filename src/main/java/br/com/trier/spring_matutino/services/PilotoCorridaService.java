package br.com.trier.spring_matutino.services;

import java.util.List;

import br.com.trier.spring_matutino.domain.Corrida;
import br.com.trier.spring_matutino.domain.Piloto;
import br.com.trier.spring_matutino.domain.PilotoCorrida;

public interface PilotoCorridaService {
	
	PilotoCorrida insert(PilotoCorrida pilotoCorrida);
	PilotoCorrida update(PilotoCorrida pilotoCorrida);
	void delete(Integer id);
	List<PilotoCorrida> listAll();
	PilotoCorrida findById(Integer id);
	List<PilotoCorrida> findByPiloto(Piloto piloto);
	List<PilotoCorrida> findByCorrida(Corrida corrida);
}
