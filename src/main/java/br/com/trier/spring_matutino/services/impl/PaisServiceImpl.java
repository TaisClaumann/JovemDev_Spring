package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.repositories.PaisRepository;
import br.com.trier.spring_matutino.services.PaisService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;

@Service
public class PaisServiceImpl implements PaisService{
	
	@Autowired
	private PaisRepository repository;
	
	@Override
	public Pais insert(Pais pais) {
		if(validaPais(pais)) {
			Optional<Pais> paisOptional = repository.findByNameEqualsIgnoreCase(pais.getName());
			if(paisOptional.isPresent()) {
				Pais p = paisOptional.get();
				if(p.getId() != pais.getId()) {
					throw new ViolacaoDeIntegridade("Esse país já existe");
				}
			}
		}
		return repository.save(pais);
	}
	
	private boolean validaPais(Pais pais) {
		if(pais == null) {
			throw new ViolacaoDeIntegridade("O país está nulo");
		} else if (pais.getName().isBlank() || pais.getName() == null) {
			throw new ViolacaoDeIntegridade("Preencha os dados do país");
		}
		return true;
	}

	@Override
	public Pais update(Pais pais) {
		if(!listAll().contains(pais)) {
			throw new ObjetoNaoEncontrado("Esse país não existe");
		}
		return insert(pais);
	}

	@Override
	public void delete(Integer id) {
		Pais pais = findById(id);
		repository.delete(pais);
	}

	@Override
	public List<Pais> listAll() {
		List<Pais> paises = repository.findAll();
		if(paises.size()==0) {
			throw new ObjetoNaoEncontrado("Não há paises cadastrados");
		}
		return paises;
	}

	@Override
	public Pais findById(Integer id) {
		Optional<Pais> pais = repository.findById(id);
		return pais.orElseThrow(() -> new ObjetoNaoEncontrado("Pais %s não encontrado".formatted(id)));
	}

	@Override
	public Pais findByNameEqualsIgnoreCase(String name) {
		Optional<Pais> pais = repository.findByNameEqualsIgnoreCase(name);
		return pais.orElseThrow(() -> new ObjetoNaoEncontrado("Pais %s não encontrado".formatted(name)));
	}

	@Override
	public List<Pais> findByNameContainsIgnoreCase(String name) {
		List<Pais> paises = repository.findByNameContainsIgnoreCase(name);
		if(paises.size()==0) {
			throw new ObjetoNaoEncontrado("Não há paises com " + name);
		}
		return paises;
	}
}
