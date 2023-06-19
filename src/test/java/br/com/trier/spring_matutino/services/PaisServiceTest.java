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
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;
import jakarta.transaction.Transactional;

@Transactional
public class PaisServiceTest extends BaseTests{
	
	@Autowired
	PaisService service;
	
	@Test
	@DisplayName("Teste inserir um pais")
	void insertTest() {
		var pais = new Pais(null, "Brasil");
		service.insert(pais);
		List<Pais> paises = service.listAll();
		assertEquals(1, paises.size());
		assertEquals("Brasil", paises.get(0).getName());
	}
	
	@Test
	@DisplayName("Teste inserir um pais com nome duplicado")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void insertDuplicatedTest() {
		var pais = new Pais(null, "Brasil");
		var exception = assertThrows(ViolacaoDeIntegridade.class, () -> service.insert(pais));
		assertEquals("Esse país já existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir um pais invalido")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void insertInvalidTest() {
		var pais = new Pais(null, "");
		var exception = assertThrows(ViolacaoDeIntegridade.class, () -> service.insert(pais));
		assertEquals("Preencha os dados do pais", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar um pais")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updateTest() {
		var pais = new Pais(1, "Pais");
		service.update(pais);
		List<Pais> paises = service.listAll();
		assertEquals("Pais", paises.get(1).getName());
	}
	
	@Test
	@DisplayName("Teste alterar um pais com nome duplicado")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updateDuplicatedTest() {
		var pais = new Pais(1, "Argentina");
		var exception = assertThrows(ViolacaoDeIntegridade.class, () -> service.update(pais));
		assertEquals("Esse país já existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar um pais")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deleteTest() {
		service.delete(1);
		List<Pais> paises = service.listAll();
		assertEquals(1, paises.size());
	}
	
	@Test
	@DisplayName("Teste deletar um pais com id inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deleteInexistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.delete(3));
		assertEquals("Pais 3 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar paises")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void listAllTest() {
		List<Pais> paises = service.listAll();
		assertEquals(2, paises.size());
	}
	
	@Test
	@DisplayName("Teste listar paises lista vazia")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.listAll());
		assertEquals("Não há paises cadastrados", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar pais por id")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByIdTest() {
		var pais = service.findById(1);
		assertThat(pais).isNotNull();
		assertEquals("Brasil", pais.getName());
	}
	
	@Test
	@DisplayName("Teste buscar pais por id inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(3));
		assertEquals("Pais 3 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar pais pelo nome ignorando o case")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNomeTest() {
		var pais = service.findByNameEqualsIgnoreCase("brasil");
		assertThat(pais).isNotNull();
		assertEquals("Brasil", pais.getName());
	}
	
	@Test
	@DisplayName("Teste buscar pais pelo nome ignorando o case sem achar")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNomeNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByNameEqualsIgnoreCase("italia"));
		assertEquals("Pais italia não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar pais pelo nome que contenha x letras")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNameContainsIgnoreCaseTest() {
		var paises = service.findByNameContainsIgnoreCase("il");
		assertThat(paises).isNotNull();
		assertEquals(1, paises.size());
	}
	
	@Test
	@DisplayName("Teste buscar pais pelo nome que contenha x letras sem achar")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNameContainsIgnoreCaseNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByNameContainsIgnoreCase("to"));
		assertEquals("Não há paises com to", exception.getMessage());
	}
}
