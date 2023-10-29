package com.doceasy.middler.enums;

public enum DocumentStatusEnum {

	EM_FILA(1),
	FINALIZADO(2),
	ERRO(3);

	private int status;
	
	DocumentStatusEnum(int i) {
		status = i;
	}
	
	public int getValue() {
		return status;
	}
	
}
