package br.com.trier.spring_matutino.services;

import java.util.List;

import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Piloto;

public interface PilotoService {

	Piloto insert(Piloto piloto);
	Piloto update(Piloto piloto);
	void delete(Integer id);
	List<Piloto> listAll();
	Piloto findById(Integer id);
	List<Piloto> findByNomeContainsIgnoreCaseOrderByNome(String nome);
	List<Piloto> findByPais(Pais pais);
	List<Piloto> findByEquipe(Equipe equipe);
}
