package com.doceasy.worker.service;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doceasy.worker.dto.FileProcessQueueDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProcessorService {

	private Logger log = LoggerFactory.getLogger(ProcessorService.class);
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private MergeService mergeService;
	
	/**
	 * Realiza o processamento dos arquivos
	 * @param payload
	 * @return Boolean - Indicando o sucesso da operação
	 */
	public Boolean process(String payload) {
		log.debug("iniciando montagem do objeto json");
		
		Boolean result = false;
		
		try {
			
			FileProcessQueueDTO dto = this.mountFromPayload(payload);
			
			// Se apenas tivermos um arquivo não temos o que processar.
			// Inclusive isso não deve acontecer nunca, ou seja isso nem na fila não deveria ter entrado.
			if (dto.getListUuidSubDocuments().size() > 1) {
				
				// Verifica se o documento principal existe no banco de dados.
				if (!documentService.documentExists(dto.getUuidDocument())) {
					throw new Exception("documento uuid: " +dto.getUuidDocument()+ " nao existe no banco de dados");
				}
				
				// buscar os arquivos
				List<File> files = documentService.loadFiles(dto);				
				
				// realizar a request para junção
				mergeService.mergeDocuments(files);
				
				// Receber retorno do arquivo de junção
				log.debug("salvando conteudo da juncao");
				
				// Adicionar esse conteúdo no banco de dados e mudar o status do arquivo.
				// Tem que remover os subdocumentos do banco de dados e do disco
				log.debug("removendo subdocumentos");
			}
			
			// Mudar o status do documento no banco
			
			result = true;
			
		} catch (Exception e) {
			log.error("falha ao processar o registro da fila");
			log.error(e.getMessage());
			result = false;
		}
		
		return result;
	}
	
	private FileProcessQueueDTO mountFromPayload(String payload) {
		ObjectMapper mapper = new ObjectMapper();
		FileProcessQueueDTO dto = mapper.readValue(payload, FileProcessQueueDTO.class);
		log.debug("json do registro "+dto.getUuidDocument().toString()+" montado com sucesso");
		log.debug("documento "+dto.getUuidDocument().toString()+" e composto por " + dto.getListUuidSubDocuments().size() + " sub documentos");
		
		return dto;
	}
	
}
