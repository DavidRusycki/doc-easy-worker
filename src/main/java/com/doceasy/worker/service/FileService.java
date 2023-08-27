package com.doceasy.worker.service;

import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.doceasy.worker.entity.SubDocument;

@Service
public class FileService {

	private Logger log = LoggerFactory.getLogger(FileService.class);
	
	public File createTempFile(SubDocument document) {
		log.debug("criando o arquivo temporario uuid: "+ document.getUuid());
		
		try {
			File tempFile = File.createTempFile("process", ".pdf");
			tempFile.deleteOnExit();

			FileOutputStream fos = new FileOutputStream(tempFile);
			
			log.debug("iniciando escrita do conteudo no arquivo temporario");
			fos.write(document.getContent());
			log.debug("finalizando a escrita no arquivo temporario");
			fos.close();
			
			log.debug("arquivo temporario criado com sucesso");
			
			return tempFile;
			
		} catch (Exception e) {
			log.debug("falha ao criar o arquivo temporario");
			log.error(e.getMessage());
			
			return null;
		}
	}
	
}
