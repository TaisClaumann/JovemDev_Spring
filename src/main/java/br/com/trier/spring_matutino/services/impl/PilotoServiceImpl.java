package br.com.trier.spring_matutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Piloto;
import br.com.trier.spring_matutino.repositories.PilotoRepository;
import br.com.trier.spring_matutino.services.PilotoService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;

@Service
public class PilotoServiceImpl implements PilotoService{

	@Autowired
	private PilotoRepository repository;
	
	@Override
	public Piloto insert(Piloto piloto) {
		validaPiloto(piloto);
		return repository.save(piloto);
	}
	
	private void validaPiloto(Piloto piloto) {
		if(piloto == null) {
			throw new ViolacaoDeIntegridade("O piloto está nulo");
		} else if(piloto.getNome() == null || piloto.getNome().isBlank()) {
			throw new ViolacaoDeIntegridade("Preencha o nome do piloto");
		}
	}

	@Override
	public Piloto update(Piloto piloto) {
		if(!listAll().contains(piloto)) {
			throw new ObjetoNaoEncontrado("Esse piloto não existe");
		}
		return insert(piloto);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public List<Piloto> listAll() {
		if(repository.findAll().size() == 0) {
			throw new ObjetoNaoEncontrado("Não há pilotos cadastrados");
		}
		return repository.findAll();
	}

	@Override
	public Piloto findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Piloto id %s não existe".formatted(id)));
	}

	@Override
	public List<Piloto> findByNomeContainsIgnoreCaseOrderByNome(String nome) {
		List<Piloto> pilotos = repository.findByNomeContainsIgnoreCaseOrderByNome(nome);
		if(pilotos.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há pilotos com " + nome);
		}
		return pilotos;
	}

	@Override
	public List<Piloto> findByPais(Pais pais) {
		List<Piloto> pilotos = repository.findByPais(pais);
		if(pilotos.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há pilotos do(a) " + pais.getName());
		}
		return pilotos;
	}

	@Override
	public List<Piloto> findByEquipe(Equipe equipe) {
		List<Piloto> pilotos = repository.findByEquipe(equipe);
		if(pilotos.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há pilotos do(a) equipe " + equipe.getName());
		}
		return pilotos;
	}
}
