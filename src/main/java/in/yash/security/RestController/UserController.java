package in.yash.security.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.yash.security.DTO.LoginRequest;
import in.yash.security.DTO.LoginResponse;
import in.yash.security.DTO.SignUpRequest;
import in.yash.security.DTO.SignUpResponse;
import in.yash.security.service.impl.UserServices;

@RestController
public class UserController {
	
	@Autowired
	private UserServices services;
	
	@PostMapping("/signUp")
	public ResponseEntity<?>signUp(@RequestBody SignUpRequest request){
		SignUpResponse response=services.registerNewUser(request);
		return new ResponseEntity<SignUpResponse>(response,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/login")
	public ResponseEntity<?>login(@RequestBody LoginRequest request){
		LoginResponse response=services.login(request);
		return new ResponseEntity<LoginResponse>(response,HttpStatus.FOUND);
	}

}
