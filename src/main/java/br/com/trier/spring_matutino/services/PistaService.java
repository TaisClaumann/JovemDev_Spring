package br.com.trier.spring_matutino.services;

import java.util.List;

import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Pista;

public interface PistaService {

	List<Pista> findByTamanhoBetween(Integer tamInicial, Integer tamFinal);
	List<Pista> findByPaisOrderByTamanhoDesc(Pais pais);
	Pista insert(Pista pista);
	Pista update(Pista pista);
	void delete(Integer id);
	List<Pista> listAll();
	Pista findById(Integer id);
}
