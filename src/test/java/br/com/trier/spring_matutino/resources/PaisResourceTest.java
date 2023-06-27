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
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.dto.UserDTO;

@ActiveProfiles("test")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaisResourceTest {
	
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
	
	private ResponseEntity<Pais> getPais(String url) {
		return rest.exchange(url, 
							 HttpMethod.GET, 
							 new HttpEntity<>(getHeaders("test@teste.com.br", "123")), 
							 Pais.class);
				
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<Pais>> getPaises(String url) {
		return rest.exchange(url, 
							 HttpMethod.GET, 
							 new HttpEntity<>(getHeaders("test@teste.com.br", "123")), 
							 new ParameterizedTypeReference<List<Pais>>() {
		});
	}

	@Test
	@DisplayName("Teste inserir pais com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void insertAdminTest() {
		Pais pais = new Pais(null, "teste");
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Pais> requestEntity = new HttpEntity<>(pais, headers);
		ResponseEntity<Pais> responseEntity = rest.exchange("/pais", HttpMethod.POST, requestEntity, Pais.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Teste inserir pais com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void insertUserTest() {
		Pais pais = new Pais(null, "teste");
		HttpHeaders headers = getHeaders("test2@teste.com.br", "123");
		HttpEntity<Pais> requestEntity = new HttpEntity<>(pais, headers);
		ResponseEntity<Pais> responseEntity = rest.exchange("/pais", HttpMethod.POST, requestEntity, Pais.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	@DisplayName("Teste alterar pais com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updateAdminTest() {
		Pais pais = new Pais(1, "teste");
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Pais> requestEntity = new HttpEntity<>(pais, headers);
		ResponseEntity<Pais> responseEntity = rest.exchange("/pais/1", HttpMethod.PUT, requestEntity, Pais.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Teste alterar pais com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updateUserTest() {
		Pais pais = new Pais(1, "teste");
		HttpHeaders headers = getHeaders("test2@teste.com.br", "123");
		HttpEntity<Pais> requestEntity = new HttpEntity<>(pais, headers);
		ResponseEntity<Pais> responseEntity = rest.exchange("/pais/1", HttpMethod.PUT, requestEntity, Pais.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	@DisplayName("Teste deletar pais com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deleteAdminTest() {
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> response = rest.exchange("/pais/1", HttpMethod.DELETE, requestEntity, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste deletar pais com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deleteUserTest() {
		HttpHeaders headers = getHeaders("test2@teste.com.br", "123");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> response = rest.exchange("/pais/1", HttpMethod.DELETE, requestEntity, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	@DisplayName("Teste listar todos os paises com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void listAllAdminTest() {
		ResponseEntity<List<Pais>> response = rest.exchange(
				"/pais", 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("test@teste.com.br", "123")),
				new ParameterizedTypeReference<List<Pais>>() {} 
		);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(2, response.getBody().size());
	}
	
	@Test
	@DisplayName("Teste listar todos os paises com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void listAllUserTest() {
		ResponseEntity<List<Pais>> response = rest.exchange(
				"/pais", 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("test2@teste.com.br", "123")),
				new ParameterizedTypeReference<List<Pais>>() {} 
		);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(2, response.getBody().size());
	}
	
	@Test
	@DisplayName("Teste buscar pais pelo id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByIdTest() {
		ResponseEntity<Pais> response = getPais("/pais/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		Pais pais = response.getBody();
		assertEquals("Brasil", pais.getName());
	}
	
	@Test
	@DisplayName("Teste buscar pais pelo nome ignorando o case")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNameEqualsIgnoreCaseTest() {
		ResponseEntity<Pais> response = getPais("/pais/name/brasil");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		Pais pais = response.getBody();
		assertEquals("Brasil", pais.getName());
	}
	
	@Test
	@DisplayName("Teste buscar pais pelo nome com like")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNameContainsIgnoreCaseTest() {
		ResponseEntity<List<Pais>> response = getPaises("/pais/like/ra");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
