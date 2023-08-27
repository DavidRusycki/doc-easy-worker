package com.doceasy.worker.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.doceasy.worker.dto.DocumentDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "tbdocumento")
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID uuid;
	
	@NotNull
	@NotBlank
	@Size(max = 64)
	private String hash;
	
	@NotNull
	private Integer status;
	
	private byte[] content;
	
	public static DocumentDTO toDTO(Document document) {
		DocumentDTO dto = new DocumentDTO();
		
		dto.setUuid(document.getUuid());
		dto.setHash(document.getHash());
		dto.setStatus(document.getStatus());
		dto.setContent(document.getContent());
		
		return dto;
	}
	
}
