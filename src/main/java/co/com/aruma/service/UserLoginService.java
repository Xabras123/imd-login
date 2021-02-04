package co.com.aruma.service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import co.com.aruma.dto.Response;
import co.com.aruma.dto.UserLoginDTOIn;
import co.com.aruma.dto.UserLoginDTOOut;
import co.com.aruma.entity.User;
import co.com.aruma.repository.UserRepository;
import co.com.aruma.security.EncryptAndDecrypt;
import co.com.aruma.type.UserStatus;
import co.com.aruma.utils.LogTransaction;
import reactor.core.publisher.Mono;

@Service
public class UserLoginService {
	
	private static final String USER_NOT_FOUND = "User not found";
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EncryptAndDecrypt encryptAndDecrypt;

	public Mono<Response> login(@Valid UserLoginDTOIn userLoginDTOIn) {
		LogTransaction log = new LogTransaction(null);
        log.startTransaction("User Login", userLoginDTOIn);
		return userRepository.findByEmailAndPasswordAndAccountStatus(userLoginDTOIn.getEmail(), encryptAndDecrypt.encrypt(userLoginDTOIn.getPassword()), UserStatus.REGISTER_EMAIL_VERIFIED.getCode()).switchIfEmpty(Mono.error(new Exception(USER_NOT_FOUND)))
		        .flatMap(user -> {
		        	
		        	
		        	return Mono.just(Response
		        			.builder()
		        			.data(mapEntityToDTO(user))
		        			.status(true)
		        			.message("Ok")
		        			.build());

		        	
		        }).onErrorResume(e -> {
					Response res =Response.builder().status(false).message(e.getMessage()).data(null).build();
					log.endTransaction(res);
					return Mono.just(res);
				});	
	}

	private UserLoginDTOOut mapEntityToDTO(User user) {
		
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        Date date = new Date();
        String todate = dateFormat.format(date);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -5);
        Date currentDate = cal.getTime();    
		
		return UserLoginDTOOut
				.builder()
				.id(user.getId().toString())
				.username(user.getUsername())
				.email(user.getEmail())
				.loguinDate(currentDate)
				.build();
	}

	//El pingolin
	public Mono<Response> ping( HttpHeaders hh) {
		
		LogTransaction log = new LogTransaction(hh);
        log.startTransaction("ping user login", null);
    	log.endTransaction(null);
        return Mono.just(Response.builder().status(true).message("Service is working...").data(null).build());
    }

}
