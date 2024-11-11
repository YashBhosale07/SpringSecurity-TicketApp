package in.yash.security.DTO;

import in.yash.security.utils.Role;
import lombok.Data;

@Data
public class SignUpRequest {
	
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private Role role;

}
