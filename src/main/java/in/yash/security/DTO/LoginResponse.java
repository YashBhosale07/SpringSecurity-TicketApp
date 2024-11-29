package in.yash.security.DTO;

import lombok.Data;

@Data
public class LoginResponse {

	private String accessToken;
	private String refreshToken;
	
}
