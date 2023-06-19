package br.com.trier.spring_matutino.services;

import java.util.List;

import br.com.trier.spring_matutino.domain.Pais;

public interface PaisService {
	
	Pais insert(Pais pais);
	Pais update(Pais pais);
	void delete(Integer id);
	List<Pais> listAll();
	Pais findById(Integer id);
	Pais findByNameEqualsIgnoreCase(String name);
	List<Pais> findByNameContainsIgnoreCase(String name);
}
