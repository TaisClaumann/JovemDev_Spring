package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTests;
import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;
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
	@DisplayName("Teste inserir equipe com nome duplicado")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void insertDuplicatedTest() {
		var equipe = new Equipe(null, "Batman");
		var exception = assertThrows(ViolacaoDeIntegridade.class, () -> service.insert(equipe));
		assertEquals("Essa equipe já existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir equipe com atributos vazios")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void insertEmptyTest() {
		var equipe = new Equipe(null, "");
		var exception = assertThrows(ViolacaoDeIntegridade.class, () -> service.insert(equipe));
		assertEquals("Preencha os dados da equipe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar equipe")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void updateTest() {
		var equipe = new Equipe(1, "Teste");
		service.update(equipe);
		List<Equipe> equipes = service.listAll();
		assertEquals("Teste", equipes.get(1).getName());
	}
	
	@Test
	@DisplayName("Teste alterar equipe nome duplicado")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void updateDuplicatedTest() {
		var equipe = new Equipe(1, "Batman");
		var exception = assertThrows(ViolacaoDeIntegridade.class, () -> service.insert(equipe));
		assertEquals("Essa equipe já existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar equipe pelo id")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByIdTest() {
		var equipe = service.findById(1);
		assertThat(equipe).isNotNull();
		assertEquals("Incriveis", equipe.getName());
	}
	
	@Test
	@DisplayName("Teste buscar equipe por id inexistente")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(4));
		assertEquals("Equipe 4 não encontrada", exception.getMessage());
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
	@DisplayName("Teste deletar equipe inexistente")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void deleteNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.delete(4));
		assertEquals("Equipe 4 não encontrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar equipes")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void listAllTest() {
		List<Equipe> equipes = service.listAll();
		assertEquals(2, equipes.size());
	}
	
	@Test
	@DisplayName("Teste listar equipes lista vazia")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.listAll());
		assertEquals("Não há equipes cadastradas", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar equipe pelo nome")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByNomeTest() {
		Equipe equipe = service.findByName("Incriveis");
		assertThat(equipe).isNotNull();
		assertEquals("Incriveis", equipe.getName());
	}
	
	@Test
	@DisplayName("Teste buscar equipe por nome inexistente")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByNomeNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByName("teste"));
		assertEquals("Equipe teste não encontrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar equipe pelo nome que contenha x letras")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByNameContainsIgnoreCaseTest() {
		List<Equipe> equipes = service.findByNameContainsIgnoreCase("cri");
		assertEquals(1, equipes.size());
	}
	
	@Test
	@DisplayName("Teste buscar equipe pelo nome que contenha x letras sem achar")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByNameContainsIgnoreCaseNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByNameContainsIgnoreCase("teste"));
		assertEquals("Não há equipes com teste", exception.getMessage());
	}
}
