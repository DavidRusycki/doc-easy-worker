package com.doceasy.worker.services;

import org.springframework.stereotype.Service;

import com.doceasy.worker.dto.FileProcessQueueDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProcessFileService {

	public Boolean processFiles(String payload) {
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			FileProcessQueueDTO dto = mapper.readValue(payload, FileProcessQueueDTO.class);
			
			// buscar os arquivos
			// Criar o array com os FileInputStream dos arquivos
			// realizar a request para junção
			// Receber retorno do arquivo de junção
			// Adicionar esse conteúdo no banco de dados e mudar o status do arquivo.
			
			
			
		} catch (Exception e) {
			
		}
		
		
		return true;
	}
	
}
