package com.doceasy.worker.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doceasy.middler.enums.DocumentStatusEnum;
import com.doceasy.worker.dto.FileProcessQueueDTO;
import com.doceasy.worker.entity.Document;
import com.doceasy.worker.entity.SubDocument;
import com.doceasy.worker.repository.DocumentRepository;
import com.doceasy.worker.repository.SubDocumentRepository;

@Service
public class DocumentService {

	private Logger log = LoggerFactory.getLogger(DocumentService.class);
	
	@Autowired
	public SubDocumentRepository repository;
	
	@Autowired
	public DocumentRepository documentRepository;
	
	@Autowired
	private FileService fileService;
	
	public List<File> loadFiles(FileProcessQueueDTO dto) {
		log.debug("iniciando busca do conteudo dos arquivos");
		
		List<UUID> list = dto.getListUuidSubDocuments();
		List<File> listFiles = new ArrayList<File>();
		
		for (UUID uuid : list) {
			
			Optional<SubDocument> optional = repository.findById(uuid);
			
			if (!optional.isEmpty()) {
				SubDocument document = optional.get();
				
				File temp = fileService.createTempFile(document);
				document.clearContent();
				
				if (temp != null) {
					listFiles.add(temp);
				}
			}
			else {
				log.error("nao foi possivel encontrar o subdocumento com uuid: " + uuid);
			}
		}
		
		log.debug("busca finalizada");
		
		return listFiles;
	}
	
	/**
	 * Salva o conteúdo novo no banco de dados.
	 * Altera a situação do documento para processado.
	 * @param file
	 * @param uuid
	 * @return
	 * @throws Exception
	 */
	public Boolean saveDocument(UUID uuid, File file) throws Exception {
		
		FileInputStream fis = new FileInputStream(file);
		Optional<Document> optional = documentRepository.findById(uuid);
		Document document = optional.get();
		document.setContent(fis.readAllBytes());
		fis.close();
		
		documentRepository.save(document);
		
		return true;
	}
	
	/**
	 * Altera o status do documento para processado.
	 * @param uuid
	 * @return
	 */
	public Boolean finish(UUID uuid) {
		Optional<Document> optional = documentRepository.findById(uuid);
		Document document = optional.get();
		document.setStatus(DocumentStatusEnum.FINALIZADO.getValue());
		documentRepository.save(document);
		
		return true;
	}
	
	/**
	 * Deleta os subdocumentos do banco de dados.
	 * @param dto
	 * @return
	 */
	public Boolean deleteAllSubDocumentsFromDataBase(FileProcessQueueDTO dto) {
		List<UUID> list = dto.getListUuidSubDocuments();
		
		for (UUID uuid : list) {
			repository.deleteById(uuid);
		}
		
		return true;
	}
	
	/**
	 * Deleta os arquivos do disco.
	 * @param files
	 * @return
	 */
	public Boolean deleteAllSubDocumentsFromDisk(List<File> files) {
		for (File file : files) {
			file.delete();			
		}
		
		return true;
	}
	
	/**
	 * Retorna se o documento existe no banco
	 * @param uuid
	 * @return
	 */
	public Boolean documentExists(UUID uuid) {
		return documentRepository.existsById(uuid);
	}
	
	/**
	 * Adiciona o arquivo na lista
	 * @param temp
	 * @param listInputStream
	 */
	private void addFileInList(File temp, List<InputStream> listFiles) {
		try {
			FileInputStream fis;
			fis = new FileInputStream(temp);
			listFiles.add(fis);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
}
