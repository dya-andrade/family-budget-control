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

import br.com.challenge.apirest.alura.model.Categoria;
import br.com.challenge.apirest.alura.testcontainers.AbstractIntegrationTest;
import br.com.challenge.apirest.alura.vo.DespesaVO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // port 8888
@TestMethodOrder(OrderAnnotation.class) 
@TestInstance(Lifecycle.PER_CLASS) //  cria apenas uma instância da classe de teste e reutilizá-la entre os testes.
@AutoConfigureMockMvc //habilita e config MockMvc
public class DespesaControllerTest extends AbstractIntegrationTest { // TestContainers
	
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	private static URI uri;
	
	private static int id = 2;


	@BeforeAll // executado antes de cada método
	void setUp() throws Exception {		
		uri = new URI("/budget-control/despesas");

		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	
	private String jsonDespesa() throws JSONException {
		JSONObject json = new JSONObject();
		
		json.put("descricao", "Pets");
		json.put("data", "17-08-2022");
		json.put("valor", "200.00");
		json.put("categoria", "Imprevistos");
		
		return json.toString();
	}
	
	private void assertVO(DespesaVO vo) {
		assertNotNull(vo);
		assertNotNull(vo.getDescricao());
		assertNotNull(vo.getData());
		assertNotNull(vo.getValor());
						
		assertEquals("Pets", vo.getDescricao());
		assertEquals(LocalDate.of(2022, 8, 17), vo.getData());
		assertEquals(200.0, vo.getValor());
		assertEquals(Categoria.IMPREVISTOS, vo.getCategoria());
	}
	
	@Test
	@Order(1) //Simulando CONTROLLER
	public void testCreateIsSuccess() throws Exception {
		
		String content = mockMvc.perform(
				MockMvcRequestBuilders
				.post(uri)
				.content(jsonDespesa())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn().getResponse().getContentAsString();
		
		DespesaVO despesaVO = objectMapper.readValue(content, DespesaVO.class);
		
		assertVO(despesaVO);
	}

	@Test
	@Order(2) 
	public void testCreateIsError() throws Exception {		
		//receita duplicada
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.post(uri)
				.content(jsonDespesa())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test
	@Order(3) 
	public void testFindAll() throws Exception {				
		String content = mockMvc.perform(
				MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		List<DespesaVO> despesas = objectMapper.readValue(content, new TypeReference<List<DespesaVO>>(){});
		
		assertFalse(despesas.isEmpty());
		
		assertVO(despesas.get(1));
	}
	
	@Test
	@Order(4) 
	public void testFindAllAndDescricao() throws Exception {				
		String content = mockMvc.perform(
				MockMvcRequestBuilders
				.get(uri)
				.queryParam("descricao", "Pets")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn().getResponse().getContentAsString();
		
		List<DespesaVO> despesas = objectMapper.readValue(content, new TypeReference<List<DespesaVO>>(){});
		
		assertFalse(despesas.isEmpty());
		
		assertVO(despesas.get(0));
	}
	
	@Test
	@Order(5) 
	public void testFindById() throws Exception {	
				
		uri = new URI("/budget-control/despesas/" + id); 
		
		String content = mockMvc.perform(
				MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn().getResponse().getContentAsString();
		
		DespesaVO despesaVO = objectMapper.readValue(content, DespesaVO.class);
		
		assertVO(despesaVO);
	}
	
	@Test
	@Order(6) 
	public void testFindByMonth() throws Exception {	
		
		uri = new URI("/budget-control/despesas/2022/8"); 

		
		String content = mockMvc.perform(
				MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		List<DespesaVO> despesas = objectMapper.readValue(content, new TypeReference<List<DespesaVO>>(){});
		
		assertVO(despesas.get(1));
	}
	
	@Test
	@Order(7)
	public void testUpdateIsSuccess() throws Exception {	
				
		uri = new URI("/budget-control/despesas/" + id); 
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.put(uri)
				.content(jsonDespesa())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@Test
	@Order(8)
	public void testDeleteIsSuccess() throws Exception {	
				
		uri = new URI("/budget-control/despesas/" + id); 
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@Test
	@Order(9)
	public void testDeleteIsError() throws Exception {	
		
		//ID não encontrado
				
		uri = new URI("/budget-control/despesas/" + id); 
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
}
