package com.doceasy.worker.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "tbsubdocumento")
public class SubDocument {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID uuid;
	
	@NotNull
	private UUID UuidDocumento;
	
	private byte[] content;
	
	/**
	 * Limpa a área de conteúdo.
	 * Usado inicialmente para não manter o conteúdo do arquivo em memória e Somente em disco.
	 * @return
	 */
	public Boolean clearContent() {
		this.content = null;
		
		return true;
	}
	
}
