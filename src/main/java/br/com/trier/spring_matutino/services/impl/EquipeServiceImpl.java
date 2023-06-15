package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.repositories.EquipeRepository;
import br.com.trier.spring_matutino.services.EquipeService;

@Service
public class EquipeServiceImpl implements EquipeService{
	
	@Autowired
	private EquipeRepository repo;

	@Override
	public Equipe insert(Equipe equipe) {
		return repo.save(equipe);
	}

	@Override
	public Equipe update(Equipe equipe) {
		return repo.save(equipe);
	}

	@Override
	public Equipe findById(Integer id) {
		Optional<Equipe> equipe = repo.findById(id);
		return equipe.orElse(null);
	}

	@Override
	public void delete(Integer id) {
		Equipe equipe = findById(id);
		if(equipe != null) {
			repo.delete(equipe);
		}
	}

	@Override
	public List<Equipe> listAll() {
		return repo.findAll();
	}

}
