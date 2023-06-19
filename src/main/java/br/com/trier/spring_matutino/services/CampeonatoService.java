package br.com.trier.spring_matutino.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.spring_matutino.domain.Campeonato;

public interface CampeonatoService {
	
	Campeonato insert(Campeonato campeonato);
	Campeonato update(Campeonato campeonato);
	List<Campeonato> listAll();
	Campeonato findById(Integer id);
	void delete(Integer id);
	Campeonato findByDescriptionEqualsIgnoreCase(String description);
	List<Campeonato> findByAno(String ano);
	List<Campeonato> findByAnoBetween(String anoInicial, String anoFinal);
	List<Campeonato> findByDescriptionContainsIgnoreCase(String description);
	List<Campeonato> findByDescriptionContainsIgnoreCaseAndAnoEquals(String description, String ano);
}
