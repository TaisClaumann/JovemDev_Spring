package br.com.trier.spring_matutino.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.spring_matutino.SpringMatutinoApplication;
import br.com.trier.spring_matutino.config.jwt.LoginDTO;
import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.domain.Pais;

@ActiveProfiles("test")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EquipeResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	private HttpHeaders getHeaders(String email, String password){
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		String token = responseEntity.getBody();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		return headers;
	}
	
	private ResponseEntity<Equipe> getEquipe(String url) {
		return rest.exchange(url, 
							 HttpMethod.GET, 
							 new HttpEntity<>(getHeaders("test@teste.com.br", "123")), 
							 Equipe.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<Equipe>> getEquipes(String url) {
		return rest.exchange(url, 
							 HttpMethod.GET, 
							 new HttpEntity<>(getHeaders("test@teste.com.br", "123")), 
							 new ParameterizedTypeReference<List<Equipe>>() {
		});
	}

	@Test
	@DisplayName("Teste inserir equipe com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void insertAdminTest() {
		Equipe equipe = new Equipe(null, "teste");
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Equipe> requestEntity = new HttpEntity<>(equipe, headers);
		ResponseEntity<Equipe> responseEntity = rest.exchange("/equipe", HttpMethod.POST, requestEntity, Equipe.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Teste inserir equipe com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void insertUserTest() {
		Equipe equipe = new Equipe(null, "teste");
		HttpHeaders headers = getHeaders("test2@teste.com.br", "123");
		HttpEntity<Equipe> requestEntity = new HttpEntity<>(equipe, headers);
		ResponseEntity<Equipe> responseEntity = rest.exchange("/equipe", HttpMethod.POST, requestEntity, Equipe.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	@DisplayName("Teste alterar equipe com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void updateAdminTest() {
		Equipe equipe = new Equipe(1, "teste");
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Equipe> requestEntity = new HttpEntity<>(equipe, headers);
		ResponseEntity<Equipe> responseEntity = rest.exchange("/equipe/1", HttpMethod.PUT, requestEntity, Equipe.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Teste alterar equipe com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void updateUserTest() {
		Equipe equipe = new Equipe(1, "teste");
		HttpHeaders headers = getHeaders("test2@teste.com.br", "123");
		HttpEntity<Equipe> requestEntity = new HttpEntity<>(equipe, headers);
		ResponseEntity<Equipe> responseEntity = rest.exchange("/equipe/1", HttpMethod.PUT, requestEntity, Equipe.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	@DisplayName("Teste deletar equipe com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void deleteAdminTest() {
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> response = rest.exchange("/equipe/1", HttpMethod.DELETE, requestEntity, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste deletar equipe com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void deleteUserTest() {
		HttpHeaders headers = getHeaders("test2@teste.com.br", "123");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> response = rest.exchange("/equipe/1", HttpMethod.DELETE, requestEntity, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	@DisplayName("Teste buscar equipe pelo id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByIdTest() {
		ResponseEntity<Equipe> response = getEquipe("/equipe/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("Incriveis", response.getBody().getName());
	}
	
	@Test
	@DisplayName("Teste buscar equipe pelo nome")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByNameTest() {
		ResponseEntity<Equipe> response = getEquipe("/equipe/name/Incriveis");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("Incriveis", response.getBody().getName());
	}
	
	@Test
	@DisplayName("Teste buscar equipe pelo nome com like")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByNameContainsIgnoreCaseTest() {
		ResponseEntity<List<Equipe>> response = getEquipes("/equipe/like/cri");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar todas as equipes com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void listAllAdminTest() {
		ResponseEntity<List<Equipe>> response = rest.exchange(
				"/equipe", 
				HttpMethod.GET,
				new HttpEntity<>(getHeaders("test@teste.com.br", "123")),
				new ParameterizedTypeReference<List<Equipe>>() {} 
		);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar todas as equipes com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void listAllUserTest() {
		ResponseEntity<List<Equipe>> response = rest.exchange(
				"/equipe", 
				HttpMethod.GET,
				new HttpEntity<>(getHeaders("test2@teste.com.br", "123")),
				new ParameterizedTypeReference<List<Equipe>>() {} 
		);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
