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
import br.com.trier.spring_matutino.ativ_dado.exceptions.ValorInvalidoException;
import br.com.trier.spring_matutino.domain.User;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;
import br.com.trier.spring_matutino.services.impl.UserServiceImpl;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests{
	
	@Autowired
	UserService service;
	
	@Test
	@DisplayName("Teste buscar usuário por id")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdTest() {
		var usuario = service.findById(1);
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("Usuario Test 1", usuario.getName());
		assertEquals("test@teste.com.br", usuario.getEmail());
		assertEquals("123", usuario.getPassword());
	}
	
	@Test
	@DisplayName("Teste buscar usuário por id inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(10));
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar usuarios")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void listAllTest() {
		List<User> usuarios = service.listAll();
		assertEquals(2, usuarios.size());
		assertEquals("Usuario Test 1", usuarios.get(0).getName());	
	}
	
	@Test
	@DisplayName("Teste listar usuarios com lista vazia")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.listAll());
		assertEquals("Não há usuários cadastrados", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste salvar usuario")
	void insertTest() {
		User user = new User(null, "Usuario Test 3", "test3@teste.com.br", "123");
		service.insert(user);
		List<User> usuarios = service.listAll();
		assertEquals(1, usuarios.size());
	}
	
	@Test
	@DisplayName("Teste salvar usuario com email duplicado")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void insertDuplicatedEmailTest() {
		User user = new User(null, "Usuario Test 3", "test@teste.com.br", "123");
		var exception = assertThrows(ViolacaoDeIntegridade.class, () -> service.insert(user));
		assertEquals("Esse email já existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste salvar usuario com dados vazios")
	void insertEmptyUserTest() {
		User user = new User(null, "", "", "");
		var exception = assertThrows(ViolacaoDeIntegridade.class, () -> service.insert(user));
		assertEquals("O nome está vazio", exception.getMessage());
		
		User user2 = new User(null, "teste", "", "");
		var exception2 = assertThrows(ViolacaoDeIntegridade.class, () -> service.insert(user2));
		assertEquals("O email está vazio", exception2.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar usuario")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void deleteTest() {
		service.delete(1);
		List<User> usuarios = service.listAll();
		assertEquals(1, usuarios.size());
	}
	
	@Test
	@DisplayName("Teste deletar usuario inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void deleteInexistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.delete(3));
		assertEquals("Usuário 3 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar usuario")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updateTest() {
		User user = new User(1, "Usuario Test", "test@teste.com.br", "123");
		service.update(user);
		List<User> usuarios = service.listAll();
		assertEquals("Usuario Test", usuarios.get(0).getName());
	}
	
	@Test
	@DisplayName("Teste alterar usuario com email duplicado")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updateDuplicatedEmailTest() {
		User user = new User(2, "Usuario Test 2", "test@teste.com.br", "123");
		var exception = assertThrows(ViolacaoDeIntegridade.class, () -> service.update(user));
		assertEquals("Esse email já existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar usuario inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updateInvalidTest() {
		User user = new User(5, "Usuario Test 2", "test@teste.com.br", "123");
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.update(user));
		assertEquals("Esse usuário não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar usuario pelo nome")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByNomeTest() {
		User user = service.findByName("Usuario Test 1");
		assertEquals(1, user.getId());
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByName("4"));
		assertEquals("Usuário 4 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar usuario por nome que contenha x letras")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByNomeContainsIgnoreCaseTest() {
		List<User> usuarios = service.findByNameContainsIgnoreCase("Usuario");
		assertEquals(2, usuarios.size());
	}
	
	@Test
	@DisplayName("Teste buscar usuario por nome inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByNomeContainsIgnoreCaseNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByNameContainsIgnoreCase("4"));
		assertEquals("Não há nenhum usuário com o nome 4", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar usuário pelo email")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByEmailTest() {
		var usuario = service.findByEmail("test@teste.com.br");
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("Usuario Test 1", usuario.getName());
		assertEquals("123", usuario.getPassword());
	}
}
