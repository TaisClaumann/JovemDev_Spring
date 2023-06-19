package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.repositories.EquipeRepository;
import br.com.trier.spring_matutino.services.EquipeService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;

@Service
public class EquipeServiceImpl implements EquipeService{
	
	@Autowired
	private EquipeRepository repo;

	@Override
	public Equipe insert(Equipe equipe) {
		if(validaEquipe(equipe)) {
			Optional<Equipe> equipeOptional = repo.findByName(equipe.getName());
			if(equipeOptional.isPresent()) {
				Equipe e = equipeOptional.get();
				if(equipe.getId() != e.getId()) {
					throw new ViolacaoDeIntegridade("Essa equipe já existe");
				}
			}
		}
		return repo.save(equipe);
	}

	private boolean validaEquipe(Equipe equipe) {
		if(equipe == null || equipe.getName().isBlank() || equipe.getName() == null) {
			throw new ViolacaoDeIntegridade("Preencha os dados da equipe");
		} 
		return true;
	}
	
	@Override
	public Equipe update(Equipe equipe) {
		return insert(equipe);
	}

	@Override
	public Equipe findById(Integer id) {
		Optional<Equipe> equipe = repo.findById(id);
		return equipe.orElseThrow(() -> new ObjetoNaoEncontrado("Equipe %s não encontrada".formatted(id)));
	}

	@Override
	public void delete(Integer id) {
		Equipe equipe = findById(id);
		repo.delete(equipe);
	}

	@Override
	public List<Equipe> listAll() {
		List<Equipe> equipes = repo.findAll();
		if(equipes.size()==0) {
			throw new ObjetoNaoEncontrado("Não há equipes cadastradas");
		}
		return equipes;
	}

	@Override
	public Equipe findByName(String name) {
		Optional<Equipe> equipe = repo.findByName(name);
		return equipe.orElseThrow(() -> new ObjetoNaoEncontrado("Equipe %s não encontrada".formatted(name)));
	}

	@Override
	public List<Equipe> findByNameContainsIgnoreCase(String name) {
		List<Equipe> equipes = repo.findByNameContainsIgnoreCase(name);
		if(equipes.size()==0) {
			throw new ObjetoNaoEncontrado("Não há equipes com " + name);
		}
		return equipes;
	}
}
