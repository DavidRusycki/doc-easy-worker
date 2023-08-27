package com.doceasy.worker.service;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MergeService {

	private Logger log = LoggerFactory.getLogger(ProcessorService.class);

	public void mergeDocuments(List<File> files) {
		log.debug("iniciando request para juncao");

		
		
		log.debug("juncao realizada com sucesso");
	}
	
}
