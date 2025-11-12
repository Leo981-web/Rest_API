package br.edu.atitus.api_example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
	
	@GetMapping(value = {"", "/", "/{namePath}"})
	public ResponseEntity<String> getGreeting(
			@RequestParam(required = false) String name, 
			@PathVariable(required = false ) String namePath) {
		String retorno = "Hello";
		if (name == null) 
			name = namePath != null ? namePath : "World";
		return ResponseEntity.ok(retorno + " " + name + "!");
		
	}
	
	@PostMapping //Diz ao Spring que esse metodo responde requisicoes HTTP POST.
	public ResponseEntity<String> postGreeting(
			@RequestBody String body) throws Exception { //'throw Exception' e necessario para saber que o metodo pode ter excecao.
		
				if (body.length() > 50) {
					throw new Exception("Deve haver no máximo 50 caracteres.");
				}
		
				return ResponseEntity.ok(body); //Codigo 200.
			}
	
	@ExceptionHandler(exception = Exception.class) //Trata mensagem de erro.
	public ResponseEntity<String> handlerException(Exception e) {
		String messageError = e.getMessage().replaceAll("\r\n", "");
		return ResponseEntity.badRequest().body(messageError);
	}
}
