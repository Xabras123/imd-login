package co.com.aruma.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import co.com.aruma.dto.Response;
import co.com.aruma.dto.UserLoginDTOIn;
import co.com.aruma.service.UserLoginService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${application.services.user-login}")
public class UserLoginController {
	
	@Autowired
	UserLoginService userLoginService;
	
	
	//Metodo para determinar si un conjunto de credenciales son validas para el logueo de un usuario particular
	@PostMapping("login")
	public Mono<Response> createUser(@Valid @RequestBody UserLoginDTOIn userLoginDTOIn, 
			ServerWebExchange exchange){
		
		return this.userLoginService.login(userLoginDTOIn);
	}
	

	
    @GetMapping("/ping")
    public Mono<Response> ping( ServerWebExchange exchange) {
        return this.userLoginService.ping( exchange.getRequest().getHeaders());
    }

}
