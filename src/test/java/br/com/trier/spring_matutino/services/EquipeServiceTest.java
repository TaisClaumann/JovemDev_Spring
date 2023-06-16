package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTests;
import br.com.trier.spring_matutino.domain.Equipe;
import jakarta.transaction.Transactional;

@Transactional
public class EquipeServiceTest extends BaseTests{
	
	@Autowired
	EquipeService service;
	
	@Test
	@DisplayName("Teste inserir equipe")
	void insertTest() {
		var equipe = new Equipe(null, "Equipe1");
		service.insert(equipe);
		List<Equipe> equipes = service.listAll();
		assertEquals(1, equipes.size());
	}

	@Test
	@DisplayName("Teste alterar equipe")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void updateTest() {
		var equipe = new Equipe(1, "Teste");
		service.update(equipe);
		List<Equipe> equipes = service.listAll();
		assertEquals("Teste", equipes.get(1).getNome());
	}
	
	@Test
	@DisplayName("Teste buscar equipe pelo id")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByIdTest() {
		var equipe = service.findById(1);
		assertThat(equipe).isNotNull();
		assertEquals("Incriveis", equipe.getNome());
	}
	
	@Test
	@DisplayName("Teste deletar equipe")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void deleteTest() {
		service.delete(1);
		List<Equipe> equipes = service.listAll();
		assertEquals(1, equipes.size());
	}
	
	@Test
	@DisplayName("Teste listar equipes")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void listAllTest() {
		List<Equipe> equipes = service.listAll();
		assertEquals(2, equipes.size());
	}
	
	@Test
	@DisplayName("Teste buscar equipe pelo nome")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByNomeTest() {
		List<Equipe> equipes = service.findByNome("Incriveis");
		assertEquals(1, equipes.size());
	}
}
