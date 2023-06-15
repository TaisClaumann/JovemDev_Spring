package br.com.trier.spring_matutino.services;

import java.util.List;

import br.com.trier.spring_matutino.domain.Campeonato;

public interface CampeonatoService {
	
	Campeonato insert(Campeonato campeonato);
	Campeonato update(Campeonato campeonato);
	List<Campeonato> listAll();
	Campeonato findById(Integer id);
	void delete(Integer id);
}
