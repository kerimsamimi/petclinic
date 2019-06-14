package com.kerimsamimi.petclinic.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerExeption extends RuntimeException {

	public InternalServerExeption(Throwable arg0) {
		super(arg0);
	}
	
}
