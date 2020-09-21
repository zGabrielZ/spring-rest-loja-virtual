package com.projeto.spring.lojavirtual.service.exceptions.api;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.projeto.spring.lojavirtual.service.exceptions.EntidadeNaoEncontrado;
import com.projeto.spring.lojavirtual.service.exceptions.Erro;
import com.projeto.spring.lojavirtual.service.exceptions.Erro.Campo;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers,HttpStatus httpStatus,WebRequest request){
		Erro erro = new Erro();
		
		List<Campo> campos = new ArrayList<Erro.Campo>();
		
		for(ObjectError error:ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String msg = error.getDefaultMessage();
			campos.add(new Erro.Campo(nome,msg));
		}
		
		Instant instant = Instant.now();

		
		erro.setStatus(httpStatus.value());
		erro.setTitulo("Um ou mais campos estão inválidos, faça o preenchimento correto e tenta novamente");
		erro.setData(instant.atZone(ZoneId.of("America/Sao_Paulo")));
		erro.setCampos(campos);
		
		return super.handleExceptionInternal(ex, erro, headers, httpStatus, request);
		
	}
	
	@ExceptionHandler(EntidadeNaoEncontrado.class)
	public ResponseEntity<Erro> entidadeNaoEncontrada(EntidadeNaoEncontrado e,
			HttpServletRequest req){
		Instant instant = Instant.now();
		Erro erro = new Erro(HttpStatus.NOT_FOUND.value(),instant.atZone(ZoneId.of("America/Sao_Paulo")),e.getMessage(),null);
		return ResponseEntity.status(erro.getStatus()).body(erro);
	}
}
