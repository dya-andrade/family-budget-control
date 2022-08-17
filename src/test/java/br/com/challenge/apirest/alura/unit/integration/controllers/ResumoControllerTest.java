package br.com.challenge.apirest.alura.unit.integration.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.nio.charset.StandardCharsets;

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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.challenge.apirest.alura.testcontainers.AbstractIntegrationTest;
import br.com.challenge.apirest.alura.vo.ResumoVO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // port 8888
@TestMethodOrder(OrderAnnotation.class) 
@TestInstance(Lifecycle.PER_CLASS) //  cria apenas uma instância da classe de teste e reutilizá-la entre os testes.
@AutoConfigureMockMvc //habilita e config MockMvc
public class ResumoControllerTest extends AbstractIntegrationTest { // TestContainers
	
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	private static URI uri;


	@BeforeAll // executado antes de cada método
	void setUp() throws Exception {		
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	@Test
	@Order(1) //Simulando CONTROLLER
	public void testMonthSummary() throws Exception {
		
		uri = new URI("/budget-control/resumo/2022/8");
		
		String content = mockMvc.perform(
				MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		ResumoVO resumoVO = objectMapper.readValue(content, ResumoVO.class);
		
		assertNotNull(resumoVO);
		assertNotNull(resumoVO.getSaldoFinal());
		assertNotNull(resumoVO.getTotalPorCategoria());
	}

	@Test
	@Order(2) 
	public void testDownload() throws Exception {		
		
		uri = new URI("/budget-control/resumo/downloadExcel/2022/8");
		
		byte[] content = mockMvc.perform(
				MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.MULTIPART_FORM_DATA))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn().getResponse().getContentAsByteArray();
		
		assertNotNull(content);
	}
	
}
