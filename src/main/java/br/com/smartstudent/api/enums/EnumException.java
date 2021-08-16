package br.com.smartstudent.api.enums;

import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

public enum EnumException {

	ITEM_NAO_ENCONTRADO(HttpStatus.NOT_FOUND, "O recurso solicitado nao esta disponivel no servidor");

    private final HttpStatus httpStatus;
	private final String descricao;

	EnumException(HttpStatus httpStatus, String descricao) {
		this.httpStatus = httpStatus;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public static EnumException of(String descricao) {
		return Stream.of(EnumException.values())
				.filter(t -> t.getDescricao().equalsIgnoreCase(descricao))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}
