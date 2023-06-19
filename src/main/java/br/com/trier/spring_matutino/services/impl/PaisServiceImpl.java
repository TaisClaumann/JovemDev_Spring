package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.repositories.PaisRepository;
import br.com.trier.spring_matutino.services.PaisService;

@Service
public class PaisServiceImpl implements PaisService{
	
	@Autowired
	private PaisRepository repo;
	
	@Override
	public Pais insert(Pais pais) {
		return repo.save(pais);
	}

	@Override
	public Pais update(Pais pais) {
		return repo.save(pais);
	}

	@Override
	public void delete(Integer id) {
		Pais pais = findById(id);
		if(pais != null) {
			repo.delete(pais);
		}
	}

	@Override
	public List<Pais> listAll() {
		return repo.findAll();
	}

	@Override
	public Pais findById(Integer id) {
		Optional<Pais> pais = repo.findById(id);
		return pais.orElse(null);
	}

	@Override
	public Pais findByNameEqualsIgnoreCase(String name) {
		Optional<Pais> pais = repo.findByNameEqualsIgnoreCase(name);
		return pais.orElse(null);
	}
}
