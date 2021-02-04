package co.com.aruma.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import co.com.aruma.dto.Response;
import co.com.aruma.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, Integer>, Serializable {	
	
	Mono<User>  findByIdAndAccountStatus(String id, int accountStatus);

	Mono<User> findByEmailAndPasswordAndAccountStatus(String email, String password, int accountStatus);



}
