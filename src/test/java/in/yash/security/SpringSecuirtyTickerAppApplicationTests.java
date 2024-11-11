package in.yash.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import in.yash.security.DTO.LoginRequest;
import in.yash.security.DTO.LoginResponse;
import in.yash.security.Entity.User;
import in.yash.security.JwtService.JwtService;
import in.yash.security.service.impl.UserServices;
import in.yash.security.utils.Role;

@SpringBootTest
class SpringSecuirtyTickerAppApplicationTests {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserServices services;

	@Test
	void contextLoads() {
//		
//		LoginRequest loginRequest=new LoginRequest();
//		loginRequest.setUserName("yash_2132");
//		loginRequest.setPassword("123");
//		LoginResponse response=services.login(loginRequest);
//		String token=response.getToken();
//		System.out.println(token);
//		System.out.println(jwtService.extractUserName(token));
		System.out.println(jwtService.extractUserName("eyJhbGciOiJIUzM4NCJ9.eyJyb2xlIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJzdWIiOiJ5YXNoXzIxMzIiLCJpYXQiOjE3MzEzMDYyNDYsImV4cCI6MTczMTMwNjg0Nn0.WE48Gs_BXmDDlIO_KBzDekd8lSb4dwqtqgsxgZ3HtgCUnzvhs18VeLO_AO3PqFke"));
	}
	
	
	

}
