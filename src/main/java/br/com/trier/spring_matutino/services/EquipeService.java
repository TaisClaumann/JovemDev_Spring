package br.com.trier.spring_matutino.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.spring_matutino.domain.Equipe;

public interface EquipeService {

	Equipe insert(Equipe equipe);
	Equipe update(Equipe equipe);
	Equipe findById(Integer id);
	void delete(Integer id);
	List<Equipe> listAll();
	List<Equipe> findByName(String name);
}
