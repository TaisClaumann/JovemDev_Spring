package br.com.trier.spring_matutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTests;
import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Piloto;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;
import br.com.trier.spring_matutino.services.impl.PilotoServiceImpl;
import jakarta.transaction.Transactional;

@Transactional
public class PilotoServiceTest extends BaseTests{

	@Autowired
	private PilotoService service;
	
	@Test
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@DisplayName("Teste inserir piloto")
	void insertTest() {
		var piloto = new Piloto(null, "Bruno", new Pais(1, null), new Equipe(1, null));
		service.insert(piloto);
		assertEquals(1, service.listAll().size());
		assertEquals("Bruno", piloto.getNome());
	}
	
	@Test
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@DisplayName("Teste inserir piloto invalido")
	void insertInvalidTest() {
		var piloto = new Piloto(null, "", new Pais(1, null), new Equipe(1, null));
		var exception = assertThrows(ViolacaoDeIntegridade.class, () -> service.insert(piloto));
		assertEquals("Preencha o nome do piloto", exception.getMessage());
	}
	
	@Test
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@DisplayName("Teste alterar piloto")
	void updateTest() {
		var piloto = new Piloto(1, "Bruno", new Pais(1, null), new Equipe(1, null));
		service.update(piloto);
		List<Piloto> pilotos = service.listAll();
		assertEquals("Bruno", pilotos.get(0).getNome());
	}
	
	@Test
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@DisplayName("Teste alterar piloto invalido")
	void updateInvalidTest() {
		var piloto = new Piloto(5, "Bruno", new Pais(1, null), new Equipe(1, null));
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.update(piloto));
		assertEquals("Esse piloto não existe", exception.getMessage());
	}
	
	@Test
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@DisplayName("Teste deletar piloto")
	void deleteTest() {
		service.delete(1);
		assertEquals(3, service.listAll().size());
	}
	
	@Test
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@DisplayName("Teste deletar piloto invalido")
	void deleteInvalidTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.delete(5));
		assertEquals("Piloto id 5 não existe", exception.getMessage());
	}
	
	@Test
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@DisplayName("Teste listar todos os pilotos")
	void listAllTest() {
		assertEquals(4, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste listar pilotos com lista vazia")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.listAll());
		assertEquals("Não há pilotos cadastrados", exception.getMessage());
	}
	
	@Test
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@DisplayName("Teste buscar piloto pelo id")
	void findByIdTest() {
		var piloto = service.findById(1);
		assertEquals("Paulo", piloto.getNome());
	}
	
	@Test
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@DisplayName("Teste buscar piloto pelo id sem achar")
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(5));
		assertEquals("Piloto id 5 não existe", exception.getMessage());
	}
	
	@Test
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@DisplayName("Teste buscar piloto pelo nome ordenando de A-Z")
	void findByNomeContainsIgnoreCaseOrderByNomeTest() {
		var pilotos = service.findByNomeContainsIgnoreCaseOrderByNome("o");
		assertEquals(3, pilotos.size());
		assertEquals("Gustavo", pilotos.get(0).getNome());
	}
	
	@Test
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@DisplayName("Teste buscar piloto pelo nome ordenando de A-Z sem achar")
	void findByNomeContainsIgnoreCaseOrderByNomeNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByNomeContainsIgnoreCaseOrderByNome("bro"));
		assertEquals("Não há pilotos com bro", exception.getMessage());
	}
	
	@Test
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@DisplayName("Teste buscar pilotos pelo país")
	void findByPaisTest() {
		var pilotos = service.findByPais(new Pais(1, null));
		assertEquals(4, pilotos.size());
	}
	
	@Test
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@DisplayName("Teste buscar pilotos pelo país sem achar")
	void findByPaisNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByPais(new Pais(2, "Argentina")));
		assertEquals("Não há pilotos do(a) Argentina", exception.getMessage());
	}
	
	@Test
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@DisplayName("Teste buscar pilotos pela equipe")
	void findByEquipeTest() {
		var pilotos = service.findByEquipe(new Equipe(1, null));
		assertEquals(4, pilotos.size());
		assertEquals("Gustavo", pilotos.get(2).getNome());
	}
	
	@Test
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@DisplayName("Teste buscar pilotos pela equipe sem achar")
	void findByEquipeNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByEquipe(new Equipe(2, "Batman")));
		assertEquals("Não há pilotos do(a) equipe Batman", exception.getMessage());
	}
}
