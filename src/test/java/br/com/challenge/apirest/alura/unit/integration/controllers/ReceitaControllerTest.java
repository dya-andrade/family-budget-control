package br.com.challenge.apirest.alura.unit.integration.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.challenge.apirest.alura.testcontainers.AbstractIntegrationTest;
import br.com.challenge.apirest.alura.vo.ReceitaVO;
import br.com.challenge.apirest.alura.vo.security.TokenVO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // port 8888
@TestMethodOrder(OrderAnnotation.class) 
@TestInstance(Lifecycle.PER_CLASS) //  cria apenas uma instância da classe de teste e reutilizá-la entre os testes.
@AutoConfigureMockMvc //habilita e config MockMvc
public class ReceitaControllerTest extends AbstractIntegrationTest { // TestContainers
	
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	private static URI uri;
	
	private static int id = 2;
	
	private static TokenVO tokenVO;


	@BeforeAll // executado antes de cada método
	void setUp() throws Exception {		
		uri = new URI("/budget-control/receitas");

		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	
	private String jsonReceita() throws JSONException {
		JSONObject json = new JSONObject();
		
		json.put("descricao", "Mesada");
		json.put("data", "17-08-2022");
		json.put("valor", "500.00");
		
		return json.toString();
	}
	
	private String jsonCredenciais() throws JSONException {
		JSONObject json = new JSONObject();
		
		//PERFIL USUARIO
		json.put("email", "dyane_araujo@outlook.com");
		json.put("senha", "senha123senha");
		
		return json.toString();
	}
	
	private void assertVO(ReceitaVO vo) {
		assertNotNull(vo);
		assertNotNull(vo.getDescricao());
		assertNotNull(vo.getData());
		assertNotNull(vo.getValor());
						
		assertEquals("Mesada", vo.getDescricao());
		assertEquals(LocalDate.of(2022, 8, 17), vo.getData());
		assertEquals(500.0, vo.getValor());
	}
	
	@Test
	@Order(0) //Simulando CONTROLLER
	public void testAutenticacao() throws Exception {
		
		URI uriAuth = new URI("/budget-control/auth/signin");
		
		String content = mockMvc.perform(
				MockMvcRequestBuilders
				.post(uriAuth)
				.content(jsonCredenciais())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn().getResponse().getContentAsString();
		
		tokenVO = objectMapper.readValue(content, TokenVO.class);
		
		assertNotNull(tokenVO);
	}
	
	@Test
	@Order(1) //Simulando CONTROLLER
	public void testCreateIsSuccess() throws Exception {
		
		String content = mockMvc.perform(
				MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", "Bearer " + tokenVO.getAccessToken())
				.content(jsonReceita())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn().getResponse().getContentAsString();
		
		ReceitaVO receitaVO = objectMapper.readValue(content, ReceitaVO.class);
		
		assertVO(receitaVO);
	}

	@Test
	@Order(2) 
	public void testCreateIsError() throws Exception {		
		//receita duplicada
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", "Bearer " + tokenVO.getAccessToken())
				.content(jsonReceita())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test
	@Order(3) 
	public void testFindAll() throws Exception {				
		String content = mockMvc.perform(
				MockMvcRequestBuilders
				.get(uri)
				.header("Authorization", "Bearer " + tokenVO.getAccessToken())
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		
        JSONObject jsonObject = new JSONObject(content);
		
		List<ReceitaVO> receitas = objectMapper.readValue(jsonObject.getString("content"), new TypeReference<List<ReceitaVO>>(){});
		
		assertFalse(receitas.isEmpty());
		
		assertVO(receitas.get(0));
	}
	
	@Test
	@Order(4) 
	public void testFindAllAndDescricao() throws Exception {				
		String content = mockMvc.perform(
				MockMvcRequestBuilders
				.get(uri)
				.header("Authorization", "Bearer " + tokenVO.getAccessToken())
				.queryParam("descricao", "Mesada")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		
        JSONObject jsonObject = new JSONObject(content);
		
		List<ReceitaVO> receitas = objectMapper.readValue(jsonObject.getString("content"), new TypeReference<List<ReceitaVO>>(){});
		
		assertFalse(receitas.isEmpty());
		
		assertVO(receitas.get(0));
	}
	
	@Test
	@Order(5) 
	public void testFindById() throws Exception {	
				
		uri = new URI("/budget-control/receitas/" + id); 
		
		String content = mockMvc.perform(
				MockMvcRequestBuilders
				.get(uri)
				.header("Authorization", "Bearer " + tokenVO.getAccessToken())
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn().getResponse().getContentAsString();
		
		ReceitaVO receitaVO = objectMapper.readValue(content, ReceitaVO.class);
		
		assertVO(receitaVO);
	}
	
	@Test
	@Order(6) 
	public void testFindByMonth() throws Exception {	
		
		uri = new URI("/budget-control/receitas/2022/8"); 

		
		String content = mockMvc.perform(
				MockMvcRequestBuilders
				.get(uri)
				.header("Authorization", "Bearer " + tokenVO.getAccessToken())
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn().getResponse().getContentAsString();
		
		List<ReceitaVO> receitas = objectMapper.readValue(content, new TypeReference<List<ReceitaVO>>(){});
		
		assertVO(receitas.get(1));
	}
	
	@Test
	@Order(7)
	public void testUpdateIsSuccess() throws Exception {	
				
		uri = new URI("/budget-control/receitas/" + id); 
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.put(uri)
				.header("Authorization", "Bearer " + tokenVO.getAccessToken())
				.content(jsonReceita())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@Test
	@Order(8)
	public void testDeleteIsErrorAcessRole() throws Exception {	
				
		//sem perfil de acesso para delete
		
		uri = new URI("/budget-control/receitas/" + id); 
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.delete(uri)
				.header("Authorization", "Bearer " + tokenVO.getAccessToken())
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	private String jsonCredenciaisAdmin() throws JSONException {
		JSONObject json = new JSONObject();
		
		//PERFIL ADMIN
		json.put("email", "dyane.aaraujo@gmail.com");
		json.put("senha", "senha123senha");
		
		return json.toString();
	}
	
	@Test
	@Order(9) //Simulando CONTROLLER
	public void testAutenticacaoRoleAdmin() throws Exception {
		
		URI uriAuth = new URI("/budget-control/auth/signin");
		
		String content = mockMvc.perform(
				MockMvcRequestBuilders
				.post(uriAuth)
				.content(jsonCredenciaisAdmin())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn().getResponse().getContentAsString();
		
		tokenVO = objectMapper.readValue(content, TokenVO.class);
		
		assertNotNull(tokenVO);
	}
	
	@Test
	@Order(10)
	public void testDeleteIsSuccess() throws Exception {	
						
		uri = new URI("/budget-control/receitas/" + id); 
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.delete(uri)
				.header("Authorization", "Bearer " + tokenVO.getAccessToken())
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@Test
	@Order(11)
	public void testDeleteIsError() throws Exception {	
		
		//ID não encontrado
				
		uri = new URI("/budget-control/receitas/" + id); 
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.delete(uri)
				.header("Authorization", "Bearer " + tokenVO.getAccessToken())
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
}
