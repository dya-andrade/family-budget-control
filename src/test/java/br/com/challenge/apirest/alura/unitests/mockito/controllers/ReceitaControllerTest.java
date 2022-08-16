package br.com.challenge.apirest.alura.unitests.mockito.controllers;

import java.net.URI;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.challenge.apirest.alura.unitests.mockito.asserts.ReceitaAssert;
import br.com.challenge.apirest.alura.unitests.mockito.mocks.ReceitaMock;
import br.com.challenge.apirest.alura.vo.ReceitaVO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) //port 8888
@AutoConfigureMockMvc //habilita e config MockMvc
@TestMethodOrder(OrderAnnotation.class) //modifica o bd mysql, falta TestContainers
public class ReceitaControllerTest {
	
	private ReceitaMock receitaMock;
	
	private ReceitaVO receitaVO;
	
	private ReceitaAssert receitaAssert;
		
	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper;
	
	
	@BeforeEach //executado antes de cada método 
	void setUpMocks() throws Exception {
		receitaAssert = new ReceitaAssert(1);
		receitaMock = new ReceitaMock();
		receitaVO = receitaMock.mockVO(receitaAssert.getNumber());
		
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); 
	}
	
	@Test
	@Order(1) //Simulando REPOSITORY
	public void create() throws Exception {
		URI uri = new URI("/budget-control/receitas");
		
		JSONObject json = new JSONObject();
		json.put("descricao", receitaVO.getDescricao());
		json.put("data", receitaVO.getData());
		json.put("valor", receitaVO.getValor());
		
		String jsonStr = json.toString();
		
		String content = mockMvc.perform(
				MockMvcRequestBuilders
				.post(uri)
				.content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn().getResponse().getContentAsString();
		
		ReceitaVO receitaVO = objectMapper.readValue(content, ReceitaVO.class);
		
		receitaAssert.assertVO(receitaVO);
	}
}
