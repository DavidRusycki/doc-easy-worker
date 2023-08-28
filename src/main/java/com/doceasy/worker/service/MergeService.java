package com.doceasy.worker.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class MergeService {

	private Logger log = LoggerFactory.getLogger(ProcessorService.class);
	
	/**
	 * Realiza a junção dos arquivos 
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public File mergeDocuments(List<File> files) throws Exception {
		log.debug("iniciando request para juncao");
		File file = this.makeRequest(files);
		log.debug("juncao realizada com sucesso");
		
		return file;
	}
	
	/**
	 * Realiza o consumo do recurso externo para junção dos arquivos.
	 * @param files
	 * @return
	 * @throws Exception
	 */
	private File makeRequest(List<File> files) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		
		for (File file : files) {
			
			try {
				
				body.add("files", new FileSystemResource(file));
				
			} catch (Exception e) {
				log.error("falha ao adicionar o arquivo na request");
				log.error(e.getMessage());
				e.printStackTrace();
			}
			
		}

		body.add("config", "{}");
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		String serverUrl = "http://localhost:8080/pdf/merge";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<byte[]> response = restTemplate.postForEntity(serverUrl, requestEntity, byte[].class);
		
		if (response.getStatusCode().value() == 200) {
			log.debug("escrevendo retorno da juncao em disco");
			File file = File.createTempFile("merged", ".pdf");
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(response.getBody());
			response = null;
			log.debug("escrita finalizada com sucesso");
			
			return file;
		}
		else {
			throw new Exception("falha na request para juncao de arquivos");
		}
	}
	
}
