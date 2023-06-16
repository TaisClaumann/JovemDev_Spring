package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTests;
import br.com.trier.spring_matutino.domain.User;
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
		assertEquals("Usuario Test 1", usuario.getNome());
		assertEquals("test@teste.com.br", usuario.getEmail());
		assertEquals("123", usuario.getPassword());
	}
	
	@Test
	@DisplayName("Teste buscar usuário por id inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdNonExistentTest() {
		var usuario = service.findById(10);
		assertThat(usuario).isNull();
	}
	
	@Test
	@DisplayName("Teste listar usuarios")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void listAllTest() {
		List<User> usuarios = service.listAll();
		assertEquals(2, usuarios.size());
		assertEquals("Usuario Test 1", usuarios.get(0).getNome());	
	}
	
	@Test
	@DisplayName("Teste salvar usuario")
	void salvarTest() {
		User user = new User(null, "Usuario Test 3", "test3@teste.com.br", "123");
		service.salvar(user);
		List<User> usuarios = service.listAll();
		assertEquals(1, usuarios.size());
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
	@DisplayName("Teste alterar usuario")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updateTest() {
		User user = new User(1, "Usuario Test", "test@teste.com.br", "123");
		service.update(user);
		List<User> usuarios = service.listAll();
		assertEquals("Usuario Test", usuarios.get(0).getNome());
	}
	
	@Test
	@DisplayName("Teste listar usuario pelo nome")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByNomeTest() {
		List<User> usuarios = service.findByNome("Usuario Test 1");
		assertEquals(1, usuarios.size());
		usuarios = service.findByNome("4");
		assertEquals(0, usuarios.size());
	}
}
