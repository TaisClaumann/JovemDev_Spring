package br.com.trier.spring_matutino.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import br.com.trier.spring_matutino.domain.dto.UserDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/resources/sqls/usuario.sql")
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<UserDTO> getUser(String url) { //converte o json
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = getTokens();
		headers.set("Authorization", "Bearer " + token);
		return rest.getForEntity(url, UserDTO.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<UserDTO>> getUsers(String url) {//conversão mais de um usuário
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = getTokens();
		headers.set("Authorization", "Bearer " + token);
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
		});
	}
	
//	@Test
//	@DisplayName("Buscar por id")
//	public void testGetOk() {
//		//String token =  getToken();
//		ResponseEntity<UserDTO> response = getUser("/user/1");
//		assertEquals(response.getStatusCode(), HttpStatus.OK);
//
//		UserDTO user = response.getBody();
//		assertEquals("Usuario Test 1", user.getName());
//	}
//
	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void testGetNotFound() {
		ResponseEntity<UserDTO> response = getUser("/user/3");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
//	@Test
//	@DisplayName("Cadastrar usuário")
//	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
//	public void testCreateUser() {
//		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
//		
//		UserDTO dto = new UserDTO(null, "Will", "will@gmail.com", "123", "ADMIN");
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		
//		String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3aWxsQGdtYWlsLmNvbSIsImlhdCI6MTY4Nzc5MDUwNCwiZXhwIjoxNjg3NzkyMzA0fQ.HAgZhMZaCPiSBy3poM_KiRvcaHiKqkEulE_4pjICcpA";
//		headers.set("Authorization", "Bearer " + accessToken);
//		
//		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
//		ResponseEntity<UserDTO> responseEntity = rest.exchange(
//	            "/user", 
//	            HttpMethod.POST,  
//	            requestEntity,    
//	            UserDTO.class   
//	    );
//		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
//		UserDTO user = responseEntity.getBody();
//		assertEquals("Will", user.getName());
//	}
	
	@Test
	@DisplayName("Cadastrar usuário")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "nome", "email", "senha", "ADMIN,USER");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = getTokens();
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
	            "/user", 
	            HttpMethod.POST,  
	            requestEntity,    
	            UserDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("nome", user.getName());
	}
	
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public String getTokens() {
		LoginDTO loginDTO = new LoginDTO ("test2@teste.com.br", "123");
		HttpHeaders headers = new HttpHeaders ();
		headers.setContentType(MediaType.APPLICATION_JSON) ;
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<> (loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity, String.class);
		assertEquals (responseEntity.getStatusCode (), HttpStatus. OK) ;
		String token = responseEntity.getBody();
		return token;
	}
	
	@Test
	@DisplayName("Obter Token")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void testGetToken() {
		LoginDTO loginDTO = new LoginDTO("test2@teste.com.br", "123");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST,
				requestEntity, String.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		
	}
	
	@Test
	@DisplayName("Teste listar todos")
	public void listAllTest() {
		ResponseEntity<List<UserDTO>> response = getUsers("/user");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste update")
	public void updateTest() {
		UserDTO dto = new UserDTO(1, "Usuario Test", "test@teste.com.br", "123", "ADMIN");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/user/1", HttpMethod.PUT, requestEntity, UserDTO.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("Usuario Test", user.getName());
	}
	
	@Test
	@DisplayName("Teste delete")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void deleteTest() {
		ResponseEntity<Void> response = rest.exchange("/user/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste achar usuario pelo nome")
	public void findByNameTest() {
		ResponseEntity<UserDTO> response = getUser("/user/name/Usuario Test 1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste achar usuario pelo email")
	public void findByEmailTest() {
		ResponseEntity<UserDTO> response = getUser("/user/email/test2@teste.com.br");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste achar usuarios pelo nome com like ignorando o case")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void findByNameContainsIgnoreCaseTest() {
		ResponseEntity<List<UserDTO>> response = getUsers("/user/like/usuario");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
