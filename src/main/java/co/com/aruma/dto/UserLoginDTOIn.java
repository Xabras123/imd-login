package co.com.aruma.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLoginDTOIn {
	

	@NotBlank(message = "please provide a email")
	private String email;

	@NotBlank(message = "please provide a user password")
	private String password;
	

}
