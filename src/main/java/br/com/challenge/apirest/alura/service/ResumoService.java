package br.com.challenge.apirest.alura.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.challenge.apirest.alura.configs.FileStorageConfig;
import br.com.challenge.apirest.alura.data.vo.v1.CategoriaVO;
import br.com.challenge.apirest.alura.data.vo.v1.ResumoVO;
import br.com.challenge.apirest.alura.exception.FileGenerateException;
import br.com.challenge.apirest.alura.exception.FileStorageException;
import br.com.challenge.apirest.alura.repository.DespesaRepository;
import br.com.challenge.apirest.alura.repository.ReceitaRepository;

@Service
public class ResumoService {
	
	private Logger logger = Logger.getLogger(ResumoService.class.getName());

	@Autowired
	private DespesaRepository despesaRepository;
	
	@Autowired
	private ReceitaRepository receitaRepository;
	
	private final Path fileStorageLocation;

	@Autowired //inject via construtor
	public ResumoService(FileStorageConfig fileStorageConfig) {
		
		Path path = Paths.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath().normalize(); 
		
		//setar o caminho absoluto, temos apenas o caminha relativo
		//nomalize - nome redundantes são eliminados
		
		this.fileStorageLocation = path;
		
		//se encontrar o dir ele ler, se não ele cria
		try {
			
			Files.createDirectories(this.fileStorageLocation);
			
		} catch (Exception e) {
			throw new FileStorageException(
					"Não foi possível criar o diretório onde os arquivos são armazenados!", e);
		}
	}

	public ResumoVO monthSummary(Integer ano, Integer mes) {
		
		logger.info("Realizando o resumo do mês!");
		
		Double valorTotalReceitas = receitaRepository.findTotalAmountByMonth(ano, mes);		
		Double valorTotalDespesas = despesaRepository.findTotalAmountByMonth(ano, mes);
		
		Double saldoFinal = valorTotalReceitas - valorTotalDespesas;
		
		List<CategoriaVO> valorTotalPorCategoria = despesaRepository.findTotalAmountByCategoria(ano, mes);
		
		ResumoVO resumoMesVO = new ResumoVO();

		resumoMesVO.setTotalReceitas(valorTotalReceitas);
		resumoMesVO.setTotalDespesas(valorTotalDespesas);
		resumoMesVO.setSaldoFinal(saldoFinal);
		resumoMesVO.setTotalPorCategoria(valorTotalPorCategoria);
		
		
		Link link = Link.of(downloadExcelUri(ano, mes));
		
		resumoMesVO.add(link); //HATEAOS
		
		return resumoMesVO;
	}
	
	private String downloadExcelUri(Integer ano, Integer mes) {
		String excelUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/budget-control/resumo/downloadCsv/")
				.path(String.valueOf(ano) + "/")
				.path(String.valueOf(mes))
				.toUriString();
		
		return excelUri;
	}
	
	private String generateExcel(ResumoVO resumoVO) {
		
		logger.info("Gerando o excel do resumo do mês!");
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		XSSFSheet sheet = workbook.createSheet("Resumo do Mês");
		
		int rownum = 0;

		XSSFRow row = sheet.createRow(rownum++);
		int cellnum = 0;
		
		String[] header = { "TOTAL DE RECEITAS", "TOTAL DESPESAS", "SALDO FINAL"};
				
		for (String h : header) {
			Cell cab = row.createCell(cellnum++);
			cab.setCellValue(h);
		}
		
		row = sheet.createRow(rownum++);
		cellnum = 0;
		
		row.createCell(cellnum++).setCellValue(resumoVO.getTotalReceitas());
		row.createCell(cellnum++).setCellValue(resumoVO.getTotalDespesas());
		row.createCell(cellnum++).setCellValue(resumoVO.getSaldoFinal());
		
		row = sheet.createRow(rownum++);
		cellnum = 0;

		Cell cab = row.createCell(cellnum++);
		cab.setCellValue("TOTAL POR CATEGORIA");
		
		List<CategoriaVO> totalPorCategoria = resumoVO.getTotalPorCategoria();
		
		for (CategoriaVO categoriaVO : totalPorCategoria) {
			
			row = sheet.createRow(rownum++);
			cellnum = 0;
			
			row.createCell(cellnum++).setCellValue("Categoria: ");
			row.createCell(cellnum++).setCellValue(categoriaVO.getCategoria().getDescricao());
			
			row.createCell(cellnum++).setCellValue("Total: ");
			row.createCell(cellnum++).setCellValue(categoriaVO.getTotal());
		}
		
		String fileName = StringUtils.cleanPath("resumoDoMes.xlsx"); //normalize o caminho e pega o fileName
		Path targetLocation = this.fileStorageLocation.resolve(fileName); //criando um arquivo vazio no dir
		
		try {
			
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			workbook.write(buffer);
			
			workbook.close();

			byte[] bytes = buffer.toByteArray();
			
			InputStream inputStream = new ByteArrayInputStream(bytes);
			
			Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING); 
			//grava os bytes no dir, se existir um arquivo com mesmo nome ele substitui
			
		} catch (IOException e) {
			throw new FileGenerateException("Erro ao gerar excel!", e);
		}
		
		return fileName;
	}
	
	public Resource loadExcelAsResource(ResumoVO resumoVO) {
		
		String fileName = generateExcel(resumoVO);
		
		logger.info("Gerando o resource do excel do resumo do mês!");
		
		try {
			
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize(); //pega o dir do arquivo

			Resource resource = new UrlResource(filePath.toUri()); //cria um resource do arquivo, bytes e dir
			
			if(resource.exists()) return resource;
			else throw new FileStorageException("Excel não encontrado!");

		} catch (Exception e) {
			throw new FileStorageException("Excel não encontrado!", e);
		}
	}
}
