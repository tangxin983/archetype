package com.github.tx.archetype.modules.core;

import org.springframework.http.HttpStatus;

/**
 * 用于Restful服务的异常.
 * @author tangx
 *
 */
@SuppressWarnings("serial")
public class RestException extends RuntimeException {

	public HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

	public RestException() {
	}

	public RestException(HttpStatus status) {
		this.status = status;
	}

	public RestException(String message) {
		super(message);
	}

	public RestException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}
}
