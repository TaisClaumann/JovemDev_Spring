package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.repositories.CampeonatoRepository;
import br.com.trier.spring_matutino.services.CampeonatoService;

@Service
public class CampeonatoServiceImpl implements CampeonatoService{
	
	@Autowired
	private CampeonatoRepository repo;

	@Override
	public Campeonato insert(Campeonato campeonato) {
		return repo.save(campeonato);
	}

	@Override
	public Campeonato update(Campeonato campeonato) {
		return repo.save(campeonato);
	}

	@Override
	public List<Campeonato> listAll() {
		return repo.findAll();
	}

	@Override
	public Campeonato findById(Integer id) {
		Optional<Campeonato> camp = repo.findById(id);
		return camp.orElse(null);
	}

	@Override
	public void delete(Integer id) {
		Campeonato camp = findById(id);
		if(camp != null) {
			repo.delete(camp);
		}
	}
}
