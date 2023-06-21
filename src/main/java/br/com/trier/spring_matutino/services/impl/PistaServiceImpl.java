package br.com.trier.spring_matutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Pista;
import br.com.trier.spring_matutino.repositories.PistaRepository;
import br.com.trier.spring_matutino.services.PistaService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;

@Service
public class PistaServiceImpl implements PistaService{
	
	@Autowired
	private PistaRepository repository;

	@Override
	public Pista insert(Pista pista) {
		validaPista(pista);
		return repository.save(pista);
	}
	
	private void validaPista(Pista pista) {
		if(pista == null) {
			throw new ViolacaoDeIntegridade("A pista está nula");
		} else if(pista.getTamanho() == null || pista.getTamanho() <= 0) {
			throw new ViolacaoDeIntegridade("Tamanho inválido");
		}
	}

	@Override
	public Pista update(Pista pista) {
		if(!listAll().contains(pista)) {
			throw new ObjetoNaoEncontrado("Essa pista não existe");
		}
		return insert(pista);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public List<Pista> listAll() {
		List<Pista> lista = repository.findAll();
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há pistas cadastradas");
		}
		return lista;
	}

	@Override
	public Pista findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Pista id %s não existe".formatted(id)));
	}

	@Override
	public List<Pista> findByTamanhoBetween(Integer tamInicial, Integer tamFinal) {
		List<Pista> lista = repository.findByTamanhoBetween(tamInicial, tamFinal);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma pista encontrada com os tamanhos %s e %s".formatted(tamInicial, tamFinal));
		}
		return lista;
	}

	@Override
	public List<Pista> findByPaisOrderByTamanhoDesc(Pais pais) {
		List<Pista> lista = repository.findByPaisOrderByTamanhoDesc(pais);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há pistas desse pais");
		}
		return lista;
	}
}
