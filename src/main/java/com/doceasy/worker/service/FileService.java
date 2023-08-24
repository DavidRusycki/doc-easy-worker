package com.doceasy.worker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.doceasy.worker.dto.FileProcessQueueDTO;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class FileService {

	private Logger log = LoggerFactory.getLogger(FileService.class);
	
	/**
	 * Realiza o processamento dos arquivos
	 * @param payload
	 * @return Boolean - Indicando o sucesso da operação
	 */
	public Boolean process(String payload) {
		log.debug("iniciando montagem do objeto json");
		
		Boolean result = false;
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			FileProcessQueueDTO dto = mapper.readValue(payload, FileProcessQueueDTO.class);
			log.debug("json do registro "+dto.getUuidDocument().toString()+" montado com sucesso");
			log.debug("documento "+dto.getUuidDocument().toString()+" e composto por " + dto.getListUuidSubDocuments().size());
			
			// buscar os arquivos
			
			log.debug("iniciando busca do conteudo dos arquivos");
			
			// Criar o array com os FileInputStream dos arquivos
			
			log.debug("iniciando criacao de arquivos temporarios");
			
			// realizar a request para junção
			
			log.debug("iniciando request para juncao");
			
			log.debug("juncao realizada com sucesso");
			
			// Receber retorno do arquivo de junção
			
			log.debug("salvando conteudo da juncao");
			
			// Adicionar esse conteúdo no banco de dados e mudar o status do arquivo.
			
			log.debug("removendo subdocumentos");
			
			result = true;
			
		} catch (Exception e) {
			log.error("falha ao processar o registro da fila");
			log.error(e.getMessage());
			result = false;
		}
		
		return result;
	}
	
}
